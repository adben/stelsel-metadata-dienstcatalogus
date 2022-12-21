package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.quarkus.qute.Template;
import org.acme.model.AuthenticatieDienst;
import org.acme.model.Dienstverlener;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public interface TemplateUtils {
    Logger LOG = Logger.getLogger(TemplateUtils.class);

    static Map<String, Object> lezen(String path) {
        // sjabloon lezen
        try (InputStream template = TemplateUtils.class.getClassLoader().getResourceAsStream(path)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(template, Map.class);
        } catch (IOException e) {
            LOG.error(e);
            return Collections.emptyMap();
        }
    }

    static Map<String, Object> obtainProviders(AuthenticatieDienst ad, Dienstverlener dv, final Template oidcProviderTemplate, final Template samlProviderTemplate) {
        return Map.of(ad.name, Map.of("oidc", generateProviderFromTemplate(dv, ad, oidcProviderTemplate),
                "saml", generateProviderFromTemplate(dv, ad, samlProviderTemplate)));
    }

    static Map<String, Object> obtainClients(Dienstverlener dv, AuthenticatieDienst ad, final Template oidcClientTemplate, final Template samlClientTemplate) {
        return Map.of(dv.name, Map.of("oidc", generateClientFromTemplate(dv, ad, oidcClientTemplate),
                "saml", generateClientFromTemplate(dv, ad, samlClientTemplate)));
    }

    private static Map<String, Object> generateClientFromTemplate(final Dienstverlener dv, final AuthenticatieDienst ad, final Template template) {
        String naam = "client-dienstverlener-".concat(dv.name).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("name", naam)
                .data("clientId", naam).render();
        try {
            return new ObjectMapper().readValue(rawJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> generateProviderFromTemplate(final Dienstverlener dv, final AuthenticatieDienst ad, final Template template) {
        String naam = "provider-dienstverlener-".concat(dv.name).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("alias", naam)
                .data("displayName", naam)
                .data("providerId", naam)
                .render();
        try {
            return new ObjectMapper().readValue(rawJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
