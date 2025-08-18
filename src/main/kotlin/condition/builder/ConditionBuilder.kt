package condition.builder

import condition.node.BetweenNode
import condition.node.BinaryLogicalNode
import condition.node.ComparisonNode
import condition.node.ConditionNode
import condition.step.ColumnStep
import condition.step.ComparisonStep
import condition.step.LogicalStep
import select.node.TableColumnNode
import util.ComparisonOperation
import util.LogicalOperation

class ConditionBuilder(var root: ConditionNode?) : ColumnStep, ComparisonStep, LogicalStep {
    private var currColumn: String? = null
    private var currLogical: LogicalOperation? = null


    private fun checkRoot(node: ConditionNode) {
        root = if (root == null) {
            node
        } else if (currLogical != null) {
            BinaryLogicalNode(currLogical!!, root!!, node)
        } else {
            throw IllegalStateException("Missing logical operation between conditions")
        }
    }


    override fun col(name: String): ComparisonStep {
        currColumn = name
        return this
    }


    override fun eq(value: Any?): LogicalStep {
        appendComparison(ComparisonOperation.EQUALS, value)
        return this
    }

    override fun neq(value: Any?): LogicalStep {
        appendComparison(ComparisonOperation.NOT_EQUALS, value)
        return this
    }

    override fun gt(value: Any?): LogicalStep {
        appendComparison(ComparisonOperation.GREATER_THAN, value)
        return this
    }

    override fun lt(value: Any?): LogicalStep {
        appendComparison(ComparisonOperation.LESS_THAN, value)
        return this
    }

    private fun appendComparison(operation: ComparisonOperation, value: Any?) {
        val comparison = ComparisonNode(operation, TableColumnNode(currColumn!!), value)
        checkRoot(comparison)
    }


    override fun <T> between(from: T, to: T): LogicalStep {
        val between = BetweenNode(currColumn!!, from, to)
        checkRoot(between)
        return this
    }


    override fun and(column: String): ComparisonStep {
        currLogical = LogicalOperation.AND
        currColumn = column
        return this
    }

    override fun or(column: String): ComparisonStep {
        currLogical = LogicalOperation.OR
        currColumn = column
        return this
    }


    override fun and(block: ColumnStep.() -> LogicalStep): LogicalStep {
        subBuilder(block, LogicalOperation.AND)
        return this
    }

    override fun or(block: ColumnStep.() -> LogicalStep): LogicalStep {
        subBuilder(block, LogicalOperation.OR)
        return this
    }


    private fun subBuilder(
        block: ColumnStep.() -> LogicalStep,
        operation: LogicalOperation
    ) {
        val subBuilder = ConditionBuilder(null)
        subBuilder.block()
        val subRoot = subBuilder.root

        root = if (root == null) {
            subRoot
        } else {
            BinaryLogicalNode(operation, root!!, subRoot!!)
        }
    }

}