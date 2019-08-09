package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKeyText", (type, f) -> Arrays.asList(
                test("string", "Hello, " + type + " world!", JsonDSL.fieldByKeyText(f, "str")),
                test("twoLevels", "5521", JsonDSL.fieldByKeyText(JsonDSL.fieldByKey(f, "obj"), "i")),
                test("nullField", null, JsonDSL.fieldByKeyText(f, "n")),
                test("notExistingField", null, JsonDSL.fieldByKeyText(f, "notExisting"))
        ));
    }

}
