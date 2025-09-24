package insert.statement

import common.node.TableNode
import common.statement.Statement
import insert.node.ValueNode

data class InsertStatement(
    var tableClause: TableNode? = null,
    var valueClause: List<ValueNode>? = null
) : Statement