package io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotTokenString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening.PollEventListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.inject.Inject
import net.dv8tion.jda.api.JDA as Jda

public class JdaFactory @Inject constructor(
	@BotTokenString private val botToken: String,
	private val initialActivity: Activity,
	private val messageEventHandlerMediator: MessageEventHandlerMediator,
	private val pollEventListener: PollEventListener,
) {

	public fun create(): Jda {
		return JDABuilder.createDefault(this.botToken)
			.setActivity(this.initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.addEventListeners(
				this.messageEventHandlerMediator,
				this.pollEventListener,
			)
			.build()
	}
}
