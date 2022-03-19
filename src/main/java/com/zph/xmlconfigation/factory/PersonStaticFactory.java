package com.zph.xmlconfigation.factory;

import com.zph.xmlconfigation.bean.Person;

/**
 * 静态工厂类
 */
public class PersonStaticFactory {

    public static Person getInstance(String name) {
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        person.setAge(30);
        return person;
    }
}
