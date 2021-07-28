package com.github.t9t.jooq.json.testing

import com.github.t9t.jooq.generated.kotlin.tables.references.JSON_TEST
import com.github.t9t.jooq.json.testing.TestDb
import org.jooq.JSON
import org.jooq.JSONB
import org.jooq.Record1
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Validates Kotlin, jOOQ and JSON(B) interoperability
 */
class JsonKotlinIT {
    private val ds = TestDb.createDataSource()
    private val dsl = DSL.using(ds, SQLDialect.POSTGRES)

    @Before
    fun setUp() {
        dsl.deleteFrom(JSON_TEST).execute()
        assertEquals(4, dsl.execute("insert into jooq.json_test (name, data, datab)" +
                " values " +
                "('both', '{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}', '{\"jsonb\": {\"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}, \"n\": null}}')," +
                "('empty', '{}', '{}')," +
                "('null-sql', null, null)," +
                "('null-json', 'null'::json, 'null'::jsonb)").toLong())
    }

    @Test
    fun `select JSON NULL SQL, returning non-null JSON type`() {
        val r: Record1<JSON?>? = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne()
        Assert.assertNull(r?.value1())
    }

    @Test
    fun `select JSON NULL SQL, returning nullable JSON type`() {
        val r: Record1<JSON?>? = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne()
        Assert.assertNull(r?.value1())
    }

    @Test
    fun `select null JSON value`() {
        val r: Record1<JSON?>? = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-json"))
                .fetchOne()
        assertEquals("null", r?.value1()?.toString())
    }

    @Test
    fun `select JSON NULL SQL, returning non-null JSONB type`() {
        val r: Record1<JSONB?>? = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne()
        Assert.assertNull(r?.value1())
    }

    @Test
    fun `select JSON NULL SQL, returning nullable JSONB type`() {
        val r: Record1<JSONB?>? = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne()
        Assert.assertNull(r?.value1())
    }

    @Test
    fun `select null JSONB value`() {
        val r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-json"))
                .fetchOne()
        assertEquals("null", r?.value1()?.toString())
    }
}
