 # Launch WildFly Swarm server

    cd swarm-rest
    mvn clean wildfly-swarm:run
    
 # Call the service
    
    Tool used is httpie (http//httpie.org)
    
    http http://localhost:8080/say/echo?value=hello
    http http://localhost:8080/say/hello
    
# To secure the endpoint using Keycloak & basic_auth
    
* Download and install keycloak

```
curl -O https://downloads.jboss.org/keycloak/2.2.1.Final/keycloak-2.2.1.Final.tar.gz
rm -rf keycloak-2.2.1.Final
tar -vxf keycloak-2.2.1.Final.tar.gz
```    

* Start server, import the basicauth realm  & launch it
``` 
cd keycloak-2.2.1.Final/bin
./add-user-keycloak.sh -u admin -p admin -r master
./standalone.sh -Djboss.http.port=8181 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=/Users/chmoulli/Google-Drive/REDHAT/RH-GP/Presentations/rest-security/scripts/basicauthrealm.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING
```    

# Open Keycloak console

    open http://localhost:8181/auth

* Uncomment the line within the web.xml file to configure the security mode 
* Re launch WildFly Swarm
```   
    cd swarm-rest
    mvn clean wildfly-swarm:run
```
    
# Call the HTTP Endpoint
    
* Pass as parameter the user & password to be used
    
    Tool used is httpie (http//httpie.org)
    
    http -a user:password http://localhost:8080/say/echo?value=hello
    
    HTTP/1.1 200 OK
    Cache-Control: no-cache, no-store, must-revalidate
    Connection: keep-alive
    Content-Length: 16
    Content-Type: application/octet-stream
    Date: Wed, 05 Oct 2016 09:35:01 GMT
    Expires: 0
    Pragma: no-cache
    
    Hello Sébastien
    
    http -a user:password http://localhost:8080/say/hello