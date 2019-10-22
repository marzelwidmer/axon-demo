Based on:  https://www.youtube.com/watch?v=kFYIrlOU0g8&t=1910s


```                                                                                                             
                               Events -->                                                                          
           +-------------------------------------------+                                                    
           |                                           |                                                    
           |                                           v                                                    
+----------|--------------+                +-----------|-------------+                                          
|                         |                |                         |                                          
|    +----+    +----+     |                |    +----+    +----+     |                                          
|    |    |----|    |     |                |    |    |----|    |     |                                          
|    +----+    +----+     |                |    +----+    +--|-+     |                                          
|      |                  |                |                 |       |                                          
|      |    +----+        |                |         +----+  |       |                                          
|      +----|    |        |                |         |    |--+       |                                          
|           +----+        |                |         +----+          |                                          
|                         |                |                         |                                          
|      Command Model      |                |       Projections       |                                          
+-------------------------+                +-------------------------+                                          
            ^                                           ^                                                       
            |                                           | Query                                                   
 Commands   |               +-----------+               |                                                      
            |               |           |               |                                                      
            +---------------|   Client  |<--------------+                                                       
                            |           |                                                                       
                            +-----------+                                                                       
```                                                                                                              
https://textik.com/#5bcca06cbc09edb8
                                                                                                                
Commands:
  * A command tells our application to do something

Events:
 * An event is a notification of something that _has_ happened.   
                                                                                                                  
Query:
 * Queries could be _simplified_ by storing a copy of the data in a form easily 
 * In many cases, updating the query models can happen asynchronously from processing the transaction: _eventual consistency_
                                                                             
                                                                                                                                                                                      
Projection : 
 * Optimized for the specific read use-cases (e.g. screens, API methods)
 * Many separated ones instead of one big one
 * Use carious technologies as appropriate (RDMS, Elastic, Mongo etc.)
 
 
# Setup Infrastructure

## Axon Server
```bash
docker run -it --rm --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

## Postgresql
```bash
docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=account -e POSTGRES_PASSWORD=secret postgres:12
```

### Connect to Postgresql  
```bash
docker exec -it postgres psql -Uaccount -a account
```
### Select * from table
```sql
SELECT * FROM account;
```

```bash
                  id                  | initial_value | remaining_value
--------------------------------------+---------------+-----------------
 8b76ad5b-e2f8-4411-bc89-2fda39ed3abd |           100 |             100
 b8c5e738-f997-42bf-928b-e32e8c8b182d |           100 |             100
 85ba51f2-a7cf-4b8c-9a7c-2c20543305df |           100 |             100
 376fd5dd-cafe-45fd-9af9-b52f1315d7f6 |           100 |             100
 a7fc627f-9d35-4752-82b8-1f7ff1135e40 |           100 |             100
 1914e15c-ebf1-4977-b2f5-7e9bf26a96f6 |        
   100 |              30
 9377f5c4-be39-493f-9b2e-aebb712015b1 |           100 |              30
 2e655109-3049-4d3c-bd27-0b1c00310513 |           100 |              30
 555fb817-3229-4a86-8b3c-7fe092390a64 |           100 |               0
 adf5be06-fe4b-4bbb-bc70-c1ef1b59d146 |           100 |              30
 e2a91918-6c20-4772-a6ae-ca5a1cbe809c |           100 |               0
 1e7d13b1-6c25-4877-9f2a-3bf10e227abe |           100 |               0
```


# OpenShift

## Create Project
```bash
$ oc new-project axon --display-name="AxonIQ Axon Server"
```

## Create OpenShift Docker Image
On OpenShift are some restriction for this we have to patch the `axoniq/axonserver` Docker image.

### Build Image
```bash
$ docker build --no-cache -t c3smonkey/axonserver-openshift:latest openshift/dockerfiles/
```

### Docker Logim
```bash
$ docker login
```

### Docker Push
```bash
$ docker push c3smonkey/axonserver-openshift:latest
```

## Deploy Axon Server
```bash
$ oc apply -f openshift/axonserver.yaml
```

## Configure Axon Server EndPoint
If you deploy AxonServer on the same Project `namespace` you can configure the Spring Boot application  in the `application.yml` file like below
```yaml
axon:
  axonserver:
    servers: axonserver:8124
```

# Deploy Command Part
Change in the `bank-account-command` Maven directory and deploy the Spring Boot Application with the `Fabric8` Maven Plug-In in the `axon` project (namespace)
```bash
$ mvn fabric8:deploy -Dfabric8.namespace=axon
```


# Deploy Query Part
Change in the `bank-account-query` Maven directory and deploy the Spring Boot Application with the `Fabric8` Maven Plug-In in the `axon` project (namespace)
```bash
$ mvn fabric8:deploy -Dfabric8.namespace=axon
```


## Scale ReplicaSet
```bash
$ oc scale statefulsets axonserver --replicas=2
```