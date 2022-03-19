

# 1 Spring介绍

官网地址：https://spring.io/projects/spring-framework#overview

压缩包下载地址：https://repo.spring.io/release/org/springframework/spring/

源码地址：https://github.com/spring-projects/spring-framework

参考文档：https://github.com/DocsHome/spring-docs

```tex
Spring makes it easy to create Java enterprise applications. It provides everything you need to embrace the Java language in an enterprise environment, with support for Groovy and Kotlin as alternative languages on the JVM, and with the flexibility to create many kinds of architectures depending on an application’s needs. As of Spring Framework 5.1, Spring requires JDK 8+ (Java SE 8+) and provides out-of-the-box support for JDK 11 LTS. Java SE 8 update 60 is suggested as the minimum patch release for Java 8, but it is generally recommended to use a recent patch release.

Spring supports a wide range of application scenarios. In a large enterprise, applications often exist for a long time and have to run on a JDK and application server whose upgrade cycle is beyond developer control. Others may run as a single jar with the server embedded, possibly in a cloud environment. Yet others may be standalone applications (such as batch or integration workloads) that do not need a server.

Spring is open source. It has a large and active community that provides continuous feedback based on a diverse range of real-world use cases. This has helped Spring to successfully evolve over a very long time.

Spring 使创建 Java 企业应用程序变得更加容易。它提供了在企业环境中接受 Java 语言所需的一切，并支持 Groovy 和 Kotlin 作为 JVM 上的替代语言，并可根据应用程序的需要灵活地创建多种体系结构。 从 Spring Framework 5.0 开始，Spring 需要 JDK 8(Java SE 8+)，并且已经为 JDK 9 提供了现成的支持。

Spring支持各种应用场景， 在大型企业中, 应用程序通常需要运行很长时间，而且必须运行在 jdk 和应用服务器上，这种场景开发人员无法控制其升级周期。 其他可能作为一个单独的jar嵌入到服务器去运行，也有可能在云环境中。还有一些可能是不需要服务器的独立应用程序(如批处理或集成的工作任务)。

Spring 是开源的。它拥有一个庞大而且活跃的社区，提供不同范围的，真实用户的持续反馈。这也帮助Spring不断地改进,不断发展。
```

## 1.1 核心解释

​		spring是一个开源框架。

​		spring是为了简化企业开发而生的，使得开发变得更加优雅和简洁。

​		spring是一个**IOC**和**AOP**的容器框架。

​				IOC：控制反转

​				AOP：面向切面编程

​				容器：包含并管理应用对象的生命周期，就好比用桶装水一样，spring就是桶，而对象就是水

## 1.2 使用spring的优点

​		1、Spring通过DI、AOP和消除样板式代码来简化企业级Java开发

​		2、Spring框架之外还存在一个构建在核心框架之上的庞大生态圈，它将Spring扩展到不同的领域，如Web服务、REST、移动开发以及NoSQL

​		3、低侵入式设计，代码的污染极低

​		4、独立于各种应用服务器，基于Spring框架的应用，可以真正实现Write Once,Run Anywhere的承诺

​		5、Spring的IoC容器降低了业务对象替换的复杂性，提高了组件之间的解耦

​		6、Spring的AOP支持允许将一些通用任务如安全、事务、日志等进行集中式处理，从而提供了更好的复用

​		7、Spring的ORM和DAO提供了与第三方持久层框架的的良好整合，并简化了底层的数据库访问

​		8、Spring的高度开放性，并不强制应用完全依赖于Spring，开发者可自由选用Spring框架的部分或全部

## 1.3 如何简化开发

​		基于POJO的轻量级和最小侵入性编程

​		通过依赖注入和面向接口实现松耦合

​		基于切面和惯例进行声明式编程

​		通过切面和模板减少样板式代码

## 1.4 Spring的模块划分图

![overview](.\document\images\spring-overview.png)

```
模块解释：
Test:Spring的单元测试模块
Core Container:核心容器模块
AOP+Aspects:面向切面编程模块
Instrumentation:提供了class instrumentation支持和类加载器的实现来在特定的应用服务器上使用,几乎不用
Messaging:包括一系列的用来映射消息到方法的注解,几乎不用
Data Access/Integration:数据的获取/整合模块，包括了JDBC,ORM,OXM,JMS和事务模块
Web:提供面向web整合特性
```

# 2 SpringIOC介绍

Inversion of Control 控制反转

## 2.1 为什么引入IOC

创建一个普通的java项目，完成下述功能

UserDao.java

```java
package com.zph.dao;

public interface UserDao {
    public void getUser();
}
```

UserDaoImpl.java

```java
package com.zph.dao.impl;

import com.zph.dao.UserDao;

public class UserDaoImpl  implements UserDao {
    @Override
    public void getUser() {
        System.out.println("获取用户数据");
    }
}
```

UserService.java

```java
package com.zph.service;

public interface UserService {
    public void getUser();
}
```

UserServiceImpl.java

```java
package com.zph.service.impl;

import com.zph.dao.UserDao;
import com.zph.dao.impl.UserDaoImpl;
import com.zph.dao.impl.UserDaoMysqlImpl;
import com.zph.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

SpringDemoTest.java

```java
package com.zph.test;

import com.zph.service.UserService;
import com.zph.service.impl.UserServiceImpl;

public class SpringDemoTest {
    public static void main(String[] args) {
       UserService service = new UserServiceImpl();
       service.getUser();
    }
}
```

在之前的代码编写过程中，我们都是这么完成我们的功能的，但是如果增加一个UserDao的实现类呢？

UserDaoMysqlImpl.java

```java
package com.zph.dao.impl;

import com.zph.dao.UserDao;

public class UserDaoMysqlImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("mysql");
    }
}
```

如果我们想要使用mysql的话，那么就必须要修改UserServiceImpl.java的代码：

```java
package com.zph.service.impl;

import com.zph.dao.UserDao;
import com.zph.dao.impl.UserDaoImpl;
import com.zph.dao.impl.UserDaoMysqlImpl;
import com.zph.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

但是如果我们再增加一个oracle的类呢？

UserDaoOracleImpl.java

```java
package com.zph.dao.impl;

import com.zph.dao.UserDao;

public class UserDaoOracleImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("oracle");
    }
}
```

此时UserService还是要继续修改，很显然这样的方式已经不适用于我们的需求了，那么怎么解决呢，可以使用如下的方式

UserServiceImpl.java

```java
package com.zph.service.impl;

import com.zph.dao.UserDao;
import com.zph.dao.impl.UserDaoImpl;
import com.zph.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

测试类SpringDemoTest.java

```java
package com.zph.test;

import com.zph.dao.impl.UserDaoMysqlImpl;
import com.zph.dao.impl.UserDaoOracleImpl;
import com.zph.service.UserService;
import com.zph.service.impl.UserServiceImpl;

public class SpringDemoTest {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(new UserDaoMysqlImpl());
        userService.getUser();

        userService.setUserDao(new UserDaoOracleImpl());
        userService.getUser();
    }
}
```

其实从刚刚的代码中，大家应该能体会解耦的重要性了，下面我们就开始学习Spring的IOC。

## 2.2 IOC初识

​		想要搞明白IOC，那么需要搞清楚如下几个问题：

```
1、谁控制谁
2、控制什么
3、什么是反转
4、哪些方面被反转
```

### 2.2.1 基本概念

```
IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.
IOC与大家熟知的依赖注入同理，这是一个通过依赖注入对象的过程也就是说，它们所使用的对象，是通过构造函数参数，工厂方法的参数或这是从工厂方法的构造函数或返回值的对象实例设置的属性，然后容器在创建bean时注入这些需要的依赖。 这个过程相对普通创建对象的过程是反向的（因此称之为IoC），bean本身通过直接构造类来控制依赖关系的实例化或位置，或提供诸如服务定位器模式之类的机制。
```

如果这个过程比较难理解的话，那么可以想象自己找女朋友和婚介公司找女朋友的过程。如果这个过程能够想明白的话，那么我们现在回答上面的问题

```
1、谁控制谁：在之前的编码过程中，都是需要什么对象自己去创建什么对象，有程序员自己来控制对象，而有了IOC容器之后，就会变成  由IOC容器来控制对象
2、控制什么：在实现过程中所需要的对象及需要依赖的对象
3、什么是反转：在没有IOC容器之前我们都是在对象中主动去创建依赖的对象，这是正转的，而有了IOC之后，依赖的对象直接由IOC容器创建后注入到对象中，由主动创建变成了被动接受，这是反转
4、哪些方面被反转：依赖的对象
```

### 2.2.2 DI与IOC

```
很多人把IOC和DI说成一个东西，笼统来说的话是没有问题的，但是本质上还是有所区别的,希望大家能够严谨一点，IOC和DI是从不同的角度描述的同一件事，IOC是从容器的角度描述，而DI是从应用程序的角度来描述，也可以这样说，IOC是设计思想，而DI是具体的实现方式
```

### 2.2.3 总结

​		在此处总结中，希望大家能够能够明白两件事：

​		**1、解耦**

​		在面向对象设计的软件系统中，底层的实现都是由N个对象组成的，所有的对象通过彼此的合作，最终实现系统的业务逻辑。

![](.\document\images\耦合对象.jpg)

​		需要注意的是，在这样的组合关系中，一旦某一个对象出现了问题，那么其他对象肯定回有所影响，这就是耦合性太高的缘故，但是对象的耦合关系是无法避免的，也是必要的。随着应用程序越来越庞大，对象的耦合关系可能越来越复杂，经常需要多重依赖关系，因此，无论是架构师还是程序员，在面临这样的场景的时候，都需要减少这些对象的耦合性。

![](.\document\images\2011052709390013.jpg)

​		耦合的关系不仅仅是对象与对象之间，也会出现在软件系统的各个模块之间，是我们需要重点解决的问题。而为了解决对象之间的耦合度过高的问题，我们就可以通过IOC来实现对象之间的解耦，spring框架就是IOC理论最最广泛的应用。

![](.\document\images\ioc.jpg)

​		从上图中可以看到，当引入了第三方的容器之后，几个对象之间就没有了耦合关系，全部对象都交由容器来控制，这个容器就相当于粘合剂，将系统的对象粘合在一起发挥作用。

​        高内聚 低耦合

​		**2、生态**

​		任何一个语言或者任何一个框架想要立于不败之地，那么很重要的就是它的生态。

## 2.3 SpringIOC基本使用

​		通过前面的介绍我们已经知道了Spring中非常重要的一个特性就是IOC,下面我们将要来看一下如何使用IOC容器，帮助大家更好的体会spring的优势。

### 2.3.1 spring_helloworld

##### **(1) 使用手动加载jar包的方式实现，分为三个步骤，现在几乎不用**

- **导包：导入这五个包即可**

  commons-logging-1.2.jar
  spring-beans-5.2.3.RELEASE.jar
  spring-context-5.2.3.RELEASE.jar
  spring-core-5.2.3.RELEASE.jar
  spring-expression-5.2.3.RELEASE.jar

- **写配置**

  Person.java

  ```java
  package com.zph.bean;
  
  public class Person {
      private int id;
      private String name;
      private int age;
      private String gender;
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
      public String getGender() {
          return gender;
      }
  
      public void setGender(String gender) {
          this.gender = gender;
      }
  
      @Override
      public String toString() {
          return "Person{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", age=" + age +
                  ", gender='" + gender + '\'' +
                  '}';
      }
  }
  ```
  

ioc.xml

```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--注册一个对象，spring回自动创建这个对象-->
      <!--
      一个bean标签就表示一个对象
      id:这个对象的唯一标识
      class:注册对象的完全限定名
      -->
      <bean id="person" class="com.zph.bean.Person">
          <!--使用property标签给对象的属性赋值
          name:表示属性的名称
          value：表示属性的值
          -->
          <property name="id" value="1"></property>
          <property name="name" value="zhangsan"></property>
          <property name="age" value="18"></property>
          <property name="gender" value="男"></property>
      </bean>
  </beans>
```

- **测试**

SpringDemoTest.java

```java
package com.zph.test;

import com.zph.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemoTest {
    public static void main(String[] args) {
        //ApplicationContext:表示ioc容器
        //ClassPathXmlApplicationContext:表示从当前classpath路径中获取xml文件的配置
        //根据spring的配置文件来获取ioc容器对象
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        Person person = (Person) context.getBean("person");
        System.out.println(person);
    }
}
```

##### **(2)使用maven的方式来构建项目**

- **创建maven项目**

  定义项目的groupId、artifactId

- **添加对应的pom依赖**

  pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.zph</groupId>
      <artifactId>spring_demo</artifactId>
      <version>1.0-SNAPSHOT</version>
  
      <dependencies>
          <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-context</artifactId>
              <version>5.2.3.RELEASE</version>
          </dependency>
      </dependencies>
  </project>
  ```

- **编写代码**

  Person.java

  ```java
  package com.zph.bean;
  public class Person {
      private int id;
      private String name;
      private int age;
      private String gender;
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
      public String getGender() {
          return gender;
      }
  
      public void setGender(String gender) {
          this.gender = gender;
      }
  
      @Override
      public String toString() {
          return "Person{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", age=" + age +
                  ", gender='" + gender + '\'' +
                  '}';
      }
  }
  ```

- **测试**

  MyTest.java

```java
import com.zph.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        Person person = (Person) context.getBean("person");
        System.out.println(person);
    }
}
```

**总结：**

​		以上两种方式创建spring的项目都是可以的，但是在现在的企业开发环境中使用更多的还是maven这样的方式，无须自己处理jar之间的依赖关系，也无须提前下载jar包，只需要配置相关的pom即可，因此推荐大家使用maven的方式，具体的maven操作大家可以看maven的详细操作文档。

​		**搭建spring项目需要注意的点：**

​		1、一定要将配置文件添加到类路径中，使用idea创建项目的时候要放在resource目录下

​		2、导包的时候别忘了commons-logging-1.2.jar包

​		**细节点：**

​		1、ApplicationContext就是IOC容器的接口，可以通过此对象获取容器中创建的对象

​		2、对象在Spring容器默认是在容器创建完成的时候就已经创建完成，不是需要用的时候才创建

​		3、对象在IOC容器中存储的时候都是单例的，如果需要多例需要修改属性

​		4、创建对象给属性赋值的时候是通过setter方法实现的

​		5、对象的属性是由setter/getter方法决定的，而不是定义的成员属性

### 2.3.2 spring对象的获取及属性赋值方式

##### 		**1、通过bean的id获取IOC容器中的对象（上面已经用过）**

##### 		**2、通过bean的类型获取对象**

​		MyTest.java

```java
import com.zph.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }
}
```

注意：通过bean的类型在查找对象的时候，在配置文件中不能存在两个类型一致的bean对象，如果有的话，可以通过如下方法

MyTest.java

```java
import com.zph.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }
}
```

##### 		**3、通过构造器给bean对象赋值**

ioc.xml

```xml
	<!--给person类添加构造方法-->
	<bean id="person2" class="com.zph.bean.Person">
        <constructor-arg name="id" value="1"></constructor-arg>
        <constructor-arg name="name" value="lisi"></constructor-arg>
        <constructor-arg name="age" value="20"></constructor-arg>
        <constructor-arg name="gender" value="女"></constructor-arg>
    </bean>

	<!--在使用构造器赋值的时候可以省略name属性，但是此时就要求必须严格按照构造器参数的顺序来填写了-->
	<bean id="person3" class="com.zph.bean.Person">
        <constructor-arg value="1"></constructor-arg>
        <constructor-arg value="lisi"></constructor-arg>
        <constructor-arg value="20"></constructor-arg>
        <constructor-arg value="女"></constructor-arg>
    </bean>

	<!--如果想不按照顺序来添加参数值，那么可以搭配index属性来使用-->
    <bean id="person4" class="com.zph.bean.Person">
        <constructor-arg value="lisi" index="1"></constructor-arg>
        <constructor-arg value="1" index="0"></constructor-arg>
        <constructor-arg value="女" index="3"></constructor-arg>
        <constructor-arg value="20" index="2"></constructor-arg>
    </bean>
	<!--当有多个参数个数相同，不同类型的构造器的时候，可以通过type来强制类型-->
	将person的age类型设置为Integer类型
	public Person(int id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        System.out.println("Age");
    }

    public Person(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        System.out.println("gender");
    }
	<bean id="person5" class="com.zph.bean.Person">
        <constructor-arg value="1"></constructor-arg>
        <constructor-arg value="lisi"></constructor-arg>
        <constructor-arg value="20" type="java.lang.Integer"></constructor-arg>
    </bean>
	<!--如果不修改为integer类型，那么需要type跟index组合使用-->
	 <bean id="person5" class="com.zph.bean.Person">
        <constructor-arg value="1"></constructor-arg>
        <constructor-arg value="lisi"></constructor-arg>
        <constructor-arg value="20" type="int" index="2"></constructor-arg>
    </bean>
```

转换器

![1647559523058](JavaFramework.assets/1647559523058.png)



##### 		**4、通过命名空间为bean赋值，简化配置文件中属性声明的写法**

​		1、导入命名空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
```

​		2、添加配置

```xml
    <bean id="person6" class="com.zph.bean.Person" p:id="3" p:name="wangwu" p:age="22" p:gender="男"></bean>
```

##### 		**5、为复杂类型进行赋值操作**

​		在之前的测试代码中，我们都是给最基本的属性进行赋值操作，在正常的企业级开发中还会遇到给各种复杂类型赋值，如集合、数组、其他对象等。

​		Person.java

```java
package com.zph.bean;

import java.util.*;

public class Person {
    private int id;
    private String name="dahuang";
    private int age;
    private String gender;
    private Address address;
    private String[] hobbies;
    private List<Book> books;
    private Set<Integer> sets;
    private Map<String,Object> maps;
    private Properties properties;

    public Person(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        System.out.println("有参构造器");
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        System.out.println("Age");
    }

    public Person(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        System.out.println("gender");
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public Set<Integer> getSets() {
        return sets;
    }

    public void setSets(Set<Integer> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address=" + address +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", books=" + books +
                ", sets=" + sets +
                ", maps=" + maps +
                ", properties=" + properties +
                '}';
    }
}
```

Book.java

```java
package com.zph.bean;

public class Book {
    private String name;
    private String author;
    private double price;

    public Book() {
    }

    public Book(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}
```

Address.java

```java
package com.zph.bean;

public class Address {
    private String province;
    private String city;
    private String town;

    public Address() {
    }

