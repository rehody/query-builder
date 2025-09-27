package select.node

import select.statement.SelectStatement

data class SubSelectColumnNode(
    val statement: SelectStatement,
    var alias: String? = null
) : SelectColumn