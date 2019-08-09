package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams((type, f) -> Arrays.asList(
                params("arrayElement_getFirstObject", type, arrayRow, "{\"d\": 4408}", JsonDSL.arrayElement(f, 0)),
                params("arrayElement_getString", type, arrayRow, "\"" + type + " array\"", JsonDSL.arrayElement(f, 3)),
                params("arrayElement_outOfBounds", type, arrayRow, null, JsonDSL.arrayElement(f, 100)),
                params("arrayElement_negativeIndex", type, arrayRow, "true", JsonDSL.arrayElement(f, -2)),
                params("arrayElement_onObject", type, genericRow, null, JsonDSL.arrayElement(f, 100))
        ));
    }

}
