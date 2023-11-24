package io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotTokenString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
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
			.build()
	}
}
