package org.acme.model;

import java.time.LocalDateTime;
import java.util.Collection;

public class Dienstverlener {

    public String name;
    public String description;

    public String iss;
    public String classification;
    public String schemeVersion;
    public String src;
    public String archiveURI;
    public Long publicationSerial;
    public LocalDateTime publicationDate;
    public LocalDateTime nextUpdate;
    public LocalDateTime validUntil;

    public Collection ADs;
    public Collection MDs;

    public Dienstverlener() {
    }

    public Dienstverlener(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * for testing
     */
    public Dienstverlener(String name, String description, Collection ADs) {
        this.name = name;
        this.description = description;
        this.ADs = ADs;
    }

}
