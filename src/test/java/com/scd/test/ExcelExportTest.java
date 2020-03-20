package com.scd.test;

import com.github.shootercheng.export.BaseExport;
import com.github.shootercheng.export.Constants;
import com.github.shootercheng.export.ExcelQueryExport;
import com.github.shootercheng.param.ExportParam;
import com.single.batch.mapper.BatchLabelMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author James
 */
public class ExcelExportTest {
    private static SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "mybatis/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void testExcel2007Export() {
        long startTime = System.currentTimeMillis();
        String exportPath = "file" + File.separator + UUID.randomUUID().toString();
        File file = new File(exportPath);
        file.mkdirs();
        String filePath = exportPath + File.separator + "test.xlsx";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        ExportParam exportParam = new ExportParam();
        Map<String, Object> searchParam = new HashMap<>(16);
        searchParam.put("tableName", "label_2");
        exportParam.setHeader("id, parent, code, status, type, customer_id, operator_id, reseller_id, user_id, product_id, batch, query_times," +
                "first_time, create_time, update_time");
        exportParam.setSum(batchLabelMapper.countTbSum(searchParam));
        exportParam.setPageSize(10000);
        exportParam.setSheetName("test2007");
        exportParam.setSearchParam(searchParam);
        ExcelQueryExport excelExport = new ExcelQueryExport(filePath, null, false, exportParam);
        excelExport.exportQueryPage(batchLabelMapper::selectPage);
        excelExport.close();
        System.out.println("time " + (System.currentTimeMillis() -  startTime) + " ms");
    }

    @Test
    public void testExcel2007ExportTemplate() {
        long startTime = System.currentTimeMillis();
        String exportPath = "file/template";
        String tempPath = exportPath + File.separator + "template.xlsx";
        String dirPath = exportPath + File.separator + UUID.randomUUID().toString();
        new File(dirPath).mkdirs();
        String filePath = dirPath + File.separator + "test.xlsx";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        ExportParam exportParam = new ExportParam();
        Map<String, Object> searchParam = new HashMap<>(16);
        searchParam.put("tableName", "label_2");
        searchParam.put("code", 1000);
        exportParam.setHeader("id, parent, code, status, type, customer_id, operator_id, reseller_id, user_id, product_id, batch, query_times," +
                "first_time, create_time, update_time");
        exportParam.setSum(batchLabelMapper.countTbSum(searchParam));
        exportParam.setPageSize(10000);
        exportParam.setSheetName("test2007");
        exportParam.setSearchParam(searchParam);
        exportParam.setStartLine(1);
        ExcelQueryExport excelExport = new ExcelQueryExport(tempPath, filePath, null, true, exportParam);
        excelExport.exportQueryPage(batchLabelMapper::selectPage);
        excelExport.close();
        System.out.println("time " + (System.currentTimeMillis() -  startTime) + " ms");
    }

    @Test
    public void testExcel2007ExportTemplatePage() {
        long startTime = System.currentTimeMillis();
        String exportPath = "file/template";
        String templatePath = exportPath + File.separator + "template.xlsx";
        String dirPath = exportPath + File.separator + UUID.randomUUID().toString();
        new File(dirPath).mkdirs();
        String filePath = dirPath + File.separator + "test.xlsx";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        ExportParam exportParam = new ExportParam();
        Map<String, Object> searchParam = new HashMap<>(16);
        searchParam.put("tableName", "label_2");
        searchParam.put("code", 1048588);
        exportParam.setHeader("id, parent, code, status, type, customer_id, operator_id, reseller_id, user_id, product_id, batch, query_times," +
                "first_time, create_time, update_time");
        exportParam.setSum(batchLabelMapper.countTbSum(searchParam));
        exportParam.setPageSize(10000);
        exportParam.setSheetName("test2007");
        exportParam.setSearchParam(searchParam);
        exportParam.setStartLine(1);
        ExcelQueryExport excelExport = new ExcelQueryExport(templatePath, filePath, null, true, exportParam);
        excelExport.exportQueryPage(batchLabelMapper::selectPage);
        excelExport.close();
        System.out.println("time " + (System.currentTimeMillis() -  startTime) + " ms");
    }

    @Test
    public void testExcel2003Export() {
        long startTime = System.currentTimeMillis();
        String exportPath = "file" + File.separator + UUID.randomUUID().toString();
        File file = new File(exportPath);
        file.mkdirs();
        String filePath = exportPath + File.separator + "test.xls";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BatchLabelMapper batchLabelMapper = sqlSession.getMapper(BatchLabelMapper.class);
        ExportParam exportParam = new ExportParam();
        Map<String, Object> searchParam = new HashMap<>(16);
        searchParam.put("code", 500000);
        searchParam.put("tableName", "label_2");
        exportParam.setHeader("id, parent, code, status, type, customer_id, operator_id, reseller_id, user_id, product_id, batch, query_times," +
                "first_time, create_time, update_time");
        exportParam.setSum(batchLabelMapper.countTbSum(searchParam));
        exportParam.setPageSize(10000);
        exportParam.setSheetName("test2003");
        exportParam.setSearchParam(searchParam);
        ExcelQueryExport excelExport = new ExcelQueryExport(filePath, null, false, exportParam);
        excelExport.exportQueryPage(batchLabelMapper::selectPage);
        excelExport.close();
        System.out.println("time " + (System.currentTimeMillis() -  startTime) + " ms");
    }
}
