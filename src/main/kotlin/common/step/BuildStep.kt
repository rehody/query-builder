package common.step

interface BuildStep : Step {
    fun build(): String
}