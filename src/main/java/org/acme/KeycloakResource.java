package org.acme;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import org.acme.model.AuthenticatieDienst;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/keycloak")
public class KeycloakResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ObjectMapper objectMapper;

    @Location("template_oidc_client.json")
    private Template oidcClientTemplate;

    @Location("template_saml_client.json")
    private Template samlClientTemplate;

    @Location("template_oidc_idp.json")
    private Template oidcIdpTemplate;

    @Location("template_saml_idp.json")
    private Template samlIdpTemplate;

    @GET
    @Path("client-configs")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ClientConfig> clientConfigs() {
        Log.info("AuthenticatieDiensten met clients");

        ResourceClient client = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);
        var ads = client.authenticatiediensten();

        return ads.stream().map(ad -> {
                    List<JsonNode> clients = client.relyingParties().relyingParties().stream()
                            .map(rp -> TemplateUtils.obtainClients(rp, ad, oidcClientTemplate, samlClientTemplate))
                            .toList();
                    return new ClientConfig(ad.name, objectMapper.createArrayNode().addAll(clients));
                })
                .collect(Collectors.toSet());
    }

    @GET
    @Path("idp-configs")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<IdpConfig> idpConfigs() {
        Log.info("AuthenticatieDiensten met clients");

        ResourceClient client = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);
        var rps = client.relyingParties().relyingParties();

        return rps.stream().map(rp -> {
                    List<JsonNode> idpConfigs = client.authenticatiediensten().stream()
                            .map(ad -> TemplateUtils.obtainProviderConfig(ad, rp, oidcIdpTemplate, samlIdpTemplate))
                            .toList();
                    return new IdpConfig(rp.name(), objectMapper.createArrayNode().addAll(idpConfigs));
                })
                .collect(Collectors.toSet());
    }
}

record IdpConfig(String forClient, JsonNode config){}

record ClientConfig(String forAd, JsonNode config){}
