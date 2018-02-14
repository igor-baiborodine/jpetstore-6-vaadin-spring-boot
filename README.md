jpetstore-6-vaadin-spring-boot
------------------------------
[![Build
Status](https://travis-ci.org/igor-baiborodine/jpetstore-6-vaadin-spring-boot.svg?branch=master)](https://travis-ci.org/igor-baiborodine/jpetstore-6-vaadin-spring-boot)

#### This project is an exercise to port the original [JPetStore-6](https://github.com/mybatis/jpetstore-6) to Vaadin and Spring Boot with JavaConfig.

> UPDATES
* IN PROGRESS Upgrade to Vaadin v8.1.5 (**without** v7 compatibility mode) 
* 2018-02-04 Refactor to vaadin-spring v2.1.0 | [217109d](https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot/commit/217109d9a6e98c2f8c7913565d85acd97f0d0826), [4e4750a](https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot/commit/4e4750ad445fcde0e99cbad2f4346c03c5913622), [557d70d](https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot/commit/557d70d8d9fd77ec78043bb9b77439f6d1bfc5f6)
* 2018-01-23 Upgrade to Vaadin v8.1.5 (v7 compatibility mode) | [b8dcad0](https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot/commit/b8dcad0fa6ca1a8e921bbe0f59d4851d5c6d2ffa)
* 2017-03-01 Update to Vaadin v7.7.7 | [3bd973a](https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot/commit/3bd973a979c983ee97ca882da7168eb7c4633f78)

#### Branches
* **master** - Vaadin v8.1.5 (active)
* **vaadin-7** - Vaadin v7.7.7 (legacy)

#### Run with Maven:
```bash
git clone https://github.com/igor-baiborodine/jpetstore-6-vaadin-spring-boot.git
cd jpetstore-6-vaadin-spring-boot
mvn clean package spring-boot:run
# Access in your browser at http://localhost:8080
```

#### Software and technologies used:
* [Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)
* [Vaadin 8](https://vaadin.com/home) with add-ons: [Viritin](https://vaadin.com/directory#!addon/viritin), [LoginForm](https://vaadin.com/directory#!addon/loginform), [FormCheckBox](https://vaadin.com/directory#!addon/formcheckbox), [Stepper](https://vaadin.com/directory#!addon/stepper)
* [Spring 4](http://projects.spring.io/spring-framework/#quick-start)
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [MyBatis 3](http://mybatis.org/mybatis-3/)
* [HSQLDB](http://hsqldb.org/)
* [Maven 3](http://maven.apache.org/)
* [JUnit 4](http://junit.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

#### Resources:
* [Vaadin Tips](https://github.com/vaadin-marcus/vaadin-tips)
* [Vaadin Spring Tutorial](https://vaadin.com/wiki/-/wiki/Spring+Vaadin/I+-+Getting+Started+with+Vaadin+Spring+and+Spring+Boot)
* [Vaadin Spring Data CRUD Example](https://github.com/mstahv/spring-data-vaadin-crud)
* [Building "Bootiful" Vaadin Applications with Spring Boot](https://github.com/joshlong/vaadin-and-spring-talk)
