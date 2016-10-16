## Secure the REST endpoint
 
The security is managed by Keycloak SSO & the HTTP Basic Auth mode will be used to access to the endopoint
    
* Download and install Keycloak

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

## Open Keycloak console

    open http://localhost:8181/auth

* Uncomment the line within the web.xml file to configure the security mode 
* Re launch WildFly Swarm
```   
    cd security/swarm-rest-secured
    mvn clean wildfly-swarm:run
```

Remark : To test from openshift, change the IP address ->   "auth-server-url": "http://dabou.10.44.178.207.xip.io:8080/auth", //"http://localhost:8180/auth",
    
## Call the HTTP Endpoint
    
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
    
    http -a user:password http://localhost:8080/demo/say/hello
    
## Use the client
    
    cd security/swarm-client-secured
    mvn clean wildfly-swarm:run
    http http://localhost:8380/say/hello
    
## Experimental
       
* Deploy Keycloak SSO
```        
    oc create -f http://repo1.maven.org/maven2/io/fabric8/devops/apps/keycloak/2.2.265/keycloak-2.2.265-openshift.yml
```  
* Hack to mount the volume

This command will create the Persistent volumes which are required for Keycloak as the template will create the Persistent Volume Claim

```
gofabric8 volumes
```

Remark: The version 2.2.265 of keycloak doesn't work !!

* Deploy the Fabric8 Management Templates
```
# gofabric8 deploy --app=logging --version-devops=2.3.28
gofabric8 deploy --app=management --version-devops=2.3.28
oc create -f http://repo1.maven.org/maven2/io/fabric8/devops/apps/fluentd/2.2.251/fluentd-2.2.251-kubernetes.yml
```

Remarks : 
- The zip file containing Fluentd, ElasticSearch, Grafana, Kibana, ... templates is available at this address : https://repo1.maven.org/maven2/io/fabric8/forge/distro/distro/
- Ipaas can be downloaded from here : https://repo1.maven.org/maven2/io/fabric8/ipaas/distro/distro/
- The version of the distro.zip does not match the different releases of each individual template. Take care !
    
    