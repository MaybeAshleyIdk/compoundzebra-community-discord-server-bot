PRAGMA user_version = 1; -- database schema version code

CREATE TABLE `GlobalConfig`(

);

CREATE TABLE `User`(
	`id` INTEGER NOT NULL,

	`timezone`   TEXT NULL DEFAULT NULL,
	`timeFormat` TEXT NULL DEFAULT NULL,

	PRIMARY KEY(`id`),

	CONSTRAINT `User_timezone_isValid` CHECK(
		-- tz database time zone in the format of "<area>/<city>"
		-- <https://en.wikipedia.org/wiki/List_of_tz_database_time_zones>
		(`timezone` GLOB 'tz:?*/?*')
		OR
		-- time zone abbreviation
		-- <https://en.wikipedia.org/wiki/List_of_time_zone_abbreviations>
		(`timezone` GLOB 'abbr:?*')
	),
	CONSTRAINT `User_timeFormat_isValid` CHECK(
		(`timeFormat` IS NULL)
		OR
		(`timeFormat` IN ('12h-omit_zero_minutes-lowercase', '24h'))
	)
);

CREATE TABLE `Guild`(
	`id` INTEGER NOT NULL,

	`languageStrings` TEXT NOT NULL,
	`commandPrefix`   TEXT NOT NULL,

	`twitchIntegrationId` INTEGER NULL DEFAULT NULL,

	PRIMARY KEY(`id`),
	FOREIGN KEY(`twitchIntegrationId`) REFERENCES `GuildTwitchIntegration`(`id`),

	CONSTRAINT `Guild_commandPrefix_isNotEmpty` CHECK(`commandPrefix` != ''),
) STRICT;

CREATE TABLE `GuildTwitchIntegration`(
	`id` INTEGER NOT NULL,

	`twitchChannelName`    TEXT    NOT NULL,
	`guildTargetChannelId` INTEGER NOT NULL,

	PRIMARY KEY(`id`),

	CONSTRAINT `GuildTwitchIntegration_twitchChannelName_isNotEmpty` CHECK(`twitchChannelName` != '')
	CONSTRAINT `GuildTwitchIntegration_unique` UNIQUE(`twitchChannelName`, `guildTargetChannelId`)
) STRICT;

-- not using STRICT for this table because the column `points` uses the type affinity NUMERIC,
-- which isn't a valid storage class for STRICT tables (it would need to be either INTEGER or REAL)
CREATE TABLE `GuildMember`(
	`guildId` INTEGER NOT NULL,
	`userId`  INTEGER NOT NULL,

	-- points can be either a non-negative integer or positive infinity
	`points` NUMERIC NOT NULL DEFAULT 0,

	PRIMARY KEY(`guildId`, `userId`),
	FOREIGN KEY(`guildId`) REFERENCES `Guild`(`id`) ON DELETE CASCADE,

	-- since this table doesn't use STRICT, the datatype constraints need to be added manually
	CONSTRAINT `GuildMember_guildId_isInteger` CHECK(typeof(`guildId`) = 'integer'),
	CONSTRAINT `GuildMember_userId_isInteger` CHECK(typeof(`userId`) = 'integer'),
	CONSTRAINT `GuildMember_points_isPositiveInfinityOrNonNegativeInteger` CHECK(
		((typeof(`points`) = 'real') AND (`points` = 1e309)) -- 1e309 = positive infinity
		OR
		((typeof(`points`) = 'integer') AND (`points` >= 0))
	)
);

CREATE TABLE `GuildMemberSubscribedKeyword`(
	`guildId` INTEGER NOT NULL,
	`userId`  INTEGER NOT NULL,
	`index`   INTEGER NOT NULL,

	`word` TEXT NOT NULL,

	`enabled` INTEGER NOT NULL DEFAULT 1,

	PRIMARY KEY(`guildId`, `userId`, `index`),
	FOREIGN KEY(`guildId`, `userId`) REFERENCES `GuildMember`(`guildId`, `userId`) ON DELETE CASCADE

	CONSTRAINT `GuildMemberSubscribedKeyword_index_isNotNegative` CHECK(`index` >= 0),
	CONSTRAINT `GuildMemberSubscribedKeyword_word_isNotEmpty` CHECK(`word` != ''),
	CONSTRAINT `GuildMemberSubscribedKeyword_enabled_isZeroOrOne` CHECK((`enabled` = 0) OR (`enabled` = 1))
) STRICT;

CREATE TABLE `GuildSimpleResponseCommand`(
	`id` INTEGER NOT NULL,

	`name`              TEXT    NOT NULL,
	`responseMessageId` INTEGER NOT NULL,

	PRIMARY KEY(`id`),
	FOREIGN KEY(`responseMessageId`) REFERENCES `PreparedMessage`(`id`),

	CONSTRAINT `GuildSimpleResponseCommand_name_isNotEmpty` CHECK(`name` != '')
) STRICT;

