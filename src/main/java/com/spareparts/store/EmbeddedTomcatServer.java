package com.spareparts.store;

import com.spareparts.store.controller.FrontController;
import com.spareparts.store.controller.filter.JwtAuthFilter;
import jakarta.servlet.FilterRegistration;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;
import java.util.Optional;

public class EmbeddedTomcatServer {

    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

    public void start() {
        String contextPath = "";
        Tomcat tomcat = new Tomcat();

        // Define a safe port
        int port = PORT.map(p -> {
            try {
                return Integer.parseInt(p);
            } catch (NumberFormatException e) {
                return 8080; // Default port
            }
        }).orElse(8080);

        tomcat.setPort(port);
        tomcat.setHostname(HOSTNAME.orElse("localhost"));

        // Create context BEFORE adding filters
        Context context = tomcat.addContext(contextPath, new File(".").getAbsolutePath());
//        context.addLifecycleListener(new ContextConfig()); // Enables annotation scanning


        Class filterClass = JwtAuthFilter.class;
        FilterDef myFilterDef = new FilterDef();
        myFilterDef.setFilterClass(filterClass.getName());
        myFilterDef.setFilterName(filterClass.getSimpleName());
        context.addFilterDef(myFilterDef);

        FilterMap myFilterMap = new FilterMap();
        myFilterMap.setFilterName(filterClass.getSimpleName());
        myFilterMap.addURLPattern("/*");
        context.addFilterMap(myFilterMap);

        // Register FrontController
        Tomcat.addServlet(context, "FrontController", new FrontController());
        context.addServletMappingDecoded("/*", "FrontController");

        // Ensure the connector is initialized
        tomcat.getConnector();

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            System.err.println("Failed to start Tomcat: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("Server started on port " + port);
        tomcat.getServer().await();

    }

}
