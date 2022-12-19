package org.acme.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OidcRelyingParty.class, name = "oidc"),
        @JsonSubTypes.Type(value = SamlRelyingParty.class, name = "saml")
})
public sealed interface RelyingParty permits OidcRelyingParty, SamlRelyingParty {
}
