package com.spareparts.store;


import com.spareparts.store.controller.FrontController;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.net.URL;
import java.util.Optional;

//@SpringBootApplication
public class GymApplication {
    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

    public static void main(String[] args) throws LifecycleException {
        String contextPath = "/gymapp";
        String appBase = ".";
        Tomcat tomcat = new Tomcat();

        //define port, host, contextpath
        tomcat.setPort(Integer.valueOf(PORT.orElse("8080")));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);


        //annotation scanning
        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        ctx.addLifecycleListener(new ContextConfig());
        Tomcat.addServlet(ctx, "FrontController", new FrontController());
        ctx.addServletMappingDecoded("/*", "FrontController");

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}