    public Address(String province, String city, String town) {
        this.province = province;
        this.city = city;
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}

```

ioc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd"
>

    <!--给复杂类型的赋值都在property标签内进行-->
    <bean id="person" class="com.zph.bean.Person">
        <property name="name">
            <!--赋空值-->
            <null></null>
        </property>
        <!--通过ref引用其他对象，引用外部bean-->
        <property name="address" ref="address"></property>
        <!--引用内部bean-->
       <!-- <property name="address">
            <bean class="com.zph.bean.Address">
                <property name="province" value="北京"></property>
                <property name="city" value="北京"></property>
                <property name="town" value="西城区"></property>
            </bean>
        </property>-->
        <!--为list赋值-->
        <property name="books">
            <list>
                <!--内部bean-->
                <bean id="book1" class="com.zph.bean.Book">
                    <property name="name" value="多线程与高并发"></property>
                    <property name="author" value="马士兵"></property>
                    <property name="price" value="1000"></property>
                </bean>
                <!--外部bean-->
                <ref bean="book2"></ref>
            </list>
        </property>
        <!--给map赋值-->
        <property name="maps" ref="myMap"></property>
        <!--给property赋值-->
        <property name="properties">
            <props>
                <prop key="aaa">aaa</prop>
                <prop key="bbb">222</prop>
            </props>
        </property>
        <!--给数组赋值-->
        <property name="hobbies">
            <array>
                <value>book</value>
                <value>movie</value>
                <value>game</value>
            </array>
        </property>
        <!--给set赋值-->
        <property name="sets">
            <set>
                <value>111</value>
                <value>222</value>
                <value>222</value>
            </set>
        </property>
    </bean>
    <bean id="address" class="com.zph.bean.Address">
        <property name="province" value="河北"></property>
        <property name="city" value="邯郸"></property>
        <property name="town" value="武安"></property>
    </bean>
    <bean id="book2" class="com.zph.bean.Book">
        <property name="name" value="JVM"></property>
        <property name="author" value="马士兵"></property>
        <property name="price" value="1200"></property>
    </bean>
    <!--级联属性-->
    <bean id="person2" class="com.zph.bean.Person">
        <property name="address" ref="address"></property>
        <property name="address.province" value="北京"></property>
    </bean>
    <!--util名称空间创建集合类型的bean-->
    <util:map id="myMap">
            <entry key="key1" value="value1"></entry>
            <entry key="key2" value-ref="book2"></entry>
            <entry key="key03">
                <bean class="com.zph.bean.Book">
                    <property name="name" value="西游记" ></property>
                    <property name="author" value="吴承恩" ></property>
                    <property name="price" value="100" ></property>
                </bean>
            </entry>
    </util:map>
</beans>

```

##### 6、继承关系bean的配置

ioc.xml

```xml
    <bean id="person" class="com.zph.bean.Person">
        <property name="id" value="1"></property>
        <property name="name" value="zhangsan"></property>
        <property name="age" value="21"></property>
        <property name="gender" value="男"></property>
    </bean>
    <!--parent:指定bean的配置信息继承于哪个bean-->
    <bean id="person2" class="com.zph.bean.Person" parent="person">
        <property name="name" value="lisi"></property>
    </bean>

```

如果想实现Java文件的抽象类，不需要将当前bean实例化的话，可以使用abstract属性

```xml
 	<bean id="person" class="com.zph.bean.Person" abstract="true">
        <property name="id" value="1"></property>
        <property name="name" value="zhangsan"></property>
        <property name="age" value="21"></property>
        <property name="gender" value="男"></property>
    </bean>
    <!--parent:指定bean的配置信息继承于哪个bean-->
    <bean id="person2" class="com.zph.bean.Person" parent="person">
        <property name="name" value="lisi"></property>
    </bean>

```

##### 7、bean对象创建的依赖关系

​		bean对象在创建的时候是按照bean在配置文件的顺序决定的，也可以使用depend-on标签来决定顺序

ioc.xml

```xml
	<bean id="book" class="com.zph.bean.Book" depends-on="person,address"></bean>
    <bean id="address" class="com.zph.bean.Address"></bean>
    <bean id="person" class="com.zph.bean.Person"></bean>

```

##### 8、bean的作用域控制，是否是单例

ioc.xml

```xml
    <!--
    bean的作用域：singleton、prototype、request、session
    默认情况下是单例的
    prototype：多实例的
        容器启动的时候不会创建多实例bean，只有在获取对象的时候才会创建该对象
        每次创建都是一个新的对象
    singleton：默认的单例对象
        在容器启动完成之前就已经创建好对象
        获取的所有对象都是同一个
    -->
    <bean id="person4" class="com.zph.bean.Person" scope="prototype"></bean>

```

##### 9、利用工厂模式创建bean对象

​		在之前的案例中，所有bean对象的创建都是通过反射得到对应的bean实例，其实在spring中还包含另外一种创建bean实例的方式，就是通过工厂模式进行对象的创建

​		在利用工厂模式创建bean实例的时候有两种方式，分别是静态工厂和实例工厂。

​		静态工厂：工厂本身不需要创建对象，但是可以通过静态方法调用，对象=工厂类.静态工厂方法名（）；

​		实例工厂：工厂本身需要创建对象，工厂类 工厂对象=new 工厂类；工厂对象.get对象名（）；

PersonStaticFactory.java

```java
package com.zph.factory;

import com.zph.bean.Person;

public class PersonStaticFactory {

    public static Person getPerson(String name){
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        return person;
    }
}

```

ioc.xml

```xml
<!--
静态工厂的使用：
class:指定静态工厂类
factory-method:指定哪个方法是工厂方法
-->
<bean id="person5" class="com.zph.factory.PersonStaticFactory" factory-method="getPerson">
        <!--constructor-arg：可以为方法指定参数-->
        <constructor-arg value="lisi"></constructor-arg>
    </bean>

```

PersonInstanceFactory.java

```java
package com.zph.factory;

import com.zph.bean.Person;

public class PersonInstanceFactory {
    public Person getPerson(String name){
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        return person;
    }
}
```

ioc.xml

```xml
    <!--实例工厂使用-->
    <!--创建实例工厂类-->
    <bean id="personInstanceFactory" class="com.zph.factory.PersonInstanceFactory"></bean>
    <!--
    factory-bean:指定使用哪个工厂实例
    factory-method:指定使用哪个工厂实例的方法
    -->
    <bean id="person6" class="com.zph.bean.Person" factory-bean="personInstanceFactory" factory-method="getPerson">
        <constructor-arg value="wangwu"></constructor-arg>
    </bean>

```

##### 10、继承FactoryBean来创建对象

​		FactoryBean是Spring规定的一个接口，当前接口的实现类，Spring都会将其作为一个工厂，但是在ioc容器启动的时候不会创建实例，只有在使用的时候才会创建对象

MyFactoryBean.java

```java
package com.zph.factory;

import com.zph.bean.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * 实现了FactoryBean接口的类是Spring中可以识别的工厂类，spring会自动调用工厂方法创建实例
 */
public class MyFactoryBean implements FactoryBean<Person> {

    /**
     * 工厂方法，返回需要创建的对象
     * @return
     * @throws Exception
     */
    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setName("maliu");
        return person;
    }

    /**
     * 返回创建对象的类型,spring会自动调用该方法返回对象的类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 创建的对象是否是单例对象
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}

```

ioc.xml

```xml
<bean id="myfactorybean" class="com.zph.factory.MyFactoryBean"></bean>

```

##### 11、bean对象的初始化和销毁方法

​		在创建对象的时候，我们可以根据需要调用初始化和销毁的方法

Address.java

```java
package com.zph.bean;

public class Address {
    private String province;
    private String city;
    private String town;

    public Address() {
        System.out.println("address被创建了");
    }

    public Address(String province, String city, String town) {
        this.province = province;
        this.city = city;
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void init(){
        System.out.println("对象被初始化");
    }
    
    public void destory(){
        System.out.println("对象被销毁");
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}

```

ioc.xml

```xml
<!--bean生命周期表示bean的创建到销毁
        如果bean是单例，容器在启动的时候会创建好，关闭的时候会销毁创建的bean
        如果bean是多礼，获取的时候创建对象，销毁的时候不会有任何的调用
    -->
    <bean id="address" class="com.zph.bean.Address" init-method="init" destroy-method="destory"></bean>

```

MyTest.java

```java
import com.zph.bean.Address;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc2.xml");
        Address address = context.getBean("address", Address.class);
        System.out.println(address);
        //applicationContext没有close方法，需要使用具体的子类
        ((ClassPathXmlApplicationContext)context).close();

    }
}

```

##### 12、配置bean对象初始化方法的前后处理方法

​		spring中包含一个BeanPostProcessor的接口，可以在bean的初始化方法的前后调用该方法，如果配置了初始化方法的前置和后置处理器，无论是否包含初始化方法，都会进行调用

MyBeanPostProcessor.java

```java
package com.zph.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * 在初始化方法调用之前执行
     * @param bean  初始化的bean对象
     * @param beanName  xml配置文件中的bean的id属性
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization:"+beanName+"调用初始化前置方法");
        return bean;
    }

    /**
     * 在初始化方法调用之后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization:"+beanName+"调用初始化后缀方法");
        return bean;
    }
}

```

ioc.xml

```xml
<bean id="myBeanPostProcessor" class="com.zph.bean.MyBeanPostProcessor"></bean>

```

#### 3、spring创建第三方bean对象

​		在Spring中，很多对象都是单实例的，在日常的开发中，我们经常需要使用某些外部的单实例对象，例如数据库连接池，下面我们来讲解下如何在spring中创建第三方bean实例。

​		1、导入数据库连接池的pom文件

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>

```

​		2、编写配置文件

ioc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="root"></property>
        <property name="password" value="123456"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/demo"></property>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
    </bean>
</beans>

```

​		3、编写测试文件

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import com.zph.bean.Address;
import com.zph.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc3.xml");
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
}

```

#### 4、spring引用外部配置文件

在resource中添加dbconfig.properties

```properties
username=root
password=123456
url=jdbc:mysql://localhost:3306/demo
driverClassName=com.mysql.jdbc.Driver

```

编写配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
	<!--加载外部配置文件
		在加载外部依赖文件的时候需要context命名空间
	-->
    <context:property-placeholder location="classpath:dbconfig.properties"/>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${username}"></property>
        <property name="password" value="${password}"></property>
        <property name="url" value="${url}"></property>
        <property name="driverClassName" value="${driverClassName}"></property>
    </bean>
</beans>

```

#### 5、spring基于xml文件的自动装配

​		当一个对象中需要引用另外一个对象的时候，在之前的配置中我们都是通过property标签来进行手动配置的，其实在spring中还提供了一个非常强大的功能就是自动装配，可以按照我们指定的规则进行配置，配置的方式有以下几种：

​		default/no：不自动装配

​		byName：按照名字进行装配，以属性名作为id去容器中查找组件，进行赋值，如果找不到则装配null

​		byType：按照类型进行装配,以属性的类型作为查找依据去容器中找到这个组件，如果有多个类型相同的bean对象，那么会报异常，如果找不到则装配null

​		constructor：按照构造器进行装配，先按照有参构造器参数的类型进行装配，没有就直接装配null；如果按照类型找到了多个，那么就使用参数名作为id继续匹配，找到就装配，找不到就装配null

ioc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="com.zph.bean.Address">
        <property name="province" value="河北"></property>
        <property name="city" value="邯郸"></property>
        <property name="town" value="武安"></property>
    </bean>
    <bean id="person" class="com.zph.bean.Person" autowire="byName"></bean>
    <bean id="person2" class="com.zph.bean.Person" autowire="byType"></bean>
    <bean id="person3" class="com.zph.bean.Person" autowire="constructor"></bean>
</beans>

```

#### 6、SpEL的使用

​		SpEL:Spring Expression Language,spring的表达式语言，支持运行时查询操作对象

​		使用#{...}作为语法规则，所有的大括号中的字符都认为是SpEL.

ioc.xml

```xml
    <bean id="person4" class="com.zph.bean.Person">
        <!--支持任何运算符-->
        <property name="age" value="#{12*2}"></property>
        <!--可以引用其他bean的某个属性值-->
        <property name="name" value="#{address.province}"></property>
        <!--引用其他bean-->
        <property name="address" value="#{address}"></property>
        <!--调用静态方法-->
        <property name="hobbies" value="#{T(java.util.UUID).randomUUID().toString().substring(0,4)}"></property>
        <!--调用非静态方法-->
        <property name="gender" value="#{address.getCity()}"></property>
    </bean>
```

### 2.2.3 SpringIOC的注解应用

​		在之前的项目中，我们都是通过xml文件进行bean或者某些属性的赋值，其实还有另外一种注解的方式，在企业开发中使用的很多，在bean上添加注解，可以快速的将bean注册到ioc容器。

#### 1、使用注解的方式注册bean到IOC容器中

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--
    如果想要将自定义的bean对象添加到IOC容器中，需要在类上添加某些注解
    Spring中包含4个主要的组件添加注解：
    @Controller:控制器，推荐给controller层添加此注解
    @Service:业务逻辑，推荐给业务逻辑层添加此注解
    @Repository:仓库管理，推荐给数据访问层添加此注解
    @Component:给不属于以上基层的组件添加此注解
    注意：我们虽然人为的给不同的层添加不同的注解，但是在spring看来，可以在任意层添加任意注解
           spring底层是不会给具体的层次验证注解，这样写的目的只是为了提高可读性，最偷懒的方式
           就是给所有想交由IOC容器管理的bean对象添加component注解

    使用注解需要如下步骤：
    1、添加上述四个注解中的任意一个
    2、添加自动扫描注解的组件，此操作需要依赖context命名空间
    3、添加自动扫描的标签context:component-scan

	注意：当使用注解注册组件和使用配置文件注册组件是一样的，但是要注意：
		1、组件的id默认就是组件的类名首字符小写，如果非要改名字的话，直接在注解中添加即可
		2、组件默认情况下都是单例的,如果需要配置多例模式的话，可以在注解下添加@Scope注解
    -->
    <!--
    定义自动扫描的基础包:
    base-package:指定扫描的基础包，spring在启动的时候会将基础包及子包下所有加了注解的类都自动
                扫描进IOC容器
    -->
    <context:component-scan base-package="com.zph"></context:component-scan>
</beans>
```

PersonController.java

```java
package com.zph.controller;

import org.springframework.stereotype.Controller;

@Controller
public class PersonController {
    public PersonController() {
        System.out.println("创建对象");
    }
}
```

PersonService.java

```java
package com.zph.service;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
}
```

PersonDao.java

```java
package com.zph.dao;

import org.springframework.stereotype.Repository;

@Repository("personDao")
@Scope(value="prototype")
public class PersonDao {
}
```

#### 2、定义扫描包时要包含的类和不要包含的类

​		当定义好基础的扫描包后，在某些情况下可能要有选择性的配置是否要注册bean到IOC容器中，此时可以通过如下的方式进行配置。

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zph" use-default-filters="false">
        <!--
        当定义好基础扫描的包之后，可以排除包中的某些类，使用如下的方式:
        type:表示指定过滤的规则
            annotation：按照注解进行排除，标注了指定注解的组件不要,expression表示要过滤的注解
            assignable：指定排除某个具体的类，按照类排除，expression表示不注册的具体类名
            aspectj：后面讲aop的时候说明要使用的aspectj表达式，不用
            custom：定义一个typeFilter,自己写代码决定哪些类被过滤掉，不用
            regex：使用正则表达式过滤，不用
        -->
<!--        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>-->

        <!--指定只扫描哪些组件，默认情况下是全部扫描的，所以此时要配置的话需要在component-scan标签中添加 use-default-filters="false"-->
        <context:include-filter type="assignable" expression="com.zph.service.PersonService"/>
    </context:component-scan>
</beans>
```

#### 3、使用@AutoWired进行自动注入

​		使用注解的方式实现自动注入需要使用@AutoWired注解。

PersonController.java

```java
package com.zph.controller;

import com.zph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    public PersonController() {
        System.out.println("创建对象");
    }

    public void getPerson(){
        personService.getPerson();
    }
}
```

PersonService.java

```java
package com.zph.service;

import com.zph.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    public void getPerson(){
        personDao.getPerson();
    }
}
```

PersonDao.java

```java
package com.zph.dao;

        import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

    public void getPerson(){
        System.out.println("PersonDao:getPerson");
    }
}
```

注意：当使用AutoWired注解的时候，自动装配的时候是根据类型实现的。

​		1、如果只找到一个，则直接进行赋值，

​		2、如果没有找到，则直接抛出异常，

​		3、如果找到多个，那么会按照变量名作为id继续匹配,

​				1、匹配上直接进行装配

​				2、如果匹配不上则直接报异常

PersonServiceExt.java

```java
package com.zph.service;

import com.zph.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceExt extends PersonService{

    @Autowired
    private PersonDao personDao;

    public void getPerson(){
        System.out.println("PersonServiceExt......");
        personDao.getPerson();
    }
}

```

PersonController.java

```java
package com.zph.controller;

import com.zph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

    @Autowired
    private PersonService personServiceExt;

    public PersonController() {
        System.out.println("创建对象");
    }

    public void getPerson(){
        personServiceExt.getPerson();
    }
}

```

​		还可以使用@Qualifier注解来指定id的名称，让spring不要使用变量名,当使用@Qualifier注解的时候也会有两种情况：

​		1、找到，则直接装配

​		2、找不到，就会报错

PersonController.java

```java
package com.zph.controller;

import com.zph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

    @Autowired
    @Qualifier("personService")
    private PersonService personServiceExt2;

    public PersonController() {
        System.out.println("创建对象");
    }

    public void getPerson(){
        personServiceExt2.getPerson();
    }
}

```

​		通过上述的代码我们能够发现，使用@AutoWired肯定是能够装配上的，如果装配不上就会报错。

#### 4、@AutoWired可以进行定义在方法上

​		当我们查看@AutoWired注解的源码的时候发现，此注解不仅可以使用在成员变量上，也可以使用在方法上。

PersonController.java

```java
package com.zph.controller;

import com.zph.dao.PersonDao;
import com.zph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

    @Qualifier("personService")
    @Autowired
    private PersonService personServiceExt2;

    public PersonController() {
        System.out.println("创建对象");
    }

    public void getPerson(){
        System.out.println("personController..."+personServiceExt2);
//        personServiceExt2.getPerson();
    }

     /**
     * 当方法上有@AutoWired注解时：
     *  1、此方法在bean创建的时候会自动调用
     *  2、这个方法的每一个参数都会自动注入值
     * @param personDao
     */
    @Autowired
    public void test(PersonDao personDao){
        System.out.println("此方法被调用:"+personDao);
    }
    
    /**
     * @Qualifier注解也可以作用在属性上，用来被当作id去匹配容器中的对象，如果没有
     * 此注解，那么直接按照类型进行匹配
     * @param personService
     */
    @Autowired
    public void test2(@Qualifier("personServiceExt") PersonService personService){
        System.out.println("此方法被调用："+personService);
    }
}

```

#### 5、自动装配的注解@AutoWired，@Resource

​		在使用自动装配的时候，出了可以使用@AutoWired注解之外，还可以使用@Resource注解，大家需要知道这两个注解的区别。

​		1、@AutoWired:是spring中提供的注解，@Resource:是jdk中定义的注解，依靠的是java的标准

​		2、@AutoWired默认是按照类型进行装配，默认情况下要求依赖的对象必须存在，@Resource默认是按照名字进行匹配的，同时可以指定name属性。

​		3、@AutoWired只适合spring框架，而@Resource扩展性更好

PersonController.java

```java
package com.zph.controller;

import com.zph.dao.PersonDao;
import com.zph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class PersonController {

    @Qualifier("personService")
    @Resource
    private PersonService personServiceExt2;

    public PersonController() {
        System.out.println("创建对象");
    }

    public void getPerson(){
        System.out.println("personController..."+personServiceExt2);
        personServiceExt2.getPerson();
    }

    /**
     * 当方法上有@AutoWired注解时：
     *  1、此方法在bean创建的时候会自动调用
     *  2、这个方法的每一个参数都会自动注入值
     * @param personDao
     */
    @Autowired
    public void test(PersonDao personDao){
        System.out.println("此方法被调用:"+personDao);
    }

    /**
     * @Qualifier注解也可以作用在属性上，用来被当作id去匹配容器中的对象，如果没有
     * 此注解，那么直接按照类型进行匹配
     * @param personService
     */
    @Autowired
    public void test2(@Qualifier("personServiceExt") PersonService personService){
        System.out.println("此方法被调用："+personService);
    }
}

```

### 2.2.4 泛型依赖注入

​		为了讲解泛型依赖注入，首先我们需要先写一个基本的案例，按照我们之前学习的知识：

Student.java

```java
package com.zph.bean;

public class Student {
}

```

Teacher.java

```java
package com.zph.bean;

public class Teacher {
}

```

BaseDao.java

```java
package com.zph.dao;

import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDao<T> {

    public abstract void save();
}

```

StudentDao.java

```java
package com.zph.dao;

import com.zph.bean.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDao<Student>{
    public void save() {
        System.out.println("保存学生");
    }
}

```

TeacherDao.java

```java
package com.zph.dao;

import com.zph.bean.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends BaseDao<Teacher> {
    public void save() {
        System.out.println("保存老师");
    }
}

```

StudentService.java

```java
package com.zph.service;

import com.zph.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public void save(){
        studentDao.save();
    }
}

```

TeacherService.java

```java
package com.zph.service;

import com.zph.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    public void save(){
        teacherDao.save();
    }
}

```

MyTest.java

```java
import com.zph.service.StudentService;
import com.zph.service.TeacherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService studentService = context.getBean("studentService",StudentService.class);
        studentService.save();

        TeacherService teacherService = context.getBean("teacherService",TeacherService.class);
        teacherService.save();
    }
}

```

​		上述代码是我们之前的可以完成的功能，但是可以思考，Service层的代码是否能够改写：

BaseService.java

```java
package com.zph.service;

import com.zph.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class BaseService<T> {
    
    @Autowired
    BaseDao<T> baseDao;
    
    public void save(){
        System.out.println("自动注入的对象："+baseDao);
        baseDao.save();
    }
}

```

StudentService.java

```java
package com.zph.service;

import com.zph.bean.Student;
import com.zph.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends BaseService<Student> {

}

```

TeacherService.java

```java
package com.zph.service;

import com.zph.bean.Teacher;
import com.zph.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends BaseService<Teacher>{

}

```

# 04Spring AOP介绍与使用

AOP：Aspect Oriented Programming  面向切面编程

OOP：Object Oriented Programming  面向对象编程

​		面向切面编程：基于OOP基础之上新的编程思想，OOP面向的主要对象是类，而AOP面向的主要对象是切面，在处理日志、安全管理、事务管理等方面有非常重要的作用。AOP是Spring中重要的核心点，虽然IOC容器没有依赖AOP，但是AOP提供了非常强大的功能，用来对IOC做补充。通俗点说的话就是在程序运行期间，将**某段代码动态切入**到**指定方法**的**指定位置**进行运行的这种编程方式。

### 1、AOP的概念

##### 为什么要引入AOP?

Calculator.java

```java
package com.zph.inter;

public interface Calculator {

    public int add(int i,int j);

    public int sub(int i,int j);

    public int mult(int i,int j);

    public int div(int i,int j);
}
```

MyCalculator.java

```java
package com.zph.inter;

public class MyCalculator implements Calculator {
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mult(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}
```

MyTest.java

```java
public class MyTest {
    public static void main(String[] args) throws SQLException {
        MyCalculator myCalculator = new MyCalculator();
        System.out.println(myCalculator.add(1, 2));
    }
}
```

​		此代码非常简单，就是基础的javase的代码实现，此时如果需要添加日志功能应该怎么做呢，只能在每个方法中添加日志输出，同时如果需要修改的话会变得非常麻烦。

MyCalculator.java

```java
package com.zph.inter;

public class MyCalculator implements Calculator {
    public int add(int i, int j) {
        System.out.println("add 方法开始执行，参数为："+i+","+j);
        int result = i + j;
        System.out.println("add 方法开始完成结果为："+result);
        return result;
    }

    public int sub(int i, int j) {
        System.out.println("sub 方法开始执行，参数为："+i+","+j);
        int result = i - j;
        System.out.println("add 方法开始完成结果为："+result);
        return result;
    }

    public int mult(int i, int j) {
        System.out.println("mult 方法开始执行，参数为："+i+","+j);
        int result = i * j;
        System.out.println("add 方法开始完成结果为："+result);
        return result;
    }

    public int div(int i, int j) {
        System.out.println("div 方法开始执行，参数为："+i+","+j);
        int result = i / j;
        System.out.println("add 方法开始完成结果为："+result);
        return result;
    }
}
```

​		可以考虑将日志的处理抽象出来，变成工具类来进行实现：

LogUtil.java

```java
package com.zph.util;

import java.util.Arrays;

public class LogUtil {

    public static void start(Object ... objects){
        System.out.println("XXX方法开始执行，使用的参数是："+ Arrays.asList(objects));
    }

    public static void stop(Object ... objects){
        System.out.println("XXX方法执行结束，结果是："+ Arrays.asList(objects));
    }
}
```

MyCalculator.java

```java
package com.zph.inter;

import com.zph.util.LogUtil;

public class MyCalculator implements Calculator {
    public int add(int i, int j) {
        LogUtil.start(i,j);
        int result = i + j;
        LogUtil.stop(result);
        return result;
    }

    public int sub(int i, int j) {
        LogUtil.start(i,j);
        int result = i - j;
        LogUtil.stop(result);
        return result;
    }

    public int mult(int i, int j) {
        LogUtil.start(i,j);
        int result = i * j;
        LogUtil.stop(result);
        return result;
    }

    public int div(int i, int j) {
        LogUtil.start(i,j);
        int result = i / j;
        LogUtil.stop(result);
        return result;
    }
}
```

​		按照上述方式抽象之后，代码确实简单很多，但是大家应该已经发现在输出的信息中并不包含具体的方法名称，我们更多的是想要在程序运行过程中动态的获取方法的名称及参数、结果等相关信息，此时可以通过使用**动态代理**的方式来进行实现。

CalculatorProxy.java

```java
package com.zph.proxy;

import com.zph.inter.Calculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 帮助Calculator生成代理对象的类
 */
public class CalculatorProxy {

    /**
     *
     *  为传入的参数对象创建一个动态代理对象
     * @param calculator 被代理对象
     * @return
     */
    public static Calculator getProxy(final Calculator calculator){


        //被代理对象的类加载器
        ClassLoader loader = calculator.getClass().getClassLoader();
        //被代理对象的接口
        Class<?>[] interfaces = calculator.getClass().getInterfaces();
        //方法执行器，执行被代理对象的目标方法
        InvocationHandler h = new InvocationHandler() {
            /**
             *  执行目标方法
             * @param proxy 代理对象，给jdk使用，任何时候都不要操作此对象
             * @param method 当前将要执行的目标对象的方法
             * @param args 这个方法调用时外界传入的参数值
             * @return
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //利用反射执行目标方法,目标方法执行后的返回值
//                System.out.println("这是动态代理执行的方法");
                Object result = null;
                try {
                    System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(args));
                    result = method.invoke(calculator, args);
                    System.out.println(method.getName()+"方法执行完成，结果是："+ result);
                } catch (Exception e) {
                    System.out.println(method.getName()+"方法出现异常："+ e.getMessage());
                } finally {
                    System.out.println(method.getName()+"方法执行结束了......");
                }
                //将结果返回回去
                return result;
            }
        };
        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }
}
```

​		我们可以看到这种方式更加灵活，而且不需要在业务方法中添加额外的代码，这才是常用的方式。如果想追求完美的同学，还可以使用上述的日志工具类来完善。

LogUtil.java

```java
package com.zph.util;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtil {

    public static void start(Method method, Object ... objects){
//        System.out.println("XXX方法开始执行，使用的参数是："+ Arrays.asList(objects));
        System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(objects));
    }

    public static void stop(Method method,Object ... objects){
//        System.out.println("XXX方法执行结束，结果是："+ Arrays.asList(objects));
        System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(objects));

    }

    public static void logException(Method method,Exception e){
        System.out.println(method.getName()+"方法出现异常："+ e.getMessage());
    }
    
    public static void end(Method method){
        System.out.println(method.getName()+"方法执行结束了......");
    }
}
```

CalculatorProxy.java

```java
package com.zph.proxy;

import com.zph.inter.Calculator;
import com.zph.util.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 帮助Calculator生成代理对象的类
 */
public class CalculatorProxy {

    /**
     *
     *  为传入的参数对象创建一个动态代理对象
     * @param calculator 被代理对象
     * @return
     */
    public static Calculator getProxy(final Calculator calculator){


        //被代理对象的类加载器
        ClassLoader loader = calculator.getClass().getClassLoader();
        //被代理对象的接口
        Class<?>[] interfaces = calculator.getClass().getInterfaces();
        //方法执行器，执行被代理对象的目标方法
        InvocationHandler h = new InvocationHandler() {
            /**
             *  执行目标方法
             * @param proxy 代理对象，给jdk使用，任何时候都不要操作此对象
             * @param method 当前将要执行的目标对象的方法
             * @param args 这个方法调用时外界传入的参数值
             * @return
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //利用反射执行目标方法,目标方法执行后的返回值
//                System.out.println("这是动态代理执行的方法");
                Object result = null;
                try {
                    LogUtil.start(method,args);
                    result = method.invoke(calculator, args);
                    LogUtil.stop(method,args);
                } catch (Exception e) {
                    LogUtil.logException(method,e);
                } finally {
                    LogUtil.end(method);
                }
                //将结果返回回去
                return result;
            }
        };
        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }
}

```

​		很多同学看到上述代码之后可能感觉已经非常完美了，但是要说明的是，这种动态代理的实现方式调用的是jdk的基本实现，如果需要代理的目标对象没有实现任何接口，那么是无法为他创建代理对象的，这也是致命的缺陷。而在Spring中我们可以不编写上述如此复杂的代码，只需要利用AOP，就能够轻轻松松实现上述功能，当然，Spring AOP的底层实现也依赖的是动态代理。

##### AOP的核心概念及术语

- 切面（Aspect）: 指关注点模块化，这个关注点可能会横切多个对象。事务管理是企业级Java应用中有关横切关注点的例子。 在Spring AOP中，切面可以使用通用类基于模式的方式（schema-based approach）或者在普通类中以`@Aspect`注解（@AspectJ 注解方式）来实现。
- 连接点（Join point）: 在程序执行过程中某个特定的点，例如某个方法调用的时间点或者处理异常的时间点。在Spring AOP中，一个连接点总是代表一个方法的执行。
- 通知（Advice）: 在切面的某个特定的连接点上执行的动作。通知有多种类型，包括“around”, “before” and “after”等等。通知的类型将在后面的章节进行讨论。 许多AOP框架，包括Spring在内，都是以拦截器做通知模型的，并维护着一个以连接点为中心的拦截器链。
- 切点（Pointcut）: 匹配连接点的断言。通知和切点表达式相关联，并在满足这个切点的连接点上运行（例如，当执行某个特定名称的方法时）。切点表达式如何和连接点匹配是AOP的核心：Spring默认使用AspectJ切点语义。
- 引入（Introduction）: 声明额外的方法或者某个类型的字段。Spring允许引入新的接口（以及一个对应的实现）到任何被通知的对象上。例如，可以使用引入来使bean实现 `IsModified`接口， 以便简化缓存机制（在AspectJ社区，引入也被称为内部类型声明（inter））。
- 目标对象（Target object）: 被一个或者多个切面所通知的对象。也被称作被通知（advised）对象。既然Spring AOP是通过运行时代理实现的，那么这个对象永远是一个被代理（proxied）的对象。
- AOP代理（AOP proxy）:AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。在Spring中，AOP代理可以是JDK动态代理或CGLIB代理。
- 织入（Weaving）: 把切面连接到其它的应用程序类型或者对象上，并创建一个被被通知的对象的过程。这个过程可以在编译时（例如使用AspectJ编译器）、类加载时或运行时中完成。 Spring和其他纯Java AOP框架一样，是在运行时完成织入的。

##### AOP的通知类型

- 前置通知（Before advice）: 在连接点之前运行但无法阻止执行流程进入连接点的通知（除非它引发异常）。
- 后置返回通知（After returning advice）:在连接点正常完成后执行的通知（例如，当方法没有抛出任何异常并正常返回时）。
- 后置异常通知（After throwing advice）: 在方法抛出异常退出时执行的通知。
- 后置通知（总会执行）（After (finally) advice）: 当连接点退出的时候执行的通知（无论是正常返回还是异常退出）。
- 环绕通知（Around Advice）:环绕连接点的通知，例如方法调用。这是最强大的一种通知类型，。环绕通知可以在方法调用前后完成自定义的行为。它可以选择是否继续执行连接点或直接返回自定义的返回值又或抛出异常将执行结束。

##### AOP的应用场景

- 日志管理
- 权限认证
- 安全检查
- 事务控制

### 2、Spring AOP的简单配置

​		在上述代码中我们是通过动态代理的方式实现日志功能的，但是比较麻烦，现在我们将要使用spring aop的功能实现此需求，其实通俗点说的话，就是把LogUtil的工具类换成另外一种实现方式。

##### 1、添加pom依赖

```xml
        <!-- https://mvnrepository.com/artifact/cglib/cglib -->
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.3.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/aopalliance/aopalliance -->
        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>

```

##### 2、编写配置

- 将目标类和切面类加入到IOC容器中，在对应的类上添加组件注解

  - 给LogUtil添加@Component注解

  - 给MyCalculator添加@Service注解

  - 添加自动扫描的配置

    ```xml
    <!--别忘了添加context命名空间-->
    <context:component-scan base-package="com.zph"></context:component-scan>
    
    ```

- 设置程序中的切面类

  - 在LogUtil.java中添加@Aspect注解

- 设置切面类中的方法是什么时候在哪里执行

  ```java
  package com.zph.util;
  
  import org.aspectj.lang.annotation.*;
  import org.springframework.stereotype.Component;
  
  import java.lang.reflect.Method;
  import java.util.Arrays;
  
  @Component
  @Aspect
  public class LogUtil {
  
      /*
      设置下面方法在什么时候运行
          @Before:在目标方法之前运行：前置通知
          @After:在目标方法之后运行：后置通知
          @AfterReturning:在目标方法正常返回之后：返回通知
          @AfterThrowing:在目标方法抛出异常后开始运行：异常通知
          @Around:环绕：环绕通知
  
          当编写完注解之后还需要设置在哪些方法上执行，使用表达式
          execution(访问修饰符  返回值类型 方法全称)
       */
      @Before("execution( public int com.zph.inter.MyCalculator.*(int,int))")
      public static void start(){
  //        System.out.println("XXX方法开始执行，使用的参数是："+ Arrays.asList(objects));
  //        System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(objects));
          System.out.println("方法开始执行，参数是：");
      }
  
      @AfterReturning("execution( public int com.zph.inter.MyCalculator.*(int,int))")
      public static void stop(){
  //        System.out.println("XXX方法执行结束，结果是："+ Arrays.asList(objects));
  //        System.out.println(method.getName()+"方法执行结束，结果是："+ Arrays.asList(objects));
          System.out.println("方法执行完成，结果是：");
  
      }
  
      @AfterThrowing("execution( public int com.zph.inter.MyCalculator.*(int,int))")
      public static void logException(){
  //        System.out.println(method.getName()+"方法出现异常："+ e.getMessage());
          System.out.println("方法出现异常：");
      }
  
      @After("execution( public int com.zph.inter.MyCalculator.*(int,int))")
      public static void end(){
  //        System.out.println(method.getName()+"方法执行结束了......");
          System.out.println("方法执行结束了......");
      }
  }
  
  ```

- 开启基于注解的aop的功能

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/aop
         https://www.springframework.org/schema/aop/spring-aop.xsd
  ">
      <context:component-scan base-package="com.zph"></context:component-scan>
      <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
  </beans>
  
  ```


##### 3、测试

  MyTest.java

  ```java
  import com.zph.inter.Calculator;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  public class MyTest {
      public static void main(String[] args){
          ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
          Calculator bean = context.getBean(Calculator.class);
          bean.add(1,1);
      }
}

  ```

​		spring AOP的动态代理方式是jdk自带的方式，容器中保存的组件是代理对象com.sun.proxy.$Proxy对象

  ##### 4、通过cglib来创建代理对象

MyCalculator.java

```java
package com.zph.inter;

import org.springframework.stereotype.Service;

@Service
public class MyCalculator {
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mult(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}

```

MyTest.java

```java
public class MyTest {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
        MyCalculator bean = context.getBean(MyCalculator.class);
        bean.add(1,1);
        System.out.println(bean);
        System.out.println(bean.getClass());
    }
}

```

​		可以通过cglib的方式来创建代理对象，此时不需要实现任何接口，代理对象是

class com.zph.inter.MyCalculator$$EnhancerBySpringCGLIB$$1f93b605类型

​		**综上所述：在spring容器中，如果有接口，那么会使用jdk自带的动态代理，如果没有接口，那么会使用cglib的动态代理。动态代理的实现原理，后续会详细讲。**

##### 注意：

###### 1、切入点表达式

​		在使用表达式的时候，除了之前的写法之外，还可以使用通配符的方式：

​		*：

​		1、匹配一个或者多个字符

​				execution( public int com.zph.inter.My\*alculator.*(int,int))

​		2、匹配任意一个参数，

​				execution( public int com.zph.inter.MyCalculator.\*(int,\*))

​		3、只能匹配一层路径，如果项目路径下有多层目录，那么*只能匹配一层路径

​		4、权限位置不能使用*，如果想表示全部权限，那么不写即可

​				execution( * com.zph.inter.MyCalculator.\*(int,\*))

​		..：

​		1、匹配多个参数，任意类型参数

​				execution( * com.zph.inter.MyCalculator.\*(..))

​		2、匹配任意多层路径

​				execution( * com.zph..MyCalculator.\*(..))

​		在写表达式的时候，可以有N多种写法，但是有一种最偷懒和最精确的方式：

​				最偷懒的方式：execution(*  \*(..))    或者   execution(*  \*.\*(..))

​				最精确的方式：execution( public int com.zph.inter.MyCalculator.add(int,int))

​		除此之外，在表达式中还支持 &&、||、！的方式

​				&&：两个表达式同时

​				execution( public int com.zph.inter.MyCalculator.*(..)) && execution(\* \*.\*(int,int) )

​				||：任意满足一个表达式即可

​				execution( public int com.zph.inter.MyCalculator.*(..)) && execution(\* \*.\*(int,int) )

​				！：只要不是这个位置都可以进行切入

​				&&：两个表达式同时

​				execution( public int com.zph.inter.MyCalculator.*(..))

###### 		2、通知方法的执行顺序

​			在之前的代码中大家一直对通知的执行顺序有疑问，其实执行的结果并没有错，大家需要注意：

​			1、正常执行：@Before--->@After--->@AfterReturning

​			2、异常执行：@Before--->@After--->@AfterThrowing

###### 		3、获取方法的详细信息

​		在上面的案例中，我们并没有获取Method的详细信息，例如方法名、参数列表等信息，想要获取的话其实非常简单，只需要添加JoinPoint参数即可。

LogUtil.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogUtil {

    @Before("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public static void start(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public static void stop(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行完成，结果是：");

    }

    @AfterThrowing("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public static void logException(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法出现异常：");
    }

    @After("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public static void end(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行结束了......");
    }
}

```

​		刚刚只是获取了方法的信息，但是如果需要获取结果，还需要添加另外一个方法参数，并且告诉spring使用哪个参数来进行结果接收

LogUtil.java

```java
    @AfterReturning(value = "execution( public int com.zph.inter.MyCalculator.*(int,int))",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行完成，结果是："+result);

    }

```

​		也可以通过相同的方式来获取异常的信息

LogUtil.java

```java
    @AfterThrowing(value = "execution( public int com.zph.inter.MyCalculator.*(int,int))",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法出现异常："+exception);
    }

```

###### 		4、spring对通过方法的要求

​		spring对于通知方法的要求并不是很高，你可以任意改变方法的返回值和方法的访问修饰符，但是唯一不能修改的就是方法的参数，会出现参数绑定的错误，原因在于通知方法是spring利用反射调用的，每次方法调用得确定这个方法的参数的值。

LogUtil.java

```java
    @After("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    private int end(JoinPoint joinPoint,String aa){
//        System.out.println(method.getName()+"方法执行结束了......");
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行结束了......");
        return 0;
    }

```

###### 		5、表达式的抽取

如果在实际使用过程中，多个方法的表达式是一致的话，那么可以考虑将切入点表达式抽取出来：

​		a、随便生命一个没有实现的返回void的空方法

​		b、给方法上标注@Potintcut注解

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogUtil {
    
    @Pointcut("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public void myPoint(){}
    
    @Before("myPoint()")
    public static void start(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning(value = "myPoint()",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行完成，结果是："+result);

    }

    @AfterThrowing(value = "myPoint()",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法出现异常："+exception.getMessage());
    }

    @After("myPoint()")
    private int end(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法执行结束了......");
        return 0;
    }
}

```

###### 		6、环绕通知的使用

LogUtil.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogUtil {
    @Pointcut("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public void myPoint(){}
    
    /**
     * 环绕通知是spring中功能最强大的通知
     * @param proceedingJoinPoint
     * @return
     */
    @Around("myPoint()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            System.out.println("环绕前置通知:"+name+"方法开始，参数是"+Arrays.asList(args));
            //利用反射调用目标方法，就是method.invoke()
            proceed = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知:"+name+"方法返回，返回值是"+proceed);
        } catch (Throwable e) {
            System.out.println("环绕异常通知"+name+"方法出现异常，异常信息是："+e);
        }finally {
            System.out.println("环绕后置通知"+name+"方法结束");
        }
        return proceed;
    }
}

```

​		总结：环绕通知的执行顺序是优于普通通知的，具体的执行顺序如下：

环绕前置-->普通前置-->目标方法执行-->环绕正常结束/出现异常-->环绕后置-->普通后置-->普通返回或者异常。

但是需要注意的是，如果出现了异常，那么环绕通知会处理或者捕获异常，普通异常通知是接收不到的，因此最好的方式是在环绕异常通知中向外抛出异常。

###### 		7、多切面运行的顺序

​		如果有多个切面要进行执行，那么顺序是什么样的呢？

LogUtil.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogUtil {
    @Pointcut("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public void myPoint(){}
    @Before("myPoint()")
    public static void start(JoinPoint joinPoint){
//        System.out.println("XXX方法开始执行，使用的参数是："+ Arrays.asList(objects));
//        System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(objects));
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning(value = "myPoint()",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
//        System.out.println("XXX方法执行结束，结果是："+ Arrays.asList(objects));
//        System.out.println(method.getName()+"方法执行结束，结果是："+ Arrays.asList(objects));
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法执行完成，结果是："+result);

    }

    @AfterThrowing(value = "myPoint()",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
//        System.out.println(method.getName()+"方法出现异常："+ e.getMessage());
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法出现异常："+exception.getMessage());
    }

    @After("myPoint()")
    private int end(JoinPoint joinPoint){
//        System.out.println(method.getName()+"方法执行结束了......");
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法执行结束了......");
        return 0;
    }

    /**
     * 环绕通知是spring中功能最强大的通知
     * @param proceedingJoinPoint
     * @return
     */
    //@Around("myPoint()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            System.out.println("环绕前置通知:"+name+"方法开始，参数是"+Arrays.asList(args));
            //利用反射调用目标方法，就是method.invoke()
            proceed = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知:"+name+"方法返回，返回值是"+proceed);
        } catch (Throwable e) {
            System.out.println("环绕异常通知"+name+"方法出现异常，异常信息是："+e);
        }finally {
            System.out.println("环绕后置通知"+name+"方法结束");
        }
        return proceed;
    }
}

```

SecurityAspect.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class SecurityAspect {

    @Before("com.zph.util.LogUtil.myPoint()")
    public static void start(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning(value = "com.zph.util.LogUtil.myPoint()",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法执行完成，结果是："+result);

    }

    @AfterThrowing(value = "com.zph.util.LogUtil.myPoint()",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法出现异常："+exception.getMessage());
    }

    @After("com.zph.util.LogUtil.myPoint()")
    private int end(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法执行结束了......");
        return 0;
    }

    /**
     * 环绕通知是spring中功能最强大的通知
     * @param proceedingJoinPoint
     * @return
     */
    //@Around("myPoint()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            System.out.println("环绕前置通知:"+name+"方法开始，参数是"+Arrays.asList(args));
            //利用反射调用目标方法，就是method.invoke()
            proceed = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知:"+name+"方法返回，返回值是"+proceed);
        } catch (Throwable e) {
            System.out.println("环绕异常通知"+name+"方法出现异常，异常信息是："+e);
        }finally {
            System.out.println("环绕后置通知"+name+"方法结束");
        }
        return proceed;
    }
}

```

​		在spring中，默认是按照切面名称的字典顺序进行执行的，但是如果想自己改变具体的执行顺序的话，可以使用@Order注解来解决，数值越小，优先级越高。

LogUtil.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Order(2)
public class LogUtil {
    @Pointcut("execution( public int com.zph.inter.MyCalculator.*(int,int))")
    public void myPoint(){}
    @Before("myPoint()")
    public static void start(JoinPoint joinPoint){
//        System.out.println("XXX方法开始执行，使用的参数是："+ Arrays.asList(objects));
//        System.out.println(method.getName()+"方法开始执行，参数是："+ Arrays.asList(objects));
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning(value = "myPoint()",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
//        System.out.println("XXX方法执行结束，结果是："+ Arrays.asList(objects));
//        System.out.println(method.getName()+"方法执行结束，结果是："+ Arrays.asList(objects));
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法执行完成，结果是："+result);

    }

    @AfterThrowing(value = "myPoint()",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
//        System.out.println(method.getName()+"方法出现异常："+ e.getMessage());
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法出现异常："+exception.getMessage());
    }

    @After("myPoint()")
    private int end(JoinPoint joinPoint){
//        System.out.println(method.getName()+"方法执行结束了......");
        String name = joinPoint.getSignature().getName();
        System.out.println("Log:"+name+"方法执行结束了......");
        return 0;
    }

    /**
     * 环绕通知是spring中功能最强大的通知
     * @param proceedingJoinPoint
     * @return
     */
    //@Around("myPoint()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            System.out.println("环绕前置通知:"+name+"方法开始，参数是"+Arrays.asList(args));
            //利用反射调用目标方法，就是method.invoke()
            proceed = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知:"+name+"方法返回，返回值是"+proceed);
        } catch (Throwable e) {
            System.out.println("环绕异常通知"+name+"方法出现异常，异常信息是："+e);
        }finally {
            System.out.println("环绕后置通知"+name+"方法结束");
        }
        return proceed;
    }
}

```

SecurityAspect.java

```java
package com.zph.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Order(1)
public class SecurityAspect {

    @Before("com.zph.util.LogUtil.myPoint()")
    public static void start(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

    @AfterReturning(value = "com.zph.util.LogUtil.myPoint()",returning = "result")
    public static void stop(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法执行完成，结果是："+result);

    }

    @AfterThrowing(value = "com.zph.util.LogUtil.myPoint()",throwing = "exception")
    public static void logException(JoinPoint joinPoint,Exception exception){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法出现异常："+exception.getMessage());
    }

    @After("com.zph.util.LogUtil.myPoint()")
    private int end(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println("Security:"+name+"方法执行结束了......");
        return 0;
    }

    /**
     * 环绕通知是spring中功能最强大的通知
     * @param proceedingJoinPoint
     * @return
     */
    //@Around("myPoint()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            System.out.println("环绕前置通知:"+name+"方法开始，参数是"+Arrays.asList(args));
            //利用反射调用目标方法，就是method.invoke()
            proceed = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知:"+name+"方法返回，返回值是"+proceed);
        } catch (Throwable e) {
            System.out.println("环绕异常通知"+name+"方法出现异常，异常信息是："+e);
        }finally {
            System.out.println("环绕后置通知"+name+"方法结束");
        }
        return proceed;
    }
}

```

​		如果需要添加环绕通知呢，具体的执行顺序又会是什么顺序呢？

​		因为环绕通知在进行添加的时候，是在切面层引入的，所以在哪个切面添加环绕通知，那么就会在哪个切面执行。

### 3、基于配置的AOP配置

​		之前我们讲解了基于注解的AOP配置方式，下面我们开始讲一下基于xml的配置方式，虽然在现在的企业级开发中使用注解的方式比较多，但是你不能不会，因此需要简单的进行配置，注解配置快速简单，配置的方式共呢个完善。

1、将所有的注解都进行删除

2、添加配置文件

aop.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">

    <context:component-scan base-package="com.zph"></context:component-scan>
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

    <bean id="logUtil" class="com.zph.util.LogUtil2"></bean>
    <bean id="securityAspect" class="com.zph.util.SecurityAspect"></bean>
    <bean id="myCalculator" class="com.zph.inter.MyCalculator"></bean>
    <aop:config>
        <aop:pointcut id="globalPoint" expression="execution(public int com.zph.inter.MyCalculator.*(int,int))"/>
        <aop:aspect ref="logUtil">
            <aop:pointcut id="mypoint" expression="execution(public int com.zph.inter.MyCalculator.*(int,int))"/>
            <aop:before method="start" pointcut-ref="mypoint"></aop:before>
            <aop:after method="end" pointcut-ref="mypoint"></aop:after>
            <aop:after-returning method="stop" pointcut-ref="mypoint" returning="result"></aop:after-returning>
            <aop:after-throwing method="logException" pointcut-ref="mypoint" throwing="exception"></aop:after-throwing>
            <aop:around method="myAround" pointcut-ref="mypoint"></aop:around>
        </aop:aspect>
        <aop:aspect ref="securityAspect">
            <aop:before method="start" pointcut-ref="globalPoint"></aop:before>
            <aop:after method="end" pointcut-ref="globalPoint"></aop:after>
            <aop:after-returning method="stop" pointcut-ref="globalPoint" returning="result"></aop:after-returning>
            <aop:after-throwing method="logException" pointcut-ref="globalPoint" throwing="exception"></aop:after-throwing>
            <aop:around method="myAround" pointcut-ref="mypoint"></aop:around>
        </aop:aspect>
    </aop:config>
</beans>

```



## 05Spring AOP的应用配置

### 1、Spring JdbcTemplate

​		在spring中为了更加方便的操作JDBC，在JDBC的基础之上定义了一个抽象层，此设计的目的是为不同类型的JDBC操作提供模板方法，每个模板方法都能控制整个过程，并允许覆盖过程中的特定任务，通过这种方式，可以尽可能保留灵活性，将数据库存取的工作量讲到最低。

##### 1、配置并测试数据源

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zph</groupId>
    <artifactId>spring_demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.21</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/cglib/cglib -->
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.3.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/aopalliance/aopalliance -->
        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

dbconfig.properties

```properties
jdbc.username=root123
password=123456
url=jdbc:mysql://localhost:3306/demo
driverClassName=com.mysql.jdbc.Driver
```

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
</beans>
```

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
}
```

##### 2、给spring容器添加JdbcTemplate

​		spring容器提供了一个JdbcTemplate类，用来方便操作数据库。

1、添加pom依赖

pom.xml

```xml
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-orm -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>
```

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
</beans>
```

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        System.out.println(jdbcTemplate);
    }
}
```

##### 3、插入数据

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(?,?)";
        int result = jdbcTemplate.update(sql, 1111, "zhangsan");
        System.out.println(result);
    }
}
```

##### 4、批量插入数据

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1,"zhangsan1"});
        list.add(new Object[]{2,"zhangsan2"});
        list.add(new Object[]{3,"zhangsan3"});
        int[] result = jdbcTemplate.batchUpdate(sql, list);
        for (int i : result) {
            System.out.println(i);
        }
    }
}
```

##### 5、查询某个值，并以对象的方式返回

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select * from emp where empno = ?";
        Emp emp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Emp.class), 7369);
        System.out.println(emp);
    }
}
```

##### 6、查询返回集合对象

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select * from emp where sal > ?";
        List<Emp> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class), 1500);
        for (Emp emp : query) {
            System.out.println(emp);
        }
    }
}
```

##### 7、返回组合函数的值

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select max(sal) from emp";
        Double aDouble = jdbcTemplate.queryForObject(sql, Double.class);
        System.out.println(aDouble);
    }
}

```

##### 8、使用具备具名函数的JdbcTemplate

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
</beans>
```

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        NamedParameterJdbcTemplate jdbcTemplate = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(:empno,:ename)";
        Map<String,Object> map = new HashMap<>();
        map.put("empno",2222);
        map.put("ename","sili");
        int update = jdbcTemplate.update(sql, map);
        System.out.println(update);
    }
}
```

##### 9、整合EmpDao

jdbcTemplate.xml

```xml
    <context:component-scan base-package="com.zph"></context:component-scan>

```

EmpDao.java

```java
package com.zph.dao;

import com.zph.bean.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmpDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Emp emp){
        String sql = "insert into emp(empno,ename) values(?,?)";
        int update = jdbcTemplate.update(sql, emp.getEmpno(), emp.getEname());
        System.out.println(update);
    }
}

```

MyTest.java

```java
import com.zph.bean.Emp;
import com.zph.dao.EmpDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        EmpDao empDao = context.getBean("empDao", EmpDao.class);
        empDao.save(new Emp(3333,"wangwu"));
    }
}

```

### 2、声明式事务

##### 1、环境准备

tx.sql

```sql
/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : tx

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-02-13 19:19:32
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `username` varchar(10) NOT NULL DEFAULT '',
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO account VALUES ('lisi', '1000');
INSERT INTO account VALUES ('zhangsan', '1000');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(10) NOT NULL,
  `book_name` varchar(10) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO book VALUES ('1', '西游记', '100');
INSERT INTO book VALUES ('2', '水浒传', '100');
INSERT INTO book VALUES ('3', '三国演义', '100');
INSERT INTO book VALUES ('4', '红楼梦', '100');

-- ----------------------------
-- Table structure for `book_stock`
-- ----------------------------
DROP TABLE IF EXISTS `book_stock`;
CREATE TABLE `book_stock` (
  `id` int(255) NOT NULL DEFAULT '0',
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book_stock
-- ----------------------------
INSERT INTO book_stock VALUES ('1', '1000');
INSERT INTO book_stock VALUES ('2', '1000');
INSERT INTO book_stock VALUES ('3', '1000');
INSERT INTO book_stock VALUES ('4', '1000');

```

BookDao.java

```java
package com.zph.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     * @param userName
     * @param price
     */
    public void updateBalance(String userName,int price){
        String sql = "update account set balance=balance-? where username=?";
        jdbcTemplate.update(sql,price,userName);
    }

    /**
     * 按照图书的id来获取图书的价格
     * @param id
     * @return
     */
    public int getPrice(int id){
        String sql = "select price from book where id=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,id);
    }

    /**
     * 减库存，减去某本书的库存
     * @param id
     */
    public void updateStock(int id){
        String sql = "update book_stock set stock=stock-1 where id=?";
        jdbcTemplate.update(sql,id);
    }
}

```

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}
```

MyTest.java

```java
import com.zph.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.checkout("zhangsan","1");
    }
}
```

总结：在事务控制方面，主要有两个分类：

编程式事务：在代码中直接加入处理事务的逻辑，可能需要在代码中显式调用beginTransaction()、commit()、rollback()等事务管理相关的方法

声明式事务：在方法的外部添加注解或者直接在配置文件中定义，将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。spring的AOP恰好可以完成此功能：事务管理代码的固定模式作为一种横切关注点，通过AOP方法模块化，进而实现声明式事务。

##### 2、声明式事务的简单配置

​		Spring从不同的事务管理API中抽象出了一整套事务管理机制，让事务管理代码从特定的事务技术中独立出来。开发人员通过配置的方式进行事务管理，而不必了解其底层是如何实现的。

​		Spring的核心事务管理抽象是PlatformTransactionManager。它为事务管理封装了一组独立于技术的方法。无论使用Spring的哪种事务管理策略(编程式或声明式)，事务管理器都是必须的。

​		事务管理器可以以普通的bean的形式声明在Spring IOC容器中。下图是spring提供的事务管理器
![事务管理器](spring/07SpringAOP的详细讲解2/image/事务管理器.png)

1、在配置文件中添加事务管理器

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       https://www.springframework.org/schema/tx/spring-tx.xsd
">
    <context:component-scan base-package="com.zph"></context:component-scan>
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <!--事务控制-->
    <!--配置事务管理器的bean-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--开启基于注解的事务控制模式，依赖tx名称空间-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
</beans>
```

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}
```

##### 3、事务配置的属性

​		isolation：设置事务的隔离级别

​		propagation：事务的传播行为

​		noRollbackFor：那些异常事务可以不回滚

​		noRollbackForClassName：填写的参数是全类名

​		rollbackFor：哪些异常事务需要回滚

​		rollbackForClassName：填写的参数是全类名

​		readOnly：设置事务是否为只读事务		

​		timeout：事务超出指定执行时长后自动终止并回滚,单位是秒

##### 4、测试超时属性

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3)
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}
```

##### 5、设置事务只读

​		如果你一次执行单条查询语句，则没有必要启用事务支持，数据库默认支持SQL执行期间的读一致性；

​		如果你一次执行多条查询语句，例如统计查询，报表查询，在这种场景下，多条查询SQL必须保证整体的读一致性，否则，在前条SQL查询之后，后条SQL查询之前，数据被其他用户改变，则该次整体的统计查询将会出现读数据不一致的状态，此时，应该启用事务支持。

​		对于只读查询，可以指定事务类型为readonly，即只读事务。由于只读事务不存在数据的修改，因此数据库将会为只读事务提供一些优化手段

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,readOnly = true)
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}
```

##### 6、设置哪些异常不回滚

​		注意：运行时异常默认回滚，编译时异常默认不回滚

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,noRollbackFor = {ArithmeticException.class,NullPointerException.class})
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
        int i = 1/0;
    }
    
        @Transactional(timeout = 3,noRollbackForClassName = {"java.lang.ArithmeticException"})
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
        int i = 1/0;
    }
}

```

##### 7、设置哪些异常回滚

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,rollbackFor = {FileNotFoundException.class})
    public void checkout(String username,int id) throws FileNotFoundException {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
//        int i = 1/0;
        new FileInputStream("aaa.txt");
    }
}
```

##### 8、设置隔离级别

​		隔离级别没有接触的同学可以看我之前的事务视频，里面有详细讲解，此处不再赘述。

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,isolation = Isolation.READ_COMMITTED)
    public void checkout(String username,int id) throws FileNotFoundException {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
//        int i = 1/0;
        new FileInputStream("aaa.txt");
    }
}
```

##### 9、事务的传播特性

事务的传播特性指的是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行？

spring的事务传播行为一共有7种：

![传播特性](.\document\images\传播特性.jpg)

##### 10、测试事务的传播特性

BookDao.java

