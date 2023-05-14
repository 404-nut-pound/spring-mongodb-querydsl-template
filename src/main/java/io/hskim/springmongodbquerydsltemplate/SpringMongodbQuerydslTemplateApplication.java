package io.hskim.springmongodbquerydsltemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringMongodbQuerydslTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringMongodbQuerydslTemplateApplication.class, args);
  }
}
