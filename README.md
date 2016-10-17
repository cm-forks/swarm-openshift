# WildFly Swarm Microservices - OpenShift Demo

This demo contains 2 microservices, a REST client and a REST Service. They can be deployed top of OpenShift
and the client will benefit of the Kubernetes Load Balancing feature to call the service from one of the pod exposing it.
By default, only one pod is created but you can scale them to verify the load balancing feature.    
  
# Run the project locally

## Launch WildFly Swarm

    cd swarm-rest && mvn clean package wildfly-swarm:run -DskipTests=true
    
## Call the service
    
    Tool used is httpie (http//httpie.org)
    
    http http://localhost:8080/say/echo?value=hello
    http http://localhost:8080/say/hello
    
    or the scripts
    ./scripts/hit-service-echo.sh
    ./scripts/hit-service-hello.sh
    
## Call the service using Swarm REST Client
     
    cd swarm-client && mvn wildfly-swarm:run 
    http http://localhost:8280/say/hello  

## Check the Swagger Doc

  open http://localhost:8080/swagger/
    
  open http://localhost:8080/swagger-ui/?url=http://localhost:8080/swagger
      
# Deploy on OpenShift
    
* Create a local OpenShift instance on the Developer machine
```
    ./scripts/create_minishift.sh
```    
* Configure Docker env variables 

To access the Docker server from the local machine

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
   cd swarm-rest && mvn clean package fabric8:deploy fabric8:log -Dfabric8.mode=kubernetes -DskipTests=true
   or
   mvn fabric8:run -Dfabric8.mode=kubernetes -DskipTests=true
```   
* Call the Rest endpoint exposed by the Service
```   
   http $(minishift service swarm-rest --url=true)/say/hello
``` 

* Deploy the Swarm REST client
```
cd swarm-client && mvn clean package fabric8:deploy fabric8:log -Dfabric8.mode=kubernetes
```

* Call the Rest endpoint exposed by the Client
```   
   http $(minishift service swarm-client --url=true)/say/hello
``` 

* Increase the number of pods & check load balancing
```
oc scale rc/swarm-rest-1 --replicas=2
```

* Check the load balancing
```
oc get pods
oc logs new_pod created
```
     
         