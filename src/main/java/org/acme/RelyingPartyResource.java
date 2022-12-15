package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.model.RelyingParty;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/relyingparties")
public class RelyingPartyResource {
    private final Set<RelyingParty> relyingParties = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @POST
    public void add(RelyingParty relyingParty) {
        relyingParties.add(relyingParty);
    }

    @GET
    public RelyingPartyResult list() {
        return RelyingPartyResult.of(relyingParties);
    }
}

record RelyingPartyResult(
        String iss,
        String classification,
        @JsonProperty("scheme-version")
        String schemeVersion,
        @JsonProperty("RPs")
        Set<RelyingParty> relyingParties
){
    static RelyingPartyResult of(Set<RelyingParty> relyingParties) {
        return new RelyingPartyResult(
                "http://test.authority.eid.minbzk.nl",
                "test",
                "0.1",
                relyingParties
        );
    }
}