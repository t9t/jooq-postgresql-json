package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams((type, f) -> Arrays.asList(
                params("arrayElementText_getFirstObject", type, arrayRow, "{\"d\": 4408}", JsonDSL.arrayElementText(f, 0)),
                params("arrayElementText_getString", type, arrayRow, type + " array", JsonDSL.arrayElementText(f, 3)),
                params("arrayElementText_outOfBounds", type, arrayRow, null, JsonDSL.arrayElementText(f, 100)),
                params("arrayElementText_negativeIndex", type, arrayRow, "true", JsonDSL.arrayElementText(f, -2)),
                params("arrayElementText_onObject", type, genericRow, null, JsonDSL.arrayElementText(f, 100))
        ));
    }

}
