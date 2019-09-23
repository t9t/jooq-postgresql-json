# jOOQ PostgreSQL JSON binding
Provides a [jOOQ custom type binding](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-type-bindings/)
for the jOOQ code generator  to interpret PostgreSQL `json` and `jsonb` fields, either simply as `String` or as
specific `Json` and `Jsonb` types, allowing use of `JsonDSL` and `JsonbDSL` classes to use
[PostgreSQL JSON functions](https://www.postgresql.org/docs/11/functions-json.html) with jOOQ code.

Requires at least Java 8.

- [Include as a Maven dependency](#include-as-a-maven-dependency)
- [Configure the jOOQ code generator](#configure-the-jooq-code-generator)
- [Using in code](#using-in-code)
- [PostgreSQL json operator support](#postgresql-json-operator-support)
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
  <version>0.3.1</version>
</dependency>
```

## Configure the jOOQ code generator
Then, configure the code generator like this:

```xml
<database>
    <forcedTypes>
        <!-- When you just want json/jsonb fields mapped as String: -->
        <forcedType>
            <userType>java.lang.String</userType>
            <binding>com.github.t9t.jooq.json.JsonStringBinding</binding>
            <types>json</types>
        </forcedType>
        <forcedType>
            <userType>java.lang.String</userType>
            <binding>com.github.t9t.jooq.json.JsonbStringBinding</binding>
            <types>jsonb</types>
        </forcedType>
        
        <!-- When you want to map json/jsonb fields to specialized Json/Jsonb types: -->
        <forcedType>
            <userType>com.github.t9t.jooq.json.Json</userType>
            <binding>com.github.t9t.jooq.json.JsonBinding</binding>
            <types>json</types>
        </forcedType>
        <forcedType>
            <userType>com.github.t9t.jooq.json.Jsonb</userType>
            <binding>com.github.t9t.jooq.json.JsonbBinding</binding>
            <types>jsonb</types>
        </forcedType>
    </forcedTypes>
</database>
```

The first example above will use a Java type of `String` for all `json` and `jsonb` fields, and will use the
`JsonStringBinding` and `JsonbStringBinding` respectively to convert the fields.

The second example instead binds `json` fields to `Json` using `JsonBinding`, and `jsonb` fields to `Jsonb` using
`JsonbBinding`. This enables the use of `JsonDSL` and `JsonbDSL`.

If you want to only configure it for certain specific fields, add an `<expression>` element to the `<forcedType>`
element, for example:

```xml
<expression>.*json.*</expression>
```

Or:

```xml
<expression>site\.users\.profile</expression>
```

The `expression` element contains a regular expression. The first example matches any field which contains the word
"json" in its name while the second matches exactly the `profile` field in the `users` table in the `site` schema. See
the [jOOQ documentation for more information](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-types/).


## Using in code
By default the jOOQ code generator will generate `Object` as a return value for json fields and return the raw
PostgreSQL driver's JDBC type, `PGobject`. It's up to the user to interpret this somehow. Using this binding, jOOQ
will instead just return the json data as `String`, `Json`, or `Jsonb`.

An example before using this binding when doing a `select` query:
```java
public String fetchData(long id) {
    Record1<Object> r = dsl.select(MY_TABLE.DATA)
        .from(MY_TABLE)
        .where(MY_TABLE.ID.eq(id))
        .fetchOne();
    Object o = r.value1();
    String s = ((PGobject) o).getValue();
    return s;
}
```

When using this binding, instead it can be simplified to:
```java
public String fetchData(long id) {
    Record1<String> r = dsl.select(MY_TABLE.DATA)
        .from(MY_TABLE)
        .where(MY_TABLE.ID.eq(id))
        .fetchOne();
    String s = r.value1();
    return s;
}
```

Or, in the case of `Json` and `Jsonb`:
```java
public String fetchData(long id) {
    Record1<Jsonb> r = dsl.select(MY_TABLE.DATA)
        .from(MY_TABLE)
        .where(MY_TABLE.ID.eq(id))
        .fetchOne();
    Jsonb j = r.value1();
    return j == null ? null : j.getValue();
}
```

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

## Example
See the [integration-tests](integration-tests) module's [pom.xml](integration-tests/pom.xml) for an example of the
`jooq-codegen` Maven plugin with the custom json bindings.

For some code usage examples, refer to:
- `String`: [integration-tests/.../JsonStringBindingIT](integration-tests/src/test/java/com/github/t9t/jooq/json/JsonStringBindingIT.java)
- `Json`/`Jsonb`: [integration-tests/.../JsonBindingIT](integration-tests/src/test/java/com/github/t9t/jooq/json/JsonBindingIT.java)


## References
- [jOOQ.org](https://www.jooq.org/)
- [jOOQ: Custom data types](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-types/)
- [jOOQ: Custom data type binding](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-type-bindings/)
- [PostgreSQL JSON data types](https://www.postgresql.org/docs/current/datatype-json.html)
