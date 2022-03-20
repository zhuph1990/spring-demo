package com.zph;

import com.zph.aop.service.Calculator;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyAOPTest {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");

    @Test
    public void test01() throws NoSuchMethodException {
        Calculator calculator = context.getBean("myCalculator", Calculator.class);
        calculator.div(1, 1);
        System.out.println(calculator.getClass());
    }


}
