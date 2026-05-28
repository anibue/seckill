# 秒杀系统 (Seckill System)

基于 Spring Boot 3.2.5 + Java 21 的高并发限时抢购秒杀系统。

## 系统介绍

本系统是使用 Spring Boot 3 开发的高并发限时抢购秒杀系统，实现了登录、查看商品列表、秒杀、下单等核心功能，并针对高并发场景实现了缓存、限流、异步下单等优化策略。

## 开发环境

- **JDK**: 21 (Oracle)
- **Maven**: 3.9.11
- **IDE**: IntelliJ IDEA
- **压测工具**: JMeter

## 技术栈

| 类别 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.2.5, Spring Security |
| 持久层 | MyBatis 3.0.3, MySQL Connector/J, Flyway |
| 缓存 | Spring Data Redis |
| 消息队列 | RabbitMQ (Spring AMQP) |
| API 文档 | SpringDoc OpenAPI (Swagger) |
| 容器化 | Docker, Docker Compose |
| CI/CD | GitHub Actions |
| 监控 | Spring Boot Actuator |
| 工具库 | Lombok, Guava 33.0.0, Commons Codec |

## 项目结构

```
src/main/java/com/jesper/seckill/
├── MainApplication.java          # 启动类
├── annotation/                   # 自定义注解
│   └── IsMobile.java
├── config/                       # 配置类
│   ├── RedisConfig.java
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   ├── RabbitMQConfig.java
│   ├── WebConfig.java
│   ├── RedisHealthIndicator.java
│   └── SeckillStatsEndpoint.java
├── controller/                   # 控制器
│   ├── LoginController.java
│   ├── GoodsController.java
│   ├── SeckillController.java
│   └── OrderController.java
├── dto/                          # 数据传输对象
│   ├── LoginDto.java
│   └── SeckillDto.java
├── entity/                       # 实体类 (Lombok @Data)
│   ├── User.java
│   ├── Goods.java
│   ├── SeckillGoods.java
│   ├── OrderInfo.java
│   └── SeckillOrder.java
├── exception/                    # 异常处理
│   ├── GlobalException.java
│   └── GlobalExceptionHandler.java
├── redis/                        # Redis 相关
│   ├── RedisService.java
│   ├── BasePrefix.java
│   ├── UserKey.java
│   ├── GoodsKey.java
│   ├── OrderKey.java
│   └── SeckillKey.java
├── result/                       # 统一返回
│   ├── Result.java
│   ├── CodeMsg.java
│   └── PageResult.java
├── service/                      # 业务层 (接口 + 实现)
│   ├── UserService.java
│   ├── GoodsService.java
│   ├── OrderService.java
│   ├── SeckillService.java
│   └── impl/
│       ├── UserServiceImpl.java
│       ├── GoodsServiceImpl.java
│       ├── OrderServiceImpl.java
│       └── SeckillServiceImpl.java
├── utils/                        # 工具类
│   ├── MD5Util.java
│   ├── UUIDUtil.java
│   ├── ValidatorUtil.java
│   └── UserContext.java
└── vo/                           # 视图对象
    ├── GoodsVo.java
    └── OrderDetailVo.java
```

## 核心功能

### 1. 两次 MD5 加密
- 第一次：用户密码 + 固定 Salt → 防止明文传输
- 第二次：加密后密码 + 随机 Salt → 防止数据库泄露后反推密码

### 2. Token-Based 会话管理
- 使用 UUID 生成 Token 存储到 Redis，实现分布式 Session 共享

### 3. 三级缓存保护
1. **本地标记**：标记已秒杀用户，减少 Redis 访问
2. **Redis 预处理**：预减库存，异步下单
3. **RabbitMQ 异步下单**：削峰填谷，保护数据库

### 4. 超卖问题解决
- 库存判断 + 用户商品唯一索引 + 乐观锁 (version 字段)

### 5. 接口限流
- 基于 Guava RateLimiter 的令牌桶算法限流

### 6. 数学公式验证码
- 防止恶意机器人，分散用户请求

## 快速开始

### 1. 环境准备

- JDK 21+
- Maven 3.9+
- MySQL 8.0+ (或使用 H2 内存数据库进行开发)

### 2. 使用 H2 内存数据库 (无需 MySQL)

```bash
# 编译打包
mvn clean package -DskipTests

# 直接运行
java -jar target/jesper_seckill.jar

# 访问 http://localhost:8080/login/to_login
# 登录：手机号码 18181818181，密码 123456
```

### 3. 使用 MySQL

```sql
-- 创建数据库
CREATE DATABASE seckill DEFAULT CHARACTER SET utf8mb4;

-- 执行初始化脚本
source src/main/resources/schema.sql;
source src/main/resources/data.sql;
```

修改 `application.yml` 中的数据源配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 4. Docker 部署

```bash
# 使用 Docker Compose 一键启动 (包含 MySQL + Redis + RabbitMQ)
docker-compose up -d

# 访问 http://localhost:8080
```

## API 文档

启动应用后访问 Swagger UI：http://localhost:8080/swagger-ui.html

### 主要接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/login/do_login` | POST | 用户登录 |
| `/login/redis/token` | GET | 获取 Redis Token |
| `/goods/to_list` | GET | 商品列表 |
| `/goods/detail/{goodsId}` | GET | 商品详情 |
| `/seckill/do_seckill` | POST | 执行秒杀 |
| `/seckill/verify` | GET | 验证码校验 |
| `/order/detail/{orderId}` | GET | 订单详情 |

## 监控端点

启动应用后访问 Actuator：http://localhost:8080/actuator

| 端点 | 说明 |
|------|------|
| `/actuator/health` | 健康检查 |
| `/actuator/info` | 应用信息 |
| `/actuator/metrics` | 指标信息 |
| `/actuator/seckillstats` | 秒杀统计 |

## CI/CD

项目配置了 GitHub Actions 自动化流水线：

- **push/PR 触发**：代码检查 → 测试 → 构建 → Docker 镜像推送
- **main 分支推送**：自动部署到生产环境

## 压测效果

优化前：1000 线程 × 10 次循环，QPS = 423

优化后：QPS = 2501

## 默认账号

- **手机号码**: 18181818181
- **密码**: 123456

## 许可证

MIT License
