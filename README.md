# Spring Library

A library book borrowing management system where citizens can freely borrow and return books,
and change their borrowing and returning statuses themselves.
Citizens can register their own accounts and check the relevant information of books.
They can borrow and return books.
We assume that every citizen is a responsible citizen who takes good care of books.
They will not borrow books but fail to perform the borrowing registration operation.

## Requirements

Server:

- Java: 17+ LTS
- Spring Boot: 3.2.5
- Domain Driven Design

Client:

- Vue: https://vuejs.org/
- ElementUI: https://github.com/element-plus/element-plus
- TypeScript: https://www.typescriptlang.org/

## Project Structure

### Single Module Server

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

### Multi-Module Server

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

Notes:

- Common files should be placed under `shared`.
- If there is only one sub-module, move it up a level to avoid overly deep directory structures, see `DepartmentRepository.java` and `port` for reference.

## Code Submission Standards

1. Each submission should facilitate Code Review.
2. Code in each submission should be formatted to enhance readability.
3. Use the .gitignore file effectively to avoid committing useless files to the repository.
4. Commit messages should follow the Angular commit convention, using formats like "feat(docs): content".
5. Use Git commit checking tools like commitlint or husky to enforce checks on submissions.

## Java Naming Conventions

1. Basic Specifications
   - All names should use meaningful, clearly descriptive English words.
   - Avoid overly long names to ensure they are easy to understand and remember.
   - Choose CamelCase, snake_case, or kebab-case naming conventions according to the scenario.
   - Use a compact layout for file placement, differentiating with type suffixes when necessary, such as UserController, UserService, etc.
   - When a directory contains only one category, avoid unnecessary suffixes, for example, use `model.User` instead of `UserModel`.
2. Variable Naming
    - Use camelCase for all variables.
    - Use plurals instead of type suffixes, e.g., use `users` instead of `userList`.
3. Constant Naming
    - Use snake_case in uppercase for all constants.
    - Examples include `MAX_SIZE`, `DEFAULT_COLOR`.
4. Enum Naming
    - Typically use snake_case in uppercase.
    - Examples include `STATUS_ACTIVE`, `STATUS_INACTIVE`.
5. Class Naming
    - Use PascalCase.
    - Class names should clearly reflect their purpose, e.g., `User`, `NetworkUtil`.
6. Method Naming
    - Use camelCase.
    - Method names should clearly describe their function, e.g., `calculateTotal`, `setUsername`.
7. Interfaces
    - Use PascalCase.
    - Avoid adding unnecessary identifiers, e.g., use `UserManagement` instead of `IUserManagement`.
    - If an interface has only one implementation, use a class instead to avoid interface abuse.
8. Controller Methods
    - Use camelCase.
    - Use verbs like add, remove, edit, find, and search instead of HTTP methods like get, post, delete, etc.
    - Append the entity type to method names, e.g., `addUser`, `findUser`.
9. Repository Class Methods
    - Use camelCase.
    - When using JPA, adhere to JPA naming conventions.
10. Service Classes
    - Use PascalCase.
    - Ensure the service name clearly indicates its function, e.g., `BillingService`, `UserAuthenticationService`.
11. Directory Naming
    - Use kebab-case, such as `assets-images`, `config-data`.
    - Directories should use singular rather than plural names, maintaining consistency.
12. Database Naming
    - Use snake_case in lowercase.
    - Use plurals for table names, like `user_accounts`, `order_details`.
    - Generally use singular for field names, but use plurals when indicating multiples, e.g., `departments`.
13. API Field Naming
    - Use camelCase for data fields in requests and responses, such as `firstName`, `orderNumber`.
    - Follow Restful standards, appropriately using POST, GET, PUT, PATCH, DELETE, etc.
    - Use plural nouns in API paths, e.g., "POST /api/users".
15. Test Case Naming
    - Use camelCase for test method names, which should reflect the purpose of the test, e.g., `testConnectionShouldFailIfTimeout`.

## References

- https://github.com/christophknabe/spring-ddd-bank
- https://github.com/spring-projects/spring-petclinic
- https://github.com/ali-bouali/spring-boot-3-jwt-security
- https://github.com/kailong321200875/vue-element-plus-admin
- https://www.conventionalcommits.org/en/v1.0.0-beta.4/
- https://github.com/ZJDoc/GitGuide
- https://typicode.github.io/husky/get-started.html
