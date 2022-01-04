package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.arrayLength;

public class JsonDSLArrayLengthIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("arrayLength", Arrays.asList(
                test("zeroLength").usingJson("[]").selecting(arrayLength(json)).expect(0),
                test("nonZeroLength").usingJson("[1, \"b\", false]").selecting(arrayLength(json)).expect(3),

                btest("zeroLength").usingJson("[]").selecting(JsonbDSL.arrayLength(jsonb)).expect(0),
                btest("nonZeroLength").usingJson("[1, \"b\", false]").selecting(JsonbDSL.arrayLength(jsonb)).expect(3)
        ));
    }
}
