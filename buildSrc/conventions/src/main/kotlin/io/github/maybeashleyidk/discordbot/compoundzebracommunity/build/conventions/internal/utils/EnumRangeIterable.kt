package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.utils

internal fun <E : Enum<E>> ClosedRange<E>.asIterable(enumClass: Class<E>): Iterable<E> {
	return EnumRangeIterable(enumClass, range = this)
}

internal inline fun <reified E : Enum<E>> ClosedRange<E>.asIterable(): Iterable<E> {
	return this.asIterable(enumClass = E::class.java)
}

private class EnumRangeIterable<E : Enum<E>>(
	private val enumClass: Class<E>,
	private val range: ClosedRange<E>,
) : Iterable<E> {

	override fun iterator(): Iterator<E> {
		return EnumRangeIterator(this.enumClass, this.range)
	}
}

private class EnumRangeIterator<E : Enum<E>>(enumClass: Class<E>, range: ClosedRange<E>) : Iterator<E> {

	private val enumValues: Array<E> = checkNotNull(enumClass.enumConstants)
	private var nextValueOrdinal: Int = range.start.ordinal
	private val lastValueOrdinal: Int = range.endInclusive.ordinal

	override fun hasNext(): Boolean {
		return (this.nextValueOrdinal <= this.lastValueOrdinal)
	}

	override fun next(): E {
		if (this.nextValueOrdinal > this.lastValueOrdinal) {
			throw NoSuchElementException()
		}

		val nextValueOrdinal: Int = this.nextValueOrdinal

		this.nextValueOrdinal = (nextValueOrdinal + 1)

		return this.enumValues[nextValueOrdinal]
	}
}
