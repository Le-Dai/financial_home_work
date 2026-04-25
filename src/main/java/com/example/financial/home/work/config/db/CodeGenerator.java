package com.example.financial.home.work.config.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * MyBatis Plus 代码生成器（达梦8专用）
 */
public class CodeGenerator {

    // ===================== 【必填修改】数据库配置 =====================
    private static final String DB_URL = "jdbc:mysql://8.153.76.128:3306/financial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Dl575153.";

    // ===================== 【必填修改】项目包配置 =====================
    private static final String PARENT_PACKAGE = "com.example.financial.home.work";
    private static final String MODULE_NAME = "oilGold"; // 模块名

    // ===================== 【必填修改】表名（可多张） =====================
    private static final String[] TABLES = {"oil_gold"};

    public static void main(String[] args) {
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                // 1. 全局配置
                .globalConfig(builder -> {
                    builder.author("auto-generate")
                            .dateType(DateType.ONLY_DATE)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .disableOpenDir(); // 不打开目录
                })

                // 2. 包配置
                .packageConfig(builder -> {
                    builder.parent(PARENT_PACKAGE)
                            .moduleName(MODULE_NAME)
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            // Mapper.xml 输出路径
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper/" + MODULE_NAME
                            ));
                })

                // 3. 策略配置（核心）
                .strategyConfig(builder -> {
                    // 添加要生成的表
                    builder.addInclude(TABLES)

                            // ===== Entity 配置 =====
                            .entityBuilder()
                            .naming(NamingStrategy.underline_to_camel)      // 表名->驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段->驼峰
                            .idType(IdType.AUTO)                             // 主键自增
                            .enableLombok()                                   // 启用 Lombok
                            .enableTableFieldAnnotation()                     // 字段加注解
                            .enableActiveRecord()                             // 支持 ActiveRecord

                            // ===== Mapper 配置 =====
                            .mapperBuilder()
                            .enableMapperAnnotation()                         // @Mapper 注解
                            .enableBaseResultMap()                           // 生成 ResultMap
                            .enableBaseColumnList()                          // 生成 columnList

                            // ===== Service 配置 =====
                            .serviceBuilder()
                            .formatServiceFileName("%sService")              // UserService
                            .formatServiceImplFileName("%sServiceImpl")      // UserServiceImpl

                            // ===== Controller 配置 =====
                            .controllerBuilder()
                            .enableRestStyle()                               // @RestController
                            .formatFileName("%sController");                 // UserController
                })

                // 4. 模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}