package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SamlRelyingParty(
        @JsonProperty(required = true)
        String name,
        @JsonProperty(required = true)
        String description,
        String consentText,
        @JsonProperty(required = true)
        String minimumLoa,
        @JsonProperty(required = true)
        String entityId,
        @JsonProperty(value = "metadata_uri", required = true)
        String metaDataUrl
) implements RelyingParty {
}
