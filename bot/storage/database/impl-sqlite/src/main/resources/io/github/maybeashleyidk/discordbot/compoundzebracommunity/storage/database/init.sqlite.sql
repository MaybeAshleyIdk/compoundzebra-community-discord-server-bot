-- documentation comments (similar to JavaDoc or KDoc) are done like this:
--
--         ----
--          -- [documentation text here]
--          -- [documentation text here]
--          -- [documentation text here]
--         ----

PRAGMA user_version = 1; -- database schema version code

CREATE TABLE `Guild`(
	`id` INTEGER NOT NULL,

	-- TODO: (maybe) columns for the initial self-timeout duration (`initialMemberSelfTimeoutDuration` TEXT NOT NULL)
	--       and the self-timeout duration growth rate (`memberSelfTimeoutGrowthRate` REAL NOT NULL)

	PRIMARY KEY(`id`)
) STRICT;

----
 -- This table doesn't use STRICT because the member guild points are stored as either an (non-negative) integer or
 -- a floating-point number (positive infinity).
 -- That means that the storage class for the column `points` is either INTEGER or REAL.
 -- Since SQLite doesn't have union/exclusive disjunction storage classes (which is completely understandable why this
 -- doesn't exist), this column isn't possible to be defined in a strict table.
 -- The result of all this is that the datatype constraints must be defined manually.
----
CREATE TABLE `GuildMember`(
	`guildId` INTEGER NOT NULL,
	`userId`  INTEGER NOT NULL,

	----
	 -- Points can be either a non-negative integer or positive infinity.
	 -- Infinite points are intended for admins and similar very high-ranking members.
	----
	`points` NUMERIC NOT NULL DEFAULT 0,

	----
	 -- Stored as an ISO 8601 duration.
	 --
	 -- The default value is an empty string and means that this member has not yet executed a self-timeout.
	 -- The initial timeout duration is defined programmatically.
	 --
	 -- SQLite isn't sophisticated enough to define a constraint that checks for fully-correct ISO 8601 syntax, so it
	 -- needs to be done programmatically.
	----
	`currentSelfTimeoutDuration` TEXT NOT NULL DEFAULT '',

	PRIMARY KEY(`guildId`, `userId`),
	FOREIGN KEY(`guildId`) REFERENCES `Guild`(`id`) ON DELETE CASCADE,

	-- Since this table doesn't use STRICT (see the documentation comment as to why), the datatype constraints must be
	-- defined manually.
	CONSTRAINT `GuildMember_guildId_isInteger` CHECK(typeof(`guildId`) = 'integer'),
	CONSTRAINT `GuildMember_userId_isInteger` CHECK(typeof(`userId`) = 'integer'),
	CONSTRAINT `GuildMember_points_isPositiveInfinityOrNonNegativeInteger` CHECK(
		((typeof(`points`) = 'integer') AND (`points` >= 0))
		OR
		-- SQLite has no special syntax for positive infinity, so instead the value 1e309 (or above) must be used,
		-- as that value is greater than what IEEE 754 double-precision floating-point numbers are capable of storing
		-- and it gets silently converted to positive infinity.
		((typeof(`points`) = 'real') AND (`points` = 1e309))
	),
	CONSTRAINT `GuildMember_currentSelfTimeoutDuration_isLikeIso8601Duration` CHECK(
		(typeof(`currentSelfTimeoutDuration`) = 'text')
		AND
		(`currentSelfTimeoutDuration` GLOB 'PT?*')
	)
);
