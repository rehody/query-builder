package update.builder

import common.node.TableNode
import common.step.BuildStep
import condition.builder.ConditionBuilder
import condition.step.ColumnStep
import condition.step.LogicalStep
import insert.node.ValueNode
import insert.step.ValueStep
import parser.StatementParser
import update.statement.UpdateStatement
import update.step.UpdateStep
import update.step.UpdateWhereStep

class UpdateBuilder : UpdateStep, ValueStep<UpdateWhereStep>, UpdateWhereStep {
    val statement = UpdateStatement()

    companion object {
        fun update(table: String): ValueStep<UpdateWhereStep> {
            return UpdateBuilder().update(table)
        }
    }

    override fun update(table: String): ValueStep<UpdateWhereStep> {
        statement.tableClause = TableNode(table)
        return this
    }

    override fun values(vararg values: ValueNode): UpdateWhereStep {
        statement.valueClause = values.asList()
        return this
    }

    override fun where(block: ColumnStep.() -> LogicalStep): BuildStep {
        val builder = ConditionBuilder(null)
        builder.block()
        statement.whereClause = builder.root
        return this
    }

    override fun build(): String {
        return StatementParser.getParsedUpdate(statement)
    }

}