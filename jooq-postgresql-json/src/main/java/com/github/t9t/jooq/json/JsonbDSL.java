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
     * <p>Does the {@code left} JSON value contain the {@code right} JSON path/value entries at the top level? Uses the
     * {@code @>} operator.</p>
     *
     * <p>Example: <code>'{"a":1, "b":2}'::jsonb @> '{"b":2}'::jsonb</code></p>
     *
     * @param left  The JSON {@code Field} that should contain {@code right}
     * @param right The JSON {@code Field} that should be contained in {@code left}
     * @return A {@code Condition} representing whether {@code left} is contained in {@code right}
     */
    public static Condition contains(Field<Jsonb> left, Field<Jsonb> right) {
        return DSL.condition("{0} @> {1}", left, right);
    }

    /**
     * <p>Are the {@code left} JSON path/value entries contained at the top level within the {@code right} JSON value?
     * Uses the {@code <@} operator.</p>
     *
     * <p>Example: <code>'{"b":2}'::jsonb <@ '{"a":1, "b":2}'::jsonb</code></p>
     *
     * @param left  The JSON {@code Field} that should be contained in {@code right}
     * @param right The JSON {@code Field} that should contain {@code left}
     * @return A {@code Condition} representing whether {@code right} is contained in {@code left}
     */
    public static Condition containedIn(Field<Jsonb> left, Field<Jsonb> right) {
        return DSL.condition("{0} <@ {1}", left, right);
    }

    /**
     * <p>Does the <i>string</i> exist as a top-level key within the JSON value? Uses the {@code ?} operator.</p>
     *
     * <p>Example: <code>'{"a":1, "b":2}'::jsonb ? 'b'</code></p>
     *
     * @param f   The JSON {@code Field} that should contain the {@code key}
     * @param key The key that should exist at the top level in the JSON
     * @return A {@code Condition} representing whether the key is contained in the JSON value
     */
    public static Condition hasKey(Field<Jsonb> f, String key) {
        return DSL.condition("{0} ?? {1}", f, key);
    }

    /**
     * <p>Do any of these array <i>strings</i> exist as top-level keys? Uses the {@code ?|} operator.</p>
     *
     * <p>Example: <code>'{"a":1, "b":2, "c":3}'::jsonb ?| array['b', 'c']</code></p>
     *
     * @param f    The JSON {@code Field} that should contain any of the {@code keys}
     * @param keys List of keys that may exist in the JSON value
     * @return A {@code Condition} representing whether any of the {@code keys} exist
     * @see #doesAnyKeyExist(Field, Collection)
     */
    public static Condition doesAnyKeyExist(Field<Jsonb> f, String... keys) {
        return DSL.condition("{0} ??| {1}", f, DSL.array(keys));
    }

    /**
     * <p>Do any of these array <i>strings</i> exist as top-level keys? Uses the {@code ?|} operator.</p>
     *
     * <p>Example: <code>'{"a":1, "b":2, "c":3}'::jsonb ?| array['b', 'c']</code></p>
     *
     * @param f    The JSON {@code Field} that should contain any of the {@code keys}
     * @param keys List of keys that may exist in the JSON value
     * @return A {@code Condition} representing whether any of the {@code keys} exist
     * @see #doesAnyKeyExist(Field, String...)
     */
    public static Condition doesAnyKeyExist(Field<Jsonb> f, Collection<String> keys) {
        return doesAnyKeyExist(f, keys.toArray(new String[0]));
    }

    /**
     * <p>Do all of these array <i>strings</i> exist as top-level keys? Uses the {@code ?&} operator.</p>
     *
     * <p>Example: <code>'["a", "b"]'::jsonb ?& array['a', 'b']</code></p>
     *
     * @param f    The JSON {@code Field} that should contain all of the {@code keys}
     * @param keys List of keys that all should exist in the JSON value
     * @return A {@code Condition} representing whether all of the {@code keys} exist
     * @see #doAllKeysExist(Field, Collection)
     */
    public static Condition doAllKeysExist(Field<Jsonb> f, String... keys) {
        return DSL.condition("{0} ??& {1}", f, keys);
    }

    /**
     * <p>Do all of these array <i>strings</i> exist as top-level keys? Uses the {@code ?&} operator.</p>
     *
     * <p>Example: <code>'["a", "b"]'::jsonb ?& array['a', 'b']</code></p>
     *
     * @param f    The JSON {@code Field} that should contain all of the {@code keys}
     * @param keys List of keys that all should exist in the JSON value
     * @return A {@code Condition} representing whether all of the {@code keys} exist
     * @see #doAllKeysExist(Field, String...)
     */
    public static Condition doAllKeysExist(Field<Jsonb> f, Collection<String> keys) {
        return doesAnyKeyExist(f, keys.toArray(new String[0]));
    }

    /**
     * <p>Concatenate two {@code jsonb} values into a new {@code jsonb} value using the {@code ||} operator.</p>
     *
     * <p>Example: <code>'["a", "b"]'::jsonb || '["c", "d"]'::jsonb</code></p>
     * <p>Example result: <code>["a", "b", "c", "d"]</code></p>
     *
     * @param field1 Field to concatenate {@code field2} to
     * @param field2 Field to concatenate to {@code field1}
     * @return A {@code Field} representing a concatenation of the two JSON fields
     */
    public static Field<Jsonb> concat(Field<Jsonb> field1, Field<Jsonb> field2) {
        return DSL.field("{0} || {1}", Jsonb.class, field1, field2);
    }

    /**
     * <p>Delete key/value pair or <i>string</i> element from left operand. Key/value pairs are matched based on their
     * key value. Uses the {@code -} operator.</p>
     *
     * <p>Example: <code>'{"a": "b", "c": "d"}'::jsonb - 'a'</code></p>
     * <p>Example result: <code>{"c": "d"}</code></p>
     *
     * @param f            JSON {@code Field} to delete the key or element from
     * @param keyOrElement The key name or element value to delete from the JSON field
     * @return A {@code Field} representing the original field with the key or element deleted
     */
    public static Field<Jsonb> delete(Field<Jsonb> f, String keyOrElement) {
        return DSL.field("{0} - {1}", Jsonb.class, f, keyOrElement);
    }

    /**
     * <p>Delete multiple key/value pairs or <i>string</i> elements from left operand. Key/value pairs are matched
     * based on their key value. Uses the {@code -} operator.</p>
     *
     * <p>Example: <code>'{"a": "b", "c": "d", "e": "f"}'::jsonb - '{a,c}'::text[]</code></p>
     * <p>Example result: <code>{"e", "f"}</code></p>
     *
     * @param f              JSON {@code Field} to delete the keys or elements from
     * @param keysOrElements The key names or element values to delete from the JSON field
     * @return A {@code Field} representing the original field with the keys or elements deleted
     */
    public static Field<Jsonb> delete(Field<Jsonb> f, String... keysOrElements) {
        return DSL.field("{0} - {1}", Jsonb.class, f, DSL.array(keysOrElements));
    }

    /**
     * <p>Delete the array element with specified index (Negative integers count from the end). Throws an error if top
     * level container is not an array. Uses the {@code -} operator.</p>
     *
     * <p>Example: <code>'["a", "b"]'::jsonb - 1</code></p>
     * <p>Example result: <code>["a"]</code></p>
     *
     * @param f JSON {@code Field} containing an array to delete the element from
     * @param index Array index to delete; negative values count from the end of the array
     * @return A {@code Field} representing the field with the array element removed
     */
    public static Field<Jsonb> deleteElement(Field<Jsonb> f, int index) {
        return DSL.field("{0} - {1}", Jsonb.class, f, index);
    }

    /**
     * <p>Delete the field or element with specified path (for JSON arrays, negative integers count from the end). Uses
     * the {@code #-} operator.</p>
     *
     * <p>Example: <code>'["a", {"b":1,"c":2}]'::jsonb #- '{1,b}'</code></p>
     * <p>Example result: <code>["a", {"c": 2}]</code></p>
     *
     * @param f JSON {@code Field} to delete the selected path from
     * @param path Path to the JSON element to remove
     * @return A {@code Field} representing the field with the chosen path removed
     */
    public static Field<Jsonb> deletePath(Field<Jsonb> f, String... path) {
        return DSL.field("{0} #- {1}", Jsonb.class, f, DSL.array(path));
    }
}
