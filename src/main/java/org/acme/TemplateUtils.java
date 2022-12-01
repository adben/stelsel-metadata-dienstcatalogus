package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.acme.model.AuthenticatieDienst;
import org.acme.model.Dienstverlener;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public interface TemplateUtils {
    Logger LOG = Logger.getLogger(AuthenticatieDienstResource.class);

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

    static Map<String, Object> generateFromTemplate(final Dienstverlener dv, final AuthenticatieDienst ad, final Map<String, Object> template) {
        String naam = "dienstverlener-".concat(dv.name).concat("-").concat("authenticatiedienst-").concat(ad.name);

        return ImmutableMap.<String, Object>builder()
                .put("name", naam)
                .put("clientId", naam)
                .putAll(template)
                .build();

    }
}
