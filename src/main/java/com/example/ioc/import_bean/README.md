# @Import 注解演示

## 概述
这个 demo 演示了 Spring 中 `@Import` 注解的使用方法和功能。

## 文件说明

### 1. UserService.java
- 普通的 Java 类，没有使用 `@Component` 等注解
- 将被 `@Import` 注解导入到 Spring 容器中

### 2. OrderService.java  
- 普通的 Java 类，没有使用 `@Component` 等注解
- 将被 `@Import` 注解导入到 Spring 容器中

### 3. ImportConfig.java
- 配置类，使用 `@Configuration` 注解
- 使用 `@Import({UserService.class, OrderService.class})` 导入两个类
- 被导入的类会自动成为 Spring 管理的 Bean

### 4. ImportDemo.java
- 主类，演示 `@Import` 注解的效果
- 展示如何从 Spring 容器中获取被导入的 Bean

## @Import 注解的作用

1. **导入普通类**：将没有 `@Component` 等注解的普通类导入到 Spring 容器中
2. **导入配置类**：可以导入其他配置类
3. **导入 ImportSelector 实现类**：动态选择要导入的类
4. **导入 ImportBeanDefinitionRegistrar 实现类**：自定义 Bean 注册逻辑

## 运行方式

直接运行 `ImportDemo` 类的 `main` 方法即可看到演示效果。

## 预期输出

运行后会看到：
1. 各个类的构造函数被调用
2. 能够成功从 Spring 容器中获取 UserService 和 OrderService 的 Bean
3. 通过类型和名称都能获取到相同的 Bean 实例
4. 容器中会包含被导入的 Bean

## 关键点

- `@Import` 注解可以让没有 `@Component` 等注解的类也能被 Spring 管理
- 被导入的类默认使用名称似乎是全限定类名作为 Bean 名称
- 被导入的类会被 Spring 容器管理，支持依赖注入等功能 