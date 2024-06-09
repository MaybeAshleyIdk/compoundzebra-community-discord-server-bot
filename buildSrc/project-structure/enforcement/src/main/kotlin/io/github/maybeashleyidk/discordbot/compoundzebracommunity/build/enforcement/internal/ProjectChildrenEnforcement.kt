package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.enforcement.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projectpolicy.children.ProjectChildrenPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttype.requireType
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.projecttypepolicies.selectPolicy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.Tree
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.TreeNode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.tree.allNodesDepth
import org.gradle.api.Project

internal object ProjectChildrenEnforcement {

	fun enforceProjectsChildren(projectTree: Tree<Project>) {
		for (node: TreeNode<Project> in projectTree.allNodesDepth()) {
			val children: List<Project> = node.subNodes.map(TreeNode<Project>::value)
			this.enforceProjectChildren(targetProject = node.value, children)
		}
	}

	private fun enforceProjectChildren(targetProject: Project, children: List<Project>) {
		for (childPath: String in children.map(Project::getPath)) {
			targetProject.evaluationDependsOn(childPath)
		}

		val childrenPolicy: ProjectChildrenPolicy = targetProject.requireType().selectPolicy().childrenPolicy
		childrenPolicy.enforce(targetProject, children)
	}
}
