package select.step

import common.step.Step
import condition.step.BaseWhereStep

interface SelectWhereStep<NEXT : Step> : Step, BaseWhereStep<NEXT>, OrderStep, LimitStep {
}