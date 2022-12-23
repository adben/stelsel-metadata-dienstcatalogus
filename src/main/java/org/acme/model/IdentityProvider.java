package org.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IdentityProvider(
    @JsonProperty(required = true)
    Long identityProviderId,
    @JsonProperty(required = true)
    Long contactId,
    @JsonProperty(required = true)
    Long oinId,
    @JsonProperty(required = true)
    String name,
    @JsonProperty(required = true)
    String description,
    @JsonProperty(required = true)
    String logo,
    @JsonProperty(required = true)
    String highestLoa,
    @JsonProperty(required = true)
    String iss,
    @JsonProperty(value = "SAML-metadata", required = true)
    String samlMetadata,
    @JsonProperty(value = "OIDC-metadata", required = true)
    String oidcMetadata
){

}
