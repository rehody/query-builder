package select.node

import common.node.Node
import select.statement.SelectStatement

data class ExistsNode(
    val statement: SelectStatement
) : Node
