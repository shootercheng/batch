package com.single.batch.controller;

import com.github.shootercheng.common.Constants;
import com.single.batch.mapper.BatchLabelMapper;
import com.single.batch.proxy.LoggerInvocationHandler;
import com.single.batch.proxy.ProxyFactory;
import com.single.batch.service.BatchLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/batch")
public class BatchController {

    private static final Logger Log = LoggerFactory.getLogger(BatchController.class);


    @Autowired
    private BatchLabelService batchLabelService;

    @Autowired
    private BatchLabelMapper batchLabelMapper;


    @RequestMapping("/insert")
    public String generate(
            @RequestParam int number,
            @RequestParam int index
            ){
        batchLabelService.startGenerate(number, index);
        return "generate task starts.";
    }

    @RequestMapping(value = "/mbatis/proxy", method = RequestMethod.GET)
    public Integer testMyBatisProxy() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PADE_QUERY_INDEX, 0);
        map.put(Constants.PAGE_QUERY_SIZE, 10000);
        map.put("tableName", "label_1");
        return batchLabelMapper.countTbSum(map);
    }

    @RequestMapping(value = "/proxy", method = RequestMethod.GET)
    public Integer testProxy() {
        LoggerInvocationHandler<BatchLabelMapper> logger = new LoggerInvocationHandler<>(BatchLabelMapper.class,
                batchLabelMapper);
        BatchLabelMapper batchLabelMapper = ProxyFactory.proxyLogger(logger);
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PADE_QUERY_INDEX, 0);
        map.put(Constants.PAGE_QUERY_SIZE, 10000);
        map.put("tableName", "label_1");
        return batchLabelMapper.countTbSum(map);
    }


}
