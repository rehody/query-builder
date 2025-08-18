package select.step

import common.step.BuildStep
import common.step.Step

interface SelectExpressionStep : Step {
    fun exists(block: SelectStep.() -> BuildStep)
}
