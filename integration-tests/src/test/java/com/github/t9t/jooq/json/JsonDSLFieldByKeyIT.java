package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams((type, f) -> Arrays.asList(
                params("fieldByKey", type, "\"Hello, " + type + " world!\"", JsonDSL.fieldByKey(f, "str")),
                params("fieldByKey_twoLevels", type, "5521", JsonDSL.fieldByKey(JsonDSL.fieldByKey(f, "obj"), "i")),
                params("fieldByKey_nullField", type, "null", JsonDSL.fieldByKey(f, "n")),
                params("fieldByKey_notExistingField", type, null, JsonDSL.fieldByKey(f, "notExisting"))
        ));
    }

}