CREATE TABLE `GuildUserGroup`(
	`id` INTEGER NOT NULL,

	`guildId` INTEGER NOT NULL,

	`creationTimestamp` TEXT NOT NULL, -- RFC 3999

	`name`  TEXT NOT NULL,
	`rules` TEXT NOT NULL,

	PRIMARY KEY(`id`),
	FOREIGN KEY(`guildId`) REFERENCES `Guild`(`id`) ON DELETE CASCADE,

	CONSTRAINT `GuildUserGroup_name_isNotEmpty` CHECK(`name` != ''),
	CONSTRAINT `GuildUserGroup_creationTimestamp_isInCorrectFormat` CHECK(`creationTimestamp` = strftime('%Y-%m-%d %H:%M:%f', `creationTimestamp`))
) STRICT;

CREATE TABLE `GuildInterjections`(
	`id` INTEGER NOT NULL,

	`pattern`,
	`response`,

	PRIMARY KEY(`id`)
) STRICT;

CREATE TABLE `PreparedMessage`(
	`id` INTEGER NOT NULL,

	`content` TEXT NOT NULL,

	PRIMARY KEY(`id`)
);

CREATE TABLE `File`(
	`id` INTEGER NOT NULL,

	`filename` TEXT NOT NULL,
	`content`  BLOB NOT NULL,

	PRIMARY KEY(`id`)
);

-- #region message history backup

CREATE TABLE `GuildMessageAuthor`(
	`guildId` INTEGER NOT NULL,
	`userId`  INTEGER NOT NULL,

	`index` INTEGER NOT NULL,

	`userName`          TEXT NOT NULL,
	`userDiscriminator` TEXT NOT NULL, -- empty string means to discriminator
	`userDisplayName`   TEXT NOT NULL, -- also called the "global name". empty string means no display name
	`memberNickname`    TEXT NOT NULL, -- empty string means no nickname

	-- mutable:
	`firstSeenTimestamp` TEXT NOT NULL, -- datetime
	`lastSeenTimestamp`  TEXT NOT NULL, -- datetime

	PRIMARY KEY(`guildId`, `userId`, `index`),
	FOREIGN KEY(`guildId`, `userId`) REFERENCES `GuildMember`(`guildId`, `userId`) ON DELETE CASCADE,

	CONSTRAINT `GuildMessageAuthor_index_isNotNegative` CHECK(`index` >= 0),
	CONSTRAINT `GuildMessageAuthor_userName_isNotEmpty` CHECK(`userName` != ''),
	CONSTRAINT `GuildMessageAuthor_firstSeenTimestamp_isInCorrectFormat` CHECK(`firstSeenTimestamp` = strftime('%Y-%m-%d %H:%M:%f', `firstSeenTimestamp`)),
	CONSTRAINT `GuildMessageAuthor_lastSeenTimestamp_isInCorrectFormat` CHECK(`lastSeenTimestamp` = strftime('%Y-%m-%d %H:%M:%f', `lastSeenTimestamp`))
) STRICT;

CREATE TABLE `GuildMessageInfo`(
	`guildId`   INTEGER NOT NULL,
	`channelId` INTEGER NOT NULL,
	`id`        INTEGER NOT NULL,
	`authorId`  INTEGER NOT NULL,

	-- mutable:
	`deletionTimestamp` TEXT NULL DEFAULT NULL, -- RFC 3999

	PRIMARY KEY(`id`),
	FOREIGN KEY(`guildId`, `authorId`) REFERENCES `GuildMessageAuthor`(`authorId`, `userId`) ON DELETE RESTRICT,

	CONSTRAINT `GuildMessageInfo_deletionTimestamp_isInCorrectFormat` CHECK(`deletionTimestamp` = strftime('%Y-%m-%d %H:%M:%f', `deletionTimestamp`))
) STRICT;

-- rows of this table represent the history of a guild message.
-- each time the message is updated, the row `index` is incremented by one.
-- the initial message has an index of 0
CREATE TABLE `MessageHistoryEntry`(
	`messageId` INTEGER NOT NULL,
	`index`     INTEGER NOT NULL,
	`timestamp` TEXT    NOT NULL, -- RFC 3999
	`content`   TEXT    NOT NULL,

	PRIMARY KEY(`messageId`, `index`),
	FOREIGN KEY(`messageId`) REFERENCES `GuildMessageInfo`(`id`) ON DELETE CASCADE,

	CONSTRAINT `MessageHistoryEntry_index_isNotNegative` CHECK(`index` >= 0),
	CONSTRAINT `MessageHistoryEntry_timestamp_isInCorrectFormat` CHECK(`timestamp` = strftime('%Y-%m-%d %H:%M:%f', `timestamp`)),
	CONSTRAINT `MessageHistoryEntry_content_isNotEmpty` CHECK(`content` != '')
) STRICT;

-- #endregion
