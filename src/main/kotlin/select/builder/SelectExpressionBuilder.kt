package select.builder

import common.node.Node
import common.step.BuildStep
import select.node.ExistsNode
import select.step.SelectExpressionStep
import select.step.SelectStep

class SelectExpressionBuilder : SelectExpressionStep {
    var expression: Node? = null

    override fun exists(block: SelectStep.() -> BuildStep) {
        val builder = SelectBuilder()
        builder.block()
        expression = ExistsNode(builder.statement)
    }
}