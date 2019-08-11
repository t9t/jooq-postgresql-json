package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.fieldByKeyText;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKeyText", Arrays.asList(
                test("string").selecting(fieldByKeyText(json, "str")).expectString("Hello, json world!"),
                test("twoLevels").selecting(fieldByKeyText(JsonDSL.fieldByKey(json, "obj"), "i")).expectString("5521"),
                test("nullField").selecting(fieldByKeyText(json, "n")).expectNull(),
                test("notExistingField").selecting(fieldByKeyText(json, "notExisting")).expectNull(),

                btest("string").selecting(JsonbDSL.fieldByKeyText(jsonb, "str")).expectString("Hello, jsonb world!"),
                btest("twoLevels").selecting(JsonbDSL.fieldByKeyText(JsonbDSL.fieldByKey(jsonb, "obj"), "i")).expectString("5521"),
                btest("nullField").selecting(JsonbDSL.fieldByKeyText(jsonb, "n")).expectNull(),
                btest("notExistingField").selecting(JsonbDSL.fieldByKeyText(jsonb, "notExisting")).expectNull()
        ));
    }

}
