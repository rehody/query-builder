package condition.node

import util.LogicalOperation

data class BinaryLogicalNode(
    val op: LogicalOperation,
    val left: ConditionNode,
    val right: ConditionNode
) : ConditionNode
