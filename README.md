# Keycloak

* Downlaod and install keycloak
* Launch it

    ./standalone.sh -Djboss.http.port=8181
    
# Demo Realm

* Import the basic-auth demo realm from scripts directory

# Launch WildFly Swarm server

    mvn wildfly-swarm:run
    
# Call the HTTP Endpoint
    
    http -a admin:password http://localhost:8080/service/echo?value=hello
    
    HTTP/1.1 302 Found
    Cache-Control: no-cache, no-store, must-revalidate
    Connection: keep-alive
    Content-Length: 0
    Date: Wed, 05 Oct 2016 06:28:55 GMT
    Expires: 0
    Location: http://localhost:8181/auth/realms/basic-auth/protocol/openid-connect/auth?response_type=code&client_id=basic-auth-service&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fservice%2Fecho?value%3Dhello&state=6%2F410dce10-9f29-4098-b0ff-e83e62a67577&login=true&scope=openid
    Pragma: no-cache
    Set-Cookie: JSESSIONID=_7tO0TOhgr0adlBf9yvpW_xtBh5Kvh7YiGk20Vua.dabou-macosx; path=/
    Set-Cookie: OAuth_Token_Request_State=6/410dce10-9f29-4098-b0ff-e83e62a67577; HttpOnly

