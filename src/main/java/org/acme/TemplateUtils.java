package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Template;
import org.acme.model.*;
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

    static Map<String, Object> obtainClients(RelyingParty rp, AuthenticatieDienst ad, final Template oidcClientTemplate, final Template samlClientTemplate) {
        Map<String, Object> clientConfig;
        if (rp instanceof OidcRelyingParty oidcRp) {
            clientConfig = Map.of("oidc", generateOidcClientFromTemplate(oidcRp, ad, oidcClientTemplate));
        } else if (rp instanceof SamlRelyingParty samlRp) {
            clientConfig = Map.of("saml", generateSamlClientFromTemplate(samlRp, ad, samlClientTemplate));
        } else {
            throw new UnsupportedOperationException("Unsupported relying party type");
        }
        return Map.of(rp.name(), clientConfig);
    }

    private static Map<String, Object> generateOidcClientFromTemplate(final OidcRelyingParty rp, final AuthenticatieDienst ad, final Template template) {
        String naam = "client-dienstverlener-".concat(rp.name()).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("name", rp.name())
                .data("clientId", rp.clientId())
                .data("description", rp.description())
                .data("redirectUris", rp.redirectUris())
                .data("jwksUrl", rp.jwksUri())
                .data("consentText", rp.consentText())
                .render();
        try {
            return new ObjectMapper().readValue(rawJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> generateSamlClientFromTemplate(final SamlRelyingParty rp, final AuthenticatieDienst ad, final Template template) {
        String naam = "client-dienstverlener-".concat(rp.name()).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("name", naam)
                .data("clientId", naam)
                .render();
        try {
            return new ObjectMapper().readValue(rawJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
