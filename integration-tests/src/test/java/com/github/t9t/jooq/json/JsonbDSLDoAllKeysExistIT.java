package com.github.t9t.jooq.json;

import org.jooq.impl.DSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.doAllKeysExist;

public class JsonbDSLDoAllKeysExistIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("doAllKeysExist", Arrays.asList(
                btest("one").selecting(DSL.field(doAllKeysExist(jsonb, "str"))).expect(true),
                btest("multiple").selecting(DSL.field(doAllKeysExist(jsonb, "str", "obj", "num"))).expect(true),
                btest("collection").selecting(DSL.field(doAllKeysExist(jsonb, Arrays.asList("str", "obj", "num")))).expect(true),
                btest("doesn't").selecting(DSL.field(doAllKeysExist(jsonb, "nope"))).expect(false),
                btest("someDon't").selecting(DSL.field(doAllKeysExist(jsonb, "str", "nope"))).expect(false),
                btest("array").forArray().selecting(DSL.field(doAllKeysExist(jsonb, "jsonb array"))).expect(true)
        ));
    }
}
