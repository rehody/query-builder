package insert.node

import common.node.Node

data class ValueNode(
    val column: String,
    val value: Any?
) : Node