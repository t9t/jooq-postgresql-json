package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElement", (type, f) -> Arrays.asList(
                test("getFirstObject").forArray().selecting(JsonDSL.arrayElement(f, 0)).expectJson("{\"d\": 4408}"),
                test("getString").forArray().selecting(JsonDSL.arrayElement(f, 3)).expectJson("\"" + type + " array\""),
                test("outOfBounds").forArray().selecting(JsonDSL.arrayElement(f, 100)).expectNull(),
                test("negativeIndex").forArray().selecting(JsonDSL.arrayElement(f, -2)).expectJson("true"),
                test("onObject").forArray().selecting(JsonDSL.arrayElement(f, 100)).expectNull()
        ));
    }

}
