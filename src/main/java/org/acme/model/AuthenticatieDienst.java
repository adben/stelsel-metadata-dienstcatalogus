package org.acme.model;

import java.time.LocalDateTime;
import java.util.Collection;

public class AuthenticatieDienst {

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

    public Collection DVs;

    public AuthenticatieDienst() {
    }

    public AuthenticatieDienst(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public AuthenticatieDienst(String name, String description, Collection DVs) {
        this.name = name;
        this.description = description;
        this.DVs = DVs;
    }
}
