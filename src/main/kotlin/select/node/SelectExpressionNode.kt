package select.node

import common.node.Node

data class SelectExpressionNode(
    val expression: Node
) : SelectNode
