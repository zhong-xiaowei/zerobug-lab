package cn.com.zerobug.demo.activiti.config.manager;

import org.activiti.engine.identity.*;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.impl.persistence.entity.data.AbstractDataManager;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
public class CustomGroupDataManager implements GroupEntityManager {


    @Override
    public Group createNewGroup(String groupId) {
        return null;
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        return null;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return 0;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        return (List<Group>) Optional.ofNullable(getDbSqlSession().selectList("selectIdByUserId", Integer.valueOf(userId)))
                .orElse(Collections.emptyList())
                .stream().<Integer>map(m -> {
                    GroupEntity groupEntity = create();
                    groupEntity.setId(m.toString());
                    groupEntity.setType("assignment");
                    groupEntity.setRevision(1);
                    return groupEntity;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0;
    }

    @Override
    public boolean isNewGroup(Group group) {
        return false;
    }

    @Override
    public GroupEntity create() {
        return null;
    }

    @Override
    public GroupEntity findById(String entityId) {
        return null;
    }

    @Override
    public void insert(GroupEntity entity) {

    }

    @Override
    public void insert(GroupEntity entity, boolean fireCreateEvent) {

    }

    @Override
    public GroupEntity update(GroupEntity entity) {
        return null;
    }

    @Override
    public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(GroupEntity entity) {

    }

    @Override
    public void delete(GroupEntity entity, boolean fireDeleteEvent) {

    }
}