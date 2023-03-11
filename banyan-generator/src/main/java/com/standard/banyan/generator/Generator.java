package com.standard.banyan.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jigang.duan
 */
@Slf4j
@SpringBootApplication
public class Generator {

    public static void main(String[] args) {
        //Step 0. [main thread]这部分代码是最早开始执行的，会早于Spring-boot本身
        String strArgs = String.join("|", args);
        log.info("使用参数启动命令行: {}", strArgs);

        // Step 1. [main thread]启动SpringApplication
        // Step 2. [main thread] Step 1中启动时扫描发现的CommandLineRunner ApplicationRunner都会被执行。
        SpringApplication.run(Generator.class, args);

        // Step 3. [main thread]执行到此。
        log.info("命令行已启动，还有其他吗?");
    }
}
