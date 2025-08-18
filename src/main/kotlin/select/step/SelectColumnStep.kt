package select.step

import common.step.BuildStep
import common.step.Step

interface SelectColumnStep : Step {
    fun col(vararg columns: String): SelectColumnStep
    fun sub(block: SelectStep.() -> BuildStep): AliasStep
}