package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.internal.utils

import java.util.EnumSet

internal inline fun <reified E : Enum<E>> selectEnumValues(noinline selector: (E) -> Boolean): EnumSet<E> {
	val set: EnumSet<E> = EnumSet.allOf(E::class.java)
	set.retainAll(selector)
	return set
}
