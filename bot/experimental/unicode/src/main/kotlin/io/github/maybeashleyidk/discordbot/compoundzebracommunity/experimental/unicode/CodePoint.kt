package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

private const val CODESPACE_MASK: Int = 0x10FFFF

@JvmInline
public value class CodePoint private constructor(internal val integer: Int) : Comparable<CodePoint> {

	init {
		require((this.integer and CODESPACE_MASK) == this.integer)
	}

	public fun isWhitespace(): Boolean {
		return Character.isWhitespace(this.integer)
	}

	override fun compareTo(other: CodePoint): Int {
		return this.integer.compareTo(other.integer)
	}

	public fun asString(): String {
		return Character.toString(this.integer)
	}

	override fun toString(): String {
		val sb = StringBuilder()

		sb.append("U+")
		sb.append(this.integer.toString(radix = 16).padStart(4, '0'))

		return sb.toString()
	}

	public companion object {

		public fun ofInt(codePointInt: Int): CodePoint {
			return CodePoint(codePointInt and CODESPACE_MASK)
		}
	}
}
