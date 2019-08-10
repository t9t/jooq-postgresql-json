package com.github.t9t.jooq.json;

import org.jooq.Converter;

public class JsonConverter implements Converter<Object, Json> {
    @Override
    public Json from(Object t) {
        return t == null ? null : Json.of(t.toString());
    }

    @Override
    public String to(Json u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<Json> toType() {
        return Json.class;
    }
}
