package select.step

import common.step.BuildStep
import common.step.Step
import select.node.SelectColumn

interface SelectStep : Step {
    fun select(vararg columns: SelectColumn): FromStep
    fun select(expression: SelectExpressionStep.() -> Unit): BuildStep
}