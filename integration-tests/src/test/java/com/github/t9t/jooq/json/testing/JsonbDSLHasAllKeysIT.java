package com.github.t9t.jooq.json.testing;

import org.jooq.impl.DSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.hasAllKeys;

public class JsonbDSLHasAllKeysIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("hasAllKeys", Arrays.asList(
                btest("one").selecting(DSL.field(hasAllKeys(jsonb, "str"))).expect(true),
                btest("multiple").selecting(DSL.field(hasAllKeys(jsonb, "str", "obj", "num"))).expect(true),
                btest("collection").selecting(DSL.field(hasAllKeys(jsonb, Arrays.asList("str", "obj", "num")))).expect(true),
                btest("doesn't").selecting(DSL.field(hasAllKeys(jsonb, "nope"))).expect(false),
                btest("someDon't").selecting(DSL.field(hasAllKeys(jsonb, "str", "nope"))).expect(false),
                btest("array").forArray().selecting(DSL.field(hasAllKeys(jsonb, "jsonb array"))).expect(true)
        ));
    }
}
