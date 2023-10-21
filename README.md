# banyan
- 项目定制业务工程，每个项目作为一个模块

## 接口文档生成器

**[smart-doc](https://smart-doc-group.github.io/#/zh-cn/)**

使用方式: 
- [最佳实践](https://smart-doc-group.github.io/#/zh-cn/start/bestPractice)
- [注释说明](https://smart-doc-group.github.io/#/zh-cn/start/javadoc)

## maven私服访问
- 刷新本地DNS
- 如果开启了vpn系统代理，需过滤公司域名：\*.standard*

## 可配置的环境变量(也可配置为虚拟机选项)
- app.tmpdir : 应用程序使用的临时目录，默认为工程目录下的tmp
- app.logdir : 应用程序的日志目录，默认为工程目录下的log
- app.component.host : 应用程序使用的组件服务地址，默认为localhost

## gradle

### 统一依赖库管理

引用库方法:

```groovy
dependencies {
    implementation project(':allspark-resource-presentation')
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation dependLibs.mybatisPlus
    compileOnly dependLibs.lombok
    annotationProcessor dependLibs.lombok
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

1. 引用 工程模块 `project(':模块名')`
2. 引用 spring 库, `group:xxx` ,不用版本号
3. 引用其它三方库，dependLibs.xxx, 在 `gradle/config.gradle` 中添加versions和dependLibs

## 通用类

1. 异常 `BasicException`
2. 异常码 `IErrorCode`
   1. code范围
   - common:[-1000,1999]
4. 断言 `CommonAssert`
5. 请求统一返回 `ApiResult`
6. 统一分页查询 `PageQuery`, 例子

    ```groovy
    QueryWrapper<ProductDO> wrapper = IServiceUtil.queryWrapperBy(query, ProductDO::getProductKey, ProductDO::getProductName);
    wrapper.lambda().eq(CharSequenceUtil.isNotBlank(classify), ProductDO::getClassify, classify);
    Page<ProductDO> page = new Page<>(query.getPageNum(), query.getPageSize());
    return this.page(page, wrapper);
    ```

## 文件存储

组件:
- `file-component`

**[spring-file-storage](https://spring-file-storage.xuyanwu.cn/)**

通一文件**http API**:

- 上传文件： POST `/api/v2/file/`
- 上传图片:  POST `/api/v2/file/image`
- 下载文件:  GET `/api/v2/file/{filePath}/{fileName}`


## 线程池

组件:
- `theardPool-component`

**[dynamic-tp](https://dynamictp.top/)**


线程配置文件： `application-dynamictp.yaml`


