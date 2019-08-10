package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayElementText;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElementText", (type, f) -> Arrays.asList(
                test("getFirstObject").forArray().selecting(arrayElementText(f, 0)).expectString("{\"d\": 4408}"),
                test("getString").forArray().selecting(arrayElementText(f, 3)).expectString(type + " array"),
                test("outOfBounds").forArray().selecting(arrayElementText(f, 100)).expectString(null),
                test("negativeIndex").forArray().selecting(arrayElementText(f, -2)).expectString("true"),
                test("onObject").selecting(arrayElementText(f, 100)).expectNull()
        ));
    }

}
