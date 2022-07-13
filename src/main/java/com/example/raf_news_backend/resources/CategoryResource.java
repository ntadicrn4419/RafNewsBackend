package com.example.raf_news_backend.resources;

import com.example.raf_news_backend.entities.Category;
import com.example.raf_news_backend.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {
    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> all()
    {
        return this.categoryService.allCategories();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category create(@Valid Category category) {
        return this.categoryService.addCategory(category);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category find(@PathParam("name") String name) {
        return this.categoryService.findCategory(name);
    }

    @PUT
    @Path("/{name}")
    public void update(@PathParam("name") String oldName,
                       @Valid Category category) {
         this.categoryService.updateCategory(oldName, category.getName(), category.getDescription());
    }

    @DELETE
    @Path("/{name}")
    public void delete(@PathParam("name") String name) {
        this.categoryService.deleteCategory(name);
    }


}
