package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKey", (type, f) -> Arrays.asList(
                test("string").selecting(JsonDSL.fieldByKey(f, "str")).expectJson("\"Hello, " + type + " world!\""),
                test("twoLevels").selecting(JsonDSL.fieldByKey(JsonDSL.fieldByKey(f, "obj"), "i")).expectJson("5521"),
                test("nullField").selecting(JsonDSL.fieldByKey(f, "n")).expectJson("null"),
                test("notExistingField").selecting(JsonDSL.fieldByKey(f, "notExisting")).expectNull()
        ));
    }
}
