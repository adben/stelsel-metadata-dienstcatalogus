package org.acme.model;

import java.util.Collection;

public class AuthenticatieDienst {

    public String name;
    public String description;

    public Collection clients;

    public AuthenticatieDienst() {
    }

    public AuthenticatieDienst(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public AuthenticatieDienst(String name, String description, Collection clients) {
        this.name = name;
        this.description = description;
        this.clients = clients;
    }
}
