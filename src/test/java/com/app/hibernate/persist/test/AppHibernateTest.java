package com.app.hibernate.persist.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.app.hibernate.persist.model.AppTestModel;
import com.app.hibernate.persist.utils.HibernateUtil;


/**
 * 测试类
 */
public class AppHibernateTest {
    /**
     * 测试AppHibernateTest
     */
    @Test
    public void test1() throws Exception {
        String hql = HibernateUtil.getListHql(AppTestModel.class);
        System.out.println(hql);
        Assert.assertEquals(" FROM AppTestModel", hql);
    }

    @Test
    public void test2() throws Exception {
        String order = "chenxing1,chenxing2";
        String[] arrays = new String[]{"chenxing1", "chenxing2"};
        List list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        Map map = new HashMap<>();
        map.put("key1", "key1");
        map.put("key2", "key2");
        map.put("key3", new String[]{"chenxing1", "chenxing"});
        map.put("key4", list);
        String hql = HibernateUtil.getCountHql(AppTestModel.class, map);
        System.out.println(hql);
        Assert.assertEquals("SELECT COUNT(0) FROM AppTestModel WHERE key1 = :key1 AND key2 = :key2 AND key3 IN :key3 AND key4 IN :key4", hql);
    }
}
