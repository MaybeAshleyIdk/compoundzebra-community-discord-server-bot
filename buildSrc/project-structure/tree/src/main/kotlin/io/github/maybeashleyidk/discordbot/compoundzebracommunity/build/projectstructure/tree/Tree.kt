package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree

@JvmInline
public value class Tree<T : Any>(internal val rootNode: TreeNode<T>) {

	override fun toString(): String {
		return this.rootNode.toString()
	}
}

public fun <T : Any> Tree<T>.allNodesDepth(): Sequence<TreeNode<T>> {
	return this.rootNode.allNodesDepth()
}

public fun <T : Any> Tree<T>.flattenDepth(): Sequence<T> {
	return this.rootNode.flattenDepth()
}

public fun <T : Any> Tree<T>.filterSubNodes(predicate: (T) -> Boolean): Tree<T> {
	val newRootNode: TreeNode<T> = this.rootNode.filterSubNodes(predicate)
	return Tree(newRootNode)
}
