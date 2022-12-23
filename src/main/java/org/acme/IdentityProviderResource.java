package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.model.IdentityProvider;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/identityproviders")
public class IdentityProviderResource {
        private final Set<IdentityProvider> identityProviders = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

        public IdentityProviderResource() {
            identityProviders.add(new IdentityProvider(
                    1L,
                    1L,
                    1L,
                    "Identity-Provider-1",
                    "eerste IdP",
                    "https://keycloak-adolfobenedetti-1-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/realms/DV/protocol/openid-connect/certs",
                    "high",
                    "https://authenticatiedienst-twee-sep-redhat-dev.apps.sandbox.x8i5.p1.openshiftapps.com/realms/AD2",
                    "https://authenticatiedienst-twee-sep-redhat-dev.apps.sandbox.x8i5.p1.openshiftapps.com/realms/AD2/protocol/saml/descriptor",
                    "https://authenticatiedienst-twee-sep-redhat-dev.apps.sandbox.x8i5.p1.openshiftapps.com/realms/AD2/.well-known/openid-configuration"
            ));

        }

        @POST
        public void add(IdentityProvider identityProvider) {
            identityProviders.add(identityProvider);
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public org.acme.IdentityProviderResult list() {
            return org.acme.IdentityProviderResult.of(identityProviders);
        }

        @GET
        @Path("/{idpId}")
        @Produces(MediaType.APPLICATION_JSON)
        public RestResponse<IdentityProvider> findIdp(@PathParam("idpId") Long idpId) {
            return identityProviders.stream()
                    .filter(idp -> idp.identityProviderId().equals(idpId))
                    .findFirst()
                    .map(RestResponse::ok)
                    .orElseGet(() -> RestResponse.status(Response.Status.NOT_FOUND));
        }

}

    record IdentityProviderResult(
            String iss,
            String classification,
            @JsonProperty("scheme-version")
            String schemeVersion,
            @JsonProperty("IdPs")
            Set<IdentityProvider> identityProviders
    ) {
        static org.acme.IdentityProviderResult of(Set<IdentityProvider> identityProviders) {
            return new org.acme.IdentityProviderResult(
                    "http://test.authority.eid.minbzk.nl",
                    "test",
                    "0.1",
                    identityProviders
            );
        }
    }

