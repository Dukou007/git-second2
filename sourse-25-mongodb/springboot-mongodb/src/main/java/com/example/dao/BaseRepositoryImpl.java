package com.example.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wanghong
 * @desc
 * @date: 2017/9/21  10:40
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T,ID> implements BaseRepository<T,ID> {

    protected final MongoOperations mongoTemplate;

    protected final MongoEntityInformation<T, ID> entityInformation;

    private Class<T> clazz;

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoTemplate=mongoOperations;
        this.entityInformation = metadata;
        clazz = entityInformation.getJavaType();
    }

    public T findByIdAndType(Long id,Integer type){
        Criteria criatira = new Criteria();
        criatira.andOperator(Criteria.where("_id").is("id"), Criteria.where("type").is(type));
        T t = mongoTemplate.findOne(new Query(criatira), clazz);
        return t;
    }

    /**
     * @param id  更新主键
     * @param updateFieldMap  key:需要更新的属性  value:对应的属性值
     */
   @Override
    public void update(ID id, Map<String, Object> updateFieldMap) {
        if (updateFieldMap != null || !updateFieldMap.isEmpty()) {
            Criteria criteria = new Criteria("_id").is(id);
            Update update = new Update();
            updateFieldMap.entrySet().forEach(entry -> update.set(entry.getKey(),entry.getValue()));
            mongoTemplate.findAndModify(new Query(criteria), update, clazz);
        }
    }

    /**
     * @param queryParamMap 查询参数
     * @param updateFieldMap  更新参数
     */
    @Override
    public void update(Map<String,Object> queryParamMap, Map<String, Object> updateFieldMap) {
        if (queryParamMap != null || !queryParamMap.isEmpty()){
            List<Criteria> criteriaList = new ArrayList<>();
            for (Map.Entry<String,Object> entry:queryParamMap.entrySet()){
                criteriaList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
            }

            int size = criteriaList.size();
            Criteria[] criterias = new Criteria[size];
            for (int i=0;i<size;i++){
                criterias[i] = criteriaList.get(i);
            }
            Criteria criteria = new Criteria( ).andOperator(criterias);

            if (updateFieldMap != null || !updateFieldMap.isEmpty()) {
                Update update = new Update();
                updateFieldMap.entrySet().forEach(entry -> update.set(entry.getKey(),entry.getValue()));
                mongoTemplate.findAndModify(new Query(criteria), update, clazz);
            }

        }
    }


    /**
     * 分页查询列表
     * @param pageIndex
     * @param pageSize
     * @param sortMap 排序 key:排序字段 value:升序0或降序1
     * @return
     */
   public PageVO<T> findPage(Integer pageIndex, Integer pageSize, Map<String,Integer> sortMap) {
        List<Sort.Order> orders = new ArrayList<>();
        Pageable pageable = null;
        if (sortMap != null && !sortMap.isEmpty()){
            sortMap.entrySet().forEach(entry -> orders.add(new Sort.Order(entry.getValue() == 0 ? Sort.Direction.ASC:Sort.Direction.DESC, entry.getKey())));
            pageable = new PageRequest(pageIndex, pageSize, new Sort(orders));
        }else {
            pageable = new PageRequest(pageIndex, pageSize);
        }
        Page<T> page = findAll(pageable);
        PageVO<T> pageVO = new PageVO<>(pageIndex, pageSize);
        pageVO.setPageData(page.getContent());
        pageVO.setTotal(page.getTotalElements());
        return pageVO;
    }

    /**
     * 带关键字和条件的查询分页
     * @param queryParamMap
     * @param searchKey
     * @param pageIndex
     * @param pageSize
     * @param sortMap
     * @return
     */
    public PageVO<T> findPageWithParam(Map<String,Object> queryParamMap,String searchKey,Integer pageIndex, Integer pageSize, Map<String,Integer> sortMap) {
        if (queryParamMap == null || queryParamMap.isEmpty()) {
            return  findPage(pageIndex, pageSize, sortMap);
        }

        List<Criteria> criteriaList = new ArrayList<>();
        for (Map.Entry<String,Object> entry:queryParamMap.entrySet()){
            criteriaList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
        }
        Criteria criteria1 = Criteria.where("status").is("0");
        criteriaList.add(criteria1);
        if(StringUtils.isEmpty(searchKey)){
            Criteria criteriaPattern = new Criteria();
            Pattern p1 = Pattern.compile(String.format(".*%1$s.*", searchKey), Pattern.CASE_INSENSITIVE);
            criteriaPattern.orOperator( Criteria.where("userName").regex(p1), Criteria.where("title").regex(p1));
            criteriaList.add(criteriaPattern);
        }

        Criteria[] criterias = new Criteria[criteriaList.size()];
        criterias = criteriaList.toArray(criterias);
        Criteria criteria = new Criteria( ).andOperator(criterias);

        Query query = new Query(criteria);

        if (sortMap != null && !sortMap.isEmpty()) {
            List<Sort.Order> orders = new ArrayList<>();
            sortMap.entrySet().forEach(entry -> orders.add(new Sort.Order(entry.getValue() == 0 ? Sort.Direction.ASC:Sort.Direction.DESC, entry.getKey())));
            Sort sort = new Sort(orders);
            query.with(sort);
        }
        long total = this.mongoTemplate.count(query,  clazz);
        query.skip(pageIndex*pageSize);
        query.limit(pageSize);

        List<T> data = this.mongoTemplate.find(query, clazz);

        PageVO<T> pageVO = new PageVO<>();
        pageVO.setPageIndex(pageIndex);
        pageVO.setPageSize(pageSize);
        pageVO.setTotal(total);
        pageVO.setPageData(data);
        return pageVO;
    }

}
