package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

public class BufferedSlashCommandsRegistry(
	private val foo: (Set<SlashCommandRegistration>) -> Unit
) : SlashCommandsRegistry {

	public data class SlashCommandRegistration(
		public val commandData: SlashCommandData,
		public val eventHandler: SlashCommandEventHandler,
	)

	private val buffer: MutableSet<SlashCommandRegistration> = mutableSetOf()

	override fun register(commandData: SlashCommandData, eventHandler: SlashCommandEventHandler) {
		this.buffer += SlashCommandRegistration(commandData, eventHandler)
	}

	public fun registerBuffered() {
		this.foo(this.buffer.toSet())
	}
}
