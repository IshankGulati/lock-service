package com.lockservice.resources;

import com.lockservice.core.LockModel;
import com.lockservice.db.LockDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by ishank on 15/1/17.
 * A resource class which exposes acquire lock REST api.
 */

@Path("/acquire-lock")
@Produces(MediaType.APPLICATION_JSON)
public class AcquireLockResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcquireLockResource.class);

    private final LockDAO lockDAO;

    public AcquireLockResource(LockDAO lockDAO) {
        this.lockDAO = lockDAO;
    }

    @GET
    @UnitOfWork
    public Response acquireLock(@QueryParam("user") String userId) {
        LOGGER.info("Received lock request", userId);
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<LockModel> locks = lockDAO.isLocked();

        if (locks == null || locks.isEmpty()) {
            lockDAO.acquireLock(userId);
            return Response.status(Response.Status.OK).entity("Lock Successfully acquired").build();
        } else {
            return Response.status(Response.Status.OK).entity("Lock has already been taken").build();
        }
    }
}
