package select.statement

import common.node.LimitNode
import common.node.OrderNode
import common.statement.Statement
import condition.node.ConditionNode
import select.node.FromNode
import select.node.SelectNode

data class SelectStatement(
    var selectClause: SelectNode? = null,
    var fromClause: FromNode? = null,
    var whereClause: ConditionNode? = null,
    var orderClause: OrderNode? = null,
    var limitClause: LimitNode? = null
) : Statement