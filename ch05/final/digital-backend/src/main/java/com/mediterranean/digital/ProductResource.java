package com.mediterranean.digital;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import io.quarkus.security.Authenticated;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class ProductResource {

    @GET
    public List<Product> list() {
        return List.of(
                new Product(1, "Olive Oil Extra Virgin", "Food", 12.50),
                new Product(2, "Dried Figs", "Food", 8.90),
                new Product(3, "Ceramic Amphora", "Home", 45.00),
                new Product(4, "Lavender Soap", "Personal Care", 6.30),
                new Product(5, "Sea Salt Flakes", "Food", 4.75)
        );
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("catalog-editor")
    public Response updateProduct(@PathParam("id") Long id, Product product) {
        return Response.ok(product).build();
    }

    @PUT
    @Path("/{id}/price")
    @RolesAllowed("price-manager")
    public Response updatePrice(@PathParam("id") Long id, Product product) {
        return Response.ok(product).build();
    }
}
