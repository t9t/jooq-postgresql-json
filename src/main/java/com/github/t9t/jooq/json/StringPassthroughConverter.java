package com.github.t9t.jooq.json;

import org.jooq.Converter;

import java.util.Objects;

public class StringPassthroughConverter implements Converter<Object, String> {
    @Override
    public String from(Object t) {
        return Objects.toString(t, null);
    }

    @Override
    public String to(String u) {
        return u;
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<String> toType() {
        return String.class;
    }
}
