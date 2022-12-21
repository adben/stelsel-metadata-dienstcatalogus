package org.acme;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import org.acme.model.AuthenticatieDienst;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/authenticatiediensten")
public class AuthenticatieDienstResource {

    private static final Logger LOG = Logger.getLogger(AuthenticatieDienstResource.class);

    @Context
    private UriInfo uriInfo;

    private final Set<AuthenticatieDienst> ads = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @Location("template_oidc_client.json")
    Template oidcClientTemplate;

    @Location("template_saml_client.json")
    Template samlClientTemplate;
    public AuthenticatieDienstResource() {
        ads.add(new AuthenticatieDienst("AD1", "Eerste Authenticatiedienst"));
        ads.add(new AuthenticatieDienst("AD2", "Tweede Authenticatiedienst"));
    }

    @GET
    @Path("/simple")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<AuthenticatieDienst> listSimple() {
        LOG.info("AuthenticatieDiensten simple");
        return ads;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<AuthenticatieDienst> list() {
        LOG.info("AuthenticatieDiensten met clients");

        ResourceClient client = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);

        return ads.stream().map(ad -> {
                    List<Map<String, Object>> clients = client.dienstverleners().stream()
                            .map(dv -> TemplateUtils.obtainClients(dv, ad, oidcClientTemplate, samlClientTemplate))
                            .toList();
                    return new AuthenticatieDienst(ad.name, ad.description, clients);
                })
                .collect(Collectors.toSet());
    }

    @POST
    public Set<AuthenticatieDienst> add(AuthenticatieDienst authenticatieDienst) {
        ads.add(authenticatieDienst);
        return ads;
    }

    @DELETE
    public Set<AuthenticatieDienst> delete(AuthenticatieDienst authenticatieDienst) {
        ads.removeIf(existingAuthenticatieDienst -> existingAuthenticatieDienst.name.contentEquals(authenticatieDienst.name));
        return ads;
    }
}
