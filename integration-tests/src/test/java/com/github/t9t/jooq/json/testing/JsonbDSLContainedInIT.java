package com.github.t9t.jooq.json.testing;

import org.jooq.impl.DSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.containedIn;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLContainedInIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("contains", Arrays.asList(
                btest("simple").selecting(DSL.field(containedIn(field("{\"num\": 1337}"), jsonb))).expect(true),
                btest("complex").selecting(DSL.field(containedIn(field("{\"obj\": {\"i\": 5521, \"b\": true}}"), jsonb))).expect(true),
                btest("array").forArray().selecting(DSL.field(containedIn(field("[\"jsonb array\", 10]"), jsonb))).expect(true),
                btest("doesn't").selecting(DSL.field(containedIn(field("{\"num\": 1338}"), jsonb))).expect(false)
        ));
    }
}
