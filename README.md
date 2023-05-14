# Spring Boot 3 + MongoDB + Querydsl Template

## 프로젝트 구성

- Spring Boot - 3.0.6
  - Spring Web MVC
  - Spring Validation 및 Security 제외
- Querydsl - 5.0.0
- MongoDB
- Spring OpenApi 2.1.0
  - `{contextRoot}/docs`

## Querydsl 설정

### gradle.build 파일

- Spring Boot 3 버전 이상부터 Querydsl 패키지 버전 뒤 `:jakarta`를 붙여야 정상 동작
  - Querydsl MongoDB 패키지는 제외
- MongoDB 드라이버 중복을 방지하기 위해 Querydsl에 포함된 드라이버를 제외 처리

  ```groovy
  ...
  configurations {
    ...
    querydsl.extendsFrom compileClasspath
    ...
  }
  ...
  dependencies {
    ...
    implementation("com.querydsl:querydsl-mongodb:5.0.0") {
      exclude group: "org.mongodb", module: "mongo-java-driver"
    }
    annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
    ...
  }
  ...
  ```

### Qclass 위치

- `/bin/generated-sources/annotations/{프로젝트 패키지}/entity`
- 프로젝트 전체 빌드(Gradle 빌드 아님) 시 자동 갱신 됨

### MongoDB Replica Set 설정

- MongoDB에서 Transaction을 활성화하려면 Replica Set을 구성해야 함
- 관련 사항은 [/docs/mongodb/](/docs/mongodb/) 이하 내용 참조
