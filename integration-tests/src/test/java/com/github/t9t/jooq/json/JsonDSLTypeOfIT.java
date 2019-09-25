package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.typeOf;

public class JsonDSLTypeOfIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("typeOf", Arrays.asList(
                test("object").selecting(typeOf(json)).expectString("object"),
                test("array").forArray().selecting(typeOf(json)).expectString("array"),
                test("string").usingJson("\"Hello, world\"").selecting(typeOf(json)).expectString("string"),
                test("number").usingJson("5521").selecting(typeOf(json)).expectString("number"),
                test("numberFraction").usingJson("55.21").selecting(typeOf(json)).expectString("number"),
                test("numberNegative").usingJson("-5521").selecting(typeOf(json)).expectString("number"),
                test("booleanTrue").usingJson("true").selecting(typeOf(json)).expectString("boolean"),
                test("booleanFalse").usingJson("false").selecting(typeOf(json)).expectString("boolean"),
                test("null").usingJson("null").selecting(typeOf(json)).expectString("null"),
                test("nullString").usingJson("\"null\"").selecting(typeOf(json)).expectString("string"),

                btest("object").selecting(JsonbDSL.typeOf(jsonb)).expectString("object"),
                btest("array").forArray().selecting(JsonbDSL.typeOf(jsonb)).expectString("array"),
                btest("string").usingJson("\"Hello, world\"").selecting(JsonbDSL.typeOf(jsonb)).expectString("string"),
                btest("number").usingJson("5521").selecting(JsonbDSL.typeOf(jsonb)).expectString("number"),
                btest("numberFraction").usingJson("55.21").selecting(JsonbDSL.typeOf(jsonb)).expectString("number"),
                btest("numberNegative").usingJson("-5521").selecting(JsonbDSL.typeOf(jsonb)).expectString("number"),
                btest("booleanTrue").usingJson("true").selecting(JsonbDSL.typeOf(jsonb)).expectString("boolean"),
                btest("booleanFalse").usingJson("false").selecting(JsonbDSL.typeOf(jsonb)).expectString("boolean"),
                btest("null").usingJson("null").selecting(JsonbDSL.typeOf(jsonb)).expectString("null"),
                btest("nullString").usingJson("\"null\"").selecting(JsonbDSL.typeOf(jsonb)).expectString("string")
        ));
    }
}
