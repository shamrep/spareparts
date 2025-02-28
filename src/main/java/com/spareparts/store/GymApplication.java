package com.spareparts.store;


public class GymApplication {

   private final EmbeddedTomcatServer tomcatServer;

    public GymApplication(EmbeddedTomcatServer tomcatServer) {
        this.tomcatServer = tomcatServer;
    }

    public GymApplication() {
        this(new EmbeddedTomcatServer());
    }

    public static void main(String[] args) {

        GymApplication gymApplication = new GymApplication();
        gymApplication.tomcatServer.start();

    }

}
