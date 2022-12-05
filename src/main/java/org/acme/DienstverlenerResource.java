package org.acme;

import org.acme.model.Dienstverlener;
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

@Path("/dienstverlener")
public class DienstverlenerResource {


    private static final Logger LOG = Logger.getLogger(DienstverlenerResource.class);

    @Context
    private UriInfo uriInfo;

    private final Set<Dienstverlener> dvs = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));


    Map<String, Object> oidcProviderTemplate;
    Map<String, Object> samlProviderTemplate;

    public DienstverlenerResource() {
        oidcProviderTemplate = TemplateUtils.lezen("META-INF/resources/template_oidc_idp.json");
        samlProviderTemplate = TemplateUtils.lezen("META-INF/resources/template_saml_idp.json");
        dvs.add(new Dienstverlener("DV1", "Eerste Dienstverlener"));
        dvs.add(new Dienstverlener("DV2", "Tweede Dienstverlener"));
    }

    @GET
    @Path("/simple")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Dienstverlener> listSimple() {
        LOG.info("dienstverleners simple");
        return dvs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Dienstverlener> list() {
        LOG.info("dienstverleners met providers");

        ResourceClient client = RestClientBuilder.newBuilder().baseUri(uriInfo.getBaseUri()).build(ResourceClient.class);

        return dvs.stream().map(dv -> {
            List<Map<String, Object>> providers = client.authenticatiediensten().stream()
                    .map(ad -> TemplateUtils.obtainProviders(ad, dv, oidcProviderTemplate, samlProviderTemplate))
                    .collect(Collectors.toList());
            return new Dienstverlener(dv.name, dv.description, providers);
        }).collect(Collectors.toSet());
    }

    @POST
    public Set<Dienstverlener> add(Dienstverlener dienstverlener) {
        dvs.add(dienstverlener);
        return dvs;
    }

    @DELETE
    public Set<Dienstverlener> delete(Dienstverlener dienstverlener) {
        dvs.removeIf(existingDienstverlener -> existingDienstverlener.name.contentEquals(dienstverlener.name));
        return dvs;
    }
}
