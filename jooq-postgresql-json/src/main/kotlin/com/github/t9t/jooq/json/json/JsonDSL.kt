package com.github.t9t.jooq.json.json

import com.github.t9t.jooq.json.JsonDSL
import org.jetbrains.annotations.NotNull
import org.jooq.Field
import org.jooq.JSON

/**
 * @see JsonDSL.field
 */
@NotNull
fun JSON.toField(): Field<JSON?> = JsonDSL.field(this)

/**
 * @see JsonDSL.arrayElement
 */
@NotNull
fun Field<JSON?>.arrayElement(index: Int): Field<JSON?> = JsonDSL.arrayElement(this, index)

/**
 * @see JsonDSL.arrayElementText
 */
@NotNull
fun Field<JSON?>.arrayElementText(index: Int): Field<String?> = JsonDSL.arrayElementText(this, index)

/**
 * @see JsonDSL.fieldByKey
 */
@NotNull
fun Field<JSON?>.fieldByKey(key: String): Field<JSON?> = JsonDSL.fieldByKey(this, key)

/**
 * @see JsonDSL.fieldByKeyText
 */
@NotNull
fun Field<JSON?>.fieldByKeyText(key: String): Field<String?> = JsonDSL.fieldByKeyText(this, key)

/**
 * @see JsonDSL.objectAtPath
 */
@NotNull
fun Field<JSON?>.objectAtPath(vararg path: String): Field<JSON?> = JsonDSL.objectAtPath(this, *path)

/**
 * @see JsonDSL.objectAtPath
 */
@NotNull
fun Field<JSON?>.objectAtPath(path: Collection<String>): Field<JSON?> = JsonDSL.objectAtPath(this, path)

/**
 * @see JsonDSL.objectAtPathText
 */
@NotNull
fun Field<JSON?>.objectAtPathText(vararg path: String): Field<String?> = JsonDSL.objectAtPathText(this, *path)

/**
 * @see JsonDSL.objectAtPathText
 */
@NotNull
fun Field<JSON?>.objectAtPathText(path: Collection<String>): Field<String?> = JsonDSL.objectAtPathText(this, path)

/**
 * @see JsonDSL.arrayLength
 */
@NotNull
fun arrayLength(jsonField: Field<JSON?>): Field<Int?> = JsonDSL.arrayLength(jsonField)

/**
 * @see JsonDSL.extractPath
 */
@NotNull
fun extractPath(jsonField: Field<JSON?>, vararg path: String): Field<JSON?> = JsonDSL.extractPath(jsonField, *path)

/**
 * @see JsonDSL.extractPath
 */
@NotNull
fun extractPath(jsonField: Field<JSON?>, path: Collection<String>): Field<JSON?> = JsonDSL.extractPath(jsonField, path)

/**
 * @see JsonDSL.extractPathText
 */
@NotNull
fun extractPathText(jsonField: Field<JSON?>, vararg path: String): Field<String?> = JsonDSL.extractPathText(jsonField, *path)

/**
 * @see JsonDSL.extractPathText
 */
@NotNull
fun extractPathText(jsonField: Field<JSON?>, path: Collection<String>): Field<String?> = JsonDSL.extractPathText(jsonField, path)

/**
 * @see JsonDSL.typeOf
 */
@NotNull
fun typeOf(jsonField: Field<JSON?>): Field<String?> = JsonDSL.typeOf(jsonField)

/**
 * @see JsonDSL.stripNulls
 */
@NotNull
fun stripNulls(jsonField: Field<JSON?>): Field<JSON?> = JsonDSL.stripNulls(jsonField)
