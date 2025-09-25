package parser

import common.node.Node
import common.node.OrderNode
import condition.node.BetweenNode
import condition.node.BinaryLogicalNode
import condition.node.ComparisonNode
import condition.node.ConditionNode
import insert.node.ValueNode
import select.node.ExistsNode
import select.node.NumberColumnNode
import select.node.SelectColumnNode
import select.node.SelectExpressionNode
import select.node.SelectNode
import select.node.SubSelectColumnNode
import select.node.TableColumnNode
import util.QueryFormatter
import util.SqlKeyword

object ClauseParser {
    fun getParsedSelect(node: SelectNode): String {
        val query = StringBuilder()
        query.append(SqlKeyword.SELECT).append(" ")

        when (node) {
            is SelectColumnNode -> {
                if (node.columns.isEmpty()) {
                    query.append("*")
                }

                for (column in node.columns) {
                    when (column) {
                        is TableColumnNode -> {
                            query
                                .append(QueryFormatter.getEscapedIdentifier(column.name))
                                .append(", ")
                        }

                        is NumberColumnNode -> {
                            query
                                .append(QueryFormatter.getEscapedParameter(column.value))
                                .append(", ")
                        }

                        is SubSelectColumnNode -> {
                            val sub = StatementParser.getParsedSelect(column.statement)
                            query.append("(").append(sub).append(") ")
                            if (column.alias != null) {
                                query
                                    .append(SqlKeyword.AS).append(" ")
                                    .append(column.alias)
                            }
                            query.append(", ")
                        }
                    }
                }
            }

            is SelectExpressionNode -> {
                when (node.expression) {
                    is ExistsNode -> {
                        query
                            .append(SqlKeyword.EXISTS).append("(")
                            .append(StatementParser.getParsedSelect(node.expression.statement))
                            .append(")")
                    }

                    else -> throwUnexpectedNode(node)
                }
            }

            else -> throwUnexpectedNode(node)
        }

        return query.toString().removeSuffix(", ").trim()
    }

    fun getParsedWhere(node: ConditionNode): String {
        fun getRecursivelyParsed(node: ConditionNode, query: StringBuilder, nested: Boolean = false) {
            when (node) {
                is ComparisonNode -> {
                    val sign = if (node.value != null) node.op.sign else SqlKeyword.IS

                    query
                        .append(QueryFormatter.getEscapedIdentifier(node.column.name)).append(" ")
                        .append(sign).append(" ")
                        .append(QueryFormatter.getEscapedParameter(node.value))
                }

                is BetweenNode<*> -> {
                    query
                        .append(QueryFormatter.getEscapedIdentifier(node.column)).append(" ")
                        .append(SqlKeyword.BETWEEN).append(" ")
                        .append(QueryFormatter.getEscapedParameter(node.from)).append(" ")
                        .append(SqlKeyword.AND).append(" ")
                        .append(QueryFormatter.getEscapedParameter(node.to))
                }

                is BinaryLogicalNode -> {
                    if (nested) query.append("(")

                    getRecursivelyParsed(node.left, query, true)
                    query.append(" ").append(node.op).append(" ")
                    getRecursivelyParsed(node.right, query, true)

                    if (nested) query.append(")")
                }

                else -> throwUnexpectedNode(node)
            }
        }


        val query = StringBuilder()
        query.append(SqlKeyword.WHERE).append(" ")
        getRecursivelyParsed(node, query)

        return query.toString()
    }

    fun getParsedOrder(node: OrderNode): String {
        val query = StringBuilder()
        query.append(SqlKeyword.ORDER_BY).append(" ")

        for (column in node.columns) {
            query
                .append(QueryFormatter.getEscapedIdentifier(column.name)).append(" ")
                .append(column.direction.toString()).append(", ")
        }

        return query.toString().removeSuffix(", ").trim()
    }

    private fun throwUnexpectedNode(node: Node): Nothing {
        throw IllegalStateException("Unexpected node: " + node::class.simpleName)
    }

    fun getParsedInsertValues(values: List<ValueNode>): String {
        val query = StringBuilder()
        val cols = values.map { QueryFormatter.getEscapedIdentifier(it.column) }
        val vals = values.map { QueryFormatter.getEscapedParameter(it.value) }

        query
            .append("(")
            .append(cols.joinToString(", "))
            .append(") ")
            .append(SqlKeyword.VALUES)
            .append(" (")
            .append(vals.joinToString(", "))
            .append(")")

        return query.toString();
    }

    fun getParsedUpdateValues(values: List<ValueNode>): String {
        val query = StringBuilder()
        val cols = values.map { QueryFormatter.getEscapedIdentifier(it.column) }
        val vals = values.map { QueryFormatter.getEscapedParameter(it.value) }

        if (cols.size != vals.size) {
            throw IllegalStateException("Number of columns and values must match")
        }

        for ((c, v) in cols.zip(vals)) {
            query
                .append(c)
                .append(" = ")
                .append(v)
                .append(", ")
        }

        return query.toString().removeSuffix(", ").trim()
    }
}
