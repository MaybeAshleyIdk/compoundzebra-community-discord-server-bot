package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.TreeNode
import org.gradle.api.Project

internal fun Project.toTree(): Tree<Project> {
	val rootNode: TreeNode<Project> = this.toTreeNode()
	return Tree(rootNode)
}

private fun Project.toTreeNode(): TreeNode<Project> {
	val children: Collection<Project> = this.childProjects.values

	if (children.isEmpty()) {
		return TreeNode.leaf(this)
	}

	val subNodes: List<TreeNode<Project>> = children
		.map(Project::toTreeNode)

	return TreeNode(
		value = this,
		subNodes = subNodes,
	)
}
