package com.github.t9t.jooq.json.jsonb

import com.github.t9t.jooq.json.JsonbDSL
import org.jooq.Condition
import org.jooq.Field
import org.jooq.JSONB

/**
 * @see JsonbDSL.field
 */
fun JSONB.toField(): Field<JSONB> = JsonbDSL.field(this)

/**
 * @see JsonbDSL.arrayElement
 */
fun Field<JSONB>.arrayElement(index: Int): Field<JSONB> = JsonbDSL.arrayElement(this, index)

/**
 * @see JsonbDSL.arrayElementText
 */
fun Field<JSONB>.arrayElementText(index: Int): Field<String> = JsonbDSL.arrayElementText(this, index)

/**
 * @see JsonbDSL.fieldByKey
 */
fun Field<JSONB>.fieldByKey(key: String): Field<JSONB> = JsonbDSL.fieldByKey(this, key)

/**
 * @see JsonbDSL.fieldByKeyText
 */
fun Field<JSONB>.fieldByKeyText(key: String): Field<String> = JsonbDSL.fieldByKeyText(this, key)

/**
 * @see JsonbDSL.objectAtPath
 */
fun Field<JSONB>.objectAtPath(vararg path: String): Field<JSONB> = JsonbDSL.objectAtPath(this, *path)

/**
 * @see JsonbDSL.objectAtPath
 */
fun Field<JSONB>.objectAtPath(path: Collection<String>): Field<JSONB> = JsonbDSL.objectAtPath(this, path)

/**
 * @see JsonbDSL.objectAtPathText
 */
fun Field<JSONB>.objectAtPathText(vararg path: String): Field<String> = JsonbDSL.objectAtPathText(this, *path)

/**
 * @see JsonbDSL.objectAtPathText
 */
fun Field<JSONB>.objectAtPathText(path: Collection<String>): Field<String> = JsonbDSL.objectAtPathText(this, path)

/**
 * @see JsonbDSL.contains
 */
fun Field<JSONB>.containsJson(other: Field<JSONB>): Condition = JsonbDSL.contains(this, other)

/**
 * @see JsonbDSL.containedIn
 */
fun Field<JSONB>.containedIn(other: Field<JSONB>): Condition = JsonbDSL.containedIn(this, other)

/**
 * @see JsonbDSL.hasKey
 */
fun Field<JSONB>.hasKey(key: String): Condition = JsonbDSL.hasKey(this, key)

/**
 * @see JsonbDSL.hasAnyKey
 */
fun Field<JSONB>.hasAnyKey(vararg keys: String): Condition = JsonbDSL.hasAnyKey(this, *keys)

/**
 * @see JsonbDSL.hasAnyKey
 */
fun Field<JSONB>.hasAnyKey(keys: Collection<String>): Condition = JsonbDSL.hasAnyKey(this, keys)

/**
 * @see JsonbDSL.hasAllKeys
 */
fun Field<JSONB>.hasAllKeys(vararg keys: String): Condition = JsonbDSL.hasAllKeys(this, *keys)

/**
 * @see JsonbDSL.hasAllKeys
 */
fun Field<JSONB>.hasAllKeys(keys: Collection<String>): Condition = JsonbDSL.hasAllKeys(this, keys)

/**
 * @see JsonbDSL.concat
 */
fun Field<JSONB>.concatJson(field2: Field<JSONB>): Field<JSONB> = JsonbDSL.concat(this, field2)

/**
 * @see JsonbDSL.delete
 */
fun Field<JSONB>.delete(keyOrElement: String): Field<JSONB> = JsonbDSL.delete(this, keyOrElement)

/**
 * @see JsonbDSL.delete
 */
fun Field<JSONB>.delete(vararg keysOrElements: String): Field<JSONB> = JsonbDSL.delete(this, *keysOrElements)

/**
 * @see JsonbDSL.deleteElement
 */
fun Field<JSONB>.deleteElement(index: Int): Field<JSONB> = JsonbDSL.deleteElement(this, index)

/**
 * @see JsonbDSL.deletePath
 */
fun Field<JSONB>.deletePath(vararg path: String): Field<JSONB> = JsonbDSL.deletePath(this, *path)

/**
 * @see JsonbDSL.arrayLength
 */
fun arrayLength(jsonField: Field<JSONB>): Field<Int> = JsonbDSL.arrayLength(jsonField)

/**
 * @see JsonbDSL.extractPath
 */
fun extractPath(jsonField: Field<JSONB>, vararg path: String): Field<JSONB> = JsonbDSL.extractPath(jsonField, *path)

/**
 * @see JsonbDSL.extractPath
 */
fun extractPath(jsonField: Field<JSONB>, path: Collection<String>): Field<JSONB>  = JsonbDSL.extractPath(jsonField, *path.toTypedArray())

/**
 * @see JsonbDSL.extractPathText
 */
fun extractPathText(jsonField: Field<JSONB>, vararg path: String): Field<String> = JsonbDSL.extractPathText(jsonField, *path)

/**
 * @see JsonbDSL.extractPathText
 */
fun extractPathText(jsonField: Field<JSONB>, path: Collection<String>): Field<String> = JsonbDSL.extractPathText(jsonField, *path.toTypedArray())

/**
 * @see JsonbDSL.typeOf
 */
fun typeOf(jsonField: Field<JSONB>): Field<String> = JsonbDSL.typeOf(jsonField)

/**
 * @see JsonbDSL.stripNulls
 */
fun stripNulls(jsonField: Field<JSONB>): Field<JSONB> = JsonbDSL.stripNulls(jsonField)

/**
 * @see JsonbDSL.pretty
 */
fun pretty(jsonField: Field<JSONB>): Field<String> = JsonbDSL.pretty(jsonField)
