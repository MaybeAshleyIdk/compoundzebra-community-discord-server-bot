package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import com.squareup.moshi.Moshi
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.buildtype.BotBuildType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening.MainEventListenerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.jdafactory.JdaFactory
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.StderrLogger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.ConfigModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.modules.MessageEventHandlingModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.PollsModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandlerImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutService
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout.SelfTimeoutServiceImpl
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.ShutdownModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.token.BotToken
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope
import net.dv8tion.jda.api.entities.Activity
import java.nio.file.Path
import java.util.function.Supplier
import net.dv8tion.jda.api.JDA as Jda

public interface BotModule {

	public val token: BotToken

	public val logger: Logger

	public val lazyJda: Lazy<Jda>

	public val shutdownCallbackRegistry: ShutdownCallbackRegistry
}

public fun BotModule(
	scope: DiScope,
	buildType: BotBuildType,
	token: BotToken,
	initialActivity: Activity,
	configFilePath: Path,
): BotModule {
	return BotModuleImpl(scope, buildType, token, initialActivity, configFilePath)
}

private class BotModuleImpl(
	scope: DiScope,
	buildType: BotBuildType,
	override val token: BotToken,
	private val initialActivity: Activity,
	configFilePath: Path,
) : DiModule(scope), BotModule {

	override val logger: Logger by this.singleton(::StderrLogger)

	private val moshi: Moshi by this.reusable {
		Moshi.Builder()
			.build()
	}

	private val configModule: ConfigModule by this.reusable {
		ConfigModule(
			scope,
			this.moshi,
			buildType,
			configFilePath,
			this.logger,
		)
	}

	private val pollsModule: PollsModule by this.reusable {
		PollsModule(
			scope,
			this.logger,
			this.configModule.configSupplier,
		)
	}

	private val selfTimeoutService: SelfTimeoutService
		get() {
			return SelfTimeoutServiceImpl(this.logger)
		}

	private val shutdownModule: ShutdownModule by this.reusable {
		ShutdownModule(
			scope,
			Supplier(this.lazyJda::value),
			this.logger,
		)
	}

	private val messageEventHandlingModule: MessageEventHandlingModule by this.reusable {
		MessageEventHandlingModule(
			scope,
			this.configModule.configSupplier,
			configFilePath,
			this.pollsModule.pollCreator,
			this.pollsModule.pollHolder,
			this.selfTimeoutService,
			this.shutdownModule.shutdownRequester,
			buildType,
			this.logger,
		)
	}

	private val privateMessageEventHandler: PrivateMessageEventHandler
		get() {
			return PrivateMessageEventHandlerImpl(this.logger)
		}

	private val mainEventListener: MainEventListener
		get() {
			return MainEventListenerImpl(
				this.logger,
				this.shutdownModule.shutdownEventHandler,
				this.shutdownModule.shutdownCallbackRegistry,
				this.messageEventHandlingModule.messageEventHandlerMediator,
				this.pollsModule.pollEventHandler,
				this.privateMessageEventHandler,
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
