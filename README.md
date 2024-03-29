Based on:  https://www.youtube.com/watch?v=kFYIrlOU0g8&t=1910s


![axon-cqrs](axon-cqrs.png)


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
$ oc new-project axon --display-name="AxonIQ Application"
```

## Create OpenShift Docker Image
On OpenShift are some restriction for this we have to patch the `axoniq/axonserver` Docker image.

### Build Axon Docker Image 
Patch Docker Image from Axon to run on Openshift. 
```bash
$ docker build --no-cache -t c3smonkey/axonserver-openshift:latest openshift/dockerfiles/
```

### Docker Login
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

# Deploy Application
Deploy `bank-account-command` and `bank-account-query` Spring Boot Application.
with the `Fabric8` Maven Plug-In in the `axon` project (namespace)
```bash

$ ./mvnw clean fabric8:deploy -pl bank-account-command,bank-account-query -Dfabric8.namespace=axon
```



## Scale ReplicaSet of Axon

```bash
$ oc scale statefulsets axonserver --replicas=2
```



### Check the physical files
Search for `axon-data` PersistentVolumeClaim `PVC`.
```bash
$ oc get pvc
  NAME                     STATUS   VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
  axon-data-axonserver-0   Bound    vol83    500Gi      RWO,RWX                       1h
```

Describe the PersistentVolume `PV` for the _Axon_
```bash
$ oc describe pv/vol83

Name:            vol83
Labels:          <none>
Annotations:     pv.kubernetes.io/bound-by-controller: yes
Finalizers:      [kubernetes.io/pv-protection]
StorageClass:
Status:          Bound
Claim:           axon/axon-data-axonserver-0
Reclaim Policy:  Retain
Access Modes:    RWO,RWX
Capacity:        500Gi
Node Affinity:   <none>
Message:
Source:
    Type:          HostPath (bare host directory volume)
    Path:          /mnt/data/vol83
    HostPathType:
Events:            <none>
```

Then login to your Cluster there you will find the data.
```bash
$  ls -la /mnt/data/vol83
   total 80
   drwxrwxrwx.   3 root       root  4096 Oct 22 11:04 .
   drwxr-xr-x. 202 root       root  4096 Sep  9 17:39 ..
   -rw-r--r--.   1 1000080000 root 69632 Oct 22 11:04 axonserver-controldb.mv.db
   drwxr-xr-x.   2 1000080000 root  4096 Oct 22 11:04 default
```