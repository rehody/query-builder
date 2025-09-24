package select.builder

import common.node.LimitNode
import common.node.OrderColumnNode
import common.node.OrderNode
import common.node.TableNode
import common.step.BuildStep
import condition.builder.ConditionBuilder
import condition.step.ColumnStep
import condition.step.LogicalStep
import parser.StatementParser
import select.node.SelectColumn
import select.node.SelectColumnNode
import select.node.SelectExpressionNode
import select.statement.SelectStatement
import select.step.FromStep
import select.step.LimitStep
import select.step.OrderStep
import select.step.SelectExpressionStep
import select.step.SelectStep
import select.step.SelectWhereStep

class SelectBuilder internal constructor() :
    SelectStep, FromStep,
    SelectWhereStep<OrderStep>,
    OrderStep, LimitStep, BuildStep {
    val statement = SelectStatement()

    companion object {
        fun select(vararg columns: SelectColumn): FromStep {
            return SelectBuilder().select(*columns)
        }

        fun select(expression: SelectExpressionStep.() -> Unit): BuildStep {
            return SelectBuilder().select(expression)
        }
    }

    override fun select(vararg columns: SelectColumn): FromStep {
        statement.selectClause = SelectColumnNode(columns.asList())
        return this
    }

    override fun select(expression: SelectExpressionStep.() -> Unit): BuildStep {
        val builder = SelectExpressionBuilder()
        builder.expression()

        statement.selectClause = SelectExpressionNode(builder.expression!!)
        return this
    }

    override fun from(table: String): SelectWhereStep<OrderStep> {
        statement.tableClause = TableNode(table)
        return this
    }

    override fun where(block: ColumnStep.() -> LogicalStep): OrderStep {
        val builder = ConditionBuilder(null)
        builder.block()

        statement.whereClause = builder.root
        return this
    }

    override fun orderBy(vararg columns: OrderColumnNode): LimitStep {
        statement.orderClause = OrderNode(columns.asList())
        return this
    }

    override fun limit(value: Int): BuildStep {
        statement.limitClause = LimitNode(value)
        return this
    }

    override fun build(): String {
        return StatementParser.getParsedSelect(statement)
    }
}