# Hadoop
Hadoop is a reliable, distributed, and scalable platform for storing and analyzing vast amounts of data.

# YARN 
### YARN stands for Yet Another Resource Negotiator. It has two major responsibilities:
- Management of cluster resources such as compute, network, and memory
- Scheduling and monitoring of jobs

### YARN achieves these goals through two long-running daemons: 

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/7e21549e-76bd-447b-95ad-ab7541756cd9)



- Resource Manager:the ultimate authority that arbitrates resources among all the applications in the system. Consist of 2 part
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

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/36140176-2f40-4b99-920a-64464531345c)

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


# HDFS
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/9c6c5fea-1db0-4fd9-8bf9-50d9d1288472)

- Designed for store large file, streaming data access, commodity hardwware
- The clients talk to the Namenode to read or write a file. The Namenode responds with the location of the right Datanodes for the client to send or receive data.
The client then contacts the Datanodes specified by the Namenode for writing or reading data blocks. The clients and the Datanodes communicate directly to avoid making the Namenode a bottleneck.
- Unsuitable for low latency, data access, numerous small files

### HDFS block
- HDFS is not a physical filesystem, but rather a virtual abstraction over distributed disk-based file systems.
- A file in HDFS is logically divided up into HDFS blocks. Each HDFS block is physically made of filesystem blocks.
- The filesystem block is often an integral multiple of disk blocks
- Default hdfs block size : 128MB
- Each HDFS block is stored as a file on a data node and replicated across the cluster equal to the number specified by the property dfs.replication.

### Block replication
- Replication ensures that if one data block becomes corrupted or hardware failure occurs, then the read request can still be fulfilled by another available clone of the block. 
- Hadoop places the first replica on the same node as the client. If the client is runnning outside the cluster, a node is chosen at random. The second replica is placed on a randomly chosen rack different than the first replica. The third replica is placed on a randomly chosen node on the same rack as the second replica. Any further replicas are placed on randomly selected nodes without placing too many replicas in the same rack.

### Namenode
- Namenode maintains the filesystem tree and all metadata for all files and directories in the tree.
- FSimage :  represents the file system’s state after all modifications up to a specific transaction ID.
- EditLog : a transaction journal or log containing records for every change that occurs to the file system’s metadata after the most recent FS image.
- Namenode starts up, it picks the FS image and applies the edit log to get the latest state of the file system’s metadata.
 Next, the Namenode writes the new HDFS state to the FS image and starts normal operation with an empty edit file
- A huge edit log takes longer to process, thus increasing the start-up time for the Namenode.
- Secondary Namenode periodically merges the edit log with FS image so the edit log does not grow beyond a reasonable limit.
- Secondary Namenode is run on a different machine because it requires similar computer and memory resources as the Namenode itself.
- Namenode is a single point of failure
  - using backups
  - using secondary namenode
  - using standby namenode
- Limits by memory of namenode

### Datanode
- Datanode stores the actual data. It stores the data blocks on its local filesystem and sends a block report to the Namenode.
- default value of directory is ＄{hadoop.tmp.dir}/dfs/data.
### Write path
- client wait -> contact to namenode -> namenode check -> return list of datanode -> client write to datanode in portions -> datanode write data to local repository and transferring to next datanode

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/201123e1-d5d4-4141-8136-0fb9bc533ac2)

### Read path
- namenode return list datanode foreach requested block, is sorted by proximity to the client
- If an error occurs when communicating with a Datanode, the next closest Datanode hosting the copy of the data block is tried.
The failed Datanode is remembered so that it does not retrieve any blocks in future.
- The received blocks are tested for corruption by computing their checksums. If the checksum does not match, a replica of the same block is read from a different Datanode.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/70346408-6a12-4b6f-818e-63cc73b7af73)

### High Availability
- Is defined as the ability of a system or system component to be continuously operational for a long period of time
- In HA setup, one Namenode serves client queries and is known as the Active Namenode. The rest are known as standby Namenodes. If the active Namenode experiences a failure, a standby Namenodes takes over.
- standby namenode deploy journal node
  - JournalNodes keep a record of all the changes the active Namenode makes on its namespace.
  -  need more than one JournalNode to record Namenode’s activities because JournalNodes themselves are prone to failure. (N -> the number of failures a JournalNode will tolerate (N-1)/2)
  -  In an HA cluster, the Datanodes send block reports and heartbeats to both the active and standby NameNodes.
  -  JournalNodes must ensure that there can only be one active Namenode that can write to them
