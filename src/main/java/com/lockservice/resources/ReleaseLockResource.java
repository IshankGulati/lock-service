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
 * A resource class which exposes release lock REST api.
 */

@Path("/release-lock")
@Produces(MediaType.APPLICATION_JSON)
public class ReleaseLockResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcquireLockResource.class);

    private final LockDAO lockDAO;

    public ReleaseLockResource(LockDAO lockDAO) {
        this.lockDAO = lockDAO;
    }

    @GET
    @UnitOfWork
    public Response releaseLock(@QueryParam("user") String userId) {
        LOGGER.info("Received release request", userId);
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<LockModel> locks = lockDAO.isLocked();

        int lSize = locks != null ? locks.size() : 0;

        if (locks == null || lSize == 0) {
            return Response.status(Response.Status.OK).entity("Lock has not been acquired").build();
        } else if (lSize > 1){
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            if (locks.get(0).getUserId().equals(userId)) {
                lockDAO.releaseLock(userId);
                return Response.status(Response.Status.OK).entity("Lock successfully released").build();
            } else {
                return Response.status(Response.Status.OK).entity("Lock acquired by some other user").build();
            }
        }
    }
}
