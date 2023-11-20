# warden-cloud
warden springcloud微服务脚手架

# 系统简介
warden-cloud是一套基于spring-cloud的java开源脚手架，适合初创企业、起步项目。
* 结合多年项目架构经验沉淀；
* 高性能安全可靠；
* 集成后台管理常规模块；
* 自主封装基于Redis的统一token凭证；
* 集成常规业务链demo教程模块；
* 持续增加落地主流业务模块；

### 使用技术栈
* Spring Cloud
* Spring Gateway
* Spring Security
* Alibaba Spring Cloud
* Nacos
* Redis
* Mysql
* Sentinel
* EasyExcel

### 代码工程结构
```
warden-cloud
├── warden-common
│       ├── warden-common-core        // 核心模块
│       ├── warden-common-config      // 前台项目配置模块
│       ├── warden-common-database    // 数据库配置模块
│       ├── warden-common-redis       // redis配置模块
│       ├── warden-common-security    // 安全模块
│       └── warden-common-forestage   // 前台共用模块
├── warden-facade
│       ├── warden-order-api          // 订单远程api
│       ├── warden-system-api         // 后台系统远程api
│       └── warden-user-api           // 前台用户api
├── warden-module
│       ├── warden-order-service      // 订单服务
│       ├── warden-system-service     // 后台系统服务
│       └── warden-user-service       // 前台用户服务
└── warden-gateway                    // 公共网关
```

### 后台系统内置功能
1. 系统用户管理
2. 系统权限管理
3. 系统角色管理
4. 部门管理
5. 岗位管理
6. 字典数据管理
7. 操作日志管理
8. 登录日志管理
9. 公告管理
10. 消息管理
11. SSE推送服务
11. 订单管理(demo)
12. 商品管理(demo)
13. 前台用户管理(demo)

### 演示地址
* 后台服务：[https://democloudsystem.microwarp.com](https://democloud.microwarp.com 'https://democloudsystem.microwarp.com')
* 前台服务：[https://democloudmobile.microwarp.com](https://democloud.microwarp.com 'https://democloudmobile.microwarp.com')

