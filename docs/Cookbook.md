# Cookbook

## Dienst aansluiten

### Metadata registreren
Een dienst op basis van OIDC koppelvlak kan aan de metadata registry worden toegevoegd door de volgende JSON naar het `/relyingparties` endpoint van de registry te POSTen:
```json
{
  "type": "oidc",
  "name": "<NAAM-DIENST>",
  "description": "<OMSCHRIJVING-DIENST>",
  "consentText": "<CONSENT-TEXT>",
  "minimumLoa": "<LOA>",
  "clientId": "<UNIQUE-CLIENT-ID>",
  "jwks_uri": "<JWKS-URL>",
  "redirect_uris": ["<REDIRECT-URL-WITH-OPTIONAL-WILDCARD>"],
  "post_logout_redirect_uri": []
}
```

Om bijvoorbeeld de dienst `Lutjebroek bouwvergunningen` (die middels een Keycloak broker authenticeert) toe te voegen, kan het volgende `curl` commando worden uitgevoerd:
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

### Authenticatiediensten instellen
> _NOTE_ Voor nu moeten authenticatiediensten nog handmatig geconfigureerd worden

#### In keycloak
Om in Keycloak een authenticatiedienst toe te voegen dient een Identity Provider aangemaakt te worden.

Voor OpenID Connect zijn de stappen als volgt:
1. Navigeer naar de _Realm_ waar de authenticatiedienst moet worden toegevoegd
2. Klik `Identity Providers`
3. Kies `Add Provider` en dan `OpenID Connect v1.0`
4. Kies een alias waarmee je de autenticatie dienst kunt herkennen, bijv `voorbeeld-ad`
5. Vul het `Discovery endpoint` in met de OIDC-metadata url die je over/van de AD hebt gekregen, 
   bijv. `https://voorbeeld-ad.example.com/.well-known/openid-configuration
6. Voor `Client authentication` kies `JWT signed with private key`
7. Voor `Client ID` vul de `clientId` van de dienst metadata in, bijv: `lutjebroek-bouwvergunningen`