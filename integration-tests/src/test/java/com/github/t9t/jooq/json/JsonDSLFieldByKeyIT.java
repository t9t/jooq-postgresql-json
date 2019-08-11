package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.fieldByKey;

public class JsonDSLFieldByKeyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKey", Arrays.asList(
                test("string").selecting(fieldByKey(json, "str")).expectJson("\"Hello, json world!\""),
                test("twoLevels").selecting(fieldByKey(fieldByKey(json, "obj"), "i")).expectJson("5521"),
                test("nullField").selecting(fieldByKey(json, "n")).expectJson("null"),
                test("notExistingField").selecting(fieldByKey(json, "notExisting")).expectNull()
        ));
    }
}
