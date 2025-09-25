import insert.builder.InsertBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Columns.col

class InsertBuilderTest {


    @Test
    fun shouldBuildQueryWithSingleColumn() {
        val expected = """INSERT INTO "table" ("id") VALUES (42)"""

        val query = InsertBuilder
            .insertInto("table")
            .values(col("id", 42))
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithSeveralColumns() {
        val expected = """INSERT INTO "table" ("f1", "f2", "f3") VALUES ('ok', 'not ok', 'maybe ok')"""

        val query = InsertBuilder
            .insertInto("table")
            .values(
                col("f1", "ok"),
                col("f2", "not ok"),
                col("f3", "maybe ok"),
            )
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldInsertNullValue() {
        val expected = """INSERT INTO "table" ("f1", "f2") VALUES (1337, NULL)"""

        val query = InsertBuilder
            .insertInto("table")
            .values(
                col("f1", 1337),
                col("f2", null),
            )
            .build()

        assertThat(query).isEqualTo(expected)
    }
}