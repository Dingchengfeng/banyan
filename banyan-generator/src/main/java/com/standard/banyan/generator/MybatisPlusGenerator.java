package com.standard.banyan.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.util.Collections;

/**
 * @author jigang.duan
 */
@Slf4j
@Component
public class MybatisPlusGenerator extends BaseCmdRunner {

    private static final String[] TABLES = new String[] {
//            "ithings_product",
//            "ithings_model",
//            "map_file",
//            "map_info",
//            "map_push_record"
            "file_detail",
    };

    @Value("${cli.mybatis.outputPath}")
    private String outputPath;

    @Value("${cli.mybatis.author}")
    private String author;

    @Value("${cli.mybatis.modelName}")
    private String dbModelName;

    @Autowired
    private DataSource dataSource;

    @Override
    protected String getCmd() {
        return "mybatis";
    }

    @Override
    protected void doRun(ApplicationArguments args) {
        File outFile = new File(outputPath);
        if (!outFile.exists()) {
            boolean ok = outFile.mkdirs();
            if (!ok) {
                log.info("输出目录 {} 创建不成功", outputPath);
                return;
            }
        }
        log.info("数据库生成模型 输出目录 {}", outFile.getAbsolutePath());
        FastAutoGenerator.create(dataSourceConfig())
                .globalConfig(this::acceptGlobalConfig)
                .packageConfig(this::acceptPackageConfig)
                .strategyConfig(this::acceptStrategyConfig)
                .templateConfig(this::acceptTemplateConfig)
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private DataSourceConfig.Builder dataSourceConfig() {
        HikariDataSource dataSourceMysql = (HikariDataSource) dataSource;
        log.info("数据源 url: {}", dataSourceMysql.getJdbcUrl());
        return new DataSourceConfig.Builder(
                    dataSourceMysql.getJdbcUrl(),
                    dataSourceMysql.getUsername(),
                    dataSourceMysql.getPassword())
                .dbQuery(new MySqlQuery())
//                .schema("riot_ithings")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
    }

    /**
     * 全局配置(GlobalConfig)
     * fileOverride	覆盖已生成文件	默认值:false
     * disableOpenDir	禁止打开输出目录	默认值:true
     * outputDir(String)	指定输出目录	/opt/baomidou/ 默认值: windows:D:// linux or mac : /tmp
     * author(String)	作者名	baomidou 默认值:作者
     * enableKotlin	开启 kotlin 模式	默认值:false
     * enableSwagger	开启 swagger 模式	默认值:false
     * dateType(DateType)	时间策略	DateType.ONLY_DATE 默认值: DateType.TIME_PACK
     * commentDate(String)	注释日期	默认值: yyyy-MM-dd
     * */
    public void acceptGlobalConfig(GlobalConfig.Builder builder) {
        builder.author(author)
//                .enableSwagger()
                .disableOpenDir()
                .fileOverride()
                .outputDir(outputPath);
    }

    /**
     * 包配置(PackageConfig)
     * 方法	说明	示例
     * parent(String)	父包名	默认值:com.baomidou
     * moduleName(String)	父包模块名	默认值:无
     * entity(String)	Entity 包名	默认值:entity
     * service(String)	Service 包名	默认值:service
     * serviceImpl(String)	Service Impl 包名	默认值:service.impl
     * mapper(String)	Mapper 包名	默认值:mapper
     * xml(String)	Mapper XML 包名	默认值:mapper.xml
     * controller(String)	Controller 包名	默认值:controller
     * other(String)	自定义文件包名	输出自定义文件时所用到的包名
     * pathInfo(Map<OutputFile, String>)	路径配置信息	Collections.singletonMap(OutputFile.mapperXml, "D://")
     * */
    private void acceptPackageConfig(PackageConfig.Builder builder) {
        builder.parent("com.standard.banyan." + dbModelName)
                .moduleName("persistent")
                .entity("entity")
                .service("repository")
                .serviceImpl("repository.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .pathInfo(Collections.singletonMap(OutputFile.mapper, outputPath));
    }

    /**
     * 模板配置(TemplateConfig)
     * 方法	说明	示例
     * disable	禁用所有模板
     * disable(TemplateType...)	禁用模板	TemplateType.ENTITY
     * entity(String)	设置实体模板路径(JAVA)	/templates/entity.java
     * entityKt(String)	设置实体模板路径(kotlin)	/templates/entity.java
     * service(String)	设置 service 模板路径	/templates/service.java
     * serviceImpl(String)	设置 serviceImpl 模板路径	/templates/serviceImpl.java
     * mapper(String)	设置 mapper 模板路径	/templates/mapper.java
     * mapperXml(String)	设置 mapperXml 模板路径	/templates/mapper.xml
     * controller(String)	设置 controller 模板路径	/templates/controller.java
     * */
    private void acceptTemplateConfig(TemplateConfig.Builder builder) {
        builder.disable(TemplateType.CONTROLLER);
    }

    /**
     * 策略配置(StrategyConfig)
     * 方法	说明	示例
     * enableCapitalMode	开启大写命名	默认值:false
     * enableSkipView	开启跳过视图	默认值:false
     * disableSqlFilter	禁用 sql 过滤	默认值:true，语法不能支持使用 sql 过滤表的话，可以考虑关闭此开关
     * enableSchema	启用 schema	默认值:false，多 schema 场景的时候打开
     * likeTable(LikeTable)	模糊表匹配(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
     * notLikeTable(LikeTable)	模糊表排除(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
     * addInclude(String...)	增加表匹配(内存过滤)	include 与 exclude 只能配置一项
     * addExclude(String...)	增加表排除匹配(内存过滤)	include 与 exclude 只能配置一项
     * addTablePrefix(String...)	增加过滤表前缀
     * addTableSuffix(String...)	增加过滤表后缀
     * addFieldPrefix(String...)	增加过滤字段前缀
     * addFieldSuffix(String...)	增加过滤字段后缀
     * entityBuilder	实体策略配置
     * controllerBuilder	controller 策略配置
     * mapperBuilder	mapper 策略配置
     * serviceBuilder	service 策略配置
     * */
    private void acceptStrategyConfig(StrategyConfig.Builder builder) {
        // addInclude 设置需要生成的表名
        // addTablePrefix 设置过滤表前缀
        builder.addInclude(TABLES)
                .addTablePrefix("t_", "c_", "fcs_", "ithings_", "fcs_");
        builder.serviceBuilder()
                .formatServiceFileName("%sRepositoryImpl")
                .formatServiceImplFileName("%sRepository");
        builder.entityBuilder()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .logicDeleteColumnName("is_deleted")
                .logicDeletePropertyName("deleteFlag")
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE))
                .addTableFills(new Column("created_by", FieldFill.INSERT))
                .addTableFills(new Column("modified_by", FieldFill.INSERT_UPDATE))
                .formatFileName("%sDO")
                .idType(IdType.AUTO);
    }
}
