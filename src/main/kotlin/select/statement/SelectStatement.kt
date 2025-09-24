package select.statement

import common.node.LimitNode
import common.node.OrderNode
import common.statement.Statement
import condition.node.ConditionNode
import common.node.TableNode
import select.node.SelectNode

data class SelectStatement(
    var selectClause: SelectNode? = null,
    var tableClause: TableNode? = null,
    var whereClause: ConditionNode? = null,
    var orderClause: OrderNode? = null,
    var limitClause: LimitNode? = null
) : Statement