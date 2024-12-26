package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.description

@JvmInline
public value class PollDescription private constructor(private val descriptionString: String) {

	init {
		require(this.descriptionString.isNotBlank()) {
			"Poll description string must not be blank"
		}
	}

	override fun toString(): String {
		return this.descriptionString
	}

	public companion object {

		public fun ofString(descriptionString: String): PollDescription? {
			if (descriptionString.isBlank()) {
				return null
			}

			return PollDescription(descriptionString)
		}
	}
}
