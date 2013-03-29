package org.jboss.as.quickstarts.kitchensink_ear.rest;

import org.jboss.as.quickstarts.kitchensink_ear.dao.stations.StationDAO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.other.Member;
import org.jboss.as.quickstarts.kitchensink_ear.other.MemberRegistration;
import org.jboss.as.quickstarts.kitchensink_ear.other.MemberRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 26.03.13
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/stations")
@RequestScoped
public class StationResourceRESTService {


    @Inject
    private Validator validator;

    @Inject
    private StationRepository repository;

    @Inject
    MemberRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StationDTO> listAllMembers() {
        return repository.getAllStations();
    }

    /**
     * Creates a new member from the values provided.  Performs validation, and will return a JAX-RS response with either
     * 200 ok, or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStation(StationDTO stationDTO) {

        Response.ResponseBuilder builder = null;

        try {
            //Validates member using bean validation

//            registration.(member);

            //Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            //Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }


    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        Map<String, String> responseObj = new HashMap<String, String>();
        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
