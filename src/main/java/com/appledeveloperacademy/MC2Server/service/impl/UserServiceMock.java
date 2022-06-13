package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateUserReq;
import com.appledeveloperacademy.MC2Server.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "userServiceMock")
public class UserServiceMock implements UserService {

    @Override
    public Member findUserByUserCode(String usercode) {
        Member member = new Member();
        member.setId(1L);
        member.setUsercode(usercode);
        member.setCreatedAt(LocalDateTime.now());
        member.setUsername("Leo");
        return member;
    }

    @Override
    public List<HealthTag> findHealthTagsByUserId(Long userId) {
        List<HealthTag> tags = new ArrayList<>();
        HealthTag tag1 = buildHealthTag(0L, "왼쪽 발을 절고 있음");
        HealthTag tag2 = buildHealthTag(1L, "오른쪽 발을 절고 있음");
        HealthTag tag3 = buildHealthTag(2L, "귀가 안들리는 것 같음");
        HealthTag tag4 = buildHealthTag(3L, "배가 부풀어올랐음");

        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        return tags;
    }

    @Override
    public Long createUser(CreateUserReq createUserReq) {
        System.out.println("\n\n\n\n\n 으아ㅏ아아ㅏ아ㅏ아ㅏ아아");
        return null;
    }

    private HealthTag buildHealthTag(Long tagId, String content) {
        HealthTag healthTag = new HealthTag();
        healthTag.setId(tagId);
        healthTag.setContent(content);
        healthTag.setCreatedAt(LocalDateTime.now());
        return healthTag;
    }


}
