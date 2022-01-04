package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonDSL;
import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.fieldByKeyText;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
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
