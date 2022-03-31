module com.unipi.datamining {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires mongojack;
    requires org.neo4j.driver;
    requires org.neo4j.ogm.core;
    requires org.neo4j.ogm.drivers.api;
    requires org.neo4j.ogm.drivers.bolt;
    requires weka.dev;
    requires xstream;
    requires java.xml;
    requires cloudinary.core;
    requires cloudinary.http44;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.jcl;


    opens com.unipi.largescale.beans to javafx.base;
    opens com.unipi.largescale.gui to javafx.fxml;
    exports com.unipi.largescale.gui to javafx.graphics, javafx.fxml;
    exports com.unipi.largescale.util to xstream;
    exports com.unipi.largescale.beans;
    exports com.unipi.largescale.entities;
    opens com.unipi.largescale to javafx.fxml;
    exports com.unipi.largescale to javafx.fxml, javafx.graphics;
    exports com.unipi.largescale.dtos to com.fasterxml.jackson.databind;
    exports com.unipi.largescale.entities.aggregations to com.fasterxml.jackson.databind;
}