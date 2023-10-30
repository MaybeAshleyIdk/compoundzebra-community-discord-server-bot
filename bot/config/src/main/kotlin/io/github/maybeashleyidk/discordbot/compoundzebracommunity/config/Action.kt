package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

public sealed class Action {
	public data class Response(val message: String) : Action()
}