```java
package com.zph.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     * @param userName
     * @param price
     */
    public void updateBalance(String userName,int price){
        String sql = "update account set balance=balance-? where username=?";
        jdbcTemplate.update(sql,price,userName);
    }

    /**
     * 按照图书的id来获取图书的价格
     * @param id
     * @return
     */
    public int getPrice(int id){
        String sql = "select price from book where id=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,id);
    }

    /**
     * 减库存，减去某本书的库存
     * @param id
     */
    public void updateStock(int id){
        String sql = "update book_stock set stock=stock-1 where id=?";
        jdbcTemplate.update(sql,id);
    }

    /**
     * 修改图书价格
     * @param id
     * @param price
     */
    public void updatePrice(int id,int price){
        String sql = "update book set price=? where id =?";
        jdbcTemplate.update(sql,price,id);
    }
}
```

BookService.java

```java
package com.zph.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
        int i = 1/0;
    }
}
```

MulService.java

```java
package com.zph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
    }
}
```

MyTest.java

```java
import com.zph.service.MulService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        MulService mulService = context.getBean("mulService", MulService.class);
        mulService.mulTx();
    }
}
```

​		通过上图的结果发现，如果设置的传播特性是Required，那么所有的事务都会统一成一个事务，一旦发生错误，所有的数据都要进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
        int i = 1/0;
    }
}
```

​		通过修改checkout方法的传播特性为Required_new,发现价格进行了回滚，而其他的数据没有进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }
}
```

MulService.java

```java
package com.zph.service;

import com.zph.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
        int i = 1/0;
    }
}
```

​		将bookservice方法的传播行为为Required，并且将报错设置在MulService中，发现会都进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }
}
```

MulService.java

```java
package com.zph.service;

import com.zph.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
        int i = 1/0;
    }
}
```

​		将bookservice方法的传播行为为Requires_new，并且将报错设置在MulService中，发现都不会进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }

    @Transactional
    public void mulTx(){
        checkout("zhangsan",1);
        updatePrice(1,1000);
        int i = 1/0;
    }
}
```

​		如果在bookservice执行的话，会发现刚刚的效果就没有了，原因是外层调用的时候使用的AOP，但是本类方法自己的调用就是最最普通的调用，就是同一个事务。

总结：

```tex
1、事务传播级别是REQUIRED，当checkout()被调用时（假定被另一类中commit()调用），如果checkout()中的代码抛出异常，即便被捕获，commit()中的其他代码都会roll back

2、是REQUIRES_NEW，如果checkout()中的代码抛出异常，并且被捕获，commit()中的其他代码不会roll back；如果commit()中的其他代码抛出异常，而且没有捕获，不会导致checkout()回滚

3、是NESTED，如果checkout()中的代码抛出异常，并且被捕获，commit()中的其他代码不会roll back；如果commit()中的其他代码抛出异常，而且没有捕获，会导致checkout()回滚

    PROPAGATION_REQUIRES_NEW 启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行. 
    另一方面, PROPAGATION_NESTED 开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 嵌套事务开始执行时,  它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交. 
    由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于, PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 roll back. 
```



### 3、基于xml的事务配置

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       https://www.springframework.org/schema/tx/spring-tx.xsd
">
    <context:component-scan base-package="com.zph"></context:component-scan>
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <!--事务控制-->
    <!--配置事务管理器的bean-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--
    基于xml配置的事务：依赖tx名称空间和aop名称空间
        1、spring中提供事务管理器（切面），配置这个事务管理器
        2、配置出事务方法
        3、告诉spring哪些方法是事务方法（事务切面按照我们的切入点表达式去切入事务方法）
    -->
    <bean id="bookService" class="com.zph.service.BookService"></bean>
    <aop:config>
        <aop:pointcut id="txPoint" expression="execution(* com.zph.service.*.*(..))"/>
        <!--事务建议：advice-ref:指向事务管理器的配置-->
        <aop:advisor advice-ref="myAdvice" pointcut-ref="txPoint"></aop:advisor>
    </aop:config>
    <tx:advice id="myAdvice" transaction-manager="transactionManager">
        <!--事务属性-->
        <tx:attributes>
            <!--指明哪些方法是事务方法-->
            <tx:method name="*"/>
            <tx:method name="checkout" propagation="REQUIRED"/>
            <tx:method name="get*" read-only="true"></tx:method>
        </tx:attributes>
    </tx:advice>
</beans>
```

## 05Spring AOP的应用配置

### 1、Spring JdbcTemplate

​		在spring中为了更加方便的操作JDBC，在JDBC的基础之上定义了一个抽象层，此设计的目的是为不同类型的JDBC操作提供模板方法，每个模板方法都能控制整个过程，并允许覆盖过程中的特定任务，通过这种方式，可以尽可能保留灵活性，将数据库存取的工作量讲到最低。

##### 1、配置并测试数据源

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zph</groupId>
    <artifactId>spring_demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.21</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/cglib/cglib -->
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.3.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/aopalliance/aopalliance -->
        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

dbconfig.properties

```properties
jdbc.username=root123
password=123456
url=jdbc:mysql://localhost:3306/demo
driverClassName=com.mysql.jdbc.Driver
```

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
</beans>
```

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
}
```

##### 2、给spring容器添加JdbcTemplate

​		spring容器提供了一个JdbcTemplate类，用来方便操作数据库。

1、添加pom依赖

pom.xml

```xml
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-orm -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.2.3.RELEASE</version>
        </dependency>
```

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
</beans>
```

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        System.out.println(jdbcTemplate);
    }
}
```

##### 3、插入数据

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(?,?)";
        int result = jdbcTemplate.update(sql, 1111, "zhangsan");
        System.out.println(result);
    }
}
```

##### 4、批量插入数据

MyTest.java

```java
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1,"zhangsan1"});
        list.add(new Object[]{2,"zhangsan2"});
        list.add(new Object[]{3,"zhangsan3"});
        int[] result = jdbcTemplate.batchUpdate(sql, list);
        for (int i : result) {
            System.out.println(i);
        }
    }
}

```

##### 5、查询某个值，并以对象的方式返回

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select * from emp where empno = ?";
        Emp emp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Emp.class), 7369);
        System.out.println(emp);
    }
}

```

##### 6、查询返回集合对象

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select * from emp where sal > ?";
        List<Emp> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class), 1500);
        for (Emp emp : query) {
            System.out.println(emp);
        }
    }
}

```

##### 7、返回组合函数的值

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select max(sal) from emp";
        Double aDouble = jdbcTemplate.queryForObject(sql, Double.class);
        System.out.println(aDouble);
    }
}

```

##### 8、使用具备具名函数的JdbcTemplate

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
">
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
</beans>

```

MyTest.java

```java
import com.zph.bean.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        NamedParameterJdbcTemplate jdbcTemplate = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
        String sql = "insert into emp(empno,ename) values(:empno,:ename)";
        Map<String,Object> map = new HashMap<>();
        map.put("empno",2222);
        map.put("ename","sili");
        int update = jdbcTemplate.update(sql, map);
        System.out.println(update);
    }
}

```

##### 9、整合EmpDao

jdbcTemplate.xml

```xml
    <context:component-scan base-package="com.zph"></context:component-scan>

```

EmpDao.java

```java
package com.zph.dao;

import com.zph.bean.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmpDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Emp emp){
        String sql = "insert into emp(empno,ename) values(?,?)";
        int update = jdbcTemplate.update(sql, emp.getEmpno(), emp.getEname());
        System.out.println(update);
    }
}

```

MyTest.java

```java
import com.zph.bean.Emp;
import com.zph.dao.EmpDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        EmpDao empDao = context.getBean("empDao", EmpDao.class);
        empDao.save(new Emp(3333,"wangwu"));
    }
}

```

### 2、声明式事务

##### 1、环境准备

tx.sql

```sql
/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : tx

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-02-13 19:19:32
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `username` varchar(10) NOT NULL DEFAULT '',
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO account VALUES ('lisi', '1000');
INSERT INTO account VALUES ('zhangsan', '1000');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(10) NOT NULL,
  `book_name` varchar(10) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO book VALUES ('1', '西游记', '100');
INSERT INTO book VALUES ('2', '水浒传', '100');
INSERT INTO book VALUES ('3', '三国演义', '100');
INSERT INTO book VALUES ('4', '红楼梦', '100');

-- ----------------------------
-- Table structure for `book_stock`
-- ----------------------------
DROP TABLE IF EXISTS `book_stock`;
CREATE TABLE `book_stock` (
  `id` int(255) NOT NULL DEFAULT '0',
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book_stock
-- ----------------------------
INSERT INTO book_stock VALUES ('1', '1000');
INSERT INTO book_stock VALUES ('2', '1000');
INSERT INTO book_stock VALUES ('3', '1000');
INSERT INTO book_stock VALUES ('4', '1000');


```

BookDao.java

```java
package com.zph.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     * @param userName
     * @param price
     */
    public void updateBalance(String userName,int price){
        String sql = "update account set balance=balance-? where username=?";
        jdbcTemplate.update(sql,price,userName);
    }

    /**
     * 按照图书的id来获取图书的价格
     * @param id
     * @return
     */
    public int getPrice(int id){
        String sql = "select price from book where id=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,id);
    }

    /**
     * 减库存，减去某本书的库存
     * @param id
     */
    public void updateStock(int id){
        String sql = "update book_stock set stock=stock-1 where id=?";
        jdbcTemplate.update(sql,id);
    }
}

```

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}

```

MyTest.java

```java
import com.zph.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.SQLException;

public class MyTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.checkout("zhangsan","1");
    }
}

```

总结：在事务控制方面，主要有两个分类：

编程式事务：在代码中直接加入处理事务的逻辑，可能需要在代码中显式调用beginTransaction()、commit()、rollback()等事务管理相关的方法

声明式事务：在方法的外部添加注解或者直接在配置文件中定义，将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。spring的AOP恰好可以完成此功能：事务管理代码的固定模式作为一种横切关注点，通过AOP方法模块化，进而实现声明式事务。

##### 2、声明式事务的简单配置

​		Spring从不同的事务管理API中抽象出了一整套事务管理机制，让事务管理代码从特定的事务技术中独立出来。开发人员通过配置的方式进行事务管理，而不必了解其底层是如何实现的。

​		Spring的核心事务管理抽象是PlatformTransactionManager。它为事务管理封装了一组独立于技术的方法。无论使用Spring的哪种事务管理策略(编程式或声明式)，事务管理器都是必须的。

​		事务管理器可以以普通的bean的形式声明在Spring IOC容器中。下图是spring提供的事务管理器
![事务管理器](spring/08Spring源码讲解/image/事务管理器.png)

1、在配置文件中添加事务管理器

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       https://www.springframework.org/schema/tx/spring-tx.xsd
">
    <context:component-scan base-package="com.zph"></context:component-scan>
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <!--事务控制-->
    <!--配置事务管理器的bean-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--开启基于注解的事务控制模式，依赖tx名称空间-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
</beans>

```

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}

```

##### 3、事务配置的属性

​		isolation：设置事务的隔离级别

​		propagation：事务的传播行为

​		noRollbackFor：那些异常事务可以不回滚

​		noRollbackForClassName：填写的参数是全类名

​		rollbackFor：哪些异常事务需要回滚

​		rollbackForClassName：填写的参数是全类名

​		readOnly：设置事务是否为只读事务		

​		timeout：事务超出指定执行时长后自动终止并回滚,单位是秒

##### 4、测试超时属性

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3)
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}

```

##### 5、设置事务只读

​		如果你一次执行单条查询语句，则没有必要启用事务支持，数据库默认支持SQL执行期间的读一致性；

​		如果你一次执行多条查询语句，例如统计查询，报表查询，在这种场景下，多条查询SQL必须保证整体的读一致性，否则，在前条SQL查询之后，后条SQL查询之前，数据被其他用户改变，则该次整体的统计查询将会出现读数据不一致的状态，此时，应该启用事务支持。

​		对于只读查询，可以指定事务类型为readonly，即只读事务。由于只读事务不存在数据的修改，因此数据库将会为只读事务提供一些优化手段

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,readOnly = true)
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }
}

```

##### 6、设置哪些异常不回滚

​		注意：运行时异常默认回滚，编译时异常默认不回滚

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,noRollbackFor = {ArithmeticException.class,NullPointerException.class})
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
        int i = 1/0;
    }
    
        @Transactional(timeout = 3,noRollbackForClassName = {"java.lang.ArithmeticException"})
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
        int i = 1/0;
    }
}

```

##### 7、设置哪些异常回滚

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,rollbackFor = {FileNotFoundException.class})
    public void checkout(String username,int id) throws FileNotFoundException {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
//        int i = 1/0;
        new FileInputStream("aaa.txt");
    }
}

```

##### 8、设置隔离级别

​		隔离级别没有接触的同学可以看我之前的事务视频，里面有详细讲解，此处不再赘述。

BookService.java

```java
package com.zph.service;

import com.zph.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(timeout = 3,isolation = Isolation.READ_COMMITTED)
    public void checkout(String username,int id) throws FileNotFoundException {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
//        int i = 1/0;
        new FileInputStream("aaa.txt");
    }
}

```

##### 9、事务的传播特性

事务的传播特性指的是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行？

spring的事务传播行为一共有7种：

![传播特性](.\document\images\传播特性.jpg)

##### 10、测试事务的传播特性

BookDao.java

```java
package com.zph.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     * @param userName
     * @param price
     */
    public void updateBalance(String userName,int price){
        String sql = "update account set balance=balance-? where username=?";
        jdbcTemplate.update(sql,price,userName);
    }

    /**
     * 按照图书的id来获取图书的价格
     * @param id
     * @return
     */
    public int getPrice(int id){
        String sql = "select price from book where id=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,id);
    }

    /**
     * 减库存，减去某本书的库存
     * @param id
     */
    public void updateStock(int id){
        String sql = "update book_stock set stock=stock-1 where id=?";
        jdbcTemplate.update(sql,id);
    }

    /**
     * 修改图书价格
     * @param id
     * @param price
     */
    public void updatePrice(int id,int price){
        String sql = "update book set price=? where id =?";
        jdbcTemplate.update(sql,price,id);
    }
}

```

BookService.java

```java
package com.zph.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
        int i = 1/0;
    }
}

```

MulService.java

```java
package com.zph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
    }
}

```

MyTest.java

```java
import com.zph.service.MulService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        MulService mulService = context.getBean("mulService", MulService.class);
        mulService.mulTx();
    }
}

```

​		通过上图的结果发现，如果设置的传播特性是Required，那么所有的事务都会统一成一个事务，一旦发生错误，所有的数据都要进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
        int i = 1/0;
    }
}

```

​		通过修改checkout方法的传播特性为Required_new,发现价格进行了回滚，而其他的数据没有进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }
}

```

MulService.java

```java
package com.zph.service;

import com.zph.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
        int i = 1/0;
    }
}

```

​		将bookservice方法的传播行为为Required，并且将报错设置在MulService中，发现会都进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }
}

```

MulService.java

```java
package com.zph.service;

import com.zph.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        bookService.checkout("zhangsan",1);
        bookService.updatePrice(1,1000);
        int i = 1/0;
    }
}

```

​		将bookservice方法的传播行为为Requires_new，并且将报错设置在MulService中，发现都不会进行回滚。

------

BookService.java

```java
package com.zph.service;

        import com.zph.dao.BookDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Isolation;
        import org.springframework.transaction.annotation.Propagation;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账：传入哪个用户买了哪本书
     * @param username
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,int id) {

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(int id,int price){
        bookDao.updatePrice(id,price);
    }

    @Transactional
    public void mulTx(){
        checkout("zhangsan",1);
        updatePrice(1,1000);
        int i = 1/0;
    }
}

```

​		如果在bookservice执行的话，会发现刚刚的效果就没有了，原因是外层调用的时候使用的AOP，但是本类方法自己的调用就是最最普通的调用，就是同一个事务。

总结：

```tex
1、事务传播级别是REQUIRED，当checkout()被调用时（假定被另一类中commit()调用），如果checkout()中的代码抛出异常，即便被捕获，commit()中的其他代码都会roll back

2、是REQUIRES_NEW，如果checkout()中的代码抛出异常，并且被捕获，commit()中的其他代码不会roll back；如果commit()中的其他代码抛出异常，而且没有捕获，不会导致checkout()回滚

3、是NESTED，如果checkout()中的代码抛出异常，并且被捕获，commit()中的其他代码不会roll back；如果commit()中的其他代码抛出异常，而且没有捕获，会导致checkout()回滚

    PROPAGATION_REQUIRES_NEW 启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行. 
    另一方面, PROPAGATION_NESTED 开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 嵌套事务开始执行时,  它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交. 
    由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于, PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 roll back. 

```



### 3、基于xml的事务配置

jdbcTemplate.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       https://www.springframework.org/schema/tx/spring-tx.xsd
">
    <context:component-scan base-package="com.zph"></context:component-scan>
    <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="driverClassName" value="${jdbc.driverClassName}"></property>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
    </bean>
    <!--事务控制-->
    <!--配置事务管理器的bean-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--
    基于xml配置的事务：依赖tx名称空间和aop名称空间
        1、spring中提供事务管理器（切面），配置这个事务管理器
        2、配置出事务方法
        3、告诉spring哪些方法是事务方法（事务切面按照我们的切入点表达式去切入事务方法）
    -->
    <bean id="bookService" class="com.zph.service.BookService"></bean>
    <aop:config>
        <aop:pointcut id="txPoint" expression="execution(* com.zph.service.*.*(..))"/>
        <!--事务建议：advice-ref:指向事务管理器的配置-->
        <aop:advisor advice-ref="myAdvice" pointcut-ref="txPoint"></aop:advisor>
    </aop:config>
    <tx:advice id="myAdvice" transaction-manager="transactionManager">
        <!--事务属性-->
        <tx:attributes>
            <!--指明哪些方法是事务方法-->
            <tx:method name="*"/>
            <tx:method name="checkout" propagation="REQUIRED"/>
            <tx:method name="get*" read-only="true"></tx:method>
        </tx:attributes>
    </tx:advice>
</beans>

```

# 06Spring原理讲解

### 1、什么是Spring框架，Spring框架主要包含哪些模块

​		Spring是一个开源框架，Spring是一个轻量级的Java 开发框架。它是为了解决企业应用开发的复杂性而创建的。框架的主要优势之一就是其分层架构，分层架构允许使用者选择使用哪一个组件，同时为 J2EE 应用程序开发提供集成的框架。Spring使用基本的JavaBean来完成以前只可能由EJB完成的事情。然而，Spring的用途不仅限于服务器端的开发。从简单性、可测试性和松耦合的角度而言，任何Java应用都可以从Spring中受益。Spring的核心是控制反转（IoC）和面向切面（AOP）。简单来说，Spring是一个分层的full-stack(一站式) 轻量级开源框架。

主要包含的模块：

![](spring/08Spring源码讲解/image/spring-overview.png)

### 2、Spring框架的优势

​		1、Spring通过DI、AOP和消除样板式代码来简化企业级Java开发

​		2、Spring框架之外还存在一个构建在核心框架之上的庞大生态圈，它将Spring扩展到不同的领域，如Web服务、REST、移动开发以及NoSQL

​		3、低侵入式设计，代码的污染极低

​		4、独立于各种应用服务器，基于Spring框架的应用，可以真正实现Write Once,Run Anywhere的承诺

​		5、Spring的IoC容器降低了业务对象替换的复杂性，提高了组件之间的解耦

​		6、Spring的AOP允许将一些通用任务如安全、事务、日志等进行集中式处理，从而提供了更好的复用

​		7、Spring的ORM和DAO提供了与第三方持久层框架的的良好整合，并简化了底层的数据库访问

​		8、Spring的高度开放性，并不强制应用完全依赖于Spring，开发者可自由选用Spring框架的部分或全部

### 3、IOC和DI是什么？

​		控制反转是就是应用本身不负责依赖对象的创建和维护,依赖对象的创建及维护是由外部容器负责的,这样控制权就有应用转移到了外部容器,控制权的转移就是控制反转。

​		依赖注入是指:在程序运行期间,由外部容器动态地将依赖对象注入到组件中如：一般，通过构造函数注入或者setter注入。

### 4、描述下Spring IOC容器的初始化过程

​		Spring IOC容器的初始化简单的可以分为三个过程：

​		第一个过程是Resource资源定位。这个Resouce指的是BeanDefinition的资源定位。这个过程就是容器找数据的过程，就像水桶装水需要先找到水一样。

​		第二个过程是BeanDefinition的载入过程。这个载入过程是把用户定义好的Bean表示成Ioc容器内部的数据结构，而这个容器内部的数据结构就是BeanDefition。

​		第三个过程是向IOC容器注册这些BeanDefinition的过程，这个过程就是将前面的BeanDefition保存到HashMap中的过程。

### 5、BeanFactory 和 FactoryBean的区别？

- **BeanFactory**是个Factory，也就是IOC容器或对象工厂，在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的，提供了实例化对象和拿对象的功能。

  使用场景：

  - 从Ioc容器中获取Bean(byName or byType)
  - 检索Ioc容器中是否包含指定的Bean
  - 判断Bean是否为单例

- **FactoryBean**是个Bean，这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,它的实现与设计模式中的工厂模式和修饰器模式类似。

  使用场景

  - ProxyFactoryBean

### 6、BeanFactory和ApplicationContext的异同

![](spring/08Spring源码讲解/image/ApplicationContext类图.png)

相同：

- Spring提供了两种不同的IOC 容器，一个是BeanFactory，另外一个是ApplicationContext，它们都是Java  interface，ApplicationContext继承于BeanFactory(ApplicationContext继承ListableBeanFactory。
- 它们都可以用来配置XML属性，也支持属性的自动注入。
- 而ListableBeanFactory继承BeanFactory)，BeanFactory 和 ApplicationContext 都提供了一种方式，使用getBean("bean name")获取bean。

不同：

- 当你调用getBean()方法时，BeanFactory仅实例化bean，而ApplicationContext 在启动容器的时候实例化单例bean，不会等待调用getBean()方法时再实例化。
- BeanFactory不支持国际化，即i18n，但ApplicationContext提供了对它的支持。
- BeanFactory与ApplicationContext之间的另一个区别是能够将事件发布到注册为监听器的bean。
- BeanFactory 的一个核心实现是XMLBeanFactory 而ApplicationContext  的一个核心实现是ClassPathXmlApplicationContext，Web容器的环境我们使用WebApplicationContext并且增加了getServletContext 方法。
- 如果使用自动注入并使用BeanFactory，则需要使用API注册AutoWiredBeanPostProcessor，如果使用ApplicationContext，则可以使用XML进行配置。
- 简而言之，BeanFactory提供基本的IOC和DI功能，而ApplicationContext提供高级功能，BeanFactory可用于测试和非生产使用，但ApplicationContext是功能更丰富的容器实现，应该优于BeanFactory

### 7、Spring Bean 的生命周期？

![](spring/08Spring源码讲解/image/bean生命周期.png)

总结：

**（1）实例化Bean：**

对于BeanFactory容器，当客户向容器请求一个尚未初始化的bean时，或初始化bean的时候需要注入另一个尚未初始化的依赖时，容器就会调用createBean进行实例化。对于ApplicationContext容器，当容器启动结束后，通过获取BeanDefinition对象中的信息，实例化所有的bean。

**（2）设置对象属性（依赖注入）：**

实例化后的对象被封装在BeanWrapper对象中，紧接着，Spring根据BeanDefinition中的信息 以及 通过BeanWrapper提供的设置属性的接口完成依赖注入。

**（3）处理Aware接口：**

接着，Spring会检测该对象是否实现了xxxAware接口，并将相关的xxxAware实例注入给Bean：

①如果这个Bean已经实现了BeanNameAware接口，会调用它实现的setBeanName(String beanId)方法，此处传递的就是Spring配置文件中Bean的id值；

②如果这个Bean已经实现了BeanFactoryAware接口，会调用它实现的setBeanFactory()方法，传递的是Spring工厂自身。

③如果这个Bean已经实现了ApplicationContextAware接口，会调用setApplicationContext(ApplicationContext)方法，传入Spring上下文；

**（4）BeanPostProcessor：**

如果想对Bean进行一些自定义的处理，那么可以让Bean实现了BeanPostProcessor接口，那将会调用postProcessBeforeInitialization(Object obj, String s)方法。

**（5）InitializingBean 与 init-method：**

如果Bean在Spring配置文件中配置了 init-method 属性，则会自动调用其配置的初始化方法。

**（6）如果这个Bean实现了BeanPostProcessor接口**，将会调用postProcessAfterInitialization(Object obj, String s)方法；由于这个方法是在Bean初始化结束时调用的，所以可以被应用于内存或缓存技术；

以上几个步骤完成后，Bean就已经被正确创建了，之后就可以使用这个Bean了。

**（7）DisposableBean：**

当Bean不再需要时，会经过清理阶段，如果Bean实现了DisposableBean这个接口，会调用其实现的destroy()方法；

**（8）destroy-method：**

最后，如果这个Bean的Spring配置中配置了destroy-method属性，会自动调用其配置的销毁方法。

### 8、Spring AOP的实现原理？

​		Spring AOP使用的动态代理，所谓的动态代理就是说AOP框架不会去修改字节码，而是在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。

​		Spring AOP中的动态代理主要有两种方式，JDK动态代理和CGLIB动态代理。JDK动态代理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK动态代理的核心是InvocationHandler接口和Proxy类。

​		如果目标类没有实现接口，那么Spring AOP会选择使用CGLIB来动态代理目标类。CGLIB（Code Generation Library），是一个代码生成的类库，可以在运行时动态的生成某个类的子类，注意，CGLIB是通过继承的方式做的动态代理，因此如果某个类被标记为final，那么它是无法使用CGLIB做动态代理的。

### 9、Spring 是如何管理事务的？

​		Spring事务管理主要包括3个接口，Spring的事务主要是由它们(**PlatformTransactionManager，TransactionDefinition，TransactionStatus**)三个共同完成的。

**1. PlatformTransactionManager**：事务管理器--主要用于平台相关事务的管理

主要有三个方法：

- commit 事务提交；
- rollback 事务回滚；
- getTransaction 获取事务状态。

**2. TransactionDefinition**：事务定义信息--用来定义事务相关的属性，给事务管理器PlatformTransactionManager使用

这个接口有下面四个主要方法：

- getIsolationLevel：获取隔离级别；
- getPropagationBehavior：获取传播行为；
- getTimeout：获取超时时间；
- isReadOnly：是否只读（保存、更新、删除时属性变为false--可读写，查询时为true--只读）

事务管理器能够根据这个返回值进行优化，这些事务的配置信息，都可以通过配置文件进行配置。

**3. TransactionStatus**：事务具体运行状态--事务管理过程中，每个时间点事务的状态信息。

例如它的几个方法：

- hasSavepoint()：返回这个事务内部是否包含一个保存点，
- isCompleted()：返回该事务是否已完成，也就是说，是否已经提交或回滚
- isNewTransaction()：判断当前事务是否是一个新事务

**声明式事务的优缺点**：

- **优点**：不需要在业务逻辑代码中编写事务相关代码，只需要在配置文件配置或使用注解（@Transaction），这种方式没有侵入性。
- **缺点**：声明式事务的最细粒度作用于方法上，如果像代码块也有事务需求，只能变通下，将代码块变为方法。

### 10、Spring 的不同事务传播行为有哪些，干什么用的？

![](spring/08Spring源码讲解/image/传播特性.jpg)

### 11、Spring 中用到了那些设计模式？

- 代理模式—在AOP中被用的比较多。
- 单例模式—在spring配置文件中定义的bean默认为单例模式。
- 模板方法—用来解决代码重复的问题。比如. RestTemplate, JmsTemplate, JpaTemplate。
- 工厂模式—BeanFactory用来创建对象的实例。
- 适配器--spring aop
- 装饰器--spring data hashmapper
- 观察者-- spring 事件驱动模型
- 回调--Spring Aware回调接口

### 12、Spring如何解决循环依赖？

https://blog.csdn.net/qq_36381855/article/details/79752689

### 13、bean的作用域

（1）singleton：默认，每个容器中只有一个bean的实例，单例的模式由BeanFactory自身来维护。

（2）prototype：为每一个bean请求提供一个实例。

（3）request：为每一个网络请求创建一个实例，在请求完成以后，bean会失效并被垃圾回收器回收。

（4）session：与request范围类似，确保每个session中有一个bean的实例，在session过期后，bean会随之失效。

### 14、Spring框架中有哪些不同类型的事件

（1）上下文更新事件（ContextRefreshedEvent）：在调用ConfigurableApplicationContext 接口中的refresh()方法时被触发。

（2）上下文开始事件（ContextStartedEvent）：当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。

（3）上下文停止事件（ContextStoppedEvent）：当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。

（4）上下文关闭事件（ContextClosedEvent）：当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。

（5）请求处理事件（RequestHandledEvent）：在Web应用中，当一个http请求（request）结束触发该事件。

### 15、Spring通知有哪些类型

（1）前置通知（Before advice）：在某连接点（join point）之前执行的通知，但这个通知不能阻止连接点前的执行（除非它抛出一个异常）。

（2）返回后通知（After returning advice）：在某连接点（join point）正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回。 

（3）抛出异常后通知（After throwing advice）：在方法抛出异常退出时执行的通知。 

（4）后通知（After (finally) advice）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。 

（5）环绕通知（Around Advice）：包围一个连接点（join point）的通知，如方法调用。这是最强大的一种通知类型。 环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行。 环绕通知是最常用的一种通知类型。

### 16、Spring的自动装配

在spring中，对象无需自己查找或创建与其关联的其他对象，由容器负责把需要相互协作的对象引用赋予各个对象，使用autowire来配置自动装载模式。

在Spring框架xml配置中共有5种自动装配：

（1）no：默认的方式是不进行自动装配的，通过手工设置ref属性来进行装配bean。

（2）byName：通过bean的名称进行自动装配，如果一个bean的 property 与另一bean 的name 相同，就进行自动装配。 

（3）byType：通过参数的数据类型进行自动装配。

（4）constructor：利用构造函数进行装配，并且构造函数的参数通过byType进行装配。

（5）autodetect：自动探测，如果有构造方法，通过 construct的方式自动装配，否则使用 byType的方式自动装配。

基于注解的方式：

使用@Autowired注解来自动装配指定的bean。在使用@Autowired注解之前需要在Spring配置文件进行配置，<context:annotation-config />。在启动spring IoC时，容器自动装载了一个AutowiredAnnotationBeanPostProcessor后置处理器，当容器扫描到@Autowied、@Resource或@Inject时，就会在IoC容器自动查找需要的bean，并装配给该对象的属性。在使用@Autowired时，首先在容器中查询对应类型的bean：

如果查询结果刚好为一个，就将该bean装配给@Autowired指定的数据；

如果查询的结果不止一个，那么@Autowired会根据名称来查找；

如果上述查找的结果为空，那么会抛出异常。解决方法时，使用required=false。

@Autowired可用于：构造函数、成员变量、Setter方法

注：@Autowired和@Resource之间的区别

(1) @Autowired默认是按照类型装配注入的，默认情况下它要求依赖对象必须存在（可以设置它required属性为false）。

(2) @Resource默认是按照名称来装配注入的，只有当找不到与名称匹配的bean才会按照类型来装配注入。

# 07动态代理

### 1、jdk的动态代理

​		动态代理与静态代理的区别此处不再赘述，大家可以看马老师的设计模式视频，我们主要讲一下动态代理的实现原理，说明白原理的话肯定是要看源码了，大家不要慌，干就完了！！！

​		其实在使用动态代理的时候最最核心的就是Proxy.newProxyInstance(loader, interfaces, h);废话不多说，直接干源码。

**动态代理的样例代码：**

Calculator.java

```java
package com.zph;

public interface Calculator {

    public int add(int i, int j);

    public int sub(int i, int j);

    public int mult(int i, int j);

    public int div(int i, int j);
}
```

MyCalculator.java

```java
package com.zph;

public class MyCalculator implements Calculator {
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mult(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}
```

CalculatorProxy.java

```java
package com.zph;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class CalculatorProxy {
    public static Calculator getProxy(final Calculator calculator){
        ClassLoader loader = calculator.getClass().getClassLoader();
        Class<?>[] interfaces = calculator.getClass().getInterfaces();
        InvocationHandler h = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                try {
                    result = method.invoke(calculator, args);
                } catch (Exception e) {
                } finally {
                }
                return result;
            }
        };
        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }
}
```

Test.java

```java
package com.zph;

public class Test {
    public static void main(String[] args) {
        Calculator proxy = CalculatorProxy.getProxy(new MyCalculator());
        proxy.add(1,1);
        System.out.println(proxy.getClass());
    }
}
```

**动态代理的源码：**

Proxy.java的newProxyInstance方法：

```java
public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
    //判断InvocationHandler是否为空，若为空，抛出空指针异常
        Objects.requireNonNull(h);

        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }

        /*
         * Look up or generate the designated proxy class.
         * 生成接口的代理类的字节码文件
         */
        Class<?> cl = getProxyClass0(loader, intfs);

        /*
         * Invoke its constructor with the designated invocation handler.
         * 使用自定义的InvocationHandler作为参数，调用构造函数获取代理类对象实例
         */
        try {
            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }
			//获取代理对象的构造方法
            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            //生成代理类的实例并把InvocationHandlerImpl的实例传给构造方法
            return cons.newInstance(new Object[]{h});
        } catch (IllegalAccessException|InstantiationException e) {
            throw new InternalError(e.toString(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new InternalError(t.toString(), t);
            }
        } catch (NoSuchMethodException e) {
            throw new InternalError(e.toString(), e);
        }
    }
```

getProxyClass0(ClassLoader loader,Class<?>... interfaces)

```java
    private static Class<?> getProxyClass0(ClassLoader loader,
                                           Class<?>... interfaces) {
        //限定代理的接口不能超过65535个
        if (interfaces.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }

        // If the proxy class defined by the given loader implementing
        // the given interfaces exists, this will simply return the cached copy;
        // otherwise, it will create the proxy class via the ProxyClassFactory
        // 如果缓存中已经存在相应接口的代理类，直接返回，否则，使用ProxyClassFactory创建代理类
        return proxyClassCache.get(loader, interfaces);
    }

    /**
     * a cache of proxy classes
     */
    private static final WeakCache<ClassLoader, Class<?>[], Class<?>>
        proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
```

get()方法：

```java
 public V get(K key, P parameter) {
        Objects.requireNonNull(parameter);

        expungeStaleEntries();

        Object cacheKey = CacheKey.valueOf(key, refQueue);

        // lazily install the 2nd level valuesMap for the particular cacheKey
        ConcurrentMap<Object, Supplier<V>> valuesMap = map.get(cacheKey);
        if (valuesMap == null) {
            ConcurrentMap<Object, Supplier<V>> oldValuesMap
                = map.putIfAbsent(cacheKey,
                                  valuesMap = new ConcurrentHashMap<>());
            if (oldValuesMap != null) {
                valuesMap = oldValuesMap;
            }
        }

        // create subKey and retrieve the possible Supplier<V> stored by that
        // subKey from valuesMap
        Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));
        Supplier<V> supplier = valuesMap.get(subKey);
        Factory factory = null;

        while (true) {
            if (supplier != null) {
                // supplier might be a Factory or a CacheValue<V> instance
                V value = supplier.get();
                if (value != null) {
                    return value;
                }
            }
            // else no supplier in cache
            // or a supplier that returned null (could be a cleared CacheValue
            // or a Factory that wasn't successful in installing the CacheValue)

            // lazily construct a Factory
            if (factory == null) {
                factory = new Factory(key, parameter, subKey, valuesMap);
            }

            if (supplier == null) {
                supplier = valuesMap.putIfAbsent(subKey, factory);
                if (supplier == null) {
                    // successfully installed Factory
                    supplier = factory;
                }
                // else retry with winning supplier
            } else {
                if (valuesMap.replace(subKey, supplier, factory)) {
                    // successfully replaced
                    // cleared CacheEntry / unsuccessful Factory
                    // with our Factory
                    supplier = factory;
                } else {
                    // retry with current supplier
                    supplier = valuesMap.get(subKey);
                }
            }
        }
    }
```

在此方法中会调用suppier.get方法，suppier就是Factory,此类定义在WeakCache内部

```java
 @Override
        public synchronized V get() { // serialize access
            // re-check
            Supplier<V> supplier = valuesMap.get(subKey);
            if (supplier != this) {
                // something changed while we were waiting:
                // might be that we were replaced by a CacheValue
                // or were removed because of failure ->
                // return null to signal WeakCache.get() to retry
                // the loop
                return null;
            }
            // else still us (supplier == this)

            // create new value
            V value = null;
            try {
                value = Objects.requireNonNull(valueFactory.apply(key, parameter));
            } finally {
                if (value == null) { // remove us on failure
                    valuesMap.remove(subKey, this);
                }
            }
            // the only path to reach here is with non-null value
            assert value != null;

            // wrap value with CacheValue (WeakReference)
            CacheValue<V> cacheValue = new CacheValue<>(value);

            // put into reverseMap
            reverseMap.put(cacheValue, Boolean.TRUE);

            // try replacing us with CacheValue (this should always succeed)
            if (!valuesMap.replace(subKey, this, cacheValue)) {
                throw new AssertionError("Should not reach here");
            }

            // successfully replaced us with new CacheValue -> return the value
            // wrapped by it
            return value;
        }
    }
```

发现在此方法中调用了valueFactory.apply(key, parameter)方法，此对象其实就是Proxy中的ProxyClassFactory，会调用其apply方法

```java
 		// prefix for all proxy class names
		//代理类的前缀
        private static final String proxyClassNamePrefix = "$Proxy";

        // next number to use for generation of unique proxy class names
		//生成代理类名称的计数器
        private static final AtomicLong nextUniqueNumber = new AtomicLong(); 
@Override
        public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {

            Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
            for (Class<?> intf : interfaces) {
                /*
                 * Verify that the class loader resolves the name of this
                 * interface to the same Class object.
                 * 家安眼泪加载器是否能通接口名称加载该类
                 */
                Class<?> interfaceClass = null;
                try {
                    interfaceClass = Class.forName(intf.getName(), false, loader);
                } catch (ClassNotFoundException e) {
                }
                if (interfaceClass != intf) {
                    throw new IllegalArgumentException(
                        intf + " is not visible from class loader");
                }
                /*
                 * Verify that the Class object actually represents an
                 * interface.
                 * 检测该类是否是接口类型
                 */
                if (!interfaceClass.isInterface()) {
                    throw new IllegalArgumentException(
                        interfaceClass.getName() + " is not an interface");
                }
                /*
                 * Verify that this interface is not a duplicate.
                 * 检验接口是否重复
                 */
                if (interfaceSet.put(interfaceClass, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException(
                        "repeated interface: " + interfaceClass.getName());
                }
            }

            //代理类包名
            String proxyPkg = null;     // package to define proxy class in
            int accessFlags = Modifier.PUBLIC | Modifier.FINAL;

            /*
             * Record the package of a non-public proxy interface so that the
             * proxy class will be defined in the same package.  Verify that
             * all non-public proxy interfaces are in the same package.
             * 非public接口，代理类的包名与接口的包名相同
             */
            for (Class<?> intf : interfaces) {
                int flags = intf.getModifiers();
                if (!Modifier.isPublic(flags)) {
                    accessFlags = Modifier.FINAL;
                    String name = intf.getName();
                    int n = name.lastIndexOf('.');
                    String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                    if (proxyPkg == null) {
                        proxyPkg = pkg;
                    } else if (!pkg.equals(proxyPkg)) {
                        throw new IllegalArgumentException(
                            "non-public interfaces from different packages");
                    }
                }
            }

            if (proxyPkg == null) {
                // if no non-public proxy interfaces, use com.sun.proxy package
                // public代理接口，使用com.sun.proxy包名
                proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
            }

            /*
             * Choose a name for the proxy class to generate.
             * 为代理类生成名字
             */
            long num = nextUniqueNumber.getAndIncrement();
            String proxyName = proxyPkg + proxyClassNamePrefix + num;

            /*
             * Generate the specified proxy class.
             * 真正生成代理类的字节码文件的地方
             */
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);
            try {
                //使用类加载器将代理类的字节码文件加载到JVM中
                return defineClass0(loader, proxyName,
                                    proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                /*
                 * A ClassFormatError here means that (barring bugs in the
                 * proxy class generation code) there was some other
                 * invalid aspect of the arguments supplied to the proxy
                 * class creation (such as virtual machine limitations
                 * exceeded).
                 */
                throw new IllegalArgumentException(e.toString());
            }
        }
    }

