package com.whatnow.serverside.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Thiago
 */
@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.whatnow.serverside.entrypoint.General.class);
        resources.add(com.whatnow.serverside.entrypoint.Login.class);
    }
    
}
