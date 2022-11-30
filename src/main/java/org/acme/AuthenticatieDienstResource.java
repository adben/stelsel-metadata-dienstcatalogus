package org.acme;

import org.acme.model.AuthenticatieDienst;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/authenticatiediensten")
public class AuthenticatieDienstResource {

    private final Set<AuthenticatieDienst> ads = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public AuthenticatieDienstResource() {
        ads.add(new AuthenticatieDienst("AD1", "Eerste Authenticatiedienst"));
        ads.add(new AuthenticatieDienst("AD2", "Tweede Authenticatiedienst"));
    }

    @GET
    public Set<AuthenticatieDienst> list() {
        return ads;
    }

    @POST
    public Set<AuthenticatieDienst> add(AuthenticatieDienst authenticatieDienst) {
        ads.add(authenticatieDienst);
        return ads;
    }

    @DELETE
    public Set<AuthenticatieDienst> delete(AuthenticatieDienst authenticatieDienst) {
        ads.removeIf(existingAuthenticatieDienst -> existingAuthenticatieDienst.name().contentEquals(authenticatieDienst.name()));
        return ads;
    }
}
