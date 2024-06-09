package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree

public data class TreeNode<T : Any>(
	val value: T,
	val subNodes: List<TreeNode<T>>,
) {

	public companion object {

		public fun <T : Any> leaf(value: T): TreeNode<T> {
			return TreeNode(value, subNodes = emptyList())
		}
	}

	override fun toString(): String {
		val sb = StringBuilder()

		sb.append(this.value)

		for (subNode: TreeNode<T> in this.subNodes.dropLast(1)) {
			val subNodeStr: String = subNode.toString()
				.replace("\n", "\n|   ")

			sb.append("\n`-- ").append(subNodeStr)
		}

		val lastSubNode: TreeNode<T>? = this.subNodes.lastOrNull()
		if (lastSubNode != null) {
			val subNodeStr: String = lastSubNode.toString()
				.replace("\n", "\n    ")

			sb.append("\n`-- ").append(subNodeStr)
		}

		return sb.toString()
	}
}

internal fun <T : Any> TreeNode<T>.allNodesDepth(): Sequence<TreeNode<T>> {
	return sequence {
		val allSubNodes: Sequence<TreeNode<T>> = this@allNodesDepth.subNodes
			.asSequence()
			.flatMap(TreeNode<T>::allNodesDepth)

		this@sequence.yieldAll(allSubNodes)
		this@sequence.yield(this@allNodesDepth)
	}
}

internal fun <T : Any> TreeNode<T>.flattenDepth(): Sequence<T> {
	return this.allNodesDepth()
		.map(TreeNode<T>::value)
}

internal fun <T : Any> TreeNode<T>.filterSubNodes(predicate: (T) -> Boolean): TreeNode<T> {
	val filteredSubNodes: List<TreeNode<T>> = this.subNodes
		.mapNotNull { subNode: TreeNode<T> ->
			subNode.filter(predicate)
		}

	return TreeNode(this.value, filteredSubNodes)
}

private fun <T : Any> TreeNode<T>.filter(predicate: (T) -> Boolean): TreeNode<T>? {
	if (!(predicate(this.value))) {
		return null
	}

	val filteredSubNodes: List<TreeNode<T>> = this.subNodes
		.mapNotNull { subNode: TreeNode<T> ->
			subNode.filter(predicate)
		}

	return TreeNode(this.value, filteredSubNodes)
}
