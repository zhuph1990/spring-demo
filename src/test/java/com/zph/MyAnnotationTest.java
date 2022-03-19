package com.zph;

import com.zph.annotation.controller.BaseController;
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
    public void test03() {

        BaseController baseController= context.getBean("baseController", BaseController.class);
        baseController.save();

    }

}
