package cn.com.zerobug.demo.mybatisplus.generator;

import cn.com.zerobug.demo.mybatisplus.dataobject.TenantDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhongxiaowei
 */
public class CodeGenerator {

    /**
     * 执行生成
     *
     * @param url              数据库连接地址
     * @param username         数据库名称
     * @param password         数据库密码
     * @param parent           父包
     * @param entitySuperClass 实体父类
     */
    public static void execute(String url, String username, String password, String parent,
                               Class<?> entitySuperClass) {
        String property = System.getProperty("user.dir");
        // 代码生成
        FastAutoGenerator.create(url, username, password)
                // 全局配置
//                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？"))
                .globalConfig((scanner, builder) -> builder.author("zhongxiaowei")
                        .outputDir(property).dateType(DateType.ONLY_DATE)
                        .enableSwagger()
                        .fileOverride())
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(parent);
                })
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(
//                        getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                getTables("all"))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .serviceBuilder()
                        .superServiceClass("cn.com.zerobug.component.orm.IServicePro")
                        .superServiceImplClass("cn.com.zerobug.component.orm.ServiceImplPro")
                        .mapperBuilder().superClass("cn.com.zerobug.component.orm.MapperPro").enableBaseResultMap()
                        .entityBuilder().enableLombok().disableSerialVersionUID().convertFileName(entityName -> entityName + "DO")
                        .addTableFills(
                                new Column("create_by", FieldFill.INSERT),
                                new Column("create_time", FieldFill.INSERT),
                                new Column("update_by", FieldFill.UPDATE),
                                new Column("update_time", FieldFill.UPDATE)
                        ).addSuperEntityColumns("id", "create_by", "create_time",
                                "update_by", "update_time", "is_delete")
                        .logicDeleteColumnName("is_delete")
                        .superClass(entitySuperClass)
                        .build())
                // 模板渲染引擎
                .templateEngine(new CodeTemplateEngine())
                // 执行
                .execute();
    }

    public static void execute(String url, String username, String password, String parent) {
        execute(url, username, password, parent, TenantDO.class);
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
