# MySQL
Logical architecture of MySql: 

![alt text](image.png)

MySQL parses queries to create an internal structure (the parse tree), and then applies a variety of optimizations. To begin, MySQL’s parser breaks the query into tokens and builds a “parse tree” from them. The parser uses MySQL’s SQL grammar to interpret and validate the query

Concurrency: 
- Table Lock : it locks the entire table
- Row Lock : available in the InnoDB and Falcon storage engines. Row locks are implemented in the storage engine

Optimizers: responsible for determining the most efficient way to execute a SQL statement. It evaluates different query execution plans and chooses the one that it estimates will have the lowest cost, where cost is defined as the amount of resources (e.g. CPU, I/O) required to execute the query (Query performance Optimization)

Storage engine: Storage engines are MySQL components that handle the SQL operations for different table types to store and manage information in a database. To determine what storage engine a particular table uses, use the SHOW TABLE STATUS command.
- MyISAM Engine
- MyISAM Merge Engine
- InnoDB Engine
- Memory Engine



## Transactions: A transaction is a group of SQL queries that are treated atomically, as a single unit of work.
![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/f657cd67-2ed9-47f3-b9fb-9842427dde15)
- START TRANSACTION or BEGIN start a new transaction.

- COMMIT commits the current transaction, making its changes permanent.

- ROLLBACK rolls back the current transaction, canceling its changes.

- SET autocommit disables or enables the default autocommit mode for the current session.

### ACID
ACID stands for Atomicity, Consistency, Isolation, and Durability, is properties of transactions
- Atomicity in a database transaction means that all the changes made during that transaction are treated as one "bundle" of changes. This means that when you are trying to modify your database, it's either all of the changes happen at the same time, or none of them happen at all.
- Consistency in databases means that the data stored in the database is always in a valid and consistent state. For example, if the database contains any constraints such as primary keys, foreign keys, and so on, it should always conform to the rules surrounding the constraint.
- The ability of multiple transactions to execute without interfering with one another is known as Isolation. The isolation level of a transaction determines how the changes made by that transaction are visible to other transactions.
- Once committed, a transaction’s changes are permanent. This means the changes must be recorded such that data won’t be lost in a system crash. 

