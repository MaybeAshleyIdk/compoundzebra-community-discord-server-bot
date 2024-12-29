package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectpath

internal fun <T : Any> Sequence<T?>.toNonNullsListOrNull(): List<T>? {
	val iterator: Iterator<T?> = this.iterator()

	// We are not expecting an empty sequence.

	val firstElement: T = iterator.next()
		?: return null

	if (!(iterator.hasNext())) {
		return listOf(firstElement)
	}

	val list: MutableList<T> = ArrayList()
	list.add(firstElement)

	while (iterator.hasNext()) {
		val element: T = iterator.next()
			?: return null

		list.add(element)
	}

	return list
}
