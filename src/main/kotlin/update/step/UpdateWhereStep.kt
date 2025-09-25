package update.step

import common.step.BuildStep
import common.step.Step
import condition.step.BaseWhereStep

interface UpdateWhereStep : Step, BaseWhereStep<BuildStep>, BuildStep {
}