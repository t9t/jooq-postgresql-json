# jOOQ PostgreSQL JSON binding
Provides a [jOOQ custom type binding](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-type-bindings/)
to interpret PostgreSQL `json` and `jsonb` fields as `String` in Java when using the jOOQ code generator.

Requires at least Java 8.

## Include as a Maven dependency
First, add the following Maven dependency:

```xml
<dependency>
  <groupId>com.github.t9t.jooq</groupId>
  <artifactId>jooq-postgresql-json</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Configure the jOOQ code generator
Then, configure the code generator like this:

```xml
<database>
    <forcedTypes>
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
    </forcedTypes>
</database>
```

The above will use a Java type of `String` for all `json` and `jsonb` fields, and will use the `JsonStringBinding`
and `JsonbStringBinding` respectively to convert the fields.

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
will instead just return the json data as `String`.

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

When using this binding, instead it will be simplified to:
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

## Example
See the [integration-tests](integration-tests) module's [pom.xml](integration-tests/pom.xml) for an example of the
`jooq-codegen` Maven plugin with the custom json binding.

See [JsonStringBindingIT](integration-tests/src/test/java/com/github/t9t/jooq/json/JsonStringBindingIT.java) for some
usage examples.


## References
- [jOOQ.org](https://www.jooq.org/)
- [jOOQ: Custom data types](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-types/)
- [jOOQ: Custom data type binding](https://www.jooq.org/doc/3.11/manual/code-generation/custom-data-type-bindings/)
- [PostgreSQL JSON data types](https://www.postgresql.org/docs/current/datatype-json.html)
