package com.github.t9t.jooq.json;

import org.jooq.impl.DSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.doesAnyKeyExist;

public class JsonbDSLDoesAnyKeyExistIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("doesAnyKeyExist", Arrays.asList(
                btest("one").selecting(DSL.field(doesAnyKeyExist(jsonb, "str"))).expect(true),
                btest("multiple").selecting(DSL.field(doesAnyKeyExist(jsonb, "str", "obj", "num"))).expect(true),
                btest("collection").selecting(DSL.field(doesAnyKeyExist(jsonb, Arrays.asList("str", "obj", "num")))).expect(true),
                btest("doesn't").selecting(DSL.field(doesAnyKeyExist(jsonb, "nope"))).expect(false),
                btest("someDon't").selecting(DSL.field(doesAnyKeyExist(jsonb, "str", "nope"))).expect(true),
                btest("array").forArray().selecting(DSL.field(doesAnyKeyExist(jsonb, "jsonb array", "nope"))).expect(true)
        ));
    }
}
