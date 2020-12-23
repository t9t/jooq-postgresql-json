package com.github.t9t.jooq.json.jsonb

import com.github.t9t.jooq.json.JsonbDSL
import org.jooq.Field
import org.jooq.JSONB
import org.jooq.impl.DSL
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonbDSLTestIT {

    private val jsonbField: Field<JSONB> = DSL.field("foo.bar", JSONB::class.java)

    @Test
    fun `should provide extension function to create field from JSON`() {
        /* Given */
        val jsonb = JSONB.valueOf("{}")
        /* When */
        val jsonbField = JsonbDSL.field(jsonb)
        val jsonbFieldExt = jsonb.toField()
        /* Then */
        assertEquals(jsonbField, jsonbFieldExt)
    }

    @Test
    fun `should provide extension function for arrayElement`() {
        /* Given */
        val index = 1
        /* When */
        val arrayElementField = JsonbDSL.arrayElement(jsonbField, index)
        val arrayElementFieldExt = jsonbField.arrayElement(index)
        /* Then */
        assertEquals(arrayElementField, arrayElementFieldExt)
    }

    @Test
    fun `should provide extension function for arrayElementText`() {
        /* Given */
        val index = 1
        /* When */
        val arrayElementTextField = JsonbDSL.arrayElementText(jsonbField, index)
        val arrayElementTextFieldExt = jsonbField.arrayElementText(index)
        /* Then */
        assertEquals(arrayElementTextField, arrayElementTextFieldExt)
    }

    @Test
    fun `should provide extension function for fieldByKey`() {
        /* Given */
        val key = "key"
        /* When */
        val fieldByKeyField = JsonbDSL.fieldByKey(jsonbField, key)
        val fieldByKeyFieldExt = jsonbField.fieldByKey(key)
        /* Then */
        assertEquals(fieldByKeyField, fieldByKeyFieldExt)
    }

    @Test
    fun `should provide extension function for fieldByKeyText`() {
        /* Given */
        val key = "key"
        /* When */
        val fieldByKeyTextField = JsonbDSL.fieldByKeyText(jsonbField, key)
        val fieldByKeyTextFieldExt = jsonbField.fieldByKeyText(key)
        /* Then */
        assertEquals(fieldByKeyTextField, fieldByKeyTextFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPath with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathField = JsonbDSL.objectAtPath(jsonbField, *path)
        val objectAtPathFieldExt = jsonbField.objectAtPath(*path)
        /* Then */
        assertEquals(objectAtPathField, objectAtPathFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPath with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathField = JsonbDSL.objectAtPath(jsonbField, path.toList())
        val objectAtPathFieldExt = jsonbField.objectAtPath(path.toList())
        /* Then */
        assertEquals(objectAtPathField, objectAtPathFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPathText with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathTextField = JsonbDSL.objectAtPathText(jsonbField, *path)
        val objectAtPathTextFieldExt = jsonbField.objectAtPathText(*path)
        /* Then */
        assertEquals(objectAtPathTextField, objectAtPathTextFieldExt)
    }

    @Test
    fun `should provide extension function for objectAtPathText with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val objectAtPathTextField = JsonbDSL.objectAtPathText(jsonbField, path.toList())
        val objectAtPathTextFieldExt = jsonbField.objectAtPathText(path.toList())
        /* Then */
        assertEquals(objectAtPathTextField, objectAtPathTextFieldExt)
    }

    @Test
    fun `should provide extension function for contains`() {
        /* Given */
        val other: Field<JSONB> = DSL.field("other.bar", JSONB::class.java)
        /* When */
        val containsField = JsonbDSL.contains(jsonbField, other)
        val containsFieldExt = jsonbField.containsJson(other)
        /* Then */
        assertEquals(containsField, containsFieldExt)
    }

    @Test
    fun `should provide extension function for containedIn`() {
        /* Given */
        val other: Field<JSONB> = DSL.field("other.bar", JSONB::class.java)
        /* When */
        val containedInField = JsonbDSL.containedIn(jsonbField, other)
        val containedInFieldExt = jsonbField.containedIn(other)
        /* Then */
        assertEquals(containedInField, containedInFieldExt)
    }

    @Test
    fun `should provide extension function for hasKey`() {
        /* Given */
        val key = "bar"
        /* When */
        val hasKeyField = JsonbDSL.hasKey(jsonbField, key)
        val hasKeyFieldExt = jsonbField.hasKey(key)
        /* Then */
        assertEquals(hasKeyField, hasKeyFieldExt)
    }

    @Test
    fun `should provide extension function for hasAnyKey with varargs`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val hasAnyKeyField = JsonbDSL.hasAnyKey(jsonbField, *keys)
        val hasAnyKeyFieldExt = jsonbField.hasAnyKey(*keys)
        /* Then */
        assertEquals(hasAnyKeyField, hasAnyKeyFieldExt)
    }

    @Test
    fun `should provide extension function for hasAnyKey with Collection`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val hasAnyKeyField = JsonbDSL.hasAnyKey(jsonbField, keys.toList())
        val hasAnyKeyFieldExt = jsonbField.hasAnyKey(keys.toList())
        /* Then */
        assertEquals(hasAnyKeyField, hasAnyKeyFieldExt)
    }

    @Test
    fun `should provide extension function for hasAllKeys with varargs`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val hasAllKeysField = JsonbDSL.hasAllKeys(jsonbField, *keys)
        val hasAllKeysFieldExt = jsonbField.hasAllKeys(*keys)
        /* Then */
        assertEquals(hasAllKeysField, hasAllKeysFieldExt)
    }

    @Test
    fun `should provide extension function for hasAllKeys with Collection`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val hasAllKeysField = JsonbDSL.hasAllKeys(jsonbField, keys.toList())
        val hasAllKeysFieldExt = jsonbField.hasAllKeys(keys.toList())
        /* Then */
        assertEquals(hasAllKeysField, hasAllKeysFieldExt)
    }

    @Test
    fun `should provide extension function for concat`() {
        /* Given */
        val other: Field<JSONB> = DSL.field("other.bar", JSONB::class.java)
        /* When */
        val concatField = JsonbDSL.concat(jsonbField, other)
        val concatFieldExt = jsonbField.concatJson(other)
        /* Then */
        assertEquals(concatField, concatFieldExt)
    }

    @Test
    fun `should provide extension function for delete with key or element`() {
        /* Given */
        val key = "foo"
        /* When */
        val deleteField = JsonbDSL.delete(jsonbField, key)
        val deleteFieldExt = jsonbField.delete(key)
        /* Then */
        assertEquals(deleteField, deleteFieldExt)
    }

    @Test
    fun `should provide extension function for delete with varargs`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val deleteField = JsonbDSL.delete(jsonbField, *keys)
        val deleteFieldExt = jsonbField.delete(*keys)
        /* Then */
        assertEquals(deleteField, deleteFieldExt)
    }

    @Test
    fun `should provide extension function for deleteElement`() {
        /* Given */
        val index = 1
        /* When */
        val deleteElementField = JsonbDSL.deleteElement(jsonbField, index)
        val deleteElementFieldExt = jsonbField.deleteElement(index)
        /* Then */
        assertEquals(deleteElementField, deleteElementFieldExt)
    }

    @Test
    fun `should provide extension function for deletePath`() {
        /* Given */
        val keys = arrayOf("key1", "key2")
        /* When */
        val deletePathField = JsonbDSL.deletePath(jsonbField, *keys)
        val deletePathFieldExt = jsonbField.deletePath(*keys)
        /* Then */
        assertEquals(deletePathField, deletePathFieldExt)
    }





    @Test
    fun `should provide function for arrayLength`() {
        /* Given */
        /* When */
        val arrayLengthField = JsonbDSL.arrayLength(jsonbField)
        val arrayLengthFieldExt = arrayLength(jsonbField)
        /* Then */
        assertEquals(arrayLengthField, arrayLengthFieldExt)
    }

    @Test
    fun `should provide function for extractPath with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathField = JsonbDSL.extractPath(jsonbField, *path)
        val extractPathFieldExt = extractPath(jsonbField, *path)
        /* Then */
        assertEquals(extractPathField, extractPathFieldExt)
    }

    @Test
    fun `should provide function for extractPath with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathField = JsonbDSL.extractPath(jsonbField, path.toList())
        val extractPathFieldExt = extractPath(jsonbField, path.toList())
        /* Then */
        assertEquals(extractPathField, extractPathFieldExt)
    }

    @Test
    fun `should provide function for extractPathText with varargs`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathTextField = JsonbDSL.extractPathText(jsonbField, *path)
        val extractPathTextFieldExt = extractPathText(jsonbField, *path)
        /* Then */
        assertEquals(extractPathTextField, extractPathTextFieldExt)
    }

    @Test
    fun `should provide function for extractPathText with collection`() {
        /* Given */
        val path = arrayOf("path", "to", "key")
        /* When */
        val extractPathTextField = JsonbDSL.extractPathText(jsonbField, path.toList())
        val extractPathTextFieldExt = extractPathText(jsonbField, path.toList())
        /* Then */
        assertEquals(extractPathTextField, extractPathTextFieldExt)
    }

    @Test
    fun `should provide function for typeOf`() {
        /* Given */
        /* When */
        val typeOfField = JsonbDSL.typeOf(jsonbField)
        val typeOfFieldExt = typeOf(jsonbField)
        /* Then */
        assertEquals(typeOfField, typeOfFieldExt)
    }

    @Test
    fun `should provide function for stripNulls`() {
        /* Given */
        /* When */
        val stripNullsField = JsonbDSL.stripNulls(jsonbField)
        val stripNullsFieldExt = stripNulls(jsonbField)
        /* Then */
        assertEquals(stripNullsField, stripNullsFieldExt)
    }

    @Test
    fun `should provide function for pretty`() {
        /* Given */
        /* When */
        val prettyField = JsonbDSL.pretty(jsonbField)
        val prettyFieldExt = pretty(jsonbField)
        /* Then */
        assertEquals(prettyField, prettyFieldExt)
    }

}
