package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OidcRelyingParty(
        @JsonProperty(required = true)

        String description,
        String consentText,
        @JsonProperty(required = true)
        String minimumLoa,
        @JsonProperty(required = true)
        String clientId,
        @JsonProperty(value = "jwks_uri", required = true)
        String jwksUri
) implements RelyingParty {
}
