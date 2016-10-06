# Openshift
    
* Create Minishift instance
```
    ./scripts/create_minishift.sh
```    
* Configure Docker env variables
```    
    minishift docker-env
    eval $(minishift docker-env)
```    
* Log on to openshift
```    
    oc login -u admin -p admin
```        
* Build Project & run
```
   mvn clean package fabric8:deploy fabric8:log -Dfabric8.mode=kubernetes
```   
* Call the Rest endpoints
```   
   http $(minishift service swarm-rest --url=true)/say/echo?value=hello
   http $(minishift service swarm-rest --url=true)/say/hello
``` 
       
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


