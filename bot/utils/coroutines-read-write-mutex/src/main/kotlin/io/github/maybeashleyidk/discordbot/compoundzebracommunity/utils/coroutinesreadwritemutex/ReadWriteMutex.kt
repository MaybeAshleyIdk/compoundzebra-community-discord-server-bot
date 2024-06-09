package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex

public interface ReadWriteMutex {

	public suspend fun lockForReading()

	public suspend fun unlockForReading()

	public suspend fun lockForWriting()

	public suspend fun unlockForWriting()
}

public suspend inline fun <R> ReadWriteMutex.withReadLock(block: () -> R): R {
	this.lockForReading()
	try {
		return block()
	} finally {
		this.unlockForReading()
	}
}

public suspend inline fun <R> ReadWriteMutex.withWriteLock(block: () -> R): R {
	this.lockForWriting()
	try {
		return block()
	} finally {
		this.unlockForWriting()
	}
}

public fun ReadWriteMutex(): ReadWriteMutex {
	return SemaphoreReadWriteMutex()
}
