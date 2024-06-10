package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

public class UnicodeString private constructor(
	private val utf16String: String,
	@Transient private var scalarValues: List<ScalarValue>?,
) : Iterable<ScalarValue> {

	public val length: Int
		get() {
			if (this.utf16String.isEmpty()) {
				return 0
			}

			return this.initScalarValues().size
		}

	public val lastIndex: Int
		get() {
			return this.initScalarValues().lastIndex
		}

	public fun isEmpty(): Boolean {
		return this.utf16String.isEmpty()
	}

	public operator fun get(index: Int): ScalarValue {
		return this.initScalarValues()[index]
	}

	public fun substring(begin: Int, end: Int): UnicodeString {
		if (begin < 0) throw IndexOutOfBoundsException(begin)
		if (end < 0) throw IndexOutOfBoundsException(begin)
		require(begin <= end)

		if (this.isEmpty()) {
			return this
		}

		val scalarValues: List<ScalarValue> = this.initScalarValues()

		if (begin > scalarValues.lastIndex) {
			return UnicodeString("", emptyList())
		}

		val endCoerced: Int = end.coerceAtMost(scalarValues.lastIndex)

		val subValues: List<ScalarValue> = scalarValues.subList(begin, endCoerced)
		val substring: String = subValues
			.joinToString(separator = "") { scalarValue: ScalarValue ->
				scalarValue.toCodePoint().asString()
			}

		return UnicodeString(substring, subValues)
	}

	public fun startsAndEndsWith(scalarValue: ScalarValue): Boolean {
		if (this.isEmpty()) {
			return false
		}

		val scalarValues: List<ScalarValue> = this.initScalarValues()

		return ((scalarValues.first() == scalarValue) && (scalarValues.last() == scalarValue))
	}

	override fun iterator(): Iterator<ScalarValue> {
		return this.initScalarValues().iterator()
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is UnicodeString) return false
		if (this.utf16String != other.utf16String) return false
		return true
	}

	override fun hashCode(): Int {
		return this.utf16String.hashCode()
	}

	override fun toString(): String {
		return this.utf16String
	}

	private fun initScalarValues(): List<ScalarValue> {
		var scalarValues: List<ScalarValue>? = this.scalarValues
		if (scalarValues != null) {
			return scalarValues
		}

		scalarValues = this.utf16String.scalarValues().toList()
		this.scalarValues = scalarValues
		return scalarValues
	}

	public companion object {

		public fun ofString(string: String): UnicodeString {
			return UnicodeString(string, scalarValues = null)
		}
	}
}

public fun String.toUnicode(): UnicodeString {
	return UnicodeString.ofString(this)
}
