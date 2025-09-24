package util

import common.node.OrderColumnNode
import common.step.BuildStep
import insert.node.ValueNode
import select.builder.SelectBuilder
import select.node.NumberColumnNode
import select.node.SelectColumn
import select.node.SubSelectColumnNode
import select.node.TableColumnNode
import select.step.SelectStep

object Columns {

    fun col(name: String): SelectColumn {
        return TableColumnNode(name)
    }

    fun col(name: Number): SelectColumn {
        return NumberColumnNode(name)
    }

    fun col(name: String, direction: OrderDirection): OrderColumnNode {
        return OrderColumnNode(name, direction)
    }

    fun col(column: String, value: Any?): ValueNode {
        return ValueNode(column, value)
    }

    fun sub(query: SelectStep.() -> BuildStep, alias: String? = null): SubSelectColumnNode {
        val builder = SelectBuilder()
        builder.query()
        return SubSelectColumnNode(builder.statement, alias)
    }
}