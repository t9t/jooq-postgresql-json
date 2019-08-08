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
