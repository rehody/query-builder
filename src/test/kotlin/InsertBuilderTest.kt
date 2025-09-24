import insert.builder.InsertBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Columns.col

class InsertBuilderTest {

    @Test
    fun shouldBuildQuery() {
        val expected = """INSERT INTO "table" ("f1", "f2", "f3") """ +
                """VALUES ('smth', 111, 1337)"""

        val query = InsertBuilder
            .insertInto("table")
            .values(
                col("f1", "smth"),
                col("f2", 111),
                col("f3", 1337),
            )
            .build()

        assertThat(query).isEqualTo(expected);
    }
}