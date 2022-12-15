package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.model.OidcRelyingParty;
import org.acme.model.RelyingParty;
import org.acme.model.SamlRelyingParty;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
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

    @GET
    @Path("/oidc/{clientId}")
    public Response findOidcRp(@PathParam("clientId") String clientId) {
        return relyingParties.stream()
                .filter(rp -> rp instanceof OidcRelyingParty orp && orp.clientId().equals(clientId))
                .findFirst()
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/saml/{entityId}")
    public Response findSamlRp(@PathParam("entityId") String entityId) {
        return relyingParties.stream()
                .filter(rp -> rp instanceof SamlRelyingParty orp && orp.entityId().equals(entityId))
                .findFirst()
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
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