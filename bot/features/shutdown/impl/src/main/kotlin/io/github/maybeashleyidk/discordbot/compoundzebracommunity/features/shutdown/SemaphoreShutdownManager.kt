package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

import java.util.concurrent.Semaphore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SemaphoreShutdownManager @Suppress("ktlint:standard:annotation") @Inject constructor() :
	ShutdownManager {

	private val semaphore: Semaphore = Semaphore(1)

	init {
		this.semaphore.acquire()
	}

	override fun requestShutdown() {
		this.semaphore.release()
	}

	override fun waitForShutdownRequest() {
		this.semaphore.acquire()
	}
}