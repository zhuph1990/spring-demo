package com.zph;

import com.alibaba.druid.pool.DruidDataSource;
import com.zph.xmlconfigation.bean.Address;
import com.zph.xmlconfigation.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

//容器中的person对象是什么时候创建的
//容器中的对象在容器创建完成之前就已经把对象创建好了
public class MyXMLConfigTest {

    public static void main(String[] args) throws SQLException {
        /*
         * applicationContext:表示IOC容器的入口，想要获取对象的话，必须要创建该类
         *   该类有两个读取配置文件的实现类
         *       ClassPathXmlApplicationContext:表示从classpath中读取数据
         *       FileSystemXmlApplicationContext:表示从当前文件系统读取数据
         * * */
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextXMLConfig.xml");

        //1. 获取具体的bean实例对象，id 强制转换类型
//        Person person = (Person) context.getBean("person");
//        System.out.println(person);

        //2. 获取具体的bean实例对象，id 类名
      //  Person person = context.getBean("person", Person.class);
       // System.out.println(person);

        //3. 根据bean的类型来获取对象
        //注意：当通过类型进行获取的时候，如果存在两个相同类型对象，将无法完成获取工作
      //  Person person = context.getBean(Person.class);
      //  System.out.println(person);


        /**
         * 当需要从容器中获取对象的时候，最好要保留无参构造方法，因为底层的实现是反射
         *
         *  Class clazz = Person.class;
         *  Object obj = clazz.newInstance();默认调用无参的构造方法，此方法已经被弃用
         *  Object obj = clazz.getDeclaredConstructor().newInstance()
         *
         */

        Person person2 = context.getBean("person2", Person.class);
     //   System.out.println(person2);

        Person person3 = context.getBean("person3", Person.class);
      //  System.out.println(person3);

        Person person4 = context.getBean("person4", Person.class);
      //  System.out.println(person4);

        Person person5 = context.getBean("person5", Person.class);
      //  System.out.println(person5);

        Person person6 = context.getBean("person6", Person.class);
     //   System.out.println(person6);

        Address address = context.getBean("address", Address.class);
      //  System.out.println(address);

        Person son = context.getBean("son", Person.class);
     //   System.out.println(son);

        Person parent = context.getBean("parent", Person.class);
     //   System.out.println(parent);

        Person person9 = context.getBean("person9", Person.class);
      //  System.out.println(person9);

        Person person10 = context.getBean("person10", Person.class);
      //  System.out.println(person10);

        Person myFactoryBean = context.getBean("myFactoryBean", Person.class);
       // System.out.println(myFactoryBean);

        Person person11 = context.getBean("person11", Person.class);
       // System.out.println(person11);
        // 手动销毁
       // ((ClassPathXmlApplicationContext)context).close();

        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
//        System.out.println(dataSource);
//        System.out.println(dataSource.getConnection());

        DruidDataSource dataSource2 = context.getBean("dataSource2", DruidDataSource.class);
//        System.out.println(dataSource2);
//        System.out.println(dataSource2.getConnection());

        Person person12 = context.getBean("person12", Person.class);
      //  System.out.println(person12);

      //  Person person13 = context.getBean("person13", Person.class);
      //  System.out.println(person13);

        Person person14 = context.getBean("person14", Person.class);
        System.out.println(person14);

    }
}
