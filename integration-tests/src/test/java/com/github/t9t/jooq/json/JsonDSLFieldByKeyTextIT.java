package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.fieldByKeyText;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKeyText", (type, f) -> Arrays.asList(
                test("string").selecting(fieldByKeyText(f, "str")).expectString("Hello, " + type + " world!"),
                test("twoLevels").selecting(fieldByKeyText(JsonDSL.fieldByKey(f, "obj"), "i")).expectString("5521"),
                test("nullField").selecting(fieldByKeyText(f, "n")).expectNull(),
                test("notExistingField").selecting(fieldByKeyText(f, "notExisting")).expectNull()
        ));
    }

}
