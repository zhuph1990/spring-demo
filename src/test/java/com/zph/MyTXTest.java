package com.zph;

import com.zph.jdbctemplate.service.BookService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.util.Properties;

public class MyTXTest {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void test01() throws FileNotFoundException {
        BookService bean = context.getBean(BookService.class);
        bean.buyBook();
    }

    /**
     * mult ---> buy
     */
    @Test
    public void test02() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        MultService bean = context.getBean(MultService.class);
//        bean.mult();
//        bean.buyBook();
        BookService bean = context.getBean(BookService.class);
        bean.mult();
    }

    /**
     * properties
     */
    @Test
    public void test03() {
        Properties properties = System.getProperties();
        System.out.println(properties);
    }

}
