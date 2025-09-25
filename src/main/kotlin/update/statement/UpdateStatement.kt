package update.statement

import common.node.TableNode
import common.statement.Statement
import condition.node.ConditionNode
import insert.node.ValueNode

data class UpdateStatement(
    var tableClause: TableNode? = null,
    var valueClause: List<ValueNode>? = null,
    var whereClause: ConditionNode? = null,
) : Statement