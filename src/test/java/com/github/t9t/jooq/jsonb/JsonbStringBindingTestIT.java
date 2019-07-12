package com.github.t9t.jooq.jsonb;


import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonbStringBindingTestIT {
    @Test
    public void testDatabaseConnection() throws Exception {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:23719/jooq");
        ds.setUser("jooq");
        ds.setPassword("jooq");

        try (Connection conn = ds.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select 5521")) {

            assertTrue(rs.next());
            assertEquals(5521, rs.getInt(1));
        }
    }
}
