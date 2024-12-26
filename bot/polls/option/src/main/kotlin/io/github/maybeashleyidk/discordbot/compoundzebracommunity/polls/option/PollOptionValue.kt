package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.option

@JvmInline
public value class PollOptionValue private constructor(private val valueString: String) {

	init {
		require(this.valueString.isNotEmpty()) {
			"Poll option value string must not be empty"
		}
	}

	override fun toString(): String {
		return this.valueString
	}

	public companion object {

		public fun ofString(valueString: String): PollOptionValue? {
			if (valueString.isEmpty()) {
				return null
			}

			return PollOptionValue(valueString)
		}
	}
}
