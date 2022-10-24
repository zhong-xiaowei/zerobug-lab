package cn.com.zerobug.demo.activiti.config;

import cn.com.zerobug.demo.activiti.mapper.RoleMapper;
import cn.com.zerobug.demo.activiti.config.manager.CustomGroupDataManager;
import cn.com.zerobug.demo.activiti.config.manager.CustomUserDataManager;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    private static final String FONT_NAME = "宋体";

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName(FONT_NAME);
        processEngineConfiguration.setAnnotationFontName(FONT_NAME);
        processEngineConfiguration.setLabelFontName(FONT_NAME);
        processEngineConfiguration.setCustomMybatisMappers(Set.of(RoleMapper.class));
        processEngineConfiguration.setGroupDataManager(new CustomGroupDataManager(processEngineConfiguration));
        processEngineConfiguration.setUserDataManager(new CustomUserDataManager(processEngineConfiguration));
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        SpringProcessEngineConfiguration springProcessEngineConfiguration = baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                transactionManager,
                springAsyncExecutor);
        // 配置自定义的用户和组管理
        springProcessEngineConfiguration.setUserEntityManager(customUserEntityManager);
        springProcessEngineConfiguration.setGroupEntityManager(customGroupEntityManager);

        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(customUserEntityManagerFactory);
        customSessionFactories.add(customGroupEntityManagerFactory);
        springProcessEngineConfiguration.setCustomSessionFactories(customSessionFactories);
        return springProcessEngineConfiguration;
    }

}
