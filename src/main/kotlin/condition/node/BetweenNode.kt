package condition.node

data class BetweenNode<T>(
    val column: String,
    val from: T,
    val to: T
) : ConditionNode
