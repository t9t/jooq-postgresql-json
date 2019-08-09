package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLFieldByKeyTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams((type, f) -> Arrays.asList(
                params("fieldByKeyText", type, "Hello, " + type + " world!", JsonDSL.fieldByKeyText(f, "str")),
                params("fieldByKeyText_twoLevels", type, "5521", JsonDSL.fieldByKeyText(JsonDSL.fieldByKey(f, "obj"), "i")),
                params("fieldByKeyText_nullField", type, null, JsonDSL.fieldByKeyText(f, "n")),
                params("fieldByKeyText_notExistingField", type, null, JsonDSL.fieldByKeyText(f, "notExisting"))
        ));
    }

}
