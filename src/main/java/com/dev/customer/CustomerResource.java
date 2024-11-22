package com.dev.customer;

import com.dev.customer.dtos.requisicao.RequisicaoCriaCustomer;
import com.dev.customer.dtos.requisicao.RequisicaoEditarCustomer;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    @Transactional
    @Path("/create")
    public Response createCustomer(final RequisicaoCriaCustomer customerDTO) {
        Customer customer = this.customerService.createCustomer(customerDTO);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") Long id) {
        try {
            final Customer customer = customerService.getCustomerById(id);
            return Response.ok(customer).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getAll")
    public Response getAllCustomers() {
        List<Customer> customers = this.customerService.getAllCustomer();
        return Response.ok(customers).build();
    }

    @PUT
    @Path("/editCustomer/{id}")
    public Response updateConsumer(@PathParam("id") Long id, final RequisicaoEditarCustomer requisicaoEditarCustomer) {
        try {
            final Customer updatedCustomer = customerService.updateCustomer(id, requisicaoEditarCustomer);
            return Response.ok(updatedCustomer).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        this.customerService.deleteCustomer(id);
        return Response.ok(Response.Status.OK).build();
    }
}
