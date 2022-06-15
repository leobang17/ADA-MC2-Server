package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateHealthTagReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateUserReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Member findUserByUserCode(String usercode);

    List<HealthTag> findHealthTagsByUserId(Long userId);
    Member createUser(CreateUserReq createUserReq);

    Long createHealthTag(Long userId, CreateHealthTagReq tagReq);

    Long verifyInvitation(String code);
}