```

实际生成的方法是generateProxyClass

```java
    public static byte[] generateProxyClass(final String var0, Class<?>[] var1, int var2) {
        ProxyGenerator var3 = new ProxyGenerator(var0, var1, var2);
        final byte[] var4 = var3.generateClassFile();
        //将要生成代理类的字节码文件保存在磁盘中
        if (saveGeneratedFiles) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    try {
                        int var1 = var0.lastIndexOf(46);
                        Path var2;
                        if (var1 > 0) {
                            Path var3 = Paths.get(var0.substring(0, var1).replace('.', File.separatorChar));
                            Files.createDirectories(var3);
                            var2 = var3.resolve(var0.substring(var1 + 1, var0.length()) + ".class");
                        } else {
                            var2 = Paths.get(var0 + ".class");
                        }

                        Files.write(var2, var4, new OpenOption[0]);
                        return null;
                    } catch (IOException var4x) {
                        throw new InternalError("I/O exception saving generated file: " + var4x);
                    }
                }
            });
        }

        return var4;
    }

```

如果想要生成的话可以添加如下参数：

```java
 System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

```

生成的代理类的反编译码为：

$Proxy.java

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sun.proxy;

import com.zph.Calculator;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements Calculator {
    private static Method m1;
    private static Method m2;
    private static Method m3;
    private static Method m4;
    private static Method m5;
    private static Method m6;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int add(int var1, int var2) throws  {
        try {
            return (Integer)super.h.invoke(this, m3, new Object[]{var1, var2});
        } catch (RuntimeException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final int sub(int var1, int var2) throws  {
        try {
            return (Integer)super.h.invoke(this, m4, new Object[]{var1, var2});
        } catch (RuntimeException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final int mult(int var1, int var2) throws  {
        try {
            return (Integer)super.h.invoke(this, m5, new Object[]{var1, var2});
        } catch (RuntimeException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final int div(int var1, int var2) throws  {
        try {
            return (Integer)super.h.invoke(this, m6, new Object[]{var1, var2});
        } catch (RuntimeException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m3 = Class.forName("com.zph.Calculator").getMethod("add", Integer.TYPE, Integer.TYPE);
            m4 = Class.forName("com.zph.Calculator").getMethod("sub", Integer.TYPE, Integer.TYPE);
            m5 = Class.forName("com.zph.Calculator").getMethod("mult", Integer.TYPE, Integer.TYPE);
            m6 = Class.forName("com.zph.Calculator").getMethod("div", Integer.TYPE, Integer.TYPE);
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}

```

### 2、cglib的动态代理

​		CGLIB(Code Generation Library)是一个开源项目！是一个强大的，高性能，高质量的Code生成类库，它可以在运行期扩展Java类与实现Java接口。

​		CGLIB是一个强大的高性能的代码生成包。它广泛的被许多AOP的框架使用，例如Spring AOP为他们提供方法的interception（拦截）。CGLIB包的底层是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。除了CGLIB包，脚本语言例如Groovy和BeanShell，也是使用ASM来生成java的字节码。当然不鼓励直接使用ASM，因为它要求你必须对JVM内部结构包括class文件的格式和指令集都很熟悉。

**操作代码：**

MyCalculator.java

```java
package com.zph.cglib;

public class MyCalculator {
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mult(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}

```

MyCglib.java

```java
package com.zph.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglib implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object o1 = methodProxy.invokeSuper(o, objects);
        return o1;
    }
}

```

MyTest.java

```java
package com.zph.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class MyTest {
    public static void main(String[] args) {
        //动态代理创建的class文件存储到本地
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"d:\\code");
        //通过cglib动态代理获取代理对象的过程，创建调用的对象
        Enhancer enhancer = new Enhancer();
        //设置enhancer对象的父类
        enhancer.setSuperclass(MyCalculator.class);
        //设置enhancer的回调对象
        enhancer.setCallback(new MyCglib());
        //创建代理对象
        MyCalculator myCalculator = (MyCalculator) enhancer.create();
        //通过代理对象调用目标方法
        myCalculator.add(1,1);
        System.out.println(myCalculator.getClass());
    }
}

```

实现原理：

​		利用cglib实现动态代理的时候，必须要实现MethodInterceptor接口，此接口源码如下：

MethodInterceptor.java

```java
package net.sf.cglib.proxy;

public interface MethodInterceptor
extends Callback
{
 	/*
 	此方法用来实现方法的拦截，四个参数分别表示的含义：
 	obj:表示增强的对象，即实现这个接口类的一个对象
 	method：表示要拦截的方法
 	args：表示被拦截的方法的参数
 	proxy:表示要触发父类的方法对象
 	*/
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                               MethodProxy proxy) throws Throwable;

}

```

​		在我们的测试类中，大家发现了，我们创建了一个cglib的Enhancer对象，并且设置了父类、回调函数等参数，最重要的入口函数是create()方法：

#### 1、Enhancer.create()

```java
    /**
     * Generate a new class if necessary and uses the specified
     * callbacks (if any) to create a new object instance.
     * Uses the no-arg constructor of the superclass.
     * @return a new instance
     * 如果必须要创建一个新类，那么就用指定的回调对象创建一个新的对象实例，使用的父类的参数的构造方法来实	 * 例化父类的部分
     */
    public Object create() {
        //不作代理类限制
        classOnly = false;
        //没有构造参数类型
        argumentTypes = null;
        //执行创建
        return createHelper();
    }

```

#### 2、Enhancer.createHelper()

```java
 private Object createHelper() {
     	//校验callbackTypes、filter是否为空，以及为空时的处理
        preValidate();
     	// 通过newInstance方法来创建EnhancerKey对象
        Object key = KEY_FACTORY.newInstance((superclass != null) ? superclass.getName() : null,
                ReflectUtils.getNames(interfaces),
                filter == ALL_ZERO ? null : new WeakCacheKey<CallbackFilter>(filter),
                callbackTypes,
                useFactory,
                interceptDuringConstruction,
                serialVersionUID);
     	// 设置当前enhancer的代理类的key标识
        this.currentKey = key;
     	// 调用父类即AbstractClassGenerator的创建代理类
        Object result = super.create(key);
        return result;
    }

```

preValidate()

```java
 private void preValidate() {
        if (callbackTypes == null) {
            //确定传入的callback类型
            callbackTypes = CallbackInfo.determineTypes(callbacks, false);
            validateCallbackTypes = true;
        }
        if (filter == null) {
            if (callbackTypes.length > 1) {
                throw new IllegalStateException("Multiple callback types possible but no filter specified");
            }
            filter = ALL_ZERO;
        }
    }

```

```java
private static final CallbackInfo[] CALLBACKS = {
        new CallbackInfo(NoOp.class, NoOpGenerator.INSTANCE),
        new CallbackInfo(MethodInterceptor.class, MethodInterceptorGenerator.INSTANCE),
        new CallbackInfo(InvocationHandler.class, InvocationHandlerGenerator.INSTANCE),
        new CallbackInfo(LazyLoader.class, LazyLoaderGenerator.INSTANCE),
        new CallbackInfo(Dispatcher.class, DispatcherGenerator.INSTANCE),
        new CallbackInfo(FixedValue.class, FixedValueGenerator.INSTANCE),
        new CallbackInfo(ProxyRefDispatcher.class, DispatcherGenerator.PROXY_REF_INSTANCE),
    };

```

​		该方法主要是做验证并确定CallBack类型，我们使用的是MethodInterceptor,然后创建当前代理类的标识代理类，用这个标识代理类调用（AbstractClassGenerator）的create(key)方法创建，下面我们开始分析标识代理类创建逻辑 以及后面父类创建我们需要的代理类的逻辑。

​		标识代理类的创建类成员变量即KEY_FACTORY是创建代理类的核心

#### 3、标识代理类

##### 1、KEY_FACTORY

​		追踪源码可以看到，KEY_FACTORY在Enhancer的初始化即会创建一个final的静态变量

```java
    private static final EnhancerKey KEY_FACTORY =
      (EnhancerKey)KeyFactory.create(EnhancerKey.class, KeyFactory.HASH_ASM_TYPE, null);

```

##### 2、Keyfactory_create方法

　	这儿可以看到使用key工厂创建出对应class的代理类，后面的KeyFactory_HASH_ASM_TYPE即代理类中创建HashCode方法的策略。我们接着点击源码查看

```java
public static KeyFactory create(ClassLoader loader, Class keyInterface, KeyFactoryCustomizer customizer,
                                    List<KeyFactoryCustomizer> next) {
        //创建一个最简易的代理类生成器 即只会生成HashCode equals toString newInstance方法
        Generator gen = new Generator();
        //设置接口为enhancerKey类型
        gen.setInterface(keyInterface);

        if (customizer != null) {
            //添加定制器
            gen.addCustomizer(customizer);
        }
        if (next != null && !next.isEmpty()) {
            for (KeyFactoryCustomizer keyFactoryCustomizer : next) {
                //添加定制器
                gen.addCustomizer(keyFactoryCustomizer);
            }
        }
        //设置生成器的类加载器
        gen.setClassLoader(loader);
        //生成enhancerKey的代理类
        return gen.create();
    }

```

##### 3、Generator的create方法

　　这儿创建了一个简易的代理类生成器(KeyFactory的内部类Generator ，与Enhancer一样继承自抽象类AbstractClassGenerator)来生成我们需要的标识代理类，我们接着看gen.create()方法

```java
public KeyFactory create() {　　　　　　　　
    //设置了该生成器生成代理类的名字前缀，即我们的接口名Enhancer.enhancerKey
            setNamePrefix(keyInterface.getName());
            return (KeyFactory)super.create(keyInterface.getName());
        }

```

##### 4、AbstractClassGenerator的create(Key)方法

```java
protected Object create(Object key) {
        try {
            //获取到当前生成器的类加载器
            ClassLoader loader = getClassLoader();
            //当前类加载器对应的缓存  缓存key为类加载器，缓存的value为ClassLoaderData  这个类后面会再讲
            Map<ClassLoader, ClassLoaderData> cache = CACHE;
            //先从缓存中获取下当前类加载器所有加载过的类
            ClassLoaderData data = cache.get(loader);
            //如果为空
            if (data == null) {
                synchronized (AbstractClassGenerator.class) {
                    cache = CACHE;
                    data = cache.get(loader);
                    //经典的防止并发修改 二次判断
                    if (data == null) {
                        //新建一个缓存Cache  并将之前的缓存Cache的数据添加进来 并将已经被gc回收的数据给清除掉
                        Map<ClassLoader, ClassLoaderData> newCache = new WeakHashMap<ClassLoader, ClassLoaderData>(cache);
                        //新建一个当前加载器对应的ClassLoaderData 并加到缓存中  但ClassLoaderData中此时还没有数据
                        data = new ClassLoaderData(loader);
                        newCache.put(loader, data);
                        //刷新全局缓存
                        CACHE = newCache;
                    }
                }
            }
            //设置一个全局key 
            this.key = key;
            
            //在刚创建的data(ClassLoaderData)中调用get方法 并将当前生成器，
            //以及是否使用缓存的标识穿进去 系统参数 System.getProperty("cglib.useCache", "true")  
            //返回的是生成好的代理类的class信息
            Object obj = data.get(this, getUseCache());
            //如果为class则实例化class并返回  就是我们需要的代理类
            if (obj instanceof Class) {
                return firstInstance((Class) obj);
            }
            //如果不是则说明是实体  则直接执行另一个方法返回实体
            return nextInstance(obj);
        } catch (RuntimeException e) {
            throw e;
        } catch (Error e) {
            throw e;
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }

```

​		这个方法可以看到主要为根据类加载器定义一个缓存，里面装载了缓存的类信息，然后调用这个ClassLoaderData的get方法获取到数据，如果为class信息 那么直接使用反射实例化，如果返回的是实体类，则解析实体类的信息，调用其newInstance方法重新生成一个实例(cglib的代理类都会生成newInstance方法)

##### 5、data.get(this,getUseCache)

```java
public Object get(AbstractClassGenerator gen, boolean useCache) {
            //如果不用缓存  (默认使用)
            if (!useCache) {
                //则直接调用生成器的命令
              return gen.generate(ClassLoaderData.this);
            } else {
              //从缓存中获取值
              Object cachedValue = generatedClasses.get(gen);
              //解包装并返回
              return gen.unwrapCachedValue(cachedValue);
            }
        }

```

​		如果可以用缓存则调用缓存，不能调用缓存则直接生成， 这儿我们先看调用缓存的，在之前的步骤中，我们设置了一个key为ClassLoader，值为ClassLoaderData的缓存，这儿我们new了一个ClassLoaderData 并将类加载器传了进去 ，并且设置了这个Generator的key，我们看下new的逻辑

```java
public ClassLoaderData(ClassLoader classLoader) {
            //判断类加载器不能为空 
            if (classLoader == null) {
                throw new IllegalArgumentException("classLoader == null is not yet supported");
            }
            //设置类加载器   弱引用 即在下次垃圾回收时就会进行回收
            this.classLoader = new WeakReference<ClassLoader>(classLoader);
            //新建一个回调函数  这个回调函数的作用在于缓存中没获取到值时  调用传入的生成的生成代理类并返回
            Function<AbstractClassGenerator, Object> load =
                    new Function<AbstractClassGenerator, Object>() {
                        public Object apply(AbstractClassGenerator gen) {
                            Class klass = gen.generate(ClassLoaderData.this);
                            return gen.wrapCachedClass(klass);
                        }
                    };
            //为这个ClassLoadData新建一个缓存类   这个loadingcache稍后会讲        
            generatedClasses = new LoadingCache<AbstractClassGenerator, Object, Object>(GET_KEY, load);
        }
        
        
 private static final Function<AbstractClassGenerator, Object> GET_KEY = new Function<AbstractClassGenerator, Object>() {
     public Object apply(AbstractClassGenerator gen) {
         return gen.key;
     }
};

```

​		可以看到每个类加载器都对应着一个代理类缓存对象 ，这里面定义了类加载器，缓存调用没查询到的调用函数，以及新建了一个LoadingCache来缓存这个类加载器对应的缓存，这儿传入的两个参数，load代表缓存查询失败时的回调函数，而GET_KEY则是回调时获取调用生成器的key 即4中传入的key 也即是我们的代理类标识符。 然后我们接着看generatedClasses.get(gen);的方法

##### 6、generatedClasses.get(gen);

​		这个方法主要传入代理类生成器 并根据代理类生成器获取值返回。这儿主要涉及到的类就是LoadingCache，这个类可以看做是某个CLassLoader对应的所有代理类缓存库，是真正缓存东西的地方。我们分析下这个类

```java
package net.sf.cglib.core.internal;

import java.util.concurrent.*;

public class LoadingCache<K, KK, V> {
    protected final ConcurrentMap<KK, Object> map;
    protected final Function<K, V> loader;
    protected final Function<K, KK> keyMapper;

    public static final Function IDENTITY = new Function() {
        public Object apply(Object key) {
            return key;
        }
    };
    //初始化类  kemapper代表获取某个代理类生成器的标识，loader即缓存查找失败后的回调函数
    public LoadingCache(Function<K, KK> keyMapper, Function<K, V> loader) {
        this.keyMapper = keyMapper;
        this.loader = loader;
        //这个map是缓存代理类的地方
        this.map = new ConcurrentHashMap<KK, Object>();
    }

    @SuppressWarnings("unchecked")
    public static <K> Function<K, K> identity() {
        return IDENTITY;
    }
    //这儿key是代理类生成器
    public V get(K key) {
        //获取到代理类生成器的标识
        final KK cacheKey = keyMapper.apply(key);
        //根据缓代理类生成器的标识获取代理类
        Object v = map.get(cacheKey);
        //如果结果不为空且不是FutureTask 即线程池中用于获取返回结果的接口
        if (v != null && !(v instanceof FutureTask)) {
            //直接返回
            return (V) v;
        }
        //否则就是没查询到  或者还未处理完
        return createEntry(key, cacheKey, v);
    }

    protected V createEntry(final K key, KK cacheKey, Object v) {
        //初始化任务task
        FutureTask<V> task;
        //初始化创建标识
        boolean creator = false;
        if (v != null) {
            // 则说明这是一个FutureTask 
            task = (FutureTask<V>) v;
        } else {
            //否则还没开始创建这个代理类  直接创建任务  
            task = new FutureTask<V>(new Callable<V>() {
                public V call() throws Exception {
                    //这儿会直接调用生成器的generate方法
                    return loader.apply(key);
                }
            });
            //将这个任务推入缓存Map  如果对应key已经有则返回已经有的task，
            Object prevTask = map.putIfAbsent(cacheKey, task);
            //如果为null则代表还没有创建  标识更新为true 且运行这个任务
            if (prevTask == null) {
                // creator does the load
                creator = true;
                task.run();
            } 
            //如果是task  说明另一个线程已经创建了task
            else if (prevTask instanceof FutureTask) {
                task = (FutureTask<V>) prevTask;
            } 
            //到这儿说明另一个线程已经执行完了  直接返回
            else {
                return (V) prevTask;
            }
            
            //上面的一堆判断主要是为了防止并发出现的问题
        }
        
        V result;
        try {
            //到这儿说明任务执行完并拿到对应的代理类了
            result = task.get();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted while loading cache item", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new IllegalStateException("Unable to load cache item", cause);
        }
        //如果这次执行是新建的
        if (creator) {
            //将之前的FutureTask缓存直接覆盖为实际的代理类信息
            map.put(cacheKey, result);
        }
        //返回结果
        return result;
    }
}

```

　　通过上面的分析可以得知，这个类主要作用是传入代理类生成器，根据这个代理类生成器以及代理类生成器的key来获取缓存，如果没有获取到则构建一个FutureTask来回调我们之前初始化时传入的 回调函数，并调用其中的apply方法，而具体调用的则是我们传入的代理类生成器的generate(LoadClassData)方法，将返回值覆盖之前的FutureTask成为真正的缓存。所以这个类的主要作用还是缓存。 这样则和5中不使用缓存时调用了一样的方法。所以我们接着来分析生成方法 generate(ClassLoadData),这儿因为我们使用的代理类生成器是Genrator，该类没有重写generate方法，所以回到了父类AbstractClassGenerator的generate方法

