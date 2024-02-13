package com.jiahaieconproj.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.jiahaieconproj.ecommerce.entity.Product;
import com.jiahaieconproj.ecommerce.entity.ProductCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

/**
 * MyDataRestConfig
 */
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer
{
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors)
    {
        HttpMethod[] theUnsupportedAction = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable HTTP method for product: PUT, POST, DELETE;
        config.getExposureConfiguration()
            .forDomainType(Product.class)
            .withItemExposure((metadata, HttpMethod) -> HttpMethod.disable(theUnsupportedAction))
            .withCollectionExposure((metadata, HttpMethod) -> HttpMethod.disable(theUnsupportedAction));

        //disable HTTP method for productCategory: PUT, POST, DELETE;
        config.getExposureConfiguration()
            .forDomainType(ProductCategory.class)
            .withItemExposure((metadata, HttpMethod) -> HttpMethod.disable(theUnsupportedAction))
            .withCollectionExposure((metadata, HttpMethod) -> HttpMethod.disable(theUnsupportedAction));

        //call an internal helper method
        exposeIds(config);
    }
    private void exposeIds(RepositoryRestConfiguration config) {
        // TODO Auto-generated method stub
        
        //expose entity id

        //get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array of entity type
        List<Class> entityClass = new ArrayList<>();

        //get entity types for the entities
        for (EntityType temp : entities)
        {
            entityClass.add(temp.getJavaType());
        }
        
        //expose entity type for array
        Class[] domainTypes = entityClass.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
    
}