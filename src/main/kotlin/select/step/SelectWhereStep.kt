package select.step

import common.step.Step
import condition.step.BaseWhereStep

interface SelectWhereStep : Step, BaseWhereStep<OrderStep>, OrderStep, LimitStep {
}