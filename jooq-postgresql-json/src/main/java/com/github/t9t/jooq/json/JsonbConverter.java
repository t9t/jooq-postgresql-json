package com.github.t9t.jooq.json;

import org.jooq.Converter;

public class JsonbConverter implements Converter<Object, Jsonb> {
    @Override
    public Jsonb from(Object t) {
        return t == null ? null : Jsonb.of(t.toString());
    }

    @Override
    public String to(Jsonb u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<Jsonb> toType() {
        return Jsonb.class;
    }
}
