package com.github.t9t.jooq.jsonb;

import org.jooq.Converter;

public class StringPassthroughConverter implements Converter<String, String> {
    @Override
    public String from(String t) {
        return t;
    }

    @Override
    public String to(String u) {
        return u;
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<String> toType() {
        return String.class;
    }
}
