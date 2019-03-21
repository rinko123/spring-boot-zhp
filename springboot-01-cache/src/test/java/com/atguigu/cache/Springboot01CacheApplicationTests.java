package com.atguigu.cache;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.DepartmentMapper;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void test() {
        System.out.println("测试。。。。" + employeeMapper.getEmpById(1).toString());
    }


    @Autowired
    StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串的


    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * stringRedisTemplate.opsForValue()[String（字符串）]
     * stringRedisTemplate.opsForList()[List（列表）]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     * stringRedisTemplate.opsForHash()[Hash（散列）]
     * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */
    @Test
    public void testString() {
        //给redis中保存数据
        stringRedisTemplate.opsForValue().append("msg", "hello");    //添加k-v

        stringRedisTemplate.opsForValue().set("msg2", "hello2", 10, TimeUnit.SECONDS);  //添加k-v并设置过期时间

        stringRedisTemplate.expire("msg", 15, TimeUnit.SECONDS);    //给已有的key设置过期时间

        String msg = stringRedisTemplate.opsForValue().get("msg");  //获得指定key的值

        stringRedisTemplate.delete("msg");  //删除指定key

        System.out.println(msg);

    }

    @Test
    public void testList() {

        stringRedisTemplate.opsForList().leftPush("mylist", "1");    //添加元素

        stringRedisTemplate.opsForList().rightPop("mylist");       //删除元素

        stringRedisTemplate.opsForList().index("mylist", 1);  //获得指定下标元素值

        Long msg = stringRedisTemplate.opsForList().size("mylist"); //列表size

        System.out.println(msg);

    }

    @Test
    public void testIncrement() {

        stringRedisTemplate.opsForValue().set("upup", "10");    //设置一个初始值

        stringRedisTemplate.opsForValue().increment("upup", 2);    //将key的upup的值增加2

        String upup = stringRedisTemplate.opsForValue().get("upup");

        System.out.println(upup);

    }

    /**
     * 使用自定义的序列化规则的redisTemplate
     */
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testJsonObj() {
        redisTemplate.opsForValue().set("emp001", new Employee(1, "员工1", "email", 0, null));

        System.out.println(redisTemplate.opsForValue().get("emp001"));
    }

    @Test
    public void testJsonObj2() {
        redisTemplate.opsForValue().set("dept001", new Department(1, "bumeng1"));
        System.out.println(redisTemplate.opsForValue().get("dept001"));
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    RedisCacheManager redisCacheManager;

    @Test
    public void getDeptById2() {

        Department department = departmentMapper.getDeptById(1);
        //获取某个缓存
        Cache abc = redisCacheManager.getCache("abc");

        abc.put("bcd", department); //插入数据

        abc.get("bcd", Department.class);   //查询

    }

}
