package insert.step

import common.step.BuildStep
import common.step.Step

interface InsertStep : Step {
    fun insertInto(table: String): ValueStep<BuildStep>
}