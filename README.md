# Keycloak

* Download and install keycloak

```
curl -O https://downloads.jboss.org/keycloak/2.2.1.Final/keycloak-2.2.1.Final.tar.gz
tar -vxf keycloak-2.2.1.Final.tar.gz
```    

* Start server, import the basicauth realm  & launch it
``` 
cd keycloak-2.2.1.Final/bin
./standalone.sh -Djboss.http.port=8181 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=/Users/chmoulli/Google-Drive/REDHAT/RH-GP/Presentations/rest-security/scripts/basicauthrealm.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING
ctrl-c
./standalone.sh -Djboss.http.port=8181
```    
# Open Keycloak console

    open http://localhost:8181/auth
    
# Launch WildFly Swarm server

    mvn clean wildfly-swarm:run
    
# Call the HTTP Endpoint
    
    Tool used is httpie (http//httpie.org)
    
    http -a admin:password http://localhost:8080/service/echo?value=hello
    
    HTTP/1.1 200 OK
    Cache-Control: no-cache, no-store, must-revalidate
    Connection: keep-alive
    Content-Length: 16
    Content-Type: application/octet-stream
    Date: Wed, 05 Oct 2016 09:35:01 GMT
    Expires: 0
    Pragma: no-cache
    
    Hello Sébastien


