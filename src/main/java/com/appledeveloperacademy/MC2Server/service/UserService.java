package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserInfoDto findUserByUserCode(String usercode);

    List<HealthTag> findHealthTagsByUserId(Long userId);
}
