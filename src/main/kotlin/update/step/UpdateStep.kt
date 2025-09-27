package update.step

import common.step.BuildStep
import common.step.Step
import insert.step.ValueStep

interface UpdateStep : Step {
    fun update(table: String): ValueStep<UpdateWhereStep>
}