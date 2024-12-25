package io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.JDA as Jda

public class JdaFactory(
	private val botToken: BotToken,
	private val initialActivity: Activity,
	private val mainEventListener: MainEventListener,
) {

	public fun create(): Jda {
		return JDABuilder.createDefault(this.botToken.toRawString())
			.setActivity(this.initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.addEventListeners(this.mainEventListener)
			.build()
	}
}