##### 7、AbstractClassGenerator.generate 方法

```java
protected Class generate(ClassLoaderData data) {
        Class gen;
        Object save = CURRENT.get();
        //当前的代理类生成器存入ThreadLocal中
        CURRENT.set(this);
        try {
            //获取到ClassLoader
            ClassLoader classLoader = data.getClassLoader();
            //判断不能为空
            if (classLoader == null) {
                throw new IllegalStateException("ClassLoader is null while trying to define class " +
                        getClassName() + ". It seems that the loader has been expired from a weak reference somehow. " +
                        "Please file an issue at cglib's issue tracker.");
            }
            synchronized (classLoader) {
             //生成代理类名字
              String name = generateClassName(data.getUniqueNamePredicate()); 
             //缓存中存入这个名字
              data.reserveName(name);
              //当前代理类生成器设置类名
              this.setClassName(name);
            }
            //尝试从缓存中获取类
            if (attemptLoad) {
                try {
                    //要是能获取到就直接返回了  即可能出现并发 其他线程已经加载
                    gen = classLoader.loadClass(getClassName());
                    return gen;
                } catch (ClassNotFoundException e) {
                    // 发现异常说明没加载到 不管了
                }
            }
            //生成字节码
            byte[] b = strategy.generate(this);
            //获取到字节码代表的class的名字
            String className = ClassNameReader.getClassName(new ClassReader(b));
            //核实是否为protect
            ProtectionDomain protectionDomain = getProtectionDomain();
            synchronized (classLoader) { // just in case
                //如果不是protect
                if (protectionDomain == null) {
                    //根据字节码 类加载器 以及类名字  将class加载到内存中
                    gen = ReflectUtils.defineClass(className, b, classLoader);
                } else {
                    //根据字节码 类加载器 以及类名字 以及找到的Protect级别的实体 将class加载到内存中
                    gen = ReflectUtils.defineClass(className, b, classLoader, protectionDomain);
                }
            }
　　　　　　　 //返回生成的class信息
            return gen;
        } catch (RuntimeException e) {
            throw e;
        } catch (Error e) {
            throw e;
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        } finally {
            CURRENT.set(save);
        }
    }

```

​		这个方法主要设置了下当前类生成器的类名，然后调用stratege的generate方法返回字节码，根据字节码 类名 类加载器将字节码所代表的类加载到内存中，这个功能看一下大概就懂，我们接下来主要分析字节码生成方法

##### 8、DefaultGeneratorStrategy.generate(ClassGenerator cg)

```java
public byte[] generate(ClassGenerator cg) throws Exception {
        //创建一个写入器
        DebuggingClassWriter cw = getClassVisitor();
        //加入自己的转换逻辑后执行代理类生成器的generateClass方法
        transform(cg).generateClass(cw);
        //将cw写入的东西转换为byte数组返回
        return transform(cw.toByteArray());
    }

```

​		这里面主要是新建一个写入器，然后执行我们代理类生成器的generateClass方法将class信息写入这个ClassWriter 最后将里面的东西转换为byte数组返回，所以又回到了我们的代理类生成器的generateClass方法，这儿进入的是Generator的generateClass方法

##### 9、Generator.generateClass（ClassVisitor v）

```java
//该方法为字节码写入方法 为最后一步
 public void generateClass(ClassVisitor v) {
            //创建类写入聚合对象
            ClassEmitter ce = new ClassEmitter(v);
            //找到被代理类的newInstance方法 如果没有会报异常  由此可知 如果想用Generator代理类生成器  必须要有newInstance方法
            Method newInstance = ReflectUtils.findNewInstance(keyInterface);
            //如果被代理类的newInstance不为Object则报异常  此处我们代理的Enchaer.EnhancerKey newInstance方法返回值为Object
            if (!newInstance.getReturnType().equals(Object.class)) {
                throw new IllegalArgumentException("newInstance method must return Object");
            }
            //找到newInstance方法的所有参数类型 并当做成员变量 
            Type[] parameterTypes = TypeUtils.getTypes(newInstance.getParameterTypes());
            
            //1.创建类开始写入类头   版本号  访问权限  类名等通用信息
            ce.begin_class(Constants.V1_8,
                           Constants.ACC_PUBLIC,
                           getClassName(), 
                           KEY_FACTORY,
                           new Type[]{ Type.getType(keyInterface) },
                           Constants.SOURCE_FILE);
            //2.写入无参构造方法               
            EmitUtils.null_constructor(ce);
            //3.写入newInstance方法
            EmitUtils.factory_method(ce, ReflectUtils.getSignature(newInstance));
            
            int seed = 0;
            //4.开始构造 有参构造方法
            CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC,
                                            TypeUtils.parseConstructor(parameterTypes),
                                            null);
            e.load_this();
            //4.1有参构造中调用父类构造方法  即super.构造方法() 
            e.super_invoke_constructor();
            e.load_this();
            //4.2找到传入的定制器 例如一开始传入的hashCode方法定制器
            List<FieldTypeCustomizer> fieldTypeCustomizers = getCustomizers(FieldTypeCustomizer.class);
            //4.3遍历成员变量即newInstance方法的所有参数
            for (int i = 0; i < parameterTypes.length; i++) {
                Type parameterType = parameterTypes[i];
                Type fieldType = parameterType;
                for (FieldTypeCustomizer customizer : fieldTypeCustomizers) {
                    fieldType = customizer.getOutType(i, fieldType);
                }
                seed += fieldType.hashCode();
                //4.3将这些参数全部声明到写入类中
                ce.declare_field(Constants.ACC_PRIVATE | Constants.ACC_FINAL,
                                 getFieldName(i),
                                 fieldType,
                                 null);
                e.dup();
                e.load_arg(i);
                for (FieldTypeCustomizer customizer : fieldTypeCustomizers) {
                    customizer.customize(e, i, parameterType);
                }
                //4.4设置每个成员变量的值  即我们常见的有参构造中的this.xx = xx
                e.putfield(getFieldName(i));
            }
            //设置返回值
            e.return_value();
            //有参构造及成员变量写入完成
            e.end_method();
            
            /*************************到此已经在class中写入了成员变量  写入实现了newInstance方法  写入无参构造  写入了有参构造 *************************/
            
            // 5.写入hashcode方法
            e = ce.begin_method(Constants.ACC_PUBLIC, HASH_CODE, null);
            int hc = (constant != 0) ? constant : PRIMES[(int)(Math.abs(seed) % PRIMES.length)];
            int hm = (multiplier != 0) ? multiplier : PRIMES[(int)(Math.abs(seed * 13) % PRIMES.length)];
            e.push(hc);
            for (int i = 0; i < parameterTypes.length; i++) {
                e.load_this();
                e.getfield(getFieldName(i));
                EmitUtils.hash_code(e, parameterTypes[i], hm, customizers);
            }
            e.return_value();
            //hashcode方法结束
            e.end_method();

            // 6.写入equals方法
            e = ce.begin_method(Constants.ACC_PUBLIC, EQUALS, null);
            Label fail = e.make_label();
            e.load_arg(0);
            e.instance_of_this();
            e.if_jump(e.EQ, fail);
            for (int i = 0; i < parameterTypes.length; i++) {
                e.load_this();
                e.getfield(getFieldName(i));
                e.load_arg(0);
                e.checkcast_this();
                e.getfield(getFieldName(i));
                EmitUtils.not_equals(e, parameterTypes[i], fail, customizers);
            }
            e.push(1);
            e.return_value();
            e.mark(fail);
            e.push(0);
            e.return_value();
            //equals方法结束
            e.end_method();

            // 7.写入toString方法
            e = ce.begin_method(Constants.ACC_PUBLIC, TO_STRING, null);
            e.new_instance(Constants.TYPE_STRING_BUFFER);
            e.dup();
            e.invoke_constructor(Constants.TYPE_STRING_BUFFER);
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    e.push(", ");
                    e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
                }
                e.load_this();
                e.getfield(getFieldName(i));
                EmitUtils.append_string(e, parameterTypes[i], EmitUtils.DEFAULT_DELIMITERS, customizers);
            }
            e.invoke_virtual(Constants.TYPE_STRING_BUFFER, TO_STRING);
            e.return_value();
            //toString方法结束
            e.end_method();
            //类写入结束  至此类信息收集完成 并全部写入ClassVisitor
            ce.end_class();
        }

```

​		这个方法主要将一个完整的类信息写入ClassVisitor中，例如目前实现的Enhancer.EnhancerKey代理，即实现了newInstance方法， 重写了HashCode,toSting,equals方法，并将newInstance的所有参数作为了成员变量，这儿我们也可以看下具体实现newInstance方法的逻辑 即这个代码 EmitUtils.factory_method(ce, ReflectUtils.getSignature(newInstance)); 如果有兴趣可以去研究asm字节码写入的操作

```java
public static void factory_method(ClassEmitter ce, Signature sig) 
    {
        //开始写入方法
        CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, sig, null);
        //写入 一个创建对象命令  即new命令
        e.new_instance_this();
        e.dup();
        //加载参数命令
        e.load_args();
        //执行该类的有参构造命令
        e.invoke_constructor_this(TypeUtils.parseConstructor(sig.getArgumentTypes()));
        //将上面指令执行的值返回
        e.return_value();
        //结束写入方法
        e.end_method();
    }

```

​		可以得知 我们通过Generator创建的代理类大致内容应该如下，Enhancer.EhancerKey代理类字节码的class内容应该是把参数换为newInstance中的参数

```java
public class EnhancerKeyProxy extends xxx implements xxx{

 private paramA;
 private paramB;
 private paramC;
 
 public EnhancerKeyProxy() {
         super.xxx();
 }
 public EnhancerKeyProxy(paramA, paramB,paramC) {
       super.xxx();
       this.paramA = paramA
       this.paramB = paramB
       this.paramC = paramC
    }

 public Object newInstance(paramA,paramB,paramC){
        EnhancerKeyProxy param = new EnhancerKeyProxy(o);
        return param;
 }
 
  public int hashCode(){
      ...
  }
   public String toString(){
      ...
  }
  
  public boolean equals(Object o){
      ...
  }

}

```

​		最后执行传入的ClassVisitor 即我们传入的实例DebuggingClassWriter的toByteArray即可以将写入的内容转换为byte[]返回

​		至此 我们的成功的生成了Enhancer.EnhancerKey的代理类，也就是我们需要的代理类标识类 用来标识被代理的类，这个代理类主要用来作为被代理类的标识，在进行缓存时作为判断相等的依据。可以看到 cglib代理主要也是利用我们传入的被代理类信息来生成对应的代理类字节码，然后用类加载器加载到内存中。虽然我们的实际的代理任务才刚刚开始，但是要了解的东西已经基本上差不多了，对具体的我们案例中的ProxyFactory代理时，只是生成器Enhancer对比生成器Generator在生成过程中重写了一些操作而已。

#### 4、回到步骤2，接着向下看执行的过程

```java
private Object createHelper() {
        preValidate();
        //获取到了代理类标识类
        Object key = KEY_FACTORY.newInstance((superclass != null) ? superclass.getName() : null,
                ReflectUtils.getNames(interfaces),
                filter == ALL_ZERO ? null : new WeakCacheKey<CallbackFilter>(filter),
                callbackTypes,
                useFactory,
                interceptDuringConstruction,
                serialVersionUID);
        //设置当前enhancer正在代理生成的类信息
        this.currentKey = key;
        //调用父类的create(key方法)
        Object result = super.create(key);
        return result;
    }

```

​		可以看到获取到代理类标志类后 将其设置为当前代理类生成器的正在代理的类 并同样调用父类AbstractClassGenerator中create(key)的方法,下面开始分析Ehancer生成器的逻辑，由于部分逻辑和Generator生成器一致

#### 5、AbstractClassGenerator.create方法

​		这个逻辑和上述步骤一致，查询当前key即代理类标志类对应的ClassLoadData缓存，如果没有则建一个空的缓存并初始化一个对应的ClassLoadData,传入相应的生成器，生成失败回调函数等

　　按照同样的逻辑一直走到generate(ClassLoadData)方法时，由于Enhancer生成器重写了这个方法 所以我们分析Enahncer的生成逻辑

#### 6、Enhancer.generate(ClassLoadData data)

```java
@Override
    protected Class generate(ClassLoaderData data) {
        validate();
        if (superclass != null) {
            setNamePrefix(superclass.getName());
        } else if (interfaces != null) {
            setNamePrefix(interfaces[ReflectUtils.findPackageProtected(interfaces)].getName());
        }
        return super.generate(data);
    }

```

​		可以发现ehancer生成器只是做了个检查命名操作 在上面的Generator中也是做了个命名操作，然后继续执行父类的generate(data)方法，这个和上述步骤一致，我们主要看其中生成字节码的方法，即最后调用的Enhancer.generatorClass(ClassVisitor c)方法，

#### 7、Enhancer.generatorClass(ClassVisitor c)

```java
public void generateClass(ClassVisitor v) throws Exception {
        //声明需代理的类 或者接口
        Class sc = (superclass == null) ? Object.class : superclass;
        //检查 final类无法被继承
        if (TypeUtils.isFinal(sc.getModifiers()))
            throw new IllegalArgumentException("Cannot subclass final class " + sc.getName());
        //找到该类所有声明了的构造函数
        List constructors = new ArrayList(Arrays.asList(sc.getDeclaredConstructors()));
        //去掉private之类的不能被继承的构造函数
        filterConstructors(sc, constructors);

        // Order is very important: must add superclass, then
        // its superclass chain, then each interface and
        // its superinterfaces.
        //这儿顺序非常重要  上面是源码的注释  直接留着  相信大家都能看懂 
        
        //声明代理类方法集合
        List actualMethods = new ArrayList();
        //声明代理接口接口方法集合
        List interfaceMethods = new ArrayList();
        //声明所有必须为public的方法集合  这儿主要是代理接口接口的方法
        final Set forcePublic = new HashSet();
        //即通过传入的代理类 代理接口，遍历所有的方法并放入对应的集合
        getMethods(sc, interfaces, actualMethods, interfaceMethods, forcePublic);
        
        //对所有代理类方法修饰符做处理 
        List methods = CollectionUtils.transform(actualMethods, new Transformer() {
            public Object transform(Object value) {
                Method method = (Method)value;
                int modifiers = Constants.ACC_FINAL
                    | (method.getModifiers()
                       & ~Constants.ACC_ABSTRACT
                       & ~Constants.ACC_NATIVE
                       & ~Constants.ACC_SYNCHRONIZED);
                if (forcePublic.contains(MethodWrapper.create(method))) {
                    modifiers = (modifiers & ~Constants.ACC_PROTECTED) | Constants.ACC_PUBLIC;
                }
                return ReflectUtils.getMethodInfo(method, modifiers);
            }
        });
        //创建类写入器
        ClassEmitter e = new ClassEmitter(v);
        
        //1.开始创建类  并写入基本信息  如java版本，类修饰符 类名等
        if (currentData == null) {
        e.begin_class(Constants.V1_8,
                      Constants.ACC_PUBLIC,
                      getClassName(),
                      Type.getType(sc),
                      (useFactory ?
                       TypeUtils.add(TypeUtils.getTypes(interfaces), FACTORY) :
                       TypeUtils.getTypes(interfaces)),
                      Constants.SOURCE_FILE);
        } else {
            e.begin_class(Constants.V1_8,
                    Constants.ACC_PUBLIC,
                    getClassName(),
                    null,
                    new Type[]{FACTORY},
                    Constants.SOURCE_FILE);
        }
        List constructorInfo = CollectionUtils.transform(constructors, MethodInfoTransformer.getInstance());
        //2. 声明一个private boolean 类型的属性：CGLIB$BOUND
        e.declare_field(Constants.ACC_PRIVATE, BOUND_FIELD, Type.BOOLEAN_TYPE, null);
        //3. 声明一个public static Object 类型的属性：CGLIB$FACTORY_DATA
        e.declare_field(Constants.ACC_PUBLIC | Constants.ACC_STATIC, FACTORY_DATA_FIELD, OBJECT_TYPE, null);
        // 这个默认为true  如果为false则会声明一个private boolean 类型的属性：CGLIB$CONSTRUCTED
        if (!interceptDuringConstruction) {
            e.declare_field(Constants.ACC_PRIVATE, CONSTRUCTED_FIELD, Type.BOOLEAN_TYPE, null);
        }
        //4. 声明一个public static final 的ThreadLocal：ThreadLocal
        e.declare_field(Constants.PRIVATE_FINAL_STATIC, THREAD_CALLBACKS_FIELD, THREAD_LOCAL, null);
        //5. 声明一个public static final 的CallBack类型的数组：CGLIB$STATIC_CALLBACKS
        e.declare_field(Constants.PRIVATE_FINAL_STATIC, STATIC_CALLBACKS_FIELD, CALLBACK_ARRAY, null);
        //如果serialVersionUID不为null  则设置一个public static final 的Long类型 serialVersionUID
        if (serialVersionUID != null) {
            e.declare_field(Constants.PRIVATE_FINAL_STATIC, Constants.SUID_FIELD_NAME, Type.LONG_TYPE, serialVersionUID);
        }
        
        //遍历CallBackTypes 即我们构建Enhancer是setCallBack的所有类的类型  本案例中是methodInterceptor 并且只传入了一个
        for (int i = 0; i < callbackTypes.length; i++) {
            //6.声明一个private 的传入的CallBack类型的属性：CGLIB$CALLBACK_0 (从0开始编号，)
            e.declare_field(Constants.ACC_PRIVATE, getCallbackField(i), callbackTypes[i], null);
        }
        //7声明一个private static 的传入的Object类型的属性：CGLIB$CALLBACK_FILTER
        e.declare_field(Constants.ACC_PRIVATE | Constants.ACC_STATIC, CALLBACK_FILTER_FIELD, OBJECT_TYPE, null);
        
        //判断currentData
        if (currentData == null) {
            //8.为null则开始声明所有的代理类方法的变量 以及其具体的重写实现方法，还有static初始化执行代码块
            emitMethods(e, methods, actualMethods);
            //9.声明构造函数
            emitConstructors(e, constructorInfo);
        } else {
            //声明默认构造函数
            emitDefaultConstructor(e);
        }
        //
        emitSetThreadCallbacks(e);
        emitSetStaticCallbacks(e);
        emitBindCallbacks(e);
        //如果currentData不为null
        if (useFactory || currentData != null) {
            //获取到所有CallBack索引数组
            int[] keys = getCallbackKeys();
            //10.声明三个newInstance方法
            //只有一个callback参数
            emitNewInstanceCallbacks(e);
            //参数为callback数组
            emitNewInstanceCallback(e);
            //参数为callback数组 以及附带的一些参数
            emitNewInstanceMultiarg(e, constructorInfo);
            //11.声明getCallBack方法
            emitGetCallback(e, keys);
            //12.声明setCallBack方法
            emitSetCallback(e, keys);
            //12.声明setCallBacks方法
            emitGetCallbacks(e);
            //12.声明setCallBacks方法
            emitSetCallbacks(e);
        }
        //类声明结束
        e.end_class();

```

​		可以看到这儿也是声明一个写入类 然后按照Ehancer的代理生成策略写入符合的class信息然后返回，最红依旧会执行toByteArray方法返回byte[]数组，这样则又回到了步骤中 根据类加载器 字节码数组来动态将代理类加载进内存中的方法了。最后我们回到根据class获取实例的代码即可返回被代理实例。 而我们执行方法时执行的是代理类中对应的方法，然后调用我们传入的callback执行 原理和jdk动态代理类似，至此 cglib动态代理源码分析到此结束。