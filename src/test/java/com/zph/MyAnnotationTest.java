package com.zph;

import com.zph.annotation.controller.PersonController;
import com.zph.annotation.service.StudentService;
import com.zph.annotation.service.TeacherService;
import com.zph.aop.proxy.CalculatorProxy;
import com.zph.aop.service.Calculator;
import com.zph.aop.service.MyCalculator;
import com.zph.aop.service.impl.MyCalculator2;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 注意，给测试类起名字的时候千万不要定义成Test
 * 测试的方法不可以有参数，不可以有返回值
 */
public class MyAnnotationTest {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAnnotation.xml");

    @Test
    public void test01() {
        PersonController personController = context.getBean("personController", PersonController.class);
        personController.save();
    }

    @Test
    public void test02() {

        StudentService studentService = context.getBean("studentService", StudentService.class);
        studentService.save();

        TeacherService teacherService = context.getBean("teacherService", TeacherService.class);
        teacherService.save();
    }

    @Test
    public void test03() throws NoSuchMethodException {

//        MyCalculator myCalculator = new MyCalculator();
//        System.out.println(myCalculator.add(1, 2));
//        System.out.println(myCalculator.div(1, 1));

        Calculator calculator = (Calculator) CalculatorProxy.getProxy(new MyCalculator());
        calculator.add(1, 1);
        calculator.sub(1, 1);
        calculator.mul(1, 1);
        calculator.div(1, 0);
        System.out.println(calculator.getClass());

//        System.out.println("------------------");
//        MyInterface proxy = (MyInterface) CalculatorProxy.getProxy(new MySubClass());
//        proxy.show(100);

    }

    @Test
    public void test04() throws NoSuchMethodException {
        Calculator calculator = context.getBean("myCalculator", Calculator.class);
        calculator.add(1, 1);
        System.out.println(calculator.getClass());
    }

    // 存疑
    @Test
    public void test05() throws NoSuchMethodException {
        MyCalculator2 myCalculator2 = context.getBean(MyCalculator2.class);
        myCalculator2.div(1, 1);
        System.out.println(myCalculator2.getClass());

    }

    @Test
    public void test06() throws NoSuchMethodException {
        MyCalculator2 myCalculator2 = context.getBean("myCalculator2", MyCalculator2.class);
        myCalculator2.add(1,1);
    }

    @Test
    public void test07() throws NoSuchMethodException {
        Calculator calculator = context.getBean("myCalculator", Calculator.class);
        calculator.div(1, 1);
        System.out.println(calculator.getClass());
    }

}
