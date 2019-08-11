package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayElement;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElement", Arrays.asList(
                test("getFirstObject").forArray().selecting(arrayElement(json, 0)).expectJson("{\"d\": 4408}"),
                test("getString").forArray().selecting(arrayElement(json, 3)).expectJson("\"json array\""),
                test("outOfBounds").forArray().selecting(arrayElement(json, 100)).expectNull(),
                test("negativeIndex").forArray().selecting(arrayElement(json, -2)).expectJson("true"),
                test("onObject").forArray().selecting(arrayElement(json, 100)).expectNull()
        ));
    }

}
