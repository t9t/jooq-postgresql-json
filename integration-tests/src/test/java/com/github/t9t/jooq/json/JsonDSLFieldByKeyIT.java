package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("fieldByKey", (type, f) -> Arrays.asList(
                params("string", "\"Hello, " + type + " world!\"", JsonDSL.fieldByKey(f, "str")),
                params("twoLevels", "5521", JsonDSL.fieldByKey(JsonDSL.fieldByKey(f, "obj"), "i")),
                params("nullField", "null", JsonDSL.fieldByKey(f, "n")),
                params("notExistingField", null, JsonDSL.fieldByKey(f, "notExisting"))
        ));
    }

}
