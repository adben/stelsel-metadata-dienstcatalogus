PoC AD metadata en Dienstcatalogus

Deployment op Sandbox

```shell

mvn clean install -Dquarkus.kubernetes-client.master-url=https://api.sandbox-m2.ll9k.p1.openshiftapps.com:6443 -Dquarkus.kubernetes-client.token=sha256~lkq9TtcnLOemznzjYtABy6XPJ4_ySuJRz44iq7W7hos
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

```shell

oc expose svc/stelsel-metadata-dienstcatalogus
```



