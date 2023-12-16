package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description

@JvmInline
public value class PollDescription private constructor(private val string: String) {

	public companion object {

		public fun ofString(descriptionString: String): PollDescription {
			require(descriptionString.isNotBlank()) {
				"Poll description string must not be blank"
			}

			return PollDescription(descriptionString)
		}
	}

	init {
		require(this.string.isNotBlank()) {
			"Poll description string must not be blank"
		}
	}

	override fun toString(): String {
		return this.string
	}
}
