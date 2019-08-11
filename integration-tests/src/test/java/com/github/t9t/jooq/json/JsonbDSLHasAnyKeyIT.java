package com.github.t9t.jooq.json;

import org.jooq.impl.DSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.hasAnyKey;

public class JsonbDSLHasAnyKeyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("hasAnyKey", Arrays.asList(
                btest("one").selecting(DSL.field(hasAnyKey(jsonb, "str"))).expect(true),
                btest("multiple").selecting(DSL.field(hasAnyKey(jsonb, "str", "obj", "num"))).expect(true),
                btest("collection").selecting(DSL.field(hasAnyKey(jsonb, Arrays.asList("str", "obj", "num")))).expect(true),
                btest("doesn't").selecting(DSL.field(hasAnyKey(jsonb, "nope"))).expect(false),
                btest("someDon't").selecting(DSL.field(hasAnyKey(jsonb, "str", "nope"))).expect(true),
                btest("array").forArray().selecting(DSL.field(hasAnyKey(jsonb, "jsonb array", "nope"))).expect(true)
        ));
    }
}
