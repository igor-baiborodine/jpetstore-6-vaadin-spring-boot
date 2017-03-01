jpetstore-6-vaadin-spring-boot
------------------------------
[![Build
Status](https://travis-ci.org/igor-baiborodine/jpetstore-6-vaadin-spring-boot.svg?branch=master)](https://travis-ci.org/igor-baiborodine/jpetstore-6-vaadin-spring-boot)

> UPDATES
* 2017-03-01  Upgrade to Vaadin 7.7.7

#####This project is an exercise to port the original [JPetStore-6](https://github.com/mybatis/jpetstore-6) to Vaadin and Spring Boot with JavaConfig.

######Deployment on the OpenShift PaaS: http://jpetstore-kiroule.rhcloud.com/
The deployed application contains two Easter eggs. Whoever finds them first will get free beer (FOB Montreal)!

NOTE: Since an in-memory database is used, all accounts and orders data will be lost whenever the application is re-started or re-deployed.

######Run with Maven:
```bash
git clone https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot.git
cd jpetstore-6-vaadin-spring-boot
mvn clean package spring-boot:run
# Access in your browser at http://localhost:8080
```

######Software and technologies used:
* [Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)
* [Vaadin 7](https://vaadin.com/home) with add-ons: [Viritin](https://vaadin.com/directory#!addon/viritin), [LoginForm](https://vaadin.com/directory#!addon/loginform), [FormCheckBox](https://vaadin.com/directory#!addon/formcheckbox), [Stepper](https://vaadin.com/directory#!addon/stepper)
* [Spring 4](http://projects.spring.io/spring-framework/#quick-start)
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [MyBatis 3](http://mybatis.org/mybatis-3/)
* [HSQLDB](http://hsqldb.org/)
* [Maven 3](http://maven.apache.org/)
* [JUnit 4](http://junit.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

######Resources:######
* [Vaadin Tips](https://github.com/vaadin-marcus/vaadin-tips)
* [Vaadin Spring Tutorial](https://vaadin.com/wiki/-/wiki/Spring+Vaadin/I+-+Getting+Started+with+Vaadin+Spring+and+Spring+Boot)
* [Vaadin Spring Data CRUD Example](https://github.com/mstahv/spring-data-vaadin-crud)
* [Building "Bootiful" Vaadin Applications with Spring Boot](https://github.com/joshlong/vaadin-and-spring-talk)
