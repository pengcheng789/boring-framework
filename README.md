# Boring Framework

## Introduction

A web framework written only for graduate thesis.

### Development Environment

* OpenJDK 9.0.1
* Ubuntu 17.10

### Runtime Environment

* jre 9

## Usage

There are two small demo with Boring Framework.
* [java](https://github.com/pengcheng789/example-boring)
* [kotlin](https://github.com/pengcheng789/example-boring4k)

## Document

See the [Wiki](https://github.com/pengcheng789/boring-framework/wiki).

## License

* Apache License v2

## Update

### 20180316

* 添加了对模型字段中的外键字段解析的支持。

### 20180311

* 框架已经会加载`top.pengcheng789.bean.Model`的子类了。
* 删除了模型字段中的`primary key`属性。

### 20180123

* Create the supporting of mongodb.
* Create the database container.

### 20180113

* Restructure the package.
* Add the feature of model container.

### 20180109

* Optimize request parameter.
* Add the feature of dependency injection.

### 20171229

* Develop with jdk 9 now.
* Replace `newInstance()` with `getConstructor().newInstance()`.

### 20171228-2

* fix a serious bug.

### 20171228

* Release the first version library v1.0.0 .
* Provides a full function framework of web Model-View-Controller.
* It's simple to configure framework.
* Use thymeleaf as only template engine instead of jsp.
* Provides two kinds of controller that you can also use as rest framework.
* Don't have the function of data persistence yet.

### 20171226

* Realized a action container, bean container and class container.

### 20171225

* Create a management util of class.
