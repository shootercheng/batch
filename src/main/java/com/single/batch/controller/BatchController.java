package com.single.batch.controller;

import com.single.batch.service.BatchLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;

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


    @RequestMapping("/insert")
    public String generate(
            @RequestParam int number,
            @RequestParam int index
            ){
        batchLabelService.startGenerate(number, index);
        return "generate task starts.";
    }
}
