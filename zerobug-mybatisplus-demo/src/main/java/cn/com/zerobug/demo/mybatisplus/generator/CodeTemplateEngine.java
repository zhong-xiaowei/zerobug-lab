package cn.com.zerobug.demo.mybatisplus.generator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.util.MapUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongxiaowei
 */
public class CodeTemplateEngine extends VelocityTemplateEngine {

    @Override
    public AbstractTemplateEngine batchOutput() {
        this.getConfigBuilder().getTableInfoList().forEach(tableInfo ->
                this.validationAnnotations(tableInfo));
        return super.batchOutput();
    }

    protected void validationAnnotations(TableInfo tableInfo) {
        List<TableField> fields = tableInfo.getFields();
        fields.forEach(tableField -> {
            TableField.MetaInfo metaInfo = tableField.getMetaInfo();
            final String lt = "\t";
            final String lnt = "\n\t";
            final JdbcType jdbcType = metaInfo.getJdbcType();
            StringBuffer annotations = new StringBuffer();
            boolean tabLine = false;
            if (!metaInfo.isNullable()) {
                annotations.append(lt);
                if (JdbcType.VARCHAR == jdbcType) {
                    annotations.append("@NotBlank(groups = Create.class)");
                    tableInfo.addImportPackages("javax.validation.constraints.NotBlank");
                } else {
                    annotations.append("@NotNull(groups = Create.class)");
                    tableInfo.addImportPackages("javax.validation.constraints.NotNull");
                }
                tabLine = true;
            }
            if (JdbcType.VARCHAR == jdbcType) {
                annotations.append(tabLine ? lnt : lt);
                annotations.append("@Size(max = ").append(metaInfo.getLength()).append(")");
                tableInfo.addImportPackages("javax.validation.constraints.Size");
            } else if (JdbcType.BIGINT == jdbcType
                    || JdbcType.INTEGER == jdbcType
                    || JdbcType.TINYINT == jdbcType
                    || JdbcType.SMALLINT == jdbcType
                    || JdbcType.BIT == jdbcType
                    || JdbcType.FLOAT == jdbcType
                    || JdbcType.DOUBLE == jdbcType
                    || JdbcType.DECIMAL == jdbcType) {
                annotations.append(tabLine ? lnt : lt);
                annotations.append("@PositiveOrZero");
                tableInfo.addImportPackages("javax.validation.constraints.PositiveOrZero");
                tableInfo.addImportPackages("");
            }
            Map<String, Object> customMap = tableField.getCustomMap();
            if (CollectionUtils.isEmpty(customMap)) {
                customMap = new HashMap<>();
            }
            String va = annotations.toString();
            if (StringUtils.isNotBlank(va)) {
                va += lnt;
                tableInfo.addImportPackages("");
            } else {
                va = lt;
            }
            customMap.put("validationAnnotations", va);
            tableField.setCustomMap(customMap);
        });
    }
}
