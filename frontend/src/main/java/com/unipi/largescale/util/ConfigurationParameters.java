package com.unipi.largescale.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@XStreamAlias("ConfigurationParameters")
public class ConfigurationParameters {
    public String serverIP;
    public int serverPort;

    public ConfigurationParameters(String fileXML){
        try {
            System.out.println("Reading the configuration parameters..");
            XStream xstream = new XStream();
            xstream.alias("ConfigurationParameters", ConfigurationParameters.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.processAnnotations(ConfigurationParameters.class);
            ConfigurationParameters p = (ConfigurationParameters)(xstream.fromXML(new String(Files.readAllBytes(Paths.get(fileXML)))));
            serverIP = p.serverIP;
            serverPort = p.serverPort;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
