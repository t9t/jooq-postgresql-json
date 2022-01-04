package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayElement;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("arrayElement", Arrays.asList(
                test("getFirstObject").forArray().selecting(arrayElement(json, 0)).expectJson("{\"d\": 4408}"),
                test("getString").forArray().selecting(arrayElement(json, 3)).expectJson("\"json array\""),
                test("outOfBounds").forArray().selecting(arrayElement(json, 100)).expectNull(),
                test("negativeIndex").forArray().selecting(arrayElement(json, -2)).expectJson("true"),
                test("onObject").forArray().selecting(arrayElement(json, 100)).expectNull(),

                btest("getFirstObject").forArray().selecting(JsonbDSL.arrayElement(jsonb, 0)).expectJsonb("{\"d\": 4408}"),
                btest("getString").forArray().selecting(JsonbDSL.arrayElement(jsonb, 3)).expectJsonb("\"jsonb array\""),
                btest("outOfBounds").forArray().selecting(JsonbDSL.arrayElement(jsonb, 100)).expectNull(),
                btest("negativeIndex").forArray().selecting(JsonbDSL.arrayElement(jsonb, -2)).expectJsonb("true"),
                btest("onObject").forArray().selecting(JsonbDSL.arrayElement(jsonb, 100)).expectNull()
        ));
    }

}
