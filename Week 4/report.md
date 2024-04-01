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

# Bad mySQL query
- Select * : 
  - For large tables, this can be slow and inefficient. Specify the exact columns you need instead.
  - select * from a -> select x, y from a
- Missing alias :
  - Improves readability and avoids ambiguity in column names.
  - select tableA.x, tableB.z, tableB.y from tableA, tableB where tableA.x = tableB.x -> select A.x, B.z, B.y from tableA as A join tableB as B on A.x = B.x
- Full table scan :
  - Scan the entire table are slow. Should use Where to target specific data
  - SELECT * FROM a  -> SELECT * FROM a WHERE x = 'x'
- Missing parentheses :
  - SELECT
  e.emp_no, e.first_name, e.last_name, s.salary
  FROM employees e
  JOIN salaries s ON e.emp_no = s.emp_no
  WHERE (e.first_name = "Ana" OR e.first_name = "Joey")
  AND s.salary >= 10000
  - Without parentheses, the result will be all the Ana, regardless of salaries, and all the Joey who make at least $10,000.
- Missing limit:
  - SELECT x, y FROM a -> SELECT TOP 100 x, y FROM a

### ACID
ACID stands for Atomicity, Consistency, Isolation, and Durability, is properties of transactions
- Atomicity in a database transaction means that all the changes made during that transaction are treated as one "bundle" of changes. This means that when you are trying to modify your database, it's either all of the changes happen at the same time, or none of them happen at all.
- Consistency in databases means that the data stored in the database is always in a valid and consistent state. For example, if the database contains any constraints such as primary keys, foreign keys, and so on, it should always conform to the rules surrounding the constraint.
- The ability of multiple transactions to execute without interfering with one another is known as Isolation. The isolation level of a transaction determines how the changes made by that transaction are visible to other transactions.
- Once committed, a transaction’s changes are permanent. This means the changes must be recorded such that data won’t be lost in a system crash. 

