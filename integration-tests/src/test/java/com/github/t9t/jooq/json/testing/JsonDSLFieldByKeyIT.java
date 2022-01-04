package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.fieldByKey;

public class JsonDSLFieldByKeyIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("fieldByKey", Arrays.asList(
                test("string").selecting(fieldByKey(json, "str")).expectJson("\"Hello, json world!\""),
                test("twoLevels").selecting(fieldByKey(fieldByKey(json, "obj"), "i")).expectJson("5521"),
                test("nullField").selecting(fieldByKey(json, "n")).expectJson("null"),
                test("notExistingField").selecting(fieldByKey(json, "notExisting")).expectNull(),

                btest("string").selecting(JsonbDSL.fieldByKey(jsonb, "str")).expectJsonb("\"Hello, jsonb world!\""),
                btest("twoLevels").selecting(JsonbDSL.fieldByKey(JsonbDSL.fieldByKey(jsonb, "obj"), "i")).expectJsonb("5521"),
                btest("nullField").selecting(JsonbDSL.fieldByKey(jsonb, "n")).expectJsonb("null"),
                btest("notExistingField").selecting(JsonbDSL.fieldByKey(jsonb, "notExisting")).expectNull()
        ));
    }
}
