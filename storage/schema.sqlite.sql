PRAGMA user_version = 1; -- database version

CREATE TABLE `Guild`(
	`id` INTEGER NOT NULL,

	PRIMARY KEY(`id`)
) STRICT;

CREATE TABLE `GuildMember`(
	`guildId` INTEGER NOT NULL,
	`userId`  INTEGER NOT NULL,

	-- mutable:
	`points` INTEGER NOT NULL DEFAULT 0,

	PRIMARY KEY(`guildId`, `userId`),
	FOREIGN KEY(`guildId`) REFERENCES `Guild`(`id`) ON DELETE CASCADE,

	CONSTRAINT `GuildMember_points_isNotNegative` CHECK(`points` >= 0)
) STRICT;

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
