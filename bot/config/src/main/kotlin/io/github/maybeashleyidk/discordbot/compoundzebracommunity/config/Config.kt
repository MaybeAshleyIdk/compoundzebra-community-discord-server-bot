package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandPrefix
import javax.annotation.CheckReturnValue

public data class Config(
	val commandPrefix: CommandPrefix,
	val strings: LanguageStrings,
	val echoCommandDefinitions: Set<EchoCommandDefinition>,
	val botAdminUserIds: Set<String>,
)

public data class LanguageStrings(
	val generic: Generic,
	val poll: Poll,
	val command: Command,
) {

	public data class Generic(
		private val invalidCommandNameFormat: String,
		private val unknownCommandFormat: String,
	) {

		@CheckReturnValue
		public fun invalidCommandName(commandNameStr: String): String {
			return this.invalidCommandNameFormat.format(commandNameStr)
		}

		@CheckReturnValue
		public fun unknownCommand(commandName: CommandName): String {
			return this.unknownCommandFormat.format(commandName.string)
		}
	}

	public data class Poll(
		private val titleFormat: String,
		private val optionFormat: String,
		private val closedMessageFormat: String,
		public val action: Action,
	) {

		public data class Action(
			public val close: String,
		)

		@CheckReturnValue
		public fun title(authorId: String): String {
			return this.titleFormat.format("<@$authorId>")
		}

		// TODO: if we want to support guilds of different languages, then we need the language here to use
		//       java.text.NumberFormat.
		//       or we do the formatting before and pass it in
		@CheckReturnValue
		public fun option(optionLabel: String, votes: Int): String {
			return this.optionFormat.format(optionLabel, votes.toString())
		}

		@CheckReturnValue
		public fun closedMessage(userId: String): String {
			return this.closedMessageFormat.format("<@$userId>")
		}
	}

	public data class Command(
		val poll: Poll,
		val queryPoll: QueryPoll,
		val getConfig: GetConfig,
		val shutdown: Shutdown,
	) {

		public data class Poll(
			val missingDescription: String,
			val lessThan2Options: String,
		)

		public data class QueryPoll(
			val insufficientPermissions: String,
			val missingId: String,
			val noSuchPollWithId: String,
		)

		public data class GetConfig(
			val insufficientPermissions: String,
		)

		public data class Shutdown(
			val response: String,
			val insufficientPermissions: String,
		)
	}
}

public data class EchoCommandDefinition(
	val commandName: CommandName,
	val responseMessage: String,
)
