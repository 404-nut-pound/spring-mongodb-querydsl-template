package io.hskim.springmongodbquerydsltemplate.api.user.service;

import static io.hskim.springmongodbquerydsltemplate.entity.QUserEntity.userEntity;

import io.hskim.springmongodbquerydsltemplate.api.user.dto.UserDto.UserRequestDto;
import io.hskim.springmongodbquerydsltemplate.api.user.dto.UserDto.UserResponseDto;
import io.hskim.springmongodbquerydsltemplate.api.user.dto.UserDto.UserSearchDto;
import io.hskim.springmongodbquerydsltemplate.api.user.repo.UserRepo;
import io.hskim.springmongodbquerydsltemplate.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final String defaultPassword = "defaultPassword";

  private final UserRepo userRepo;

  @Transactional
  public void postUser(UserRequestDto userRequestDto) {
    userRepo.save(
      UserEntity
        .builder()
        .userId(userRequestDto.getUserId())
        .userName(userRequestDto.getUserName())
        .email(userRequestDto.getEmail())
        .password(defaultPassword) //Spring Security 패키지의 BcryptPasswordEncoder 필요
        .build()
    );
  }

  public Page<UserResponseDto> getUserList(
    UserSearchDto userSearchDto,
    Pageable pageable
  ) {
    return userRepo
      .findAll(
        userEntity.userId
          .contains(userSearchDto.getUserId())
          .and(userEntity.userName.contains(userSearchDto.getUserName()))
          .and(userEntity.email.contains(userSearchDto.getEmail())),
        pageable
      )
      .map(UserEntity::toDto);
  }

  public UserResponseDto getUser(String userId) {
    return userRepo.findById(userId).orElseThrow().toDto();
  }

  @Transactional
  public void patchUser(UserRequestDto userRequestDto) {
    UserEntity findUserEntity = userRepo
      .findById(userRequestDto.getUserId())
      .orElseThrow();

    findUserEntity.setUserName(userRequestDto.getUserName());
    findUserEntity.setEmail(userRequestDto.getEmail());

    // 입력한 비밀번호가 빈 값이거나 비밀번호, 비밀번호 확인 값이 다를 때 비밀번호를 변경하지 않음
    if (
      StringUtils.hasText(userRequestDto.getPassword()) &&
      userRequestDto.getPassword().equals(userRequestDto.getPasswordCheck())
    ) {
      findUserEntity.setPassword(userRequestDto.getPassword());
    }
  }

  @Transactional
  public void deleteUser(UserRequestDto userRequestDto) {
    UserEntity findUserEntity = userRepo
      .findById(userRequestDto.getUserId())
      .orElseThrow();

    findUserEntity.setDeleteYn(true);
  }
}
