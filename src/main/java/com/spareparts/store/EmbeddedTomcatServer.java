package com.spareparts.store;

import com.spareparts.store.controller.FrontController;
import com.spareparts.store.controller.filter.JwtAuthFilter;
import jakarta.servlet.FilterRegistration;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.Optional;

public class EmbeddedTomcatServer {

    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

    public void start() {

        String contextPath = "/gymapp";
        String appBase = ".";
        Tomcat tomcat = new Tomcat();

        //define port, host, contextpath
        tomcat.setPort(Integer.valueOf(PORT.orElse("8080")));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
//        tomcat.addWebapp(contextPath, appBase);


        //annotation scanning
        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
//        context.addLifecycleListener(new ContextConfig());

        FilterRegistration.Dynamic jwtFilter = context.getServletContext().addFilter("jwtAuthFilter", new JwtAuthFilter());
        jwtFilter.addMappingForUrlPatterns(null, false, "/*");

        Tomcat.addServlet(context, "FrontController", new FrontController());
        context.addServletMappingDecoded("/*", "FrontController");


//        tomcat.getConnector();

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        tomcat.getServer().await();
    }

}
