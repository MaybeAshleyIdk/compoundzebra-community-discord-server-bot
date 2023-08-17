package io.github.maybeashleyidk.discordbot.compoundzebracommunity

import java.util.concurrent.Semaphore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShutdownManager @Suppress("ktlint:standard:annotation") @Inject constructor() {

	private val semaphore: Semaphore = Semaphore(1)

	init {
		this.semaphore.acquire()
	}

	fun requestShutdown() {
		this.semaphore.release()
	}

	fun waitForShutdownRequest() {
		this.semaphore.acquire()
	}
}
