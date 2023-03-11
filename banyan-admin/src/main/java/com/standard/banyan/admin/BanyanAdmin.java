package com.standard.banyan.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jigang.duan
 */
@EnableAdminServer
@SpringBootApplication
public class BanyanAdmin {

    public static void main(String[] args) {
        SpringApplication.run(BanyanAdmin.class, args);
    }
}
