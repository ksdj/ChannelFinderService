package gov.bnl.channelfinder;
/**
 * #%L
 * ChannelFinder Directory Service
 * %%
 * Copyright (C) 2010 - 2012 Helmholtz-Zentrum Berlin für Materialien und Energie GmbH
 * %%
 * Copyright (C) 2010 - 2012 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 * #L%
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.core.Response;

/**
 * JDBC query to delete one channel.
 *
 * @author Ralph Lange {@literal <ralph.lange@gmx.de>}
 */
public class DeleteChannelQuery {

    private String name;

    private DeleteChannelQuery(String name) {
        this.name = name;
    }

    /**
     * Creates and executes a JDBC based query for deleting one channel.
     *
     * @param con db connection to use
     * @param ignoreNoExist flag: true = do not generate an error if channel does not exist
     * @throws CFException wrapping an SQLException
     */
    private void executeQuery(Connection con, boolean ignoreNoExist) throws CFException {
        String query;
        PreparedStatement ps;
        try {
            query = "DELETE FROM channel WHERE name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            int rows = ps.executeUpdate();
            if (rows == 0 && !ignoreNoExist) {
                throw new CFException(Response.Status.NOT_FOUND,
                        "Channel '" + name + "' does not exist");
            }
            ps.close();
        } catch (SQLException e) {
            throw new CFException(Response.Status.INTERNAL_SERVER_ERROR,
                    "SQL Exception while deleting channel '" + name + "'", e);
        }
    }

    /**
     * Deletes a channel and its properties/tags from the database, failing if the
     * channel does not exist.
     *
     * @param name channel to delete
     * @throws CFException on fail or wrapping an SQLException
     */
    public static void deleteChannelFailNoexist(String name) throws CFException {
        DeleteChannelQuery q = new DeleteChannelQuery(name);
        q.executeQuery(DbConnection.getInstance().getConnection(), false);
    }

    /**
     * Deletes a channel and its properties/tags from the database.
     *
     * @param name channel to delete
     * @throws CFException wrapping an SQLException
     */
    public static void deleteChannelIgnoreNoexist(String name) throws CFException {
        DeleteChannelQuery q = new DeleteChannelQuery(name);
        q.executeQuery(DbConnection.getInstance().getConnection(), true);
    }
}
