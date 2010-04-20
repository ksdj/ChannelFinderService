/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.bnl.channelfinder;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author rlange
 */

@Path("/tags/{name}")
public class TagsResource {
    /**
     *
     */
    @Context
    protected UriInfo uriInfo;
    /**
     *
     */
    @Context
    protected ResourceContext resourceContext;
    /**
     *
     */
    protected Integer id;

    /** Creates a new instance of TagsResource */
    public TagsResource() {
    }

    /**
     * HTTP GET method for retrieving the list of Channels that are tagged with <tt>name</tt>.
     *
     * @param name tag name
     * @return an instance of XmlChannels
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public XmlChannels get(
            @PathParam("name") String name) {
        return AccessManager.getInstance().findChannelsByTag(name);
    }

    /**
     * HTTP PUT method for creating/updating an instance of Channel identified by the
     * XML input.
     * The <em>complete</em> set of properties for the channel must be supplied,
     * which will replace the existing set of properties.
     *
     * @param data an XmlChannel entity that is deserialized from a XML stream
     */
//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void put(@PathParam("name") String name, XmlChannel data) {
//        AccessManager.getInstance().updateChannel(name, data);
//    }

    /**
     * HTTP POST method for adding the tag identified by <tt>name</tt> to all Channels
     * identified by the XML structure <tt>data</tt>.
     *
     * @param name tag name
     * @param data XmlChannels list that is deserialized from a XML stream
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public void post(@PathParam("name") String name, XmlChannels data) {
        AccessManager.getInstance().addTag(name, data);
    }

    /**
     * HTTP DELETE method for deleting a tag identified by the
     * path parameter <tt>name</tt> from all Channels.
     *
     * @param name tag to delete
     */
    @DELETE
    public void delete(@PathParam("name") String name) {
        AccessManager.getInstance().deleteTag(name);
    }
}