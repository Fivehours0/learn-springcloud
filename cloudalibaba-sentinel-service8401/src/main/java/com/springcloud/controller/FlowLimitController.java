package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        System.out.println(Thread.currentThread().toString());
        Thread.sleep(3000);
        return "--------A";
    }

    @GetMapping("/testB")
    public String testB() {
        return "--------B";
    }


    @GetMapping("/testHotKey")
    // 这个testHotKey可以是任何名字，只要唯一就行。blockHandler是兜底的方法
    @SentinelResource(value = "testHotKey", blockHandler = "dealTestHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }
    public String dealTestHotKey(String p1, String p2, BlockException exception) {
        // sentinel系统默认的提示是：Blocked by Sentinel (flow limiting)
        return "----------dealTestHotKey";
    }
}
