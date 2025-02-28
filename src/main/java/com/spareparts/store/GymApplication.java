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
    public static void main(String[] args) {
        EmbeddedTomcatServer server = new EmbeddedTomcatServer();
        server.start();
    }
}
