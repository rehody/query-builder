import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import select.builder.SelectBuilder
import util.Columns.col
import util.Columns.sub
import util.OrderDirection.ASC
import util.OrderDirection.DESC
import java.time.LocalDateTime

class SelectBuilderTest {

    @Test
    fun shouldBuildMinimalQuery() {
        val expected = """SELECT * FROM "table""""

        val query = SelectBuilder
            .select()
            .from("table")
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildQueryWithOrderAndLimit() {
        val expected = """SELECT "f1", "f2", "f3" FROM "table" """ +
                """ORDER BY "f1" DESC, "f2" ASC, "f3" ASC, "f4" ASC LIMIT 10"""

        val query = SelectBuilder
            .select(
                col("f1"),
                col("f2"),
                col("f3"),
            )
            .from("table")
            .orderBy(
                col("f1", direction = DESC),
                col("f2", direction = ASC),
                col("f3", direction = ASC),
                col("f4", direction = ASC),
            ).limit(10)
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithNestedConditions() {
        val expected = """SELECT "f1", "f2", "f3" FROM "table" """ +
                """WHERE "f1" = 100 AND (("f2" < 500 OR "f3" BETWEEN 100 AND 200) OR "f1" > 'val')"""

        val query = SelectBuilder
            .select(
                col("f1"),
                col("f2"),
                col("f3"),
            )
            .from("table")
            .where {
                col("f1").eq(100)
                    .and {
                        col("f2").lt(500)
                            .or("f3").between(100, 200)
                            .or {
                                col("f1").gt("val")
                            }
                    }
            }
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithExists() {
        val expected = """SELECT EXISTS(SELECT 1 FROM "table" WHERE "f1" <> 'val')"""

        val query = SelectBuilder
            .select {
                exists {
                    select(col(1))
                        .from("table")
                        .where { col("f1").neq("val") }
                }
            }
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithMixedColumnTypes() {
        val expected = """SELECT 1, "f1", 2, 3, "f2" FROM "table""""

        val query = SelectBuilder
            .select(
                col(1),
                col("f1"),
                col(2),
                col(3),
                col("f2")
            )
            .from("table")
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldBuildWithSubSelectAsColumn() {
        val expected = """SELECT "f1", "f2", "f3", (SELECT 1 FROM "table") AS sub FROM "table""""

        val query = SelectBuilder
            .select(
                col("f1"),
                col("f2"),
                col("f3"),
                sub(
                    {
                        select(col(1))
                            .from("table")
                    },
                    alias = "sub"
                )
            )
            .from("table")
            .build()

        assertThat(query).isEqualTo(expected)
    }

    @Test
    fun shouldParseDateCorrectly() {
        val date = LocalDateTime.of(2025, 9, 29, 15, 57)
        val expected = """SELECT * FROM "table" WHERE "date" = '2025-09-29 15:57:00'"""

        val query = SelectBuilder
            .select()
            .from("table")
            .where {
                col("date").eq(date)
            }
            .build()

        assertThat(query).isEqualTo(expected)
    }

//    This code won't compile - limit can't come before orderBy
//
//    @Test
//    fun shouldNotCompileWhenLimitBeforeOrderBy() {
//        SelectBuilder.select().from("table").limit(100).orderBy(col("f1", "f2"))
//    }
}