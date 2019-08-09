package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElement", (type, f) -> Arrays.asList(
                params("getFirstObject", arrayRow, "{\"d\": 4408}", JsonDSL.arrayElement(f, 0)),
                params("getString", arrayRow, "\"" + type + " array\"", JsonDSL.arrayElement(f, 3)),
                params("outOfBounds", arrayRow, null, JsonDSL.arrayElement(f, 100)),
                params("negativeIndex", arrayRow, "true", JsonDSL.arrayElement(f, -2)),
                params("onObject", genericRow, null, JsonDSL.arrayElement(f, 100))
        ));
    }

}
