package org.ccs.cdnsign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author chenjh
 */
@SpringBootApplication
@ServletComponentScan
public class CdnsignApplication {
    public static void main(String[] args) {
        SpringApplication.run(CdnsignApplication.class, args);
    }
}