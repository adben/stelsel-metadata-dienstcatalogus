package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OidcRelyingParty(
        @JsonProperty(required = true)
        String name,
        @JsonProperty(required = true)
        String description,
        String consentText,
        @JsonProperty(required = true)
        String minimumLoa,
        @JsonProperty(required = true)
        String clientId,
        @JsonProperty(value = "jwks_uri", required = true)
        String jwksUri,
        @JsonProperty(value = "redirect_uris", required = true)
        List<String> redirectUris,
        @JsonProperty(value = "post_logout_redirect_uri", required = true)
        List<String> postLogoutRedirectUris
) implements RelyingParty {
}
