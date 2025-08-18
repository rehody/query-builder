package select.step

import common.step.Step

interface AliasStep : Step {
    fun `as`(alias: String): SelectColumnStep
}