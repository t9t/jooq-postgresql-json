package com.github.t9t.jooq.json;

import org.jooq.*;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * <p>jOOQ {@link Binding} to use {@code jsonb} fields as {@link Jsonb}. When selecting fields, the data is returned as
 * {@code Jsonb}.</p>
 *
 * <p><b>Note</b> that {@code null} values result in a {@code null} object as well, <i>not</i> a {@code Jsonb}
 * object with {@code null} value!</p>
 *
 * <p>When inputting data (eg. on insert and update), the data is sent as text to the
 * server, and converted to json there (by adding {@code ::json} to the placeholder).</p>
 *
 * <p>To use this with the jOOQ code generator, use configuration like this:</p>
 * <pre>{@code
 * <forcedTypes>
 *     <forcedType>
 *         <userType>com.github.t9t.jooq.json.Jsonb</userType>
 *         <binding>JsonbBinding</binding>
 *         <types>jsonb</types>
 *     </forcedType>
 * </forcedTypes>
 * }</pre>
 */
public class JsonbBinding implements Binding<Object, Jsonb> {
    private static final Converter<Object, Jsonb> CONVERTER = new JsonbConverter();

    @Override
    public Converter<Object, Jsonb> converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<Jsonb> ctx) {
        ctx.render().sql("?::jsonb");
    }

    @Override
    public void register(BindingRegisterContext<Jsonb> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<Jsonb> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<Jsonb> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<Jsonb> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<Jsonb> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<Jsonb> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
