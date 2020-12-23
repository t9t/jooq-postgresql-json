package com.github.t9t.jooq.json.json

import com.github.t9t.jooq.json.JsonDSL
import org.jooq.Field
import org.jooq.JSON
import org.jooq.impl.DSL
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDSLTestIT {

    private val jsonField: Field<JSON> = DSL.field("foo.bar", JSON::class.java)

    @Test
    fun `should provide extension function to create field from JSON`() {
        /* Given */
        val json = JSON.valueOf("{}")
        /* When */
        val jsonField = JsonDSL.field(json)
        val jsonFieldExt = json.toField()
        /* Then */
        assertEquals(jsonField, jsonFieldExt)
    }

    @Test
    fun `should provide extension function for arrayElement`() {
        /* Given */
        val index = 1
        /* When */
        val arrayElementField = JsonDSL.arrayElement(jsonField, index)
        val arrayElementFieldExt = jsonField.arrayElement(index)
        /* Then */
        assertEquals(arrayElementField, arrayElementFieldExt)
    }

    @Test
    fun `should provide extension function for arrayElementText`() {
        /* Given */
        val index = 1
        /* When */
        val arrayElementTextField = JsonDSL.arrayElementText(jsonField, index)
        val arrayElementTextFieldExt = jsonField.arrayElementText(index)
        /* Then */
        assertEquals(arrayElementTextField, arrayElementTextFieldExt)
    }

    @Test
    fun `should provide extension function for fieldByKey`() {
        /* Given */
        val key = "key"
        /* When */
        val fieldByKeyField = JsonDSL.fieldByKey(jsonField, key)
        val fieldByKeyFieldExt = jsonField.fieldByKey(key)
        /* Then */
        assertEquals(fieldByKeyField, fieldByKeyFieldExt)
    }

    @Test
    fun `should provide extension function for fieldByKeyText`() {
        /* Given */
        val key = "key"
        /* When */
        val fieldByKeyTextField = JsonDSL.fieldByKeyText(jsonField, key)
        val fieldByKeyTextFieldExt = jsonField.fieldByKeyText(key)
        /* Then */
        assertEquals(fieldByKeyTextField, fieldByKeyTextFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPath with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathField = JsonDSL.objectAtPath(jsonField, *path)
        val objectAtPathFieldExt = jsonField.objectAtPath(*path)
        /* Then */
        assertEquals(objectAtPathField, objectAtPathFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPath with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathField = JsonDSL.objectAtPath(jsonField, path.toList())
        val objectAtPathFieldExt = jsonField.objectAtPath(path.toList())
        /* Then */
        assertEquals(objectAtPathField, objectAtPathFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPathText with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathTextField = JsonDSL.objectAtPathText(jsonField, *path)
        val objectAtPathTextFieldExt = jsonField.objectAtPathText(*path)
        /* Then */
        assertEquals(objectAtPathTextField, objectAtPathTextFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPathText with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathTextField = JsonDSL.objectAtPathText(jsonField, path.toList())
        val objectAtPathTextFieldExt = jsonField.objectAtPathText(path.toList())
        /* Then */
        assertEquals(objectAtPathTextField, objectAtPathTextFieldExt)
    }

    @Test
    fun `should provide function for arrayLength`() {
        /* Given */
        /* When */
        val arrayLengthField = JsonDSL.arrayLength(jsonField)
        val arrayLengthFieldExt = arrayLength(jsonField)
        /* Then */
        assertEquals(arrayLengthField, arrayLengthFieldExt)
    }

    @Test
    fun `should provide function for extractPath with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathField = JsonDSL.extractPath(jsonField, *path)
        val extractPathFieldExt = extractPath(jsonField, *path)
        /* Then */
        assertEquals(extractPathField, extractPathFieldExt)
    }

    @Test
    fun `should provide function for extractPath with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathField = JsonDSL.extractPath(jsonField, path.toList())
        val extractPathFieldExt = extractPath(jsonField, path.toList())
        /* Then */
        assertEquals(extractPathField, extractPathFieldExt)
    }

    @Test
    fun `should provide function for extractPathText with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathTextField = JsonDSL.extractPathText(jsonField, *path)
        val extractPathTextFieldExt = extractPathText(jsonField, *path)
        /* Then */
        assertEquals(extractPathTextField, extractPathTextFieldExt)
    }

    @Test
    fun `should provide function for extractPathText with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathTextField = JsonDSL.extractPathText(jsonField, path.toList())
        val extractPathTextFieldExt = extractPathText(jsonField, path.toList())
        /* Then */
        assertEquals(extractPathTextField, extractPathTextFieldExt)
    }

    @Test
    fun `should provide function for typeOf`() {
        /* Given */
        /* When */
        val typeOfField = JsonDSL.typeOf(jsonField)
        val typeOfFieldExt = typeOf(jsonField)
        /* Then */
        assertEquals(typeOfField, typeOfFieldExt)
    }

    @Test
    fun `should provide function for stripNulls`() {
        /* Given */
        /* When */
        val stripNullsField = JsonDSL.stripNulls(jsonField)
        val stripNullsFieldExt = stripNulls(jsonField)
        /* Then */
        assertEquals(stripNullsField, stripNullsFieldExt)
    }

}
