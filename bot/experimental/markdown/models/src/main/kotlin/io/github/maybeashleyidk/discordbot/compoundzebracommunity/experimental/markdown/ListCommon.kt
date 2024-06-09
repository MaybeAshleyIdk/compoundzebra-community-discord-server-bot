package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

public sealed class ListItemBullet {

	public sealed class Unordered : ListItemBullet()
	public data object Asterisk : Unordered()
	public data object Dash : Unordered()

	// TODO: check what the max is
	public data class Ordered(public val n: Int) : ListItemBullet()
}

public enum class MListStyle {
	UNORDERED,
	ORDERED,
}
