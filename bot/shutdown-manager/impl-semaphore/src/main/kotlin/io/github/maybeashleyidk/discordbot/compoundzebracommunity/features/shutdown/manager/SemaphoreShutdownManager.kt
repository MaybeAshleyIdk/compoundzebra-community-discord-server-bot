package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager

import java.util.concurrent.Semaphore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class SemaphoreShutdownManager @Inject internal constructor() : ShutdownManager {

	private val semaphore: Semaphore = Semaphore(1)

	init {
		this.semaphore.acquire()
	}

	override fun requestShutdown() {
		this.semaphore.release()
	}

	override fun awaitShutdownRequest() {
		this.semaphore.acquire()
	}
}
