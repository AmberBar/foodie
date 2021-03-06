package com.amber.foodie.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@Slf4j
public class FlywayConfig {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        // 设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径（表示是src/main/resources/flyway下面，前缀默认为src/main/resources，因为这个路径默认在classpath下面）
        flyway.setLocations("db/migration");
        // 设置sql脚本文件的编码
        flyway.setEncoding("UTF-8");
        flyway.setOutOfOrder(true);
        try {
            flyway.migrate();
        } catch (FlywayException e) {
            flyway.repair();
            log.error("Flyway配置加载出错", e);
        }
    }
}