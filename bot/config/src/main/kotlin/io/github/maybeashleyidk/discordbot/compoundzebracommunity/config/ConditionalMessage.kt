package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

public data class ConditionalMessage(
	val condition: Condition,
	val messageContent: String,
) {

	public data class Condition(
		val regex: Regex,
	)
}
