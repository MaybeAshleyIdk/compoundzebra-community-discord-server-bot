package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandPrefix

public data class Config(
	val strings: LanguageStrings,
	val botAdminUserIds: Set<String>,
	val commandPrefix: CommandPrefix,
	val commandDefinitions: Set<CommandDefinition>,
	val conditionalMessages: Set<ConditionalMessage>,
)

public data class LanguageStrings(
	val generic: Generic,
	val poll: Poll,
	val command: Command,
) {

	public data class Generic(
		val invalidCommandNameFormat: String,
		val unknownCommandFormat: String,
	) {

		public fun invalidCommandName(commandNameStr: String): String {
			return this.invalidCommandNameFormat.format(commandNameStr)
		}

		public fun unknownCommand(commandName: CommandName): String {
			return this.unknownCommandFormat.format(commandName.toString())
		}
	}

	public data class Poll(
		val titleFormat: String,
		val optionFormat: String,
		val closedMessageFormat: String,
		val action: Action,
	) {

		public data class Action(
			public val close: String,
		)

		public fun title(authorId: String): String {
			return this.titleFormat.format("<@$authorId>")
		}

		// TODO: if we want to support guilds of different languages, then we need the language here to use
		//       java.text.NumberFormat.
		//       or we do the formatting before and pass it in
		public fun option(optionLabel: String, votes: Int): String {
			return this.optionFormat.format(optionLabel, votes.toString())
		}

		public fun closedMessage(userId: String): String {
			return this.closedMessageFormat.format("<@$userId>")
		}
	}

	public data class Command(
		val coinFlip: CoinFlip,
		val poll: Poll,
		val queryPoll: QueryPoll,
		val magic8Ball: Magic8Ball,
		val emojiStats: EmojiStats,
		val getConfig: GetConfig,
		val rng: Rng,
		val shutdown: Shutdown,
	) {

		public data class CoinFlip(
			val heads: String,
			val tails: String,
		)

		public data class Poll(
			val missingDescription: String,
			val lessThan2Options: String,
		)

		public data class QueryPoll(
			val insufficientPermissions: String,
			val missingId: String,
			val noSuchPollWithId: String,
		)

		public data class Magic8Ball(
			val missingQuestion: String,
			val responses: List<String>,
		)

		public data class EmojiStats(
			val loading: String,
			val errorOccurred: String,
			val empty: String,
			val headingFormat: String,
			val statLineFormat: String,
		) {

			public fun heading(userMention: String): String {
				return this.headingFormat.format(userMention)
			}

			public fun statLine(emojiFormatted: String, countFormatted: String): String {
				return this.statLineFormat.format(emojiFormatted, countFormatted)
			}
		}

		public data class GetConfig(
			val insufficientPermissions: String,
		)

		public data class Rng(
			val missingMinAndMaxArguments: String,
			val missingMaxArgument: String,
			val excessArguments: String,
			val minStringTooLong: String,
			val minInvalidNumber: String,
			val minTooSmall: String,
			val minTooBig: String,
			val minIsDecimal: String,
			val maxStringTooLong: String,
			val maxInvalidNumber: String,
			val maxTooSmall: String,
			val maxTooBig: String,
			val maxIsDecimal: String,
			val minAndMaxAreEqual: String,
			val minGreaterThanMax: String,
			val responseFormat: String,
		) {

			public fun response(numberFormatted: String): String {
				return this.responseFormat.format(numberFormatted)
			}
		}

		public data class Shutdown(
			val response: String,
			val insufficientPermissions: String,
		)
	}
}

public data class CommandDefinition(
	val commandName: CommandName,
	val action: Action,
)

public data class ConditionalMessage(
	val condition: Condition,
	val messageContent: String,
) {
	public data class Condition(
		val regex: Regex,
	)
}

public sealed class Action {
	public data class Response(val message: String) : Action()
}
