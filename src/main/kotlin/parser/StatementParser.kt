package parser

import insert.statement.InsertStatement
import select.node.SelectExpressionNode
import select.statement.SelectStatement
import update.statement.UpdateStatement
import util.QueryFormatter
import util.SqlKeyword

object StatementParser {
    fun getParsedSelect(statement: SelectStatement): String {
        val clauses = ArrayList<String>()
        clauses += ClauseParser.getParsedSelect(statement.selectClause!!)

        if (statement.selectClause is SelectExpressionNode) {
            return clauses.joinToString(" ")
        }

        clauses += SqlKeyword.FROM
        clauses += QueryFormatter.getEscapedIdentifier(statement.tableClause!!.table)

        if (statement.whereClause != null) {
            clauses += ClauseParser.getParsedWhere(statement.whereClause!!)
        }

        if (statement.orderClause != null) {
            clauses += ClauseParser.getParsedOrder(statement.orderClause!!)
        }

        if (statement.limitClause != null) {
            clauses += SqlKeyword.LIMIT
            clauses += QueryFormatter.getEscapedParameter(statement.limitClause!!.value)
        }

        return clauses.joinToString(" ")
    }

    fun getParsedInsert(statement: InsertStatement): String {
        val clauses = ArrayList<String>()

        clauses += SqlKeyword.INSERT_INTO
        clauses += QueryFormatter.getEscapedIdentifier(statement.tableClause!!.table)
        clauses += ClauseParser.getParsedInsertValues(statement.valueClause!!)

        return clauses.joinToString(" ")
    }

    fun getParsedUpdate(statement: UpdateStatement): String {
        val clauses = ArrayList<String>()

        clauses += SqlKeyword.UPDATE
        clauses += QueryFormatter.getEscapedIdentifier(statement.tableClause!!.table)
        clauses += SqlKeyword.SET
        clauses += ClauseParser.getParsedUpdateValues(statement.valueClause!!)

        if (statement.whereClause != null) {
            clauses += ClauseParser.getParsedWhere(statement.whereClause!!)
        }

        return clauses.joinToString(" ")
    }

}