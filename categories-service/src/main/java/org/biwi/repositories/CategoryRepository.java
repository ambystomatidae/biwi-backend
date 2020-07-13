package org.biwi.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.biwi.models.Category;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category, Integer> {

    public Category findByName(String name) {
        return find("name", name).firstResult();
    }
}
