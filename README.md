配置中心
=========

配置中心能统一管理多个应用的资源配置信息，如memcached、DB、redis等的配置，这些配置多是K-V结构。

配置中心使得配置信息不散落在各个项目，简化了配置文件的管理和维护；支持多环境、多版本的配置管理，隔离了测试和生产环境配置；支持在不改变应用源码的情况下无缝的切换配置。

配置中心分为server和client两个部分。server负责配置的添加、修改、变更通知等，所有的配置信息均记录到Mysql数据库中；client负责与server通信，获取配置、替换本地配置等。

本项目为配置中心的server部分，client部分请参看[configcenter-client项目](https://github.com/Baidu-ecom/configcenter-client)。 

## 环境要求 ##

Ant，JDK 6或以上版本，Tomcat 6或以上版本，Mysql，Memcache（可选）

## 编译发布说明 ##

### 编译前操作 ###

1. 配置中心的编译依赖Ant和JDK 1.6+，请确保已经安装Ant和JDK 1.6+，并且添加到path。

2. 配置中心server服务依赖Mysql和Memcache（或本地cache）。

   1) 确保已经安装mysql服务，并执行源码src/main/resources/database/1.0.0目录下的init.sql脚本，初始化配置中心数据表。
   然后替换src/main/resources/conf/local目录下的jdbc-mysql.properties中的mysql服务地址、用户名和密码等。
   
   2) 如果已经安装memcache，替换src/main/resources/conf/local目录下的memcache.properties中的memcache服务地址；
   如果不想使用memcache，本项目也提供了本地缓存，只需要将src/main/resources/conf目录下的applicationContext.xml中的<import resource="applicationContext-cache-memcache.xml"/>替换为<import resource="applicationContext-cache-local.xml"/>即可。

### 编译 ###

Windows下：
在命令行运行build_ant.bat脚本。

Linux下：
在命令行运行build_ant.sh脚本。

脚本运行成功后会在源码目录的dist/war目录下生成名为ROOT的war包。

### 发布 ###
确保mysql和cache服务配置正确并将ROOT.war发布到Tomcat的webapps目录下，启动Tomcat服务即可。

## 配置中心server使用说明 ##

### 名词解释 ###

1. 工程：即项目，建议名称与应用项目一致。

2. 环境：工程下面通常会有多个环境的配置，如测试环境、开发环境等。

3. 版本：每个环境下支持多个版本的配置，如1.0.0.0等。版本名称全局唯一。

4. 分组：用来将一组K-V聚集在一起，方便管理，如jdbc相关配置为一个分组，memcache配置为另一个分组。可以与工程中的.properties文件名相同，方便查找。

5. 配置项：key-value形式，对应配置文件中的一个配置项，如jdbc.username=root等。 

### 使用说明 ###

服务启动之后，可通过http://{ip}:{port}访问访问配置中心。比如在本地启动了Tomcat服务，则可通过http://localhost:8080/，进入到配置中心的登录页面。

#### 添加用户 ####

数据库初始化时默认添加了admin用户（登录密码为123）。admin用户登录后，可点击“添加用户”添加普通用户，添加用户时需要提供用户名、密码和API密码。其中API密码是调用server端提供的API时需要提供的密码。

#### 添加配置 ####

用户登录后，可点击“添加工程”，新建一个需要集中管理配置的工程。

添加工程后，可在工程下“添加环境”。一个工程可以有多个环境，如qa、rd、performance等。

然后在环境下“添加版本”，使得配置修改可追溯。由于版本名称是全局唯一的，建议以工程名_环境名_版本号的方式命名。

版本添加之后，点击版本对应的“管理分组配置”就进入“添加分组”页面。分组用来将一组配置聚集在一起，通常对应.properties文件，因此建议分组名称与.properties文件一样。

分组添加之后，点击分组对应的“管理配置项”进入具体配置项的添加。配置项添加支持两种方式。一，逐条维护：每次添加一行配置（一个K-V对）；二，批量维护：可以输入或粘贴多行配置。

用户添加的配置会以树形结构的形式列在左侧的工程列表中，用户可点击不同的内容进行增删改。

配置修改之后，可以在对应版本下点击“推送变更”，将变更推送给客户端，客户端拿到变更后进行处理（具体可参看configcenter-client项目），使变更生效。

## 联系我们 ##

email: [rigel-opensource@baidu.com](mailto://email:rigel-opensource@baidu.com "发邮件给配置中心开发组")


