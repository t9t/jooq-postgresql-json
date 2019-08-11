package com.github.t9t.jooq.json;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.Collection;

/**
 * <p>Functions for {@code jsonb} PostgreSQL operator support in jOOQ</p>
 *
 * <p>Reference: <a href="https://www.postgresql.org/docs/11/functions-json.html">https://www.postgresql.org/docs/11/functions-json.html</a></p>
 */
public final class JsonbDSL {
    public static Field<Jsonb> field(String s) {
        return DSL.field("{0}", Jsonb.class, Jsonb.of(s));
    }

    public static Field<Jsonb> field(Jsonb jsonb) {
        return DSL.field("{0}", Jsonb.class, jsonb);
    }

    /**
     * <p>Get JSON array element (indexed from zero, negative integers count from the end), using the
     * <code>-&gt;</code> operator</p>
     *
     * <p>Example: <code>'[{"a":"foo"},{"b":"bar"},{"c":"baz"}]'::json-&gt;2</code></p>
     * <p>Example result: <code>{"c":"baz"}</code></p>
     *
     * @param jsonField A JSON {@code Field} containing an array to get the array element from
     * @param index     Array index; negative values count from the end
     * @return A {@code Field} representing the extracted array element
     */
    public static Field<Jsonb> arrayElement(Field<Jsonb> jsonField, int index) {
        return DSL.field("{0}->{1}", Jsonb.class, jsonField, index);
    }

    /**
     * <p>Get JSON array element as {@code text} rather than {@code json(b)} (indexed from zero, negative integers
     * count from the end), using the <code>-&gt;&gt;</code> operator</p>
     *
     * <p>Example: <code>'[1,2,3]'::json-&gt;&gt;2</code></p>
     * <p>Example result: <code>3</code></p>
     *
     * @param jsonField A JSON {@code Field} containing an array to get the array element from
     * @param index     Array index; negative values count from the end
     * @return A {@code Field} representing the extracted array element, as text
     */
    public static Field<String> arrayElementText(Field<Jsonb> jsonField, int index) {
        return DSL.field("{0}->>{1}", String.class, jsonField, index);
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
    public static Field<Jsonb> fieldByKey(Field<Jsonb> jsonField, String key) {
        return DSL.field("{0}->{1}", Jsonb.class, jsonField, key);
    }

    /**
     * <p>Get JSON object field as {@code text} rather than {@code json(b)}, using the <code>-&gt;&gt;</code>
     * operator</p>
     *
     * <p>Example: <code>'{"a":1,"b":2}'::json-&gt;&gt;'b'</code></p>
     * <p>Example result: <code>2</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the field from
     * @param key       JSON field key name
     * @return A {@code Field} representing the extracted array element, as text
     */
    public static Field<String> fieldByKeyText(Field<Jsonb> jsonField, String key) {
        return DSL.field("{0}->>{1}", String.class, jsonField, key);
    }

    /**
     * <p>Get JSON object at specified path using the <code>#&gt;</code> operator</p>
     *
     * <p>Example: <code>'{"a": {"b":{"c": "foo"}}}'::json#>'{a,b}'</code></p>
     * <p>Example result: <code>{"c": "foo"}</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the path from
     * @param path      Path to the the object to return
     * @return A {@code Field} representing the object at the specified path
     * @see #objectAtPath(Field, Collection)
     */
    public static Field<Jsonb> objectAtPath(Field<Jsonb> jsonField, String... path) {
        return DSL.field("{0}#>{1}", Jsonb.class, jsonField, DSL.array(path));
    }

    /**
     * <p>Get JSON object at specified path using the <code>#&gt;</code> operator</p>
     *
     * <p>Example: <code>'{"a": {"b":{"c": "foo"}}}'::json#>'{a,b}'</code></p>
     * <p>Example result: <code>{"c": "foo"}</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the path from
     * @param path      Path to the the object to return
     * @return A {@code Field} representing the object at the specified path
     * @see #objectAtPath(Field, String...)
     */
    public static Field<Jsonb> objectAtPath(Field<Jsonb> jsonField, Collection<String> path) {
        return objectAtPath(jsonField, path.toArray(new String[0]));
    }

    /**
     * <p>Get JSON object at specified path as {@code text} rather than {@code json(b)}, using the <code>#&gt;</code>
     * operator</p>
     *
     * <p>Example: <code>'{"a":[1,2,3],"b":[4,5,6]}'::json#>>'{a,2}'</code></p>
     * <p>Example result: <code>3</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the path from
     * @param path      Path to the the object to return
     * @return A {@code Field} representing the object at the specified path, as text
     * @see #objectAtPathText(Field, Collection)
     */
    public static Field<String> objectAtPathText(Field<Jsonb> jsonField, String... path) {
        return DSL.field("{0}#>>{1}", String.class, jsonField, DSL.array(path));
    }

    /**
     * <p>Get JSON object at specified path as {@code text} rather than {@code json(b)}, using the <code>#&gt;</code>
     * operator</p>
     *
     * <p>Example: <code>'{"a":[1,2,3],"b":[4,5,6]}'::json#>>'{a,2}'</code></p>
     * <p>Example result: <code>3</code></p>
     *
     * @param jsonField The JSON {@code Field} to extract the path from
     * @param path      Path to the the object to return
     * @return A {@code Field} representing the object at the specified path, as text
     * @see #objectAtPath(Field, String...)
     */
    public static Field<String> objectAtPathText(Field<Jsonb> jsonField, Collection<String> path) {
        return objectAtPathText(jsonField, path.toArray(new String[0]));
    }

    /**
     * TODO: write docs
     * <p>
     * {@code @}> jsonb    Does the left JSON value contain the right JSON path/value entries at the top level? 	'{"a":1, "b":2}'::jsonb @> '{"b":2}'::jsonb
     */
    public static Condition contains(Field<Jsonb> left, Field<Jsonb> right) {
        return DSL.condition("{0} @> {1}", left, right);
    }

    /**
     * TODO: write docs
     * <@ 	jsonb 	Are the left JSON path/value entries contained at the top level within the right JSON value? 	'{"b":2}'::jsonb <@ '{"a":1, "b":2}'::jsonb
     */
    public static Condition containedIn(Field<Jsonb> left, Field<Jsonb> right) {
        return DSL.condition("{0} <@ {1}", left, right);
    }

    /**
     * TODO: write docs
     * ?  	text 	Does the string exist as a top-level key within the JSON value? 	'{"a":1, "b":2}'::jsonb ? 'b'
     */
    public static Condition hasKey(Field<Jsonb> f, String key) {
        return DSL.condition("{0} ?? {1}", f, key);
    }

    /**
     * TODO: write docs
     * ?| 	text[] 	Do any of these array strings exist as top-level keys? 	'{"a":1, "b":2, "c":3}'::jsonb ?| array['b', 'c']
     */
    public static Condition doesAnyKeyExist(Field<Jsonb> f, String... keys) {
        return DSL.condition("{0} ??| {1}", f, DSL.array(keys));
    }

    /**
     * TODO: write docs
     * ?| 	text[] 	Do any of these array strings exist as top-level keys? 	'{"a":1, "b":2, "c":3}'::jsonb ?| array['b', 'c']
     */
    public static Condition doesAnyKeyExist(Field<Jsonb> f, Collection<String> keys) {
        return doesAnyKeyExist(f, keys.toArray(new String[0]));
    }

    /**
     * TODO: write docs
     * ?& 	text[] 	Do all of these array strings exist as top-level keys? 	'["a", "b"]'::jsonb ?& array['a', 'b']
     */
    public static Condition doAllKeysExist(Field<Jsonb> f, String... keys) {
        return DSL.condition("{0} ??& {1}", f, keys);
    }

    /**
     * TODO: write docs
     * ?& 	text[] 	Do all of these array strings exist as top-level keys? 	'["a", "b"]'::jsonb ?& array['a', 'b']
     */
    public static Condition doAllKeysExist(Field<Jsonb> f, Collection<String> keys) {
        return doesAnyKeyExist(f, keys.toArray(new String[0]));
    }

    /**
     * TODO: write docs
     * || 	jsonb 	Concatenate two jsonb values into a new jsonb value 	'["a", "b"]'::jsonb || '["c", "d"]'::jsonb
     */
    public static Field<Jsonb> concat(Field<Jsonb> field1, Field<Jsonb> field2) {
        return DSL.field("{0} || {1}", Jsonb.class, field1, field2);
    }

    /**
     * TODO: write docs
     * - 	text 	Delete key/value pair or string element from left operand. Key/value pairs are matched based on their key value. 	'{"a": "b"}'::jsonb - 'a'
     */
    public static Field<Jsonb> delete(Field<Jsonb> f, String keyOrElement) {
        return DSL.field("{0} - {1}", Jsonb.class, f, keyOrElement);
    }

    /**
     * TODO: write docs
     * - 	text[] 	Delete multiple key/value pairs or string elements from left operand. Key/value pairs are matched based on their key value. 	'{"a": "b", "c": "d"}'::jsonb - '{a,c}'::text[]
     */
    public static Field<Jsonb> delete(Field<Jsonb> f, String... keysOrElements) {
        return DSL.field("{0} - {1}", Jsonb.class, f, DSL.array(keysOrElements));
    }

    /**
     * TODO: write docs
     * - 	integer 	Delete the array element with specified index (Negative integers count from the end). Throws an error if top level container is not an array. 	'["a", "b"]'::jsonb - 1
     */
    public static Field<Jsonb> deleteElement(Field<Jsonb> f, int index) {
        return DSL.field("{0} - {1}", Jsonb.class, f, index);
    }

    /**
     * TODO: write docs
     * #- 	text[] 	Delete the field or element with specified path (for JSON arrays, negative integers count from the end) 	'["a", {"b":1}]'::jsonb #- '{1,b}'
     */
    public static Field<Jsonb> deletePath(Field<Jsonb> f, String... path) {
        return DSL.field("{0} #- {1}", Jsonb.class, f, DSL.array(path));
    }
}
