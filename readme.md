# jOOQ PostgreSQL JSON binding
Provides jOOQ support for [PostgreSQL JSON functions](https://www.postgresql.org/docs/11/functions-json.html) through
the use of the `JsonDSL` and `JsonbDSL` classes (for `json` and `jsonb` fields respectively).

Requires at least Java 8. **Note:** this project is currently only compatible with jOOQ version 3.12. For jOOQ version
3.11 you can temporarily fall back to version `0.4.0`.

- [Include as a Maven dependency](#include-as-a-maven-dependency)
- [Using in code](#using-in-code)
- [PostgreSQL json operator support](#postgresql-json-operator-support)
- [Available PostgreSQL json processing functions](#available-postgresql-json-processing-functions)
- [Example](#example)
- [References](#references)
- [![Javadocs](https://javadoc.io/badge/com.github.t9t.jooq/jooq-postgresql-json.svg)](https://javadoc.io/doc/com.github.t9t.jooq/jooq-postgresql-json)

---


## Include as a Maven dependency
First, add the following Maven dependency:

```xml
<dependency>
  <groupId>com.github.t9t.jooq</groupId>
  <artifactId>jooq-postgresql-json</artifactId>
  <version>1.0.0</version>
</dependency>
```

**Warning**: upgrading from `0.4.0` to `1.0.0` **breaking change**. jOOQ has been updated from 3.11 to 3.12, meaning
the custom classes `Json` and `Jsonb` (and their bindings) have been removed, and replaced by the jOOQ `JSON` and
`JSONB` classes respectively (which are automatically bound to `json` and `jsonb` PostgreSQL fields).

If you need jOOQ 3.11 support, please continue to use version `0.4.0`.


## PostgreSQL json operator support
Reference: https://www.postgresql.org/docs/11/functions-json.html

Operators available for both `json` (through `JsonDSL`) and `jsonb` (through `JsonbDSL`):

| Op | Operand | Description | Method |
| --- | --- | --- | --- |
| `->` | `int` | Get array element | `arrayElement()` |
| `->` | `text` | Get object field | `fieldByKey()` |
| `->>` | `int` | Get array element as text | `arrayElementText()` |
| `->>` | `text` | Get object field as text | `fieldByKeyText()` |
| `#>` | `text[]` | Get object at path | `objectAtPath()` |
| `#>>` | `text[]` | Get object at path as text | `objectAtPathText()` |

Operators available only for `jsonb` (through `JsonbDSL`):


| Op | Operand | Description | Method |
| --- | --- | --- | --- |
| `@>` | `jsonb` | Does contain value? | `contains()` |
| `<@` | `jsonb` | Are entries contained? | `containedIn()` |
| `?` | `text` | Does the key exist? | `hasKey()` |
| <code>?&#124;</code> | `text[]` | Does any key exist? | `hasAnyKey()` |
| `?&` | `text[]` | Do all keys exist? | `hasAllKeys()` |
| <code>&#124;&#124;</code> | `jsonb` | Concatenate values | `concat()` |
| `-` | `text` | Delete key or element | `delete()` |
| `-` | `text[]` | Delete multiple keys or elements | `delete()` |
| `-` | `int` | Delete array element | `deleteElement()` |
| `#-` | `text[]` | Delete field for path | `deletePath()` |


## Available PostgreSQL json processing functions
Reference: https://www.postgresql.org/docs/11/functions-json.html#FUNCTIONS-JSON-PROCESSING-TABLE

Processing functions available for both `json` (through `JsonDSL`) and `jsonb` (through `JsonbDSL`):

| Function | Return type | Description | Method |
| --- | --- | --- | --- |
| `json(b)_array_length` | `int` | Get length of JSON array | `arrayLength()` |
| `json(b)_extract_path` | `json`/`jsonb` | Extract object at path (same as `#>`) | `extractPath()` |
| `json(b)_extract_path_text` | `text` | Extract object at path as text (same as `#>>`) | `extractPathText()` |
| `json(b)_typeof` | `text` | Get the type of a JSON field | `typeOf()` |
| `json(b)_strip_nulls` | `json`/`jsonb` | Remove object fields with `null` values | `stripNulls()` |

Functions only available for `json` (through `JsonbDSL`):

| Function | Return type | Description | Method |
| --- | --- | --- | --- |
| `jsonb_pretty` | `text` | Pretty format JSON field | `pretty()` |



## Example
See the [integration-tests](integration-tests) module's [pom.xml](integration-tests/pom.xml) for an example of the
`jooq-codegen` Maven plugin.

For some code usage examples, refer to
[integration-tests/.../JsonBindingIT](integration-tests/src/test/java/com/github/t9t/jooq/json/JsonBindingIT.java).


## References
- [jOOQ.org](https://www.jooq.org/)
- [PostgreSQL JSON data types](https://www.postgresql.org/docs/current/datatype-json.html)
