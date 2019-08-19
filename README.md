# batch

1. create a database named : test

2. run the project:
    
    build it by maven (eg. mvn install).
    
    start the project (eg. java -jar batch-0.0.1-SNAPSHOT.jar)
    
3. generate batch insert by calling the api:
   "http://localhost:8888/batch/insert?number=1000000&index=11"
  
   parameters:
   
      number: how manay rows will you insert in this batch.
      
      index: table name index. (the table will be generated with the name of 'label_$index')
      
      
4. run the same jar in linux server (centos7).


======================


Note:
To change the batch insert method, modify the insert function in class service.BatchLabelService in line 97：

This is batch insert by mybatis:

---  //batchLabelMapper.insertBatch(tableName, labels);

This is insert by ExecutorType.BATCH:

--- batchInsert(tableName, labels);

======================

To change the batch size in class service.BatchLabelService :

public final static int INSERT_BATCH = 100000;

