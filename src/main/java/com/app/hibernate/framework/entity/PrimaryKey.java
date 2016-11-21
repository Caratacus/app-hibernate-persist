package com.app.hibernate.framework.entity;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.commons.exceptions.CommonsAssistantException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * <p>
 * 统一表主键
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
@MappedSuperclass
//@JsonFilter("objFilter")
public abstract class PrimaryKey implements Serializable {

    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getTargetObject(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return BeanConverter.convert(t, this);
        } catch (Exception e) {
            throw new CommonsAssistantException("转换对象失败", e);
        }
    }


}
