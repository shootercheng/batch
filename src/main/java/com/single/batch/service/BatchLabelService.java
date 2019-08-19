package com.single.batch.service;

import com.single.batch.entity.Label;
import com.single.batch.mapper.BatchLabelMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class BatchLabelService {

    private static final Logger Log = LoggerFactory.getLogger(BatchLabelService.class);

    public final static int INSERT_BATCH = 7000;

    @Autowired
    private BatchLabelMapper batchLabelMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void startGenerate(int number, int index) {
        Log.info("Start Generate Number:"+number +", Index:"+index);
        Runnable syncRunnable = () -> {
            generate(number, index);
        };
        new Thread(syncRunnable).start();

    }


    public void generate(int number, int index) {
        Long stepLast = System.currentTimeMillis();
        Long startTime = stepLast;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tableName = getTableName(index);
        List<Label> labels = new ArrayList<>();
        long[] ids = ThreadLocalRandom.current().longs(1000000000L, 10000000000L).distinct().limit(number).toArray();
        Long stepNow = System.currentTimeMillis();
        stepLast = stepNow;
        Label label;
        
        try (SqlSession session = sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH, false)) {
            BatchLabelMapper batchTableDao = session
                    .getMapper(BatchLabelMapper.class);
            for (int i = 0; i < ids.length; i++) {
                label = new Label();
                label.setId(ids[i]);
                label.setType(0);
                label.setStatus(1);
                label.setCode("" + ids[i]);
                label.setCustomerId(1L);
                label.setOperatorId(1L);
                label.setResellerId(1L);
                label.setProductId(1L);
                label.setBatch(index);
                label.setParent(1L);
                label.setCreateTime(new Date(System.currentTimeMillis()));
                label.setUpdateTime(new Date(System.currentTimeMillis()));
                batchTableDao.insert(tableName, label);
                if (i % INSERT_BATCH == 0 || i == labels.size() - 1) {
                    Log.warn("StartAt:"+df.format(new Date(System.currentTimeMillis())) + " inserting: " + i +"/"+number);
                    session.flushStatements();
                    session.clearCache();
                }
            }
            session.commit();
        }
        stepNow = System.currentTimeMillis();
        Log.warn("TotalUsedTime:" + (stepNow - startTime) + "ms");

    }

    public String getTableName(int index) {
        String tableName = "label_"+index;
        batchLabelMapper.dropTable(tableName);
        batchLabelMapper.createNewTable(tableName);
        return tableName;
    }


}
