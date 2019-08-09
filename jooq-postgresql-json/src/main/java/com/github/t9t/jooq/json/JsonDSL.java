package com.github.t9t.jooq.json;

import org.jooq.Field;
import org.jooq.impl.DSL;

/**
 * <p>Functions for JSON and JSONB PostgreSQL operator support in jOOQ</p>
 *
 * <p>Reference: <a href="https://www.postgresql.org/docs/11/functions-json.html">https://www.postgresql.org/docs/11/functions-json.html</a></p>
 */
public final class JsonDSL {


    /**
     * <p>Get JSON array element (indexed from zero, negative integers count from the end), using the
     * <code>-&gt;</code> operator</p>
     *
     * <p>Example: <code>'[{"a":"foo"},{"b":"bar"},{"c":"baz"}]'::json-&gt;2</code></p>
     * <p>Example result: <code>{"c":"baz"}</code></p>
     *
     * @param jsonField A JSON {@code Field} containing an array to get the array element from
     * @param index     Array index
     * @return A {@code Field} representing the extracted array element
     */
    public static Field<String> arrayElement(Field<String> jsonField, int index) {
        return DSL.field("{0}->{1}", String.class, jsonField, index);
    }

    /**
     * <p>Get JSON object field by key using the <code>-&gt;</code> operator</p>
     *
     * <p>Example: <code>'{"a": {"b":"foo"}}'::json-&gt;'a'</code></p>
     * <p>Example result: <code>{"b":"foo"}</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the field from
     * @param key       JSON field key name
     * @return A {@code Field} representing the extracted value
     */
    public static Field<String> fieldByKey(Field<String> jsonField, String key) {
        return DSL.field("{0}->{1}", String.class, jsonField, key);
    }
}
