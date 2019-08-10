package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKeyText", (type, f) -> Arrays.asList(
                stringTest("string", "Hello, " + type + " world!", JsonDSL.fieldByKeyText(f, "str")),
                stringTest("twoLevels", "5521", JsonDSL.fieldByKeyText(JsonDSL.fieldByKey(f, "obj"), "i")),
                testNull("nullField", JsonDSL.fieldByKeyText(f, "n")),
                testNull("notExistingField", JsonDSL.fieldByKeyText(f, "notExisting"))
        ));
    }

}
