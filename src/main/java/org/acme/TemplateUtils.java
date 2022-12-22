package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Template;
import org.acme.model.AuthenticatieDienst;
import org.acme.model.OidcRelyingParty;
import org.acme.model.RelyingParty;
import org.acme.model.SamlRelyingParty;

import java.util.Map;

public interface TemplateUtils {

    static JsonNode obtainProviderConfig(AuthenticatieDienst ad, RelyingParty rp, final Template oidcProviderTemplate, final Template samlProviderTemplate) {
        if (rp instanceof OidcRelyingParty oidcRp) {
            return generateOidcProviderFromTemplate(ad, oidcRp, oidcProviderTemplate);
        } else if (rp instanceof SamlRelyingParty samlRp) {
            return generateSamlProviderFromTemplate(ad, samlRp, samlProviderTemplate);
        } else {
            throw new UnsupportedOperationException("Unsupported relying party type: " + rp.getClass().getSimpleName());
        }
    }

    static JsonNode obtainClients(RelyingParty rp, AuthenticatieDienst ad, final Template oidcClientTemplate, final Template samlClientTemplate) {
        if (rp instanceof OidcRelyingParty oidcRp) {
            return generateOidcClientFromTemplate(oidcRp, ad, oidcClientTemplate);
        } else if (rp instanceof SamlRelyingParty samlRp) {
            return generateSamlClientFromTemplate(samlRp, ad, samlClientTemplate);
        } else {
            throw new UnsupportedOperationException("Unsupported relying party type");
        }
    }

    private static JsonNode generateOidcClientFromTemplate(final OidcRelyingParty rp, final AuthenticatieDienst ad, final Template template) {
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
            return new ObjectMapper().readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode generateSamlClientFromTemplate(final SamlRelyingParty rp, final AuthenticatieDienst ad, final Template template) {
        String naam = "client-dienstverlener-".concat(rp.name()).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("name", naam)
                .data("clientId", naam)
                .render();
        try {
            return new ObjectMapper().readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode generateOidcProviderFromTemplate(final AuthenticatieDienst ad, final RelyingParty dv, final Template template) {
        String naam = "provider-dienstverlener-".concat(dv.name()).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("alias", naam)
                .data("displayName", naam)
                .data("providerId", naam)
                .render();
        try {
            return new ObjectMapper().readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode generateSamlProviderFromTemplate(final AuthenticatieDienst ad, final RelyingParty dv, final Template template) {
        String naam = "provider-dienstverlener-".concat(dv.name()).concat("-").concat("authenticatiedienst-").concat(ad.name);
        var rawJson = template
                .data("alias", naam)
                .data("displayName", naam)
                .data("providerId", naam)
                .render();
        try {
            return new ObjectMapper().readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
