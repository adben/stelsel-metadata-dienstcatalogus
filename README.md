PoC AD metadata en Dienstcatalogus

Deployment op Sandbox

mvn clean install -Dquarkus.kubernetes-client.master-url=xxxxx -Dquarkus.kubernetes-client.token=xxxxx

mvn install -Dquarkus.kubernetes.deploy=true

oc get is

oc get pods

oc get svc

oc expose svc/stelsel-metadata-dienstcatalogus