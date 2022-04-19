# mysql-driver-test

测试在 mysql 的连接串中插入自义定的 cluster id

## 插入规则

|||
|---|---|
|-D 12345.test|cluster-id=12345, db=test|
|-D 12345|cluster-id=12345, db=|
|-D test|cluster-id=test, db=|
|不提供|不合法|

## hack tidb 代码

https://github.com/pingcap/tidb/compare/master...oh-my-tidb:hack-dbname

只允许 cluster-id=42，否则报错

## 测试结果

### mysql client

```
mysql -uroot -h127.0.0.1 -P4000
ERROR 1105 (HY000): cluster id is not privided

mysql -uroot -h127.0.0.1 -P4000 -Dtest
ERROR 1105 (HY000): cluster test not found

mysql -uroot -h127.0.0.1 -P4000 -D1234.test 
ERROR 1105 (HY000): cluster 1234 not found

mysql -uroot -h127.0.0.1 -P4000 -D42.test
mysql> select DATABASE();
+------------+
| DATABASE() |
+------------+
| test       |
+------------+
1 row in set (0.00 sec)

mysql -uroot -h127.0.0.1 -P4000 -D42
mysql> select DATABASE();
+------------+
| DATABASE() |
+------------+
| NULL       |
+------------+
1 row in set (0.00 sec)
```

### Go (go-sql-driver/mysql)

```
connect with 'root@tcp(127.0.0.1:4000)/'
query error: Error 1105: cluster id is not privided
--
connect with 'root@tcp(127.0.0.1:4000)/test'
query error: Error 1105: cluster test not found
--
connect with 'root@tcp(127.0.0.1:4000)/1234.test'
query error: Error 1105: cluster 1234 not found
--
connect with 'root@tcp(127.0.0.1:4000)/42.test'
using db: test
--
connect with 'root@tcp(127.0.0.1:4000)/42'
using db: 
--
```

**以上报错都是 query 阶段报的错，即 sql.Open 会成功，后面 db.Query 才报错，应该跟 Go 的某种 lazy init 有关。**

### Java (JDBC)

```
conn using: jdbc:mysql://docker.for.mac.localhost:4000/
connect error:java.sql.SQLException: cluster id is not privided
--
conn using: jdbc:mysql://docker.for.mac.localhost:4000/test
connect error:java.sql.SQLException: cluster test not found
--
conn using: jdbc:mysql://docker.for.mac.localhost:4000/1234.test
connect error:java.sql.SQLException: cluster 1234 not found
--
conn using: jdbc:mysql://docker.for.mac.localhost:4000/42.test
using db:test
--
conn using: jdbc:mysql://docker.for.mac.localhost:4000/42
using db:null
--
```

### Node (mysql)

```
test conn using db:  test
error connecting: Error: ER_UNKNOWN_ERROR: cluster test not found

test conn using db:
error connecting: Error: ER_UNKNOWN_ERROR: cluster id is not privided

test conn using db:  1234.test
error connecting: Error: ER_UNKNOWN_ERROR: cluster 1234 not found

test conn using db:  42.test
using db:  test

test conn using db:  42
using db:  null
```

### Python (MySQLdb)

```
--
test using db =
connect failed, err: (1105, 'cluster id is not privided')
--
test using db = test
connect failed, err: (1105, 'cluster test not found')
--
test using db = 1234.test
connect failed, err: (1105, 'cluster 1234 not found')
--
test using db = 42.test
using database:  test
--
test using db = 42
using database:  None
```

### PHP

**试了几个 library，都不支持在建立连接时设置DB（即使允许设置，实际执行仍是先连接再 use db）。如果要支持 php，我们需要在建立连接时判断如果没有DB，不直接报错而是再等待下一条消息是 use db。**

### 其他

C++/ruby 没跑起来，目测可以指定 db 应该没问题。
