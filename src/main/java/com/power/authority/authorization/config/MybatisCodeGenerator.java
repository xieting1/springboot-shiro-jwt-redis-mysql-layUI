package com.power.authority.authorization.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 快速集成数据库工具
 * 自动封装生成对象pojo 和 mapper xml文件
 */
public class MybatisCodeGenerator {

    public static void main(String[] args) {
        //驱动类全名
        String driverName = "com.mysql.cj.jdbc.Driver";
        //数据库URL
        String url = "jdbc:mysql://101.200.169.9:3306/xtTest?useUnicode=true&characterEncoding=utf8&serverTimeZone=Shanghai&useSSL=false";
        //数据库账号
        String userName = "xttest";
        //数据库密码
        String password = "6090152@test";
        //作者
        String authorName = "xieting";
        //项目路径
        String projectPath = System.getProperty("user.dir");
        //要生成的表名
        String[] tables = {"sys_role"};
        //table前缀
        String tablePrefix = "sys_";
        String basePackage = "com.power.authority.authorization";
        AutoGenerator generator = new AutoGenerator();
        generator.setDataSource(new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName(driverName)
                .setUrl(url)
                .setUsername(userName)
                .setPassword(password));
        generator.setGlobalConfig(new GlobalConfig()
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(true)
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setOpen(false)
                .setAuthor(authorName)
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setIdType(IdType.ID_WORKER)
        );
        generator.setStrategy(new StrategyConfig()
                .setTablePrefix(tablePrefix)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tables)
                .setRestControllerStyle(true)
                .setEntityLombokModel(false)
        );
        generator.setPackageInfo(new PackageConfig()
                .setParent(basePackage)
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mapping")
        );
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.setTemplate(
                new TemplateConfig().setServiceImpl(null).setService(null).setController(null)
        );
        generator.execute();
    }

}
