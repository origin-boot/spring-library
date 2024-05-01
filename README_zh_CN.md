# Spring Library

这是一个图书借阅管理系统，市民可以自由借阅和归还图书，并自行更改借阅与归还的状态。
市民可以注册自己的账户并查看图书的相关信息，他们可以借书和还书。
我们假设每位市民都是负责任的，会妥善保管图书，并且他们不会借了书而不执行借书登记操作。

## 安装要求 

服务端:

- Java: 17+ LTS
- Spring Boot: 3.2.5
- Domain Driven Design

客户端:

- Vue: https://vuejs.org/
- ElementUI: https://github.com/element-plus/element-plus
- TypeScript: https://www.typescriptlang.org/

## 项目结构

### 单模块服务端

- domain
  - error
  - success
- infrastructure
  - controller
  - jwt
  - repository
  - util
  - ...
- restful

### 多模块服务端

- shared
  - domain
  - infrastructure
    - controller
    - jwt
    - repository
    - util
    - ...
- module1
  - domain
  - infrastructure
    - DepartmentRepository.java
  - restful
- module2
  - domain
  - infrastructure
  - port
    - restful
    - console
  
说明：

- 公共的文件，放到shared下面
- 子模块只有一个，直接往上提一层，避免目录过深，参考`DepartmentRepository.java`和`port`。

## 代码提交规范

1. 单次提交的内容是方便Code Review的。
2. 单次提交的代码，需要是代码格式化后的，方便阅读的。
3. 有效使用.gitignore文件，不能提交无用文件到代码仓库。
4. 单次提交的message，需遵守Angular提交规范，使用如"feat(docs): content"的格式。
5. 单次提交使用Git提交检查工具进行强制检测，如使用commitlint、husky等。

## Java命名规范

1. 基础规范
    - 所有命名使用有意义的、清晰描述性的英文单词。
    - 杜绝过长，确保名称易于理解和记忆。
    - 按场景选择使用驼峰式（CamelCase）、蛇形（snake_case）或串式（kebab-case）命名。
    - 文件放置使用紧凑模式，必要时用类型后缀进行区分，如UserController、UserService等。
    - 目录下是单一的类别时，避免不必要的后缀，例如使用 `model.User` 而非 `UserModel`。
2. 变量命名
    - 均使用小驼峰法（camelCase）。
    - 使用复数代替类型后缀，如使用users代替userList。
3. 常量命名
    - 全部使用蛇形命名法（snake_case），全部大写。
    - 如 `MAX_SIZE`, `DEFAULT_COLOR`。
4. 枚举命名
    - 通常使用蛇形命名法（snake_case），全部大写。
    - 如 `STATUS_ACTIVE`, `STATUS_INACTIVE`。
5. 类命名
    - 使用大驼峰法（PascalCase）。
    - 类名应明确反映其功能，如 `User`, `NetworkUtil`。
6. 方法命名
    - 使用小驼峰法（camelCase）。
    - 方法名应清楚描述其功能，如 `calculateTotal`, `setUsername`。
7. 接口
    - 使用大驼峰法（PascalCase）。
    - 不添加多余的标识，如使用`UserManagement`代替`IUserManagement`。
    - 若接口只有一个实现，此时直接使用类替代接口，避免接口滥用。
8. 控制器方法
    - 使用小驼峰法。
    - 使用add、remove、edit、find、search代替get、gets、post、update、delete等。
    - 方法后面应添加实体类型，如addUser、findUser等。
9. 仓库类方法
    - 使用小驼峰法。
    - 使用JPA时需准守JPA的命名规范。
10. 服务类
    - 使用大驼峰法。
    - 确保服务名称清晰表明其功能，如 `BillingService`, `UserAuthenticationService`。
11. 目录命名
    - 使用串式命名法（kebab-case），如 `assets-images`，`config-data`。
    - 目录使用单数替代复数，需一直保持统一。
12. 数据库命名
    - 使用蛇形命名法（snake_case），小写。
    - 表名使用复数，如 `user_accounts`, `order_details`。
    - 字段名通常使用单数，代表多个时需使用复数，如`departments`。
13. API字段命名
    - 上下行数据字段使用小驼峰法（camelCase），如 `firstName`, `orderNumber`。
    - 遵守Restful规范，合理使用POST、GET、PUT、PATCH、DELETE等。
    - 接口路径应使用复数，如"POST /api/users"。
15. 测试用例命名
    - 测试方法名使用小驼峰法，并应体现测试目的，如 `testConnectionShouldFailIfTimeout`。

## 参考资料

- https://github.com/christophknabe/spring-ddd-bank
- https://github.com/spring-projects/spring-petclinic
- https://github.com/ali-bouali/spring-boot-3-jwt-security
- https://github.com/kailong321200875/vue-element-plus-admin
- https://www.conventionalcommits.org/en/v1.0.0-beta.4/
- https://github.com/ZJDoc/GitGuide
- https://typicode.github.io/husky/get-started.html
