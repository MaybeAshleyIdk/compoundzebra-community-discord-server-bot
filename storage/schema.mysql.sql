------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
----------------------                                                                            ----------------------
----------------------                            !!!OUTDATED FILE!!!                             ----------------------
----------------------                                                                            ----------------------
----------------------                  We're gonna use SQLite instead of MySQL.                  ----------------------
----------------------                                                                            ----------------------
----------------------                                 RATIONALE:                                 ----------------------
----------------------                       While I am currently admin and                       ----------------------
----------------------           the bot is hosted on a remote machine that I control,            ----------------------
----------------------                    this might not forever be the case.                     ----------------------
----------------------      If and when this happens that I step down as admin and bot host,      ----------------------
----------------------      the databases should be easy to move to another remote machine.       ----------------------
----------------------  With SQLite, the databases are literally just files; super easy to move.  ----------------------
----------------------                   With MySQL, it's not nearly as simple.                   ----------------------
----------------------                                                                            ----------------------
----------------------          Also: MySQL has a user system that we don't need at all,          ----------------------
----------------------          so we don't need to deal with setting up a user and such.         ----------------------
----------------------                                                                            ----------------------
----------------------                                                                            ----------------------
----------------------              TODO: move this rationale out of this file and                ----------------------
----------------------                    into a community file like the readme                   ----------------------
----------------------                                                                            ----------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

-- TODO: Discord seems to cap usernames & display names at 32 characters,
--       but not C's definition of "character" (i.e.: bytes), actually 32 unicode glyphs
--       mysql has some weirdness when it comes to unicode glyphs and their length. needs more research

CREATE TABLE `Guild`(
	`id` UNSIGNED BIGINT NOT NULL,

	PRIMARY KEY(`id`)
);

CREATE TABLE `GuildMember`(
	`guildId` UNSIGNED BIGINT NOT NULL,
	`userId`  UNSIGNED BIGINT NOT NULL,

	`points` UNSIGNED BIGINT NOT NULL DEFAULT 0,

	PRIMARY KEY(`guildId`, `userId`),
	FOREIGN KEY(`guildId`) REFERENCES `Guild`(`id`) ON DELETE CASCADE
);

CREATE TABLE `GuildMessageAuthor`(
	`guildId` UNSIGNED BIGINT NOT NULL,
	`userId`  UNSIGNED BIGINT NOT NULL,

	`index` UNSIGNED INT NOT NULL,

	`userName`          VARCHAR(  ) NOT NULL,
	`userDiscriminator` VARCHAR(8)  NOT NULL,
	`userDisplayName`   VARCHAR(  ) NOT NULL, -- also called the "global name". empty means no display name
	`memberNickname`    VARCHAR(  ) NOT NULL, -- empty means no nickname

	-- mutable:
	`firstSeenTimestamp` DATETIME NOT NULL,
	`lastSeenTimestamp`  DATETIME NOT NULL,

	PRIMARY KEY(`guildId`, `userId`, `index`),
	FOREIGN KEY(`guildId`, `userId`) REFERENCES `GuildMember`(`guildId`, `userId`) ON DELETE CASCADE
);

CREATE TABLE `GuildMessageInfo`(
	`guildId`   UNSIGNED BIGINT NOT NULL,
	`channelId` UNSIGNED BIGINT NOT NULL,
	`id`        UNSIGNED BIGINT NOT NULL,
	`authorId`  UNSIGNED BIGINT NOT NULL,

	-- this column the only mutable one
	`deletionTimestamp` DATETIME NULL DEFAULT NULL,

	PRIMARY KEY(`id`),
	FOREIGN KEY(`guildId`, `authorId`) REFERENCES `GuildMessageAuthor`(`authorId`, `userId`) ON DELETE RESTRICT
);

-- rows of this table represent the history of a guild message.
-- each time the message is updated, the row `index` is incremented by one.
--
CREATE TABLE `MessageHistoryEntry`(
	`messageId` BIGINT       NOT NULL,
	`index`     UNSIGNED INT NOT NULL,
	`timestamp` DATETIME     NOT NULL,
	`content`   VARCHAR(  )  NOT NULL,

	PRIMARY KEY(`messageId`, `index`),
	FOREIGN KEY(`messageId`) REFERENCES `GuildMessageInfo`(`id`) ON DELETE CASCADE
);
