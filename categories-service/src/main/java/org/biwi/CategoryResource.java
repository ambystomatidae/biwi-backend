package org.biwi;

import org.biwi.models.Category;
import org.biwi.repositories.CategoryRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    private CategoryRepository repository;


    @GET
    @Path("/{id}")
    public Category get(@PathParam("id") Integer id) {
        return repository.findById(id);
    }


    @GET
    @Path("/")
    public List<Category> list() {
        return repository.listAll();
    }


    @POST
    @Path("/")
    @Transactional
    public Response add(Category category) {
        if (category.getName() != null) {
            Category c = repository.findByName(category.getName());
            if (c == null) {
                repository.persist(category);
                return Response.ok(category).build();
                //return Response.created(URI.create(category.getName())).build();
            }
            return Response.status(409).build();
        }
        return Response.status(400).build();
    }


    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Category category) {
        Category c = repository.findById(id);

        if (c != null) {
            if (repository.findByName(category.getName()) == null) {
                c.setName(category.getName());
                repository.persist(c);
                return Response.ok(c).build();
            }
            return Response.status(409).build();
        }
        return Response.status(404).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        repository.deleteById(id);
    }


}
