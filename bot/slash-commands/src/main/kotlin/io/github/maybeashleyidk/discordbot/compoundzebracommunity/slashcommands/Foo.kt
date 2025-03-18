package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.Handled
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult.NotHandled
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

public fun SlashCommandsRegistry.register(
	commandData: SlashCommandData,
	eventHandler: suspend (SlashCommandInteractionEvent) -> Unit,
) {
	this.register(commandData, Foo(eventHandler))
}

private class Foo(
	private val eventHandler: suspend (SlashCommandInteractionEvent) -> Unit,
) : SlashCommandEventHandler {

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		return NotHandled
	}

	override suspend fun handleEvent(commandId: Long, event: GenericEvent): EventHandlingResult {
		if (event !is SlashCommandInteractionEvent) {
			return NotHandled
		}

		if (event.idLong != commandId) {
			return NotHandled
		}

		this.eventHandler(event)

		return Handled
	}
}
