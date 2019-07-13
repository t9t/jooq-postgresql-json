package com.github.t9t.jooq.json;

import org.jooq.*;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * <p>
 * jOOQ {@link Binding} to use {@code json} and {@code jsonb} fields as {@code String}. When selecting fields, the data
 * is returned as {@code String}. When inputting data (eg. on insert and update), the data is sent as text to the
 * server, and converted to json there (by adding {@code ::json} to the placeholder).
 * </p>
 * <p>
 * To use this with the jOOQ code generator, use configuration like this:
 * </p>
 * <pre>{@code
 * <forcedTypes>
 *     <forcedType>
 *         <userType>java.lang.String</userType>
 *         <binding>JsonStringBinding</binding>
 *         <types>json|jsonb</types>
 *     </forcedType>
 * </forcedTypes>
 * }</pre>
 */
public class JsonStringBinding implements Binding<Object, String> {
    private static final Converter<Object, String> CONVERTER = new StringConverter();

    @Override
    public Converter<Object, String> converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<String> ctx) {
        ctx.render().sql("?::json");
    }

    @Override
    public void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<String> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
