package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;


//@CacheConfig(cacheManager = "xxx") 有多个cacheManager的话可以在这指定
@SuppressWarnings("ALL")
@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    RedisCacheManager redisCacheManager;

    /**
     * 缓存的数据能存入redis；
     * 第二次从缓存中查询就不能反序列化回来；
     * 存的是dept的json数据;CacheManager默认使用RedisTemplate<Object, Employee>操作Redis
     */
    @Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id) {
        System.out.println("查询部门" + id);
        Department department = departmentMapper.getDeptById(id);
        return department;
    }

}
