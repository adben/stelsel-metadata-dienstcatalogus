PoC AD metadata en Dienstcatalogus

## API build & deployment op Sandbox
Zorg dat je ingelogd bent op OpenShift. Gebruik bijv. `Copy login command` in de OpenShift (sandbox) web interface.

```shell
mvn clean install -Dquarkus.kubernetes-client.master-url=xxxxx -Dquarkus.kubernetes-client.token=xxxxx
```

```shell
mvn install -Dquarkus.kubernetes.deploy=true
```

```shell
oc get is
```

```shell
oc get pods
```

```shell
oc get svc
```

Maak een `edge` route.
Dit zorgt ervoor dat de service via HTTPS geexposed wordt, zonder dat we zelf HTTPS setup moeten doen.
```shell
oc create route edge --service=stelsel-metadata-dienstcatalogus
```

## Keycloak deployment op Sandbox
```bash
oc process -f https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/latest/openshift-examples/keycloak.yaml \
-p APPLICATION_NAME=authenticatiedienst-een \
-p KEYCLOAK_ADMIN=admin \
-p KEYCLOAK_ADMIN_PASSWORD=xxxxx \
-p NAMESPACE=AD1 \
| oc create -f -
```