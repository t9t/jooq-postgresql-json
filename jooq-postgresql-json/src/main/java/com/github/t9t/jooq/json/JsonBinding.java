package com.github.t9t.jooq.json;

import org.jooq.*;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * <p>jOOQ {@link Binding} to use {@code json} fields as {@link Json}. When selecting fields, the data is returned as
 * {@code Json}.</p>
 *
 * <p><b>Note</b> that {@code null} values result in a {@code null} object as well, <i>not</i> a {@code Json}
 * object with {@code null} value!</p>
 *
 * <p>When inputting data (eg. on insert and update), the data is sent as text to the
 * server, and converted to json there (by adding {@code ::json} to the placeholder).</p>
 *
 * <p>To use this with the jOOQ code generator, use configuration like this:</p>
 * <pre>{@code
 * <forcedTypes>
 *     <forcedType>
 *         <userType>com.github.t9t.jooq.json.Json</userType>
 *         <binding>JsonBinding</binding>
 *         <types>json|jsonb</types>
 *     </forcedType>
 * </forcedTypes>
 * }</pre>
 */
public class JsonBinding implements Binding<Object, Json> {
    private static final Converter<Object, Json> CONVERTER = new JsonConverter();

    @Override
    public Converter<Object, Json> converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<Json> ctx) {
        ctx.render().sql("?::json");
    }

    @Override
    public void register(BindingRegisterContext<Json> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<Json> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<Json> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<Json> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<Json> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<Json> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
