package condition.node

import select.node.TableColumnNode
import util.ComparisonOperation

data class ComparisonNode(
    val op: ComparisonOperation,
    val column: TableColumnNode,
    val value: Any?
) : ConditionNode
