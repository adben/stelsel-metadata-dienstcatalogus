package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.model.OidcRelyingParty;
import org.acme.model.RelyingParty;
import org.acme.model.SamlRelyingParty;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Path("/relyingparties")
public class RelyingPartyResource {
    private final Set<RelyingParty> relyingParties = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public RelyingPartyResource() {
        relyingParties.add(new OidcRelyingParty(
            "client-dienstverlener-DV1",
            "Eerste Dienstverlener",
            "Consent DV1",
            "Substantial",
            "client-dv1",
            "https://keycloak-adolfobenedetti-1-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/realms/DV/protocol/openid-connect/certs",
            List.of("https://keycloak-adolfobenedetti-1-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/realms/DV/broker/*"),
            List.of()
        ));
    }

    @POST
    public void add(RelyingParty relyingParty) {
        relyingParties.add(relyingParty);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RelyingPartyResult list() {
        return RelyingPartyResult.of(relyingParties);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        relyingParties.removeIf(rp -> {
            if (rp instanceof OidcRelyingParty oidcRp) return oidcRp.clientId().equals(id);
            else if (rp instanceof SamlRelyingParty samlRp) return samlRp.entityId().equals(id);
            else return false;
        });
    }

    @GET
    @Path("/oidc/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<OidcRelyingParty> findOidcRp(@PathParam("clientId") String clientId) {
        return relyingParties.stream()
                .filter(rp -> rp instanceof OidcRelyingParty orp && orp.clientId().equals(clientId))
                .map(OidcRelyingParty.class::cast)
                .findFirst()
                .map(RestResponse::ok)
                .orElseGet(() -> RestResponse.status(Response.Status.NOT_FOUND));
    }

    @GET
    @Path("/saml/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<SamlRelyingParty> findSamlRp(@PathParam("entityId") String entityId) {
        return relyingParties.stream()
                .filter(rp -> rp instanceof SamlRelyingParty orp && orp.entityId().equals(entityId))
                .map(SamlRelyingParty.class::cast)
                .findFirst()
                .map(RestResponse::ok)
                .orElseGet(() -> RestResponse.status(Response.Status.NOT_FOUND));
    }
}

record RelyingPartyResult(
        String iss,
        String classification,
        @JsonProperty("scheme-version")
        String schemeVersion,
        @JsonProperty("RPs")
        Set<RelyingParty> relyingParties
) {
    static RelyingPartyResult of(Set<RelyingParty> relyingParties) {
        return new RelyingPartyResult(
                "http://test.authority.eid.minbzk.nl",
                "test",
                "0.1",
                relyingParties
        );
    }
}