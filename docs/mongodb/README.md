# Backend Database with MongoDB

## 1. DB 구성

- MongoDB 5.0.16 - [MongoDB](https://www.mongodb.com/)
- Mongo Express 0.54.0 - [Mongo Express](https://github.com/mongo-express/mongo-express)
- Docker Container 환경 구성

## 2. 설치 방법

### 1. Docker Compose 설정 확인

- `docker-compose-mongo.yml` 파일에서 DB 데이터를 저장할 볼륨 정보 확인 및 수정

```yml
# 같은 설정 값으로 mongo-1, mongo-2, mongo-3을 확인
volumes:
  - ./mongodb-1:/data/db
  - ./mongodb.key:/etc/mongodb.key
```

- Replicat Set 통신을 위한 Key 생성

```shell
# base64로 키 파일 생성
openssl rand -base64 756 > mongodb.key
# key 파일 권한 설정
# 사용자 999는 일반적으로 docker를 의미함
# docker-compose 파일 내 user를 설정한다면 해당 유저로 설정
chmod 400 mongodb.key && chown 999 mongodb.key
```

### 2. Docker Container 기동

- `docker-compose-mongo.yml` 파일 내 `mongo-express`가 포함돼 있으므로 미사용 시 해당 설정 삭제

```shell
docker-compose -f docker-compose-mongo.yml up -d
```

### 3. 정상 기동 확인

```shell
docker ps -a
# 혹은
docker stats
# 상태 확인 후 mongo-1의 컨테이너 ID 복사
```

### 4. Replica Set 설정

- Main DB에 해당하는 `mongo-1` 컨테이너 내부로 접속 후 다음 명령어 실행

```shell
# 컨테이너 내부 접속
docker exec -it {mongo-1 컨테이너 ID} bash
# MongoDB 콘솔 접속
mongo -u root -p {docker-compose-mongo.yml 내 mongo-1의 MONGO_INITDB_ROOT_PASSWORD 값}
# Replica Set 정보 입력
# docker-compose-mongo.yml에 명시된 서비스 이름 확인
rs.initiate({
  _id: "rs0",
  members: [
    {_id: 0, host: "mongodb-1"},
    {_id: 1, host: "mongodb-2"},
    {_id: 2, host: "mongodb-3"}
  ]
});
# Replica Set 상태 확인
rs.status();
```

- `docker-compose-mongo.yml`에서 `mongo-express`를 활성화 했다면 인터넷 브라우저에서 DB 확인 가능
  - 접속 주소 - 서버 호스트:{docker-compose-mongo.yml 내 mongo-express의 포트}
  - 계정 정보
    - ID - {{docker-compose-mongo.yml 내 mongo-express의 ME_CONFIG_BASICAUTH_USERNAME}
    - PW - {{docker-compose-mongo.yml 내 mongo-express의 ME_CONFIG_BASICAUTH_PASSWORD}
