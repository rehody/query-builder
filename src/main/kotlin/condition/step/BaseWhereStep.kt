package condition.step

import common.step.BuildStep
import common.step.Step

interface BaseWhereStep<NEXT : Step> : Step, BuildStep {
    fun where(block: ColumnStep.() -> LogicalStep): NEXT
}