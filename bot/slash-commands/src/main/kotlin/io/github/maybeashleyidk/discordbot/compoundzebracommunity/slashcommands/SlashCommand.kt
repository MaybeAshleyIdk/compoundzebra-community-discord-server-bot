package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

public interface SlashCommandsRegistry {

	public fun register(commandData: SlashCommandData, eventHandler: SlashCommandEventHandler)
}

public interface SlashCommandEventHandler {

	/**
	 * Called for incoming events before the slash command is registered.
	 */
	public suspend fun handleEvent(event: GenericEvent): EventHandlingResult

	public suspend fun handleEvent(commandId: Long, event: GenericEvent): EventHandlingResult
}
