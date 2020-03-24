package com.scd.test;

import com.github.shootercheng.common.Constants;
import com.github.shootercheng.export.BaseExport;
import com.github.shootercheng.export.CsvExport;
import com.github.shootercheng.param.ExportParam;
import com.single.batch.mapper.BatchLabelMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author chengdu
 * @date 2020/2/24
 */
public class CsvExportTest {
    private static SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "mybatis/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


    @Test
    public void testQuery() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PADE_QUERY_INDEX, 0);
        map.put(Constants.PAGE_QUERY_SIZE, 10000);
        map.put("tableName", "label_1");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        int sum = batchLabelMapper.countTbSum(map);
        List<String> list = batchLabelMapper.selectPage(map);
        sqlSession.close();
        Assert.assertEquals(1000000, sum);
        Assert.assertEquals(10000, list.size());
    }

    @Test
    public void testCsvExport() {
        long startTime = System.currentTimeMillis();
        String exportPath = "file" + File.separator + UUID.randomUUID().toString();
        File file = new File(exportPath);
        file.mkdirs();
        String filePath = exportPath + File.separator + "test.csv";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        try (BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {
            ExportParam exportParam = new ExportParam();
            Map<String, Object> searchParam = new HashMap<>(16);
            searchParam.put("tableName", "label_1");
            exportParam.setHeader("id, parent, code, status, type, customer_id, operator_id, reseller_id, user_id, product_id, batch, query_times," +
                    "first_time, create_time, update_time");
            exportParam.setSum(batchLabelMapper.countTbSum(searchParam));
            exportParam.setPageSize(10000);
            exportParam.setRecordSeparator(Constants.CRLF);
            exportParam.setSearchParam(searchParam);
            BaseExport baseExport = new CsvExport(bufferedWriter, exportParam);
            baseExport.exportQueryPage(batchLabelMapper::selectPage);
        } catch (IOException e) {
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        System.out.println("time " + (System.currentTimeMillis() -  startTime) + " ms");
    }
}
