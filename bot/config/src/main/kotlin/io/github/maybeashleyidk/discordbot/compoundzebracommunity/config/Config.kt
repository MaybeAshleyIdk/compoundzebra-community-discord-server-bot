package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import javax.annotation.CheckReturnValue

public data class Config(
	val commandPrefix: String,
	val strings: LanguageStrings,
	val echoCommandDefinitions: Set<EchoCommandDefinition>,
	val botAdminUserIds: Set<String>,
)

public data class LanguageStrings(
	val generic: Generic,
	val command: Command,
) {

	public data class Generic(
		val invalidCommandNameFormat: String,
		val unknownCommandFormat: String,
	) {

		@CheckReturnValue
		public fun invalidCommandName(commandNameStr: String): String {
			return this.invalidCommandNameFormat.format(commandNameStr)
		}

		@CheckReturnValue
		public fun unknownCommand(commandNameStr: String): String {
			return this.unknownCommandFormat.format(commandNameStr)
		}
	}

	public data class Command(
		val getConfig: GetConfig,
		val shutdown: Shutdown,
	) {

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
	val commandNameStr: String,
	val responseMessage: String,
)
