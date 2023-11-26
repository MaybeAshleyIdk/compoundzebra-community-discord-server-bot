////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////                                                          ///////////////////////////////
///////////////////////////////                   !!! N O T I C E !!!                    ///////////////////////////////
///////////////////////////////                                                          ///////////////////////////////
///////////////////////////////             THIS FILE IS **VERY** OUTDATED!              ///////////////////////////////
///////////////////////////////  THE CONFIG FILE WILL BE REPLACED BY A SQLITE DATABASE!  ///////////////////////////////
///////////////////////////////                                                          ///////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * This scheme is how the final version of the bot config will probably look like.
 * Ironically, this isn't final itself.
 */

type ChannelSnowflakeId = string;


type UserSnowflakeId = string;
type RoleSnowflakeId = string;


type UserGroupName = string;

type UserGroupEntrySpec =
	`${"include" | "exclude"}Owner` |
	`${"include" | "exclude"}User:${UserSnowflakeId}` |
	`${"include" | "exclude"}Role:${RoleSnowflakeId | "@everyone"}` |
	`${"include" | "exclude"}UserGroup:${UserGroupName}`;


/**
 * Must be non-empty and must only consist of the ASCII characters `!`-`.`, `:`-`;`, `=`-`?`, `[`, `]`, `^` and `{`-`~`.
 */
type CommandPrefix = string;


/**
 * Must be non-empty and must only consist of ASCII lowercase letters and digits.
 */
type CommandName = string;


type ExecuteCommandNameSpec = `custom:${CommandName}` | `builtin:${CommandName}`;

type GenericAction = // TODO: global variables and/or counters
	({
		"commandName": ExecuteCommandNameSpec,
	} | {
		"channelId": ChannelSnowflakeId,
		"message": string,
	}) & {
		"andThen"?: GenericAction,
	};

type MessageTriggeredAction = // TODO: global variables and/or counters
	({
		"response": string,
	} | {
		"commandName": ExecuteCommandNameSpec,
	} | {
		"channelId": ChannelSnowflakeId,
		"message": string,
	} | {
		"if": MessageCondition,
		"then": MessageTriggeredAction,
		"else": MessageTriggeredAction,
	}) & {
		"andThen"?: MessageTriggeredAction,
	};


type CommandDetails = {
	/**
	 * The default value for custom commands is whatever is configured as the `defaultCommandPrefix`.
	 */
	"prefix"?: CommandPrefix,

	/**
	 * The list of user groups that are permitted to execute this command.
	 */
	"permittedUserGroups"?: UserGroupName[],

	/**
	 * Hidden command appear to be non-existing for non-permitted users.
	 *
	 * The default value for custom commands is `false`.
	 */
	"hidden"?: boolean,

	/**
	 * Sensitive commands can only be executed in channels that are also marked as sensitive.
	 *
	 * The default value for custom commands is `false`.
	 */
	"sensitive"?: boolean,

	"args"?: "one" | "multiple",

	"action": MessageTriggeredAction,

	/**
	 * Allows the command to be disabled completely.
	 * A disabled command act just like it doesn't exist.
	 *
	 * The default value is `true`.
	 */
	"enabled"?: boolean,
};

/**
 * For built-in commands, either the notation `@override:` or `@adjust:` must be used, otherwise it is ignored.
 * If either of these notation is used for a non-built-in command, it is also ignored.
 *
 * * The `@override:` notation completely replaces the built-in command.
 *
 * * The `@adjust:` notation changes the built-in command.
 *   This can be used to restrict built-in commands more than usual.
 *
 * There are a few commands that cannot be overridden, even with the `@override:` notation.
 * Most notable the `shutdown` command.
 */
type CommandNameSpec = CommandName | `@override:${CommandName}` | `@adjust:${CommandName}`;


type MessageCondition =
	{
		"any": MessageCondition[],
	} | {
		"all": MessageCondition[],
	} | {
		"not": MessageCondition,
	} | {
		/**
		 * Decimal number where 0 = 0% and 1 = 100%
		 */
		"randomChance": number,
	} | {
		"matchesRegexPattern": string,
	} | {
		"isAuthor": UserSnowflakeId,
	} | {
		"isAuthorInUserGroup": UserGroupName,
	};

type ConditionalMessageAction = {
	"condition": MessageCondition,
	"action": MessageTriggeredAction,
};


type BotConfig = {
	"strings"?: {
		[key: string]: string,
	},

	/**
	 * A list of channel IDs that are considered "sensitive".
	 * The bot will refuse to execute commands marked as sensitive in non-sensitive channels.
	 */
	"sensitiveChannels"?: ChannelSnowflakeId[],

	/**
	 * "User groups" are the way this bot handles permissions for either commands or the conditional message actions.
	 */
	"userGroups"?: {
		[groupName: UserGroupName]: UserGroupEntrySpec[],
	},

	"actions"?: {
		[actionName: string]: GenericAction,
	},

	/**
	 * The default value is `"!"`.
	 */
	"defaultCommandPrefix"?: CommandPrefix,
	"commands"?: {
		[commandNameSpec: CommandNameSpec]: CommandDetails,
	},

	"conditionalMessageActions"?: ConditionalMessageAction[],

	"timedAction"?,
};
