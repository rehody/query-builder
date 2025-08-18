package parser

import select.node.SelectExpressionNode
import select.statement.SelectStatement
import util.QueryFormatter
import util.SqlKeyword

object StatementParser {
    fun getParsedSelect(statement: SelectStatement): String {
        val clauses = ArrayList<String>()

        clauses += ClauseParser.getParsedSelect(statement.selectClause!!)

        if (statement.selectClause is SelectExpressionNode) {
            return clauses.joinToString(" ")
        }

        clauses += SqlKeyword.FROM + " " +
                QueryFormatter.getEscapedIdentifier(statement.fromClause!!.table)

        if (statement.whereClause != null) {
            clauses += ClauseParser.getParsedWhere(statement.whereClause!!)
        }

        if (statement.orderClause != null) {
            clauses += ClauseParser.getParsedOrder(statement.orderClause!!)
        }

        if (statement.limitClause != null) {
            clauses += SqlKeyword.LIMIT + " " +
                    QueryFormatter.getEscapedParameter(statement.limitClause!!.value)
        }

        return clauses.joinToString(" ")
    }
}