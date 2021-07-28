# jOOQ PostgreSQL JSON support
Provides jOOQ support for [PostgreSQL JSON functions and operators](https://www.postgresql.org/docs/11/functions-json.html)
for `json` and `jsonb` fields.

**Requires at least Java 11.**

- [Include as a Maven dependency](#include-as-a-maven-dependency)
- [Usage](#usage)
  - [Kotlin extension functions](#kotlin)
- [PostgreSQL json operator support](#postgresql-json-operator-support)
- [Available PostgreSQL json processing functions](#available-postgresql-json-processing-functions)
- [References](#references)
- [Contributors](#contributors)
- [![Javadocs](https://javadoc.io/badge/com.github.t9t.jooq/jooq-postgresql-json.svg)](https://javadoc.io/doc/com.github.t9t.jooq/jooq-postgresql-json)

---


## Include as a Maven dependency
First, add the following Maven dependency:

```xml
<dependency>
  <groupId>com.github.t9t.jooq</groupId>
  <artifactId>jooq-postgresql-json</artifactId>
  <version>3.0.0</version>
</dependency>
```

### Version matrix

This shows only which jOOQ version is used in this library. Minor version differences should still be compatible and
newer major versions of jOOQ might still work with older versions of this library if nothing changed much in the jOOQ
JSON APIs.

See [the changelog](changelog.md) for more information about what is included in the various releases and the reason for
the breaking changes.

| Library version | jOOQ version | Note |
| --- | --- | --- |
| 3.0.0 | 3.14.12 | Breaking change, upgraded from Java 8 to Java 11. Java 8 no longer supported. |
| 2.0.3 | 3.14.12 |
| 2.0.2 | 3.14.9 |
| 2.0.1 | 3.14.6 |
| 2.0.0 | 3.14.4 | Breaking change because of Kotlin extension function receiver type change. |
| 1.3.0 | 3.14.4 |
| 1.2.2 | 3.14.4 |
| 1.2.1 | 3.14.3 |
| 1.2.0 | 3.14.0 |
| 1.1.2 | 3.13.4 |
| 1.1.1 | 3.13.2 |
| 1.1.0 | 3.13.1 |
| 1.0.0 | 3.12.3 | Breaking change becauuse of jOOQ upgrade from `3.11` to `3.12`. |
| 0.4.0 | 3.11.11 |

## Usage

Use
the [`JsonDSL`](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/JsonDSL.html)
and [`JsonbDSL`](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/JsonbDSL.html)
classes to access the JSON functions and operators.

For example, to extract a JSON nested property value as text from a `json` field:

```java
/* Sample JSON:
{
  "data": {
    "productCode": "Z-5521"
  }
}
*/
String productCode = dsl.select(JsonDSL.extractPathText(MY_TABLE.DATA_FIELD, "data", "productCode"))
    .from(MY_TABLE).fetchOneInto(String.class);
``` 

Or for example using the `@>` operator to update a row of which a `jsonb` field contains a certain id:

```java
/* Sample JSON:
{
  "id": "1337",
  "name": "The Hitchhiker's Guide to the Galaxy"
}
*/
dsl.update(MY_TABLE)
    .set(MY_TABLE.RATING, 100)
    .where(JsonbDSL.contains(MY_TABLE.DATA_FIELD, JsonbDSL.field("{\"id\": \"1337\"}")))
    .execute()
``` 

- [`JsonDSL` Javadoc](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/JsonDSL.html)
- [`JsonbDSL` Javadoc](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/JsonbDSL.html)

### Kotlin

Kotlin extension functions are available for `Field<JSON?>` and `Field<JSONB?>`. That means that instead of something
like `JsonDSL.extractPathText(MY_TABLE.DATA_FIELD, "data", "productCode")` you can instead write:
`MY_TABLE.DATA_FIELD.extractPathText("data", "productCode")`.

The extension functions are available in the following packages:

- [`com.github.t9t.jooq.json.json`](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/json)
- [`com.github.t9t.jooq.json.jsonb`](https://javadoc.io/static/com.github.t9t.jooq/jooq-postgresql-json/3.0.0/com/github/t9t/jooq/json/jsonb)

The names of extension functions match the names of the methods on `JsonDSL` and `JsonbDSL`, except for `concat` and
`contains`, which are called `concatJson` and `containsJson` respectively to prevent clashes with existing methods
of `Field`.

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


## References
- [jOOQ.org](https://www.jooq.org/)
- [PostgreSQL JSON data types](https://www.postgresql.org/docs/current/datatype-json.html)


## Contributors
- [@davinkevin](https://github.com/davinkevin) was kind enough to implement Kotlin extension functions ([#11](https://github.com/t9t/jooq-postgresql-json/issues/11) and [#12](https://github.com/t9t/jooq-postgresql-json/pull/12))!
