package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghong on 2017/9/21
 */
@NoRepositoryBean  //避免spring扫描BaseRepository
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

    void update(ID id, Map<String, Object> updateFieldMap);

    void update(Map<String, Object> queryParamMap, Map<String, Object> updateFieldMap);

    PageVO<T> findPage(Integer pageIndex, Integer pageSize, Map<String, Integer> sortMap);

    T findByIdAndType(Long id, Integer type);

    PageVO<T> findPageWithParam(Map<String, Object> queryParamMap, String searchKey, Integer pageIndex, Integer pageSize, Map<String, Integer> sortMap);
}
