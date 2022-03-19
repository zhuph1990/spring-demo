package com.zph.xmlconfigation.factory;

import com.zph.xmlconfigation.bean.Person;

import org.springframework.beans.factory.FactoryBean;

/**
 * 此方式是spring创建bean方式的一种补充，用户可以按照需求创建对象，
 * 创建的对象交由spring IOC容器来进行管理，无论是否是单例，都是在
 * 用到的时候才会创建该对象，不用该对象不会创建
 */
public class MyFactoryBean implements FactoryBean<Person> {

    /**
    * 返回获取的bean
    * */
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setId(3);
        person.setName("pique");
        return person;
    }

    /**
     * 获取返回bean的类型
     * @return
     */
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 判断当前bean是否是单例的
     * @return
     */
    public boolean isSingleton() {
        return true;
    }
}
