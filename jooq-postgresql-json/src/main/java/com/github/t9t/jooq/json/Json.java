package com.github.t9t.jooq.json;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * <p>Represents a JSON value. When using {@link JsonBinding}, {@code null} values from the database are turned as a
 * {@code null}, <i>not</i> a {@code Json} instance with a {@code null} value.</p>
 *
 * <p>The {@link #toString()} method returns only the value without any additions, so can be used safely in place of
 * {@link #getValue()}.</p>
 *
 * <p>To create an instance of a non-{@code null} value, use {@link #of(String)}. When you have a nullable value, use
 * {@link #ofNullable(String)} which returns {@code null} in case the input is {@code null}.</p>
 */
public final class Json {
    private final String value;

    private Json(String value) {
        this.value = requireNonNull(value, "value");
    }

    /**
     * Create an instance containing the non-{@code null} string. No checks are done to validate if the string is valid
     * JSON.
     *
     * @param value non-{@code null} string
     * @return Instance representing the string value
     * @see #ofNullable(String)
     */
    public static Json of(String value) {
        return new Json(value);
    }

    /**
     * Create an instance containing string if it's not {@code null}. When the {@code value} is {@code null},
     * {@code null} is returned. No checks are done to validate if the string is valid JSON.
     *
     * @param value nullable string
     * @return Instance representing the string value or {@code null}
     * @see #of(String)
     */
    public static Json ofNullable(String value) {
        return value == null ? null : of(value);
    }

    public static Json copy(Json in) {
        return in == null ? null : of(in.value);
    }

    /**
     * Retrieve the non-{@code null} JSON value
     *
     * @return JSON value, non-{@code null}
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Json json = (Json) o;
        return Objects.equals(value, json.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
