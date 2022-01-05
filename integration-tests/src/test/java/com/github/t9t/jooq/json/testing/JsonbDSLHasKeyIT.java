package com.github.t9t.jooq.json.testing;

import org.jooq.impl.DSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.hasKey;

public class JsonbDSLHasKeyIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("hasKey", Arrays.asList(
                btest("does").selecting(DSL.field(hasKey(jsonb, "str"))).expect(true),
                btest("array").forArray().selecting(DSL.field(hasKey(jsonb, "jsonb array"))).expect(true),
                btest("doesn't").selecting(DSL.field(hasKey(jsonb, "nope"))).expect(false)
        ));
    }
}
