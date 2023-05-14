package io.hskim.springmongodbquerydsltemplate.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
@EnableMongoAuditing
public class MongoDBConfig extends AbstractMongoClientConfiguration {

  @Value("${spring.data.mongodb.authentication-database}")
  private String mongodbAuth;

  @Value("${spring.data.mongodb.host}")
  private String mongodbHost;

  @Value("${spring.data.mongodb.port}")
  private int mongodbPort;

  @Value("${spring.data.mongodb.database}")
  private String mongodbDatabase;

  @Value("${spring.data.mongodb.username}")
  private String mongodbUsername;

  @Value("${spring.data.mongodb.password}")
  private String mongodbPassword;

  @Override
  protected String getDatabaseName() {
    return mongodbDatabase;
  }

  @Override
  public MongoClient mongoClient() {
    String connectionString =
      "mongodb://%s:%s@%s:%d/%s?authSource=%s".formatted(
          mongodbUsername,
          mongodbPassword,
          mongodbHost,
          mongodbPort,
          mongodbDatabase,
          mongodbAuth
        );

    return MongoClients.create(connectionString);
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }

  @Override
  public MappingMongoConverter mappingMongoConverter(
    MongoDatabaseFactory databaseFactory,
    MongoCustomConversions customConversions,
    MongoMappingContext mappingContext
  ) {
    MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter(
      databaseFactory,
      customConversions,
      mappingContext
    );
    mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

    return mappingMongoConverter;
  }

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }

  public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.of("system");
      // 아래 코드는 Spring Security 필요
      // Authentication authentication = SecurityContextHolder
      //   .getContext()
      //   .getAuthentication();

      // //사용자 인증 정보가 없을 때 기본 반환 값
      // if (
      //   authentication == null ||
      //   !authentication.isAuthenticated() ||
      //   authentication.getPrincipal().equals("anonymousUser")
      // ) {
      //   return Optional.of("system");
      // }

      // //사용자 인증 정보가 있으면 사용자 ID 반환
      // return Optional.of(((User) authentication.getPrincipal()).getUsername());
    }
  }
}
