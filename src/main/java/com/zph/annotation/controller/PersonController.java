package com.zph.annotation.controller;

import com.zph.annotation.dao.PersonDao;
import com.zph.annotation.service.PersonService;
import com.zph.annotation.service.PersonService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
//@Scope(value = "prototype")
public class PersonController {

    /**
     * 通过@AutoWired注解能够完成自动注入的功能
     * 是按照什么方式进行自动注入的呢？
     * 默认情况下是按照ByType来进行装配的,如果找到直接赋值，找不到报错
     * 如果有多个类型一样的bean对象，此时会按照id来进行查找，默认的id是类名首字符小写
     * 如果找到了直接注入，如果找不到那么就报错
     *
     * 如果你想通过名字进行查找，可以自己规定名称，使用注解@Qualifier
     *
     * 使用@Resource可以完成跟@AutoWired相同的功能，但是要注意他们之间的区别
     * 1、@Resource是jdk提供的功能，@AutoWired是spring提供的功能
     * 2、@Resource可以在其他框架中使用，而@AutoWired只能在spring中使用
     *     换句话说：@Resource扩展性好，而@AutoWired支持的框架比较单一
     * 3、@Resource是按照名称进行装配的，如果名字找不到那么就使用类型 找到 多个就报错
     *    而@AutoWired是按照类型进行装配，如果类型找不到那么久使用名字进行查找
     */
    @Resource
    @Autowired
///    @Qualifier("personService")
    private PersonService personService;

    public void save() {
        personService.save();
    }

    /**
     * 当@AutoWired添加到方法上的时候，此方法在创建对象的时候会默认调用，同时方法中的参数会进行自动装配
     *  注解@Qualifier也可以定义在方法的参数列表中,可以指定当前属性的id名称
     * @param personDao123
     */
    @Autowired
    public void test(@Qualifier("personDao") PersonDao personDao123) {
      //  System.out.println("test");
        personDao123.update();
    }

}
