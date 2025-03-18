package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler.GenericEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands.builtins.CoinFlipSlashCommand
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import kotlinx.coroutines.flow.MutableStateFlow
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.JDA as Jda

@JvmInline
internal value class CommandId(val idLong: Long)

public class EventHandler : GenericEventHandler {

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		TODO("Not yet implemented")
	}
}

public class Manager {

	internal val x: MutableStateFlow<List<Pair<CommandId, SlashCommandEventHandler>>?> = MutableStateFlow(null)
}

public suspend fun jda(configSupplier: ConfigSupplier, jda: Jda, manager: Manager) {
	jda.deleteAllCommands()

	val l: List<Pair<ImmutableSlashCommandData, SlashCommandEventHandler>> = create(configSupplier)

	val commands: List<Command> = jda.updateCommands()
		.addCommands(l.map { it.first.mutable() })
		.await()

	check(commands.size == l.size)

	val y: List<Pair<CommandId, SlashCommandEventHandler>> = commands.indices
		.map { i ->
			CommandId(commands[i].idLong) to l[i].second
		}

	manager.x.value = y
}

private fun create(configSupplier: ConfigSupplier): List<Pair<ImmutableSlashCommandData, SlashCommandEventHandler>> {
	return listOf(
		CoinFlipSlashCommand.DATA to CoinFlipSlashCommand.createEventHandler(configSupplier),
	)
}

private suspend fun Jda.deleteAllCommands() {
	val commands: List<Command> = this.retrieveCommands().await()

	if (commands.isEmpty()) {
		return
	}

	commands
		.map { command: Command ->
			this.deleteCommandById(command.idLong)
		}
		.let { RestAction.allOf(it) }
		.await()
}
