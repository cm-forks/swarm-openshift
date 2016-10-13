package io.swarm.demo;

import java.io.FileInputStream;

public class KCServerBootStrap {

    public static void main(String[] args) throws Throwable {
        KeycloakServer.KeycloakServerConfig cfg = new KeycloakServer.KeycloakServerConfig();
        // TODO - ADD Server params
        KeycloakServer kc = new KeycloakServer(cfg);
        kc.start();

        // TODO - Import Realm File
        //kc.importRealm();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                kc.stop();
            }
        });
    }
}
