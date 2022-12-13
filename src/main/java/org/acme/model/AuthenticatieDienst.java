package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class AuthenticatieDienst {

    public String name;
    public String iss;

    public String fullname;
    @JsonProperty("fullname+nl")
    public String fullnameNl;
    @JsonProperty("fullname+en")
    public String fullnameEn;

    public String logo;
    public Set<String> LoA;

    @JsonProperty("SAML-metadata")
    public String samlMetadata;

    @JsonProperty("OIDC-metadata")
    public String oidcMetadata;

    public Collection DVs;

    public AuthenticatieDienst() {
    }

    public AuthenticatieDienst(String name, String fullname) {
        this(name, fullname, new ArrayList());
    }


    public AuthenticatieDienst(String name, String fullname, Collection DVs) {
        this.name = name;
        this.fullname = fullname;
        this.fullnameNl = fullname;
        this.fullnameEn = fullname;
        this.DVs = DVs;
    }
}
