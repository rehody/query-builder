# QueryBuilder

**Type-safe Kotlin DSL** для декларативного построения SQL-запросов (SELECT / INSERT / UPDATE).  

---

QueryBuilder позволяет писать читаемый Kotlin-DSL вида:

```kotlin
SelectBuilder
    .select()
    .from("table")
    .where { col("id").eq(1) }
    .orderBy(col("f1", direction = DESC))
    .limit(10)
    .build()
````
---

## Особенности / фичи

* Type-safe fluent API / Builder pattern для `SELECT`, `INSERT` (и базовый `UPDATE`).
* Lambda-with-receiver DSL для выражений.
* AST -> SQL парсер (модули `StatementParser` / `ClauseParser`) с поддержкой вложенных условий и подзапросов.
* Корректное экранирование идентификаторов и параметров, поддержка `Raw` для контролируемых SQL-вставок.
* Юнит-тесты

---

## Примеры использования

### Минимальный `SELECT *`

```kotlin
val q = SelectBuilder
    .select()
    .from("table")
    .build()
// SELECT * FROM "table"
```

### SELECT с колонками, ORDER BY и LIMIT

```kotlin
val q = SelectBuilder
    .select(col("f1"), col("f2"), col("f3"))
    .from("table")
    .orderBy(col("f1", direction = DESC), col("f2", direction = ASC))
    .limit(10)
    .build()
```

### Nested conditions

```kotlin
val q = SelectBuilder
    .select(col("f1"), col("f2"), col("f3"))
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
```

### EXISTS и подзапросы

```kotlin
val q = SelectBuilder
    .select {
        exists {
            select(col(1))
                .from("table")
                .where { col("f1").neq("val") }
        }
    }
    .build()
// SELECT EXISTS(SELECT 1 FROM "table" WHERE "f1" <> 'val')
```

### INSERT

```kotlin
val q = InsertBuilder
    .insertInto("table")
    .values(col("id", 42))
    .build()
// INSERT INTO "table" ("id") VALUES (42)
```

---

## Структура проекта

* `select.*`, `insert.*`, `update.*` — билдеры, шаги (step interfaces), node / statement классы (модель AST).
* `parser.*` — `StatementParser` / `ClauseParser` — логика преобразования AST в SQL.
* `util.*` — `QueryFormatter`, `Raw` и утилиты форматирования литералов.
* `src/test` — unit-тесты, демонстрирующие кейсы использования.
