package cn.com.zerobug.demo.activiti.config.manager;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.AbstractDataManager;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;

import java.util.List;
import java.util.Map;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
public class CustomUserDataManager extends AbstractDataManager<UserEntity> implements UserDataManager {

    public CustomUserDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public Class<? extends UserEntity> getManagedEntityClass() {
        return UserEntityImpl.class;
    }

    @Override
    public UserEntity create() {
        return new UserEntityImpl();
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        return null;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return 0;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        return null;
    }

    @Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0;
    }

}
