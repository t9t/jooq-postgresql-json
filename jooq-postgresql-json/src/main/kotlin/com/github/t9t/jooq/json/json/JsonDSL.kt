package com.github.t9t.jooq.json.json

import com.github.t9t.jooq.json.JsonDSL
import org.jooq.Field
import org.jooq.JSON


/**
 * @see JsonDSL.field
 */
fun JSON.toField(): Field<JSON> = JsonDSL.field(this)

/**
 * @see JsonDSL.arrayElement
 */
fun Field<JSON>.arrayElement(index: Int): Field<JSON> = JsonDSL.arrayElement(this, index)

/**
 * @see JsonDSL.arrayElementText
 */
fun Field<JSON>.arrayElementText(index: Int): Field<String> = JsonDSL.arrayElementText(this, index)

/**
 * @see JsonDSL.fieldByKey
 */
fun Field<JSON>.fieldByKey(key: String): Field<JSON> = JsonDSL.fieldByKey(this, key)

/**
 * @see JsonDSL.fieldByKeyText
 */
fun Field<JSON>.fieldByKeyText(key: String): Field<String> = JsonDSL.fieldByKeyText(this, key)

/**
 * @see JsonDSL.objectAtPath
 */
fun Field<JSON>.objectAtPath(vararg path: String): Field<JSON> = JsonDSL.objectAtPath(this, *path)

/**
 * @see JsonDSL.objectAtPath
 */
fun Field<JSON>.objectAtPath(path: Collection<String>): Field<JSON> = JsonDSL.objectAtPath(this, path)

/**
 * @see JsonDSL.objectAtPathText
 */
fun Field<JSON>.objectAtPathText(vararg path: String): Field<String> = JsonDSL.objectAtPathText(this, *path)

/**
 * @see JsonDSL.objectAtPathText
 */
fun Field<JSON>.objectAtPathText(path: Collection<String>): Field<String> = JsonDSL.objectAtPathText(this, path)

/**
 * @see JsonDSL.arrayLength
 */
fun arrayLength(jsonField: Field<JSON>): Field<Int> = JsonDSL.arrayLength(jsonField)

/**
 * @see JsonDSL.extractPath
 */
fun extractPath(jsonField: Field<JSON>, vararg path: String): Field<JSON> = JsonDSL.extractPath(jsonField, *path)

/**
 * @see JsonDSL.extractPath
 */
fun extractPath(jsonField: Field<JSON>, path: Collection<String>): Field<JSON> = JsonDSL.extractPath(jsonField, path)

/**
 * @see JsonDSL.extractPathText
 */
fun extractPathText(jsonField: Field<JSON>, vararg path: String): Field<String> = JsonDSL.extractPathText(jsonField, *path)

/**
 * @see JsonDSL.extractPathText
 */
fun extractPathText(jsonField: Field<JSON>, path: Collection<String>): Field<String> = JsonDSL.extractPathText(jsonField, path)

/**
 * @see JsonDSL.typeOf
 */
fun typeOf(jsonField: Field<JSON>): Field<String> = JsonDSL.typeOf(jsonField)

/**
 * @see JsonDSL.stripNulls
 */
fun stripNulls(jsonField: Field<JSON>): Field<JSON> = JsonDSL.stripNulls(jsonField)
