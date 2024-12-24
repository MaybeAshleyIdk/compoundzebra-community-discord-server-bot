package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.environmenttype.BotEnvironmentType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListenerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.StderrLogger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediatorImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import java.util.function.Supplier
import net.dv8tion.jda.api.JDA as Jda

public interface BotModule_ {

	public val token: BotToken

	public val logger: Logger

	public val lazyJda: Lazy<Jda>

	public val shutdownCallbackRegistry: ShutdownCallbackRegistry
}

public fun BotModule_(
	scope: DiScope,
	environmentType: BotEnvironmentType,
	token: BotToken,
	initialActivity: Activity,
	configFilePath: Path,
): BotModule_ {
	return BotModule_Impl(scope, environmentType, token, initialActivity, configFilePath)
}



private class BotModule_Impl(
	scope: DiScope,
	environmentType: BotEnvironmentType,
	override val token: BotToken,
	private val initialActivity: Activity,
	configFilePath: Path,
) : DiModule(scope), BotModule_ {

	override val logger: Logger by this.singleton(::StderrLogger)

	private val shutdownModule: ShutdownModule by this.reusable {
		ShutdownModule(scope, Supplier(this.lazyJda::value), this.logger)
	}

	private val messageEventHandlerMediator: MessageEventHandlerMediator
		get() {
			MessageEventHandlerMediatorImpl
		}

	private val mainEventListener: MainEventListener
		get() {
			return MainEventListenerImpl(
				this.logger,
				this.shutdownModule.shutdownEventHandler,
				this.shutdownModule.shutdownCallbackRegistry,
				this.messageEventHandlerMediator,
			)
		}

	private val jdaFactory: JdaFactory
		get() {
			return JdaFactory(
				this.token,
				this.initialActivity,
				this.mainEventListener,
			)
		}

	override val lazyJda: Lazy<Jda> = this.singleton {
		this.jdaFactory.create()
	}

	override val shutdownCallbackRegistry: ShutdownCallbackRegistry
		get() {
			return this.shutdownModule.shutdownCallbackRegistry
		}
}
