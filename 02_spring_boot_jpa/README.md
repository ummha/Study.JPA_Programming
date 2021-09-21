## 목차

- [Spring Data JPA](#spring-data-jpa)
  - [JPA 구조도](#jpa-구조도)
  - [JPA 필수 도구 Lombok](#jpa-필수-도구-lombok)
  - [H2 Database setting](#h2-database-setting)
- [Basic JpaRepository](#basic-jparepository)
  - [CRUD](#crud)
    - [find](#find)
    - [save](#save)
    - [delete](#delete)
  - [QBE QBM](#qbe-qbm)
- [Query Methods](#query-methods)
    - [Spring data jpa tables](#spring-data-jpa-tables)
  - [Methods](#methods)
    - [Kinds of find/select](#kinds-of-findselect)
    - [FirstN TopN](#firstn-topn)
    - [And Or](#and-or)
    - [After Before](#after-before)
    - [Than](#than)
    - [Between](#between)
    - [IsNotNull](#isnotnull)
    - [IsNotEmpty](#isnotempty)
    - [Like](#like)
    - [In](#in)
    - [OrderBy](#orderby)
    - [Pageable](#pageable)

# Spring Data JPA

## JPA 구조도

![](../images/jpa-structure.jpg)

## JPA 필수 도구 Lombok

- [domain: User](./src/main/java/com/example/jpa/bookmanager/domain/User.java)
- [Test Code](./src/test/java/com/example/jpa/bookmanager/domain/UserTest.java)

## H2 Database setting

- build.gradle

```gradle
dependencies {
  ...
  runtimeOnly 'com.h2database:h2'
  ...
}
```

- src/main/resources/application.yml

```yml
spring:
  h2:
    console:
      enabled: true # h2 console을 사용하는지 여부 설정
  jpa:
    defer-datasource-initialization: true # data.sql을 시스템이 올라온 후 사용할지 여부 설정
    show-sql: true  # jpa 쿼리를 볼 것인지 여부 설정
    properties:
      hibernate:
        format_sql: true  # jpa 쿼리를 정렬된 상태로 볼 수 있는 설정
```

# Basic JpaRepository

- src/main/java/.../UserRepository.java

```java
public interface UserRepository extends JpaRepository<User, Long> {}
```

## CRUD

- 참고: [src/test/.../UserRepository.java](src/test/java/com/example/jpa/bookmanager/repository/UserRepositoryTest.java)

### find

DML의 SELECT와 같은 조회 기능을 하기 위한 메소드 네이밍

- `findAll()`
  - 전체 조회
  - `findAll(Sort sort)`
    - 정렬 조회 (ORDER BY와 동일한 기능)
  - `findAll(Pageable pageable)`
    - Paging 기능
    - `Page<T>`를 리턴
- `findAllById(Iterable<ID> ids)`
  - 복수의 Id를 대상으로 조회 ex) WHERE id IN (?,?,?,...)
- `findById(ID id)`
  - 하나의 Id를 대상으로 조회 ex) WHERE id = ?
- `getById(ID id)`
  - getOne() 메소드와 같은 기능 (@Deprecated -> getOne())

### save

DML의 INSERT, UPDATE와 같은 삽입과 수정 기능을 하기 위한 메소드 네이밍

- `save(S entity)` : 단수/한개의 객체 저장 및 수정
- `saveAll(Iterable<S> entities)` : 복수, 다수의 객체 리스트 저장

### delete

DML의 DELETE와 같은 삭제 기능을 하기 위한 메소드 네이밍

- `delete(T entity)`
  - 매개변수가 null이면 안됨
  - delete를 하기 전에 해당 자료가 존재하는지 여부를 위한 select 쿼리가 돌아감
  - `deleteById(ID id)`
- `deleteAll()`
  - delete 쿼리가 연속적으로 실행됨
  - `deleteAll(Iterable<? extends T> entities)`
- `deleteAllByIdInBatch(Iterable<ID> ids)`
  - delete 쿼리가 하나만 실행되며 or 연산자를 사용하여 삭제 쿼리가 작성됨
  - select 쿼리로 자료존재 여부 확인 안함
  - `deleteAllInBatch(Iterable<T> entities)`
  - `deleteAllInBatch()`
    - 파라미터를 전달하지 않을시 where 조건문 없이 delete from [table-name] 쿼리가 작성됨.
    - 전체 데이터가 삭제되므로 주의해야함.

## QBE QBM

- QBE = Query By example
- QBM = Query By Matcher
- matcher는 어떻게 보면 만능같아 보이지만, 약간의 제약이 있다. 예) String 자료형만 가능
- 실제로 matcher는 생각보다 많이 쓰이지 않는다고 한다.

의도 : `WHERE email LIKE '%.com'`

```java
ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnorePaths("name")    // 무시할 필드명
        .withMatcher("email", endsWith());  // 매칭할(조건절) 필드명
Example<User> example = Example.of(new User("mi", ".com"), matcher);
userRepository.findAll(example).forEach(System.out::println);
```

의도 : `WHERE email LIKE '%gmail%'`

```java
ExampleMatcher matcher = ExampleMatcher.matching()
      .withIgnorePaths("name")
        .withMatcher("email", contains());
Example<User> example = Example.of(new User("", "gmail"), matcher);
userRepository.findAll(example).forEach(System.out::println);
```
의도 : `WHERE email LIKE 'email%'`

```java
User user = new User();
user.setEmail("min");
ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email", startsWith());
Example<User> example = Example.of(user, matcher);
userRepository.findAll(example).forEach(System.out::println);
```

의도: `WHERE email = 'minseo@gmail.com' AND name = 'minseo'`

```java
Example<User> example = Example.of(new User("minseo", "minseo@gmail.com"));
userRepository.findAll(example).forEach(System.out::println);
```

# Query Methods

### Spring data jpa tables

- [Supported Keywords Indside Method Names](../images/table3_supported_keywords_inside_method_names.png)
- [Query Subject Keywords](../images/table8_query_subject_keywords.png)
- [Query Predicate Modifier Keywords](../images/table10_query_predicate_modifier_keywords.png)
- [Query Return Types](../images/table11_query_return_types.png)

## Methods

> 가독성을 위해 매개변수 생략  
> 참고: [src/test/.../UserRepository.java](src/test/java/com/example/jpa/bookmanager/repository/UserRepositoryTest.java)

### Kinds of find/select

SELECT 기능을 뜻하는 메소드 네이밍은 find 뿐만이 아니라 몇가지 더 존재한다.

- **find**ByEmail
- **get**ByEmail
- **read**ByEmail
- **query**ByEmail
- **search**ByEmail
- **stream**ByEmail
- **findUser**ByEmail
- **findSomething**ByEmail

### FirstN TopN

위에서부터 N행의 결과 조회

- find**First1**ByName
- find**Top2**ByName
- find**Last1**ByName (X) 
  - 인식하지 않는 키워드 작성시 -> 오류 또는 무시됨

### And Or

다수의 AND/OR 조건절

    find...By...And...And...And...

- findByEmail**And**Name
- findByEmail**Or**Name

### After Before

비교 연산자 사용

- findById**After**
- findById**Before**

### Than

After/Before는 equal(=)을 포함한 비교 연산자가 적용되지 않는다.  
하지만 than 구문은 GreaterThanEqual(>=) 방식으로 작성 가능하다.  
즉, 가독성은 After, Before가 더 좋겠지만 Than 구문이 더 범용성이 넓다고 볼 수 있다.

- findByCreatedAt**GreaterThan**
- findById**GreaterThanEqual**
- findById**LessThanEqual**

### Between

- findByCreatedAt**Between**

### IsNotNull 

`WHERE id IS NOT NULL` 과 같은 구문을 작성함

- findById**IsNotNull**()

### IsNotEmpty

자바 문자열 not empty는 일반적으로 null과 빈문자열("")의 여부를 확인한다.  
하지만 해당 NotEmpty 구문은 Collection 타입의 NotEmpty를 의미한다.  
relational에서 사용하게 되지만, 잘 사용되지 않는다고 한다.

- findByAddress**IsNotEmpty**()
  - Address : User 엔터티와 관계가 있다고 가정

### Like

- findByName**StartingWith**
- findByName**EndingWith**
- findByName**Contains**
- findByName**Like**
  - ex) `findByNameLike("%ns%")`

### In

**WHERE name IN (?, ?, ?)** 과 같은 IN 연산자 작성

- findByName**In**

### OrderBy

메소드 네이밍 방식 정렬

- findTop1ByName**OrderBy**Id**Desc**
- findTopByName**OrderBy**Id**Desc**

파라미터 방식 정렬 (매개변수로 **Sort** 클래스를 받음)

      List<User> findFirstByName(String name, Sort sort);

- 예시

```java
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

userRepository.findFirstByName("John", Sort.by(Order.desc("id"), Order.asc("email")));
```

### Pageable

페이징 기능

      Page<User> findByName(String name, Pageable pageable);

- 예시, 페이징과 정렬 동시 적용 가능

```java
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

userRepository.findByName(
        "John",
        PageRequest.of(0, 1, Sort.by(Order.desc("id")))
).getContent()
```