package select.step

import common.step.BuildStep
import common.step.Step

interface LimitStep : Step, BuildStep {
    fun limit(value: Int): BuildStep
}