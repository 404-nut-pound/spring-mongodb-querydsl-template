package io.hskim.springmongodbquerydsltemplate.api.user.repo;

import io.hskim.springmongodbquerydsltemplate.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepo
  extends
    MongoRepository<UserEntity, String>,
    QuerydslPredicateExecutor<UserEntity> {}
