package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayElementText;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("arrayElementText", Arrays.asList(
                test("getFirstObject").forArray().selecting(arrayElementText(json, 0)).expectString("{\"d\": 4408}"),
                test("getString").forArray().selecting(arrayElementText(json, 3)).expectString("json array"),
                test("outOfBounds").forArray().selecting(arrayElementText(json, 100)).expectString(null),
                test("negativeIndex").forArray().selecting(arrayElementText(json, -2)).expectString("true"),
                test("onObject").selecting(arrayElementText(json, 100)).expectNull(),

                btest("getFirstObject").forArray().selecting(JsonbDSL.arrayElementText(jsonb, 0)).expectString("{\"d\": 4408}"),
                btest("getString").forArray().selecting(JsonbDSL.arrayElementText(jsonb, 3)).expectString("jsonb array"),
                btest("outOfBounds").forArray().selecting(JsonbDSL.arrayElementText(jsonb, 100)).expectString(null),
                btest("negativeIndex").forArray().selecting(JsonbDSL.arrayElementText(jsonb, -2)).expectString("true"),
                btest("onObject").selecting(JsonbDSL.arrayElementText(jsonb, 100)).expectNull()
        ));
    }

}
