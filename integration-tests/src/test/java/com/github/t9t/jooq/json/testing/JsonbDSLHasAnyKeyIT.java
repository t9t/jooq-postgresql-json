package com.github.t9t.jooq.json.testing;

import org.jooq.impl.DSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.hasAnyKey;

public class JsonbDSLHasAnyKeyIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
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