-  NFS : standby namenode monitor shared dir (editlog) to apply change

### Distcp tool
- Distcp tool allows for parallel processing of files on the same Hadoop cluster or between two Hadoop clusters.


# Spark
- Spark, the ubiquitous platform for data processing, and has taken over the traditional MapReduce framework.
### Compare to MapReduce
- Iterative job : When disk I/O is involved, job execution time increases manifold when compared to the same data accessed from main memory.
- Interactive analysis: MapReduce read data from disk, increase latency
- Rich API: offering a variety of rich APIs
- Spark excels at an efficient reuse of data by caching it in memory across the cluster, saving costly round-trips to disk

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/52496bd2-f4cc-43a2-8488-710755762ee5)

### Architecture 
- Driver :  the master process that manages the execution of a Spark job
- Excecutors : the slave processes that execute the code assigned to them by the driver process.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/6e4e78f9-a347-42f3-a6cd-a7c88d6bdc36)

### Cluster manager
- The Spark driver negotiates resources with the cluster manager to launch executor processes
- Spark is compatible with 
  -   Hadoop YARN
  -   Apache Mesos
  -   Built-in standalone cluster manager
  -   Kubernetes
  -   Local mode
### Execution modes
- Cluster mode: user submit job to cluster manager -> cluster manager spawn driver and executor on woker node. Both driver and executor live in cluster


- Client mode : driver live in client machine out side the cluster.
The client machine is responsible for maintaining the driver process, while the cluster is responsible for maintaining the executor processes.

### Life circle
- user submit : spark-submit -> spawn process talk to cluster manager (YARN -> RM create spark driver on one node)
- driver start - establish SparkSession when set up Spark cluster (The driver process and the executor processes are collectively referred to as the Spark cluster).
SparkSession is a unified single point of entry to interact with underlying Spark functionality. It allows programming Spark with DataFrame and Dataset APIs.
The SparkSession talks to the cluster manager daemon, in our case the RM, to launch Spark executor processes on worker nodes.
- RM return location of executor process to driver, driver communicate directly with the executor processes.
- The driver assigns tasks to executor processes and job execution begins. Data may be moved around and executors report on their status to the driver.
- The driver exits when the Spark job completes and the cluster manager shuts down the executor processes on behalf of the driver.

## Spark APIs 
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/9ead1abe-9b84-4bbc-b661-f2565a3b3457)

### Resilient Distributed Datasets
- the fundamental data-structure abstraction in Spark.
- Low-level APIs
- Immutable
- partitioned across cluster, can constructed when failure
- discourage working directly
- Spark RDDs can be cached and manually partitioned
- Create RDDs from local collections : parallelize(...) method exposed by the SparkContext
- Create RDDs from data sources : SparkContext
- Creating RDDs from DataFrames & Datasets

### DataFrames
- table with rows and columns
- columns defined type maintained by schema
- data distributed across machine in cluster (partition)
- The number of partitions also dictate the parallelism achieved in a Spark job
- partitions are never manually or individually manipulated

### Datasets 
- A Dataset is a strongly-typed, immutable collection of objects that are mapped to a relational schema.
- Main different with DataFrames :
  -   Schema: dataframe difined schema at runtime. Datasets have a schema defined at compile time. This is achieved by specifying a case class in Scala or a class in Java that defines the structure and data types of each column.
  -   Type-safety: Dataframe are not strongly type. They represent data as generic Rows, which can hold different data types in each column. This allows for flexibility but can lead to potential runtime errors if data types mismatch operations. Datasets are strongly type. Enforcing types at compile time helps prevent errors due to data type mismatches during operations. This improves code reliability and allows for better IDE support with features like autocompletion.
  -   Use-case: Datasets are ideal for production-grade Spark applications where data quality and type safety are crucial. Their strong typing enables faster execution due to optimized code generation by the Spark compiler. DataFrames are a good choice for working with exploratory data analysis or when the data schema might be evolving during processing. Their SQL-like operations make them easy to use for various data manipulations.

### Anatomy of Spark Application

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/0edcc920-debc-4226-a3c6-ebc2390b4977)

- Each Spark job constitutes a directed acyclic graph (DAG) of stages.
- A stage is roughly equivalent to a map or reduce phase in MapReduce.
- stage is split into tasks by the Spark runtime and executed in parallel on partitions of an RDD across the cluster.
- Eeach stage can consist of one of two task types. One is shuffle. The other is result task.
- A task is a combination of data blocks and a set of transformations that will be applied to that data.
If there’s a single partition, there will be one task. If there are ten partitions, there will be ten tasks that can execute in parallel. 
  -   Shuffle map task : similar to the map-side part of the shuffle in MapReduce, run in all stages except the final stage, runs a computation on one RDD partition, writes its output to a new set of partitions, to disk
  -   Result task : run in the last stage and return a result to the user’s program
- transformations are lazily evaluated meaning they are not performed until an action is performed on an RDD.
- Kind of transformations :
  -   Narrow: one partition -> one partition
  -   Wide : many partition -> many partition
- each action corresponds to one Spark job.
- Pipelining: performs as many steps as possible before initiating a write to disk,
any sequence of operations that doesn’t require moving data across nodes, feeds into one another are collapsed into a single stage
- Shuffle persistence : Tasks write to shuffle files on their local disk.  The shuffle files are persisted; they can be reused if another job operates on the same input data or if reduction fails on a node and needs to be executed again.
### Execution of a Spark Application
- DAG scheduler : breaks a job into a directed acyclic graph (DAG) of stages and constituent tasks, skip stages for computing RDDs that have been persisted earlier, considers data locality when requesting placement preference for tasks.
- Task scheduler : responsible for submitting (shuffle or results) tasks from each stage to the cluster, receives a list of tasks and matches them with executors. The executors are assigned tasks in the following preference:
  -   Process-local tasks

  -   Node-local tasks

  -   Rack-local tasks

  -   Arbitrary nonlocal tasks
- If the task fails, it is resubmitted by the task scheduler onto another executor.
- Before executing the task, the executor makes sure jar and file dependencies are up to date
- the task code is deserialized and is received as part of the launch task message
- the task code is executed in the same JVM as the executor
- result is serialized and sent back to the executor backend which forwards it to the driver as a status message update.


# Input & Output format
### Sequence file 
- Can be used as a persistent data-structure to store binary key-value pairs.
- Can be used as a container for smaller files. In the case of storing small files as a sequence file, the names of the files become the key and the value their content.
- Three forms :
  -   Uncompressed
  -   Record compressed

  ![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/43db3afd-5fb2-41f7-9362-523b928cc1c7)

  - Block compressed
  
  ![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/906bcba9-3e8b-4709-9202-703c09712c95)

- Header:  contains metadata information such as the class names of the key and value classes, compression details, user-defined metadata, and the sync marker.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/dc326cff-6520-4a50-a7fd-7870b77e0c37)

- An individual record comprises of four parts:
  -   Record length
  -   key length
  -   key
  -   value
- sync marker:  Sync points make it possible for multiple map tasks to read and processes independent portions of a sequence file

### Read and write Sequence file
- writer = SequenceFile.createWriter(fs, config, path, Text.class, Car.class, SequenceFile.CompressionType.RECORD);
- reader = new SequenceFile.Reader(fs, path, config);

### SerDe
- Serializer/Deserializer
- Encoding an object as a byte stream is called serializing the object.
- serialize an object using a particular technology, the serialized object cannot be deserialized using another technology.
- a serialization system should address the following concerns:
  -   Minimizing Data on Wire
  -   Portability
  -   Versioning
  -   Data Integrity
### Rows vl Columnar databases
- Rows format

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/79bb6ce6-6bf0-4793-98c9-bebe39ed0a72)

- Columnar format:
  -   query times can be significantly reduced if only the required column values are read-in, instead of entire rows.
  -   ability to store a database table in less space than required to store the same table in row format.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/dfc8c7ac-62fe-47c4-a970-762c5ddcc81c)

