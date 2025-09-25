package insert.builder

import common.node.TableNode
import common.step.BuildStep
import insert.node.ValueNode
import insert.statement.InsertStatement
import insert.step.InsertStep
import insert.step.ValueStep
import parser.StatementParser

class InsertBuilder internal constructor() :
    InsertStep, ValueStep, BuildStep {
    val statement = InsertStatement()

    companion object {
        fun insertInto(table: String): ValueStep {
            return InsertBuilder().insertInto(table)
        }
    }

    override fun insertInto(table: String): ValueStep {
        statement.tableClause = TableNode(table)
        return this
    }

    override fun values(vararg values: ValueNode): BuildStep {
        statement.valueClause = values.toList()
        return this
    }

    override fun build(): String {
        return StatementParser.getParsedInsert(statement)
    }

}