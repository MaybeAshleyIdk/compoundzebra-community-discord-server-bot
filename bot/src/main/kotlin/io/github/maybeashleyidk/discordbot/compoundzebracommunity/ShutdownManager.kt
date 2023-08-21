package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.ShutdownAction
import java.util.concurrent.Semaphore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ShutdownManager @Suppress("ktlint:standard:annotation") @Inject constructor() : ShutdownAction {

	private val semaphore: Semaphore = Semaphore(1)

	init {
		this.semaphore.acquire()
	}

	override fun requestShutdown() {
		this.semaphore.release()
	}

	fun waitForShutdownRequest() {
		this.semaphore.acquire()
	}
}
