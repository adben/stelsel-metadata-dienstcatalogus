package org.acme.model;

import java.util.Collection;

public class Dienstverlener {

    public String name;
    public String description;

    public Collection providers;

    public Dienstverlener() {
    }

    public Dienstverlener(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * for testing
     */
    public Dienstverlener(String name, String description, Collection providers) {
        this.name = name;
        this.description = description;
        this.providers = providers;
    }

}
