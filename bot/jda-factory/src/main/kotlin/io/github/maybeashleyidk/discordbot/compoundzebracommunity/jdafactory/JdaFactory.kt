package io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.BotTokenString
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediator.MessageEventHandlerMediator
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.inject.Inject
import net.dv8tion.jda.api.JDA as Jda

public class JdaFactory @Inject internal constructor(
	@BotTokenString private val botToken: String,
	private val initialActivity: Activity,
	private val messageEventHandlerMediator: MessageEventHandlerMediator,
	private val otherEventListeners: Set<@JvmSuppressWildcards EventListener>,
) {

	public fun create(): Jda {
		return JDABuilder.createDefault(this.botToken)
			.setActivity(this.initialActivity)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT)
			.addEventListeners(
				this.messageEventHandlerMediator,
				*(this.otherEventListeners.toTypedArray()),
			)
			.build()
	}
}
