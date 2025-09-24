package insert.step

import common.step.Step

interface InsertStep : Step {
    fun insertInto(table: String): ValueStep
}