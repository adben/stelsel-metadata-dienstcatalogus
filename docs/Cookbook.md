# Cookbook

## Connect a service/relying party

### Register metadata
A service based on the OIDC specification should be registered in the metadata registry by POSTing the following JSON to the `/relyingparties` endpoint of the registry:

```json
{
  "type": "oidc",
  "name": "<NAME-OF-SERVICE>",
  "description": "<DESCRIPTION-SERVICE>",
  "consentText": "<CONSENT-TEXT>",
  "minimumLoa": "<LOA>",
  "clientId": "<UNIQUE-CLIENT-ID>",
  "jwks_uri": "<JWKS-URL>",
  "redirect_uris": ["<REDIRECT-URL-WITH-OPTIONAL-WILDCARD>"],
  "post_logout_redirect_uri": []
}
```

For example, to add the service called `Lutjebroek bouwvergunningen` (which, for this example, will autenticate via a Keycloak broker), the following `curl` command should be executed:
```shell
curl --location --request POST 'https://stelsel-metadata-dienstcatalogus.example.com/relyingparties' \
--header 'Content-Type: application/json' \
--data-raw '{
    "type": "oidc",
    "name": "Lutjebroek",
    "description": "Lutjebroek bouwvergunningen",
    "consentText": "Lutjebroek bouwvergunningen",
    "minimumLoa": "high",
    "clientId": "lutjebroek-bouwvergunningen",
    "jwks_uri": "https://lutjebroek.example.com/realms/example/protocol/openid-connect/certs",
    "redirect_uris": ["https://lutjebroek.example.com/realms/example/broker/*"],
    "post_logout_redirect_uri": []
}'
```

### Configure Identity Provider
> _NOTE_ For now, Identity Providers will be manually configured on the relying party side

#### In keycloak
To add an OIDC Identity Provider in Keycloak (20.x) the steps are as follows:
1. Navigate to the _Realm_ where the Identity Provider should be added
2. Click `Identity Providers`
3. Choose `Add Provider` en then `OpenID Connect v1.0`
4. Choose an alias with which you could recognize the identity provider, for example `example-ad`
5. Enter the OIDC-metadata url, which you received/fetch about the identity provider, and fill it in the `Discovery endpoint` field, 
   for example: `https://example-ad.example.com/.well-known/openid-configuration
6. For `Client authentication` choose `JWT signed with private key`
7. For `Client ID` enter the `clientId` of the relying party, which you registered in the metadata registry, in this example: `lutjebroek-bouwvergunningen`