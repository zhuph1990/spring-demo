package com.zph.xmlconfigation.factory;

import com.zph.xmlconfigation.bean.Person;

/**
 * 实例工厂 友好 易于扩展 兼容性
 */
public class PersonInstanceFactory {

    public Person getInstance(String name) {
        Person person = new Person();
        person.setId(2);
        person.setName(name);
        person.setAge(35);
        return person;
    }
}
