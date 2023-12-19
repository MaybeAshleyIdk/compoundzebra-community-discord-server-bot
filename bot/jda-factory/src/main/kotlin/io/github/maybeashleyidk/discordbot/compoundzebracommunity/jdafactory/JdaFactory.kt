package io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotTokenString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutinesjda.await
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.inject.Inject
import net.dv8tion.jda.api.JDA as Jda

public class JdaFactory @Inject constructor(
	@BotTokenString private val botToken: String,
	private val initialActivity: Activity,
	private val mainEventListener: MainEventListener,
) {

	public fun create(): Jda {
		return JDABuilder.createDefault(this.botToken)
			.setActivity(this.initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.addEventListeners(this.mainEventListener)
			.addEventListeners(
				object : ListenerAdapter() {
					override fun onReady(event: ReadyEvent) {
						val c = Commands.slash("poll", "Opens a new poll")
						for (guild: Guild in event.jda.guilds) {
							val x = runBlocking {
								guild.updateCommands()
									.addCommands(c)
									.await()
							}
						}
					}
				},
			)
			.build()
	}
}
