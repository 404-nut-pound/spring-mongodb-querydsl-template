package io.hskim.springmongodbquerydsltemplate.entity;

import io.hskim.springmongodbquerydsltemplate.api.user.dto.UserDto.UserResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Entity
@Document(collection = "aa_user")
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class UserEntity extends AuditEntity {

  @Id
  private String id;

  @Indexed(unique = true)
  private String userId;

  private String password;

  private String userName;

  @Indexed(unique = true)
  private String email;

  @Builder.Default
  @Setter
  private boolean deleteYn = false;

  public void setUserName(String userName) {
    if (StringUtils.hasText(userName)) {
      this.userName = userName;
    }
  }

  public void setEmail(String email) {
    if (StringUtils.hasText(email)) {
      this.email = email;
    }
  }

  public void setPassword(String password) {
    if (StringUtils.hasText(password)) {
      this.password = password;
    }
  }

  public UserResponseDto toDto() {
    return UserResponseDto
      .builder()
      .userId(this.userId)
      .userName(this.userName)
      .email(this.email)
      .deleteYn(this.deleteYn)
      .build();
  }
}
