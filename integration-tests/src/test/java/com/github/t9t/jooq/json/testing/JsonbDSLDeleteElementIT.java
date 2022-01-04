package com.github.t9t.jooq.json.testing;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.deleteElement;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLDeleteElementIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("deleteElement", Arrays.asList(
                btest("array").selecting(deleteElement(field("[\"a\", 1, \"b\", false, \"c\"]"), 2)).expectJsonb("[\"a\", 1, false, \"c\"]"),
                btest("nested").selecting(deleteElement(deleteElement(field("[\"a\", 1, \"b\", false, \"c\"]"), 2), 2)).expectJsonb("[\"a\", 1, \"c\"]"),
                btest("negativeIndex").selecting(deleteElement(field("[\"a\", 1, \"b\", false, \"c\"]"), -2)).expectJsonb("[\"a\", 1, \"b\", \"c\"]")
        ));
    }
}
