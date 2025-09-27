import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import update.builder.UpdateBuilder
import util.Columns.col

class UpdateBuilderTest {

    @Test
    fun shouldBuildMinimalQuery() {
        val expected = """UPDATE "table" SET "f1" = 1, "f2" = 'hi'"""

        val query = UpdateBuilder
            .update("table")
            .values(
                col("f1", 1),
                col("f2", "hi")
            )
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithConditions() {
        val expected = """UPDATE "table" SET "f1" = 1, "f2" = NULL """ +
                """WHERE "f1" = 1337 OR "f2" BETWEEN 100 AND 500"""

        val query = UpdateBuilder
            .update("table")
            .values(
                col("f1", 1),
                col("f2", null)
            )
            .where {
                col("f1").eq(1337)
                    .or("f2").between(100, 500)
            }
            .build()

        assertThat(query).isEqualTo(expected)
    }
}