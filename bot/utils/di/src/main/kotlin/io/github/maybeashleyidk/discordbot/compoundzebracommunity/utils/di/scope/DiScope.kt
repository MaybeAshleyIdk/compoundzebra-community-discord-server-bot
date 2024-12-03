package io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope

public interface DiScope {

	public fun <T : Any> save(key: Any, initializer: () -> T): Lazy<T>
}
