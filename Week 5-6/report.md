# Hadoop
Hadoop is a reliable, distributed, and scalable platform for storing and analyzing vast amounts of data.

# YARN 
### YARN stands for Yet Another Resource Negotiator. It has two major responsibilities:
- Management of cluster resources such as compute, network, and memory
- Scheduling and monitoring of jobs

### YARN achieves these goals through two long-running daemons: 

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/7e21549e-76bd-447b-95ad-ab7541756cd9)



- Resource Manager:the ultimate authority that arbitrates resources among all the applications in the system. From Hadoop 2.4, A pair of Resource Managers runs in active/standby 
configuration to achieve high availability. If the active Resource Manager dies, then the standby one becomes the active and the cluster continues to function correctly. Consist of 2 part
  -   Applications Manager: is responsible for accepting job submissions and starting a container for an entity called the ApplicationMaster
  -   Scheduler: The scheduler is responsible for allocating resources such as disk, CPU, and network running applications, subject to restrictions imposed by queues and capacity.

- Node Manager: is an agent that runs on every machine in the cluster. It is responsible for launching containers on that machine 
and managing the use of resources by the containers. It reports the usage back to the Scheduler component of the Resource Manager.

### Workflow of YARN application:
- The first step of running a YARN application involves requesting the RM (resource manager) to create an Application Master(AM) process.
- A client submits a job and the RM finds a Node Manager that can launch a container to host the AM process.
The AM process represents the client job/application. It can either run the job itself and return or request for additional resources from the RM.
- In the latter, the RM has Node Managers on other machines launch containers on behalf of the AM process to run the distributed computation.

### Type of YARN application:
- One job per application: simplest model.
- Several jobs per application: This is suitable for running several jobs (maybe related) as a workflow or in a single user session.
The benefit is that containers can be reused within jobs and intermediate data between jobs can be cached in memory.
- Perpetually running application: an application that acts as a coordinator keeps running, even forever and is shared amongst various users.
An always-on application master reduces the latency to execute a job because the overhead of starting an application master is eliminated.

### YARN scheduling
- FIFO Scheduler: the jobs are placed in a queue and executed in the order of their submission.
Isn’t suitable for a shared cluster. A large job could deny smaller jobs from running.
- Capacity Scheduler: defines queues with each queue being allocated a fraction of the cluster resources.
A queue can be divided further into sub-queues in a hierarchicaly. jobs are executed in a FIFO manner.
A job can be destined for a queue using the property mapreduce.job.queuename. If skipped, the job is sent to the default queue.
- Fair Scheduler: attempts to allocate resources fairly among all the running applications.
Queues can also be assigned weights and each queue can have nested child queues.
- Delay scheduler: the scheduler doesn’t loosen the data locality constraints immediately to schedule a container elsewhere, if a container can’t be started on the same node on which the data lives.
In fact, it waits for a configurable delay for an opportunity to start the container on the requested node.

# MapReduce
### Concept:
- MapReduce is a programming model used to process large data sets on a cluster of commodity machines by using a distributed algorithm.
At the core, MapReduce is a divide and conquer approach to solving the challenges of big data. The model consists of two phases:
  -   Map: Users specify a map function that processes a key/value pair to generate a set of intermediate key/value pairs.
  -   Reduce: The reduce phase merges all intermediate values associated with the same intermediate key.

### Characteristics:
- Distributed: The MapReduce is a distributed framework consisting of clusters of commodity hardware which run map or reduce tasks.

- Parallel: The map and reduce tasks always work in parallel.

- Fault tolerant: If any task fails, it is rescheduled on a different node.

- Scalable: It can scale arbitrarily. As the problem becomes bigger, more machines can be added to solve the problem in a reasonable amount of time; the framework can scale horizontally rather than vertically.

### Mapper
- Note that sorting of data happens on the map side, and not on the reduce side.
- Map work on input split. Writes its output to memory in a circular buffer, local disk of the node, 100 MB by default. This intermediate output is never written to HDFS because it is temporary
- Each map task works on a unit of data called the input split (chunk). The HDFS block size (default 128 MB) is considered a good split size
- The framework tries to schedule the map task on the same node that has the input data for that map task.
If the map task can’t be scheduled on the same node where its data or one of its copies resides, the framework tries to pick a node in a rack which has the data.
If that isn’t possible, an off-rack node is chosen to run the map task and an inter-rack data transfer occurs.
### Reducer
- All records for a given key reside in a single partition, allowing a single reduce task to process all data for a given key. Partitions are only created when the number of reducers is greater than one
- The reduce tasks does not wait for all map tasks to finish, but rather starts to copy in parallel from finished map tasks.
- The process of sorting map outputs by key and moving them to the reducers is known as Shuffle and Sort. Sorting happens at the map side; the reduce side copies the sorted data for further processing.
In the shuffle phase, the already sorted output from several mappers is merged.
In the reduce step, the reduction function is invoked for each key and its associated list of values.

### End to End 

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/b13aabf9-2601-456d-a9ea-af92ae7a5d61)
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/6734f275-8561-4bbc-9c4b-afd123c6a190)

- Combiner
- Partitioner
### Flow of MapReduce job
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/867eeb51-da67-4ecd-894c-57cf16207e57)

- B1. Submit job  job.waitForCompletion(true);
- B2. Get new application ID, sanity check
- B3. Copy resource to HDFS
- B4. submitApplication()
- B5. RM -> Scheduler. A container is created for the ApplicationMaster. An ApplicationMaster is an entity responsible for managing the lifecycle of a job
- B6. AM initializes object tracks the progress of the map and reduce tasks
- B7. The ApplicationMaster then needs to decide to run the map and reduce tasks in the same JVM as itself or request containers so the tasks can run in parallel.
 If a job is executed in the same JVM as the ApplicationMaster, it is uberized and the tasks are referred to as uber tasks.
- B8. If a job can’t run as an uber job, then the ApplicationMaster requests containers for all the map and reduce tasks from the resource manager.
Requests for reduce tasks are not made by the ApplicationMaster until 5% of the map tasks have finished. Reduce tasks can be run anywhere on the cluster.
In contrast, the map tasks are constrained by data locality requirements, which the Scheduler tries to satisfy
- B9. Start containers

### Resiliency
- Task failure: AM reschedules task for execution on a different node manager if possible.
- AM failure: RM receives periodic heartbeats from ApplicationMaster and can detect when the ApplicationMaster fails.
The Resource Manager restarts the ApplicationMaster in a different container managed by the Node Manager.
- Node manager: Resource Manager restarts any failed tasks or ApplicationMasters running on the failed Node Manager on other, healthy nodes.
However, map tasks completed on the failed node must be re-run.
- RM: Usually, two Resource Managers are run together as a pair in an active/standby configuration.