### Avro
- a data serialization system that allows for remote procedure calls and the fast and compact serialization of data.
- Avro schemas are defined using JSON
- a higher abstraction, the Avro IDL language, lets developers specify schemas in a form readable for people
#### Json schema
- import org.apache.avro.generic.GenericRecord;
- ReadSchema:
  -     Schema schema = new Schema.Parser().parse(new File("./car.avsc"));
- Create record:
  -     GenericRecord porsche = new GenericData.Record(schema);
        porsche.put("make", "Porsche");
        porsche.put("model", "911");
        porsche.put("year", 2019);
        porsche.put("horsepower", 443);
- Write record to file
  -     File file = new File("output/fancyCars.avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(porsche);
        dataFileWriter.close();

- Read avro file
  -     
            public void deserializeAvroFile() throws IOException {
              DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
              DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(new File("./fancyCars.avro"), datumReader);

              Schema schema = dataFileReader.getSchema();
              System.out.println("Data from fancyCars.avro :");
              GenericRecord record = null;
              while (dataFileReader.hasNext()) {
                  record = dataFileReader.next(record);
                  System.out.println(record);
              }
            }
        
- use the avro-tools jar file to compile the schema
  -      java -jar /path/to/avro-tools-1.9.1.jar compile schema <schema file> <destination>
  -      DatumWriter<Car> datumWriter = new SpecificDatumWriter<>(Car.class);
         DataFileWriter<Car> dataFileWriter = new DataFileWriter<>(datumWriter);
         dataFileWriter.create(porsche.getSchema(), file);
  -      Car car = null;
         while (dataFileReader.hasNext()) {
            car = dataFileReader.next(car);
            System.out.println(car.getYear());
         }
#### Avro IDL
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/c0f1f1a1-9807-4d90-8c18-1e65689be2ff)

- Each Avro IDL file defines a single Avro Protocol. When compiled, the output is a JSON format Avro Protocol file with extension .avpr.
### Parquet
- column format, nested
- Interoperability, Space efficiency, Query efficiency
- repetition :
  -   required: exactly one occurrence
  -   optional: 0 or 1 occurrence
  -   repeated: 0 or more occurrences
- One ideas behind Parquet is representing the schema as a tree, where the leaves of the tree always represent primitive types.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/122495e9-8479-445c-b236-c333bada8016)

#### Definition level
- defines the number of optional fields in a path for a column

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/723c65f1-7174-4826-aefe-6c427ad6a820) ![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/5786160c-f1ec-449f-bd18-6aa05c129649)

- The goal is to store the definition levels in as few bits as possible.

#### Repetition level
-  the repetition level is a marker of when to start a new list, and at which level.

![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/f77f552e-ad68-496d-a9c4-c15d0a8d5b54)

#### Reading & writing

# MISC

## Zookeeper
#### Intro
- ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization and group services. 
-  The basic building block of Zookeeper is a znode. A znode can store data (like a file) or have child znodes (like a directory).
-  Zookeeper can be run as a single server in standalone mode or on a cluster of machines in replicated mode, called an ensemble.
High availability in replicated mode is achieved by ensuring modifications to the znodes tree are replicated to a majority of the ensemble.
If a minority of machines in the ensemble fail, at least one live machine in the ensemble will have the latest state
- Zookeeper uses a protocol called Zab to implement this
  -   The first phase involves selecting a leader.
  -   In the second phase, all write requests are forwarded to the leader, which broadcasts the update to followers.
#### Data model
- the reads of and writes to a znode are atomic
- Znodes have an associated access control list (ACL) that governs who can read or write a particular znode.
- sequential znodes which have a sequence number attached as part of the name
- Zookeeper can set watches on znodes that trigger when a znode is changed
#### Consistency
- Sequential Consistency: Updates from a client are applied in order
- Atomicity: Modifications either succeed or fail. There are no partial successes or failures
- Single System Image: A client sees the same view of the system irrespective of the Zookeeper server it connects to an ensemble.
- Durability: Once an update has succeeded, it is persisted.
- Timeliness: The clients view of the system is guaranteed to be up-to-date within a certain time bound

