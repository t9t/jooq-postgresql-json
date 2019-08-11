package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayElementText;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElementText", Arrays.asList(
                test("getFirstObject").forArray().selecting(arrayElementText(json, 0)).expectString("{\"d\": 4408}"),
                test("getString").forArray().selecting(arrayElementText(json, 3)).expectString("json array"),
                test("outOfBounds").forArray().selecting(arrayElementText(json, 100)).expectString(null),
                test("negativeIndex").forArray().selecting(arrayElementText(json, -2)).expectString("true"),
                test("onObject").selecting(arrayElementText(json, 100)).expectNull()
        ));
    }

}
