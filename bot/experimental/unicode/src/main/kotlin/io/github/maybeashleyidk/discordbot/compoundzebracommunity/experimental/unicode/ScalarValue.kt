package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

@JvmInline
public value class ScalarValue private constructor(private val codePoint: CodePoint) {

	init {
		require(this.codePoint.isNotSurrogate())
	}

	public fun toCodePoint(): CodePoint {
		return this.codePoint
	}

	override fun toString(): String {
		val sb = StringBuilder()

		sb.append(this.codePoint.toString())

		sb.append(" ('")
		if (this.codePoint == CodePoint.ofInt('\''.code)) {
			sb.append('\\')
		}
		sb.append(this.codePoint.asString())
		sb.append("')")

		return sb.toString()
	}

	public companion object {

		public fun ofCodePoint(codePoint: CodePoint): ScalarValue? {
			if (codePoint.isSurrogate()) {
				return null
			}

			return ScalarValue(codePoint)
		}

		public fun ofChar(ch: Char): ScalarValue {
			return this.ofCodePoint(CodePoint.ofInt(ch.code))!!
		}
	}
}
