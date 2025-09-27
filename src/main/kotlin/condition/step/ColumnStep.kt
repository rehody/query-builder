package condition.step

import common.step.Step

interface ColumnStep : Step {
    fun col(name: String): ComparisonStep
}
