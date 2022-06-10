package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import com.appledeveloperacademy.MC2Server.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceMock implements UserService {

    @Override
    public UserInfoDto findUserByUserCode(String usercode) {
        return new UserInfoDto(1L, "Leo", usercode, LocalDateTime.now());
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

    private HealthTag buildHealthTag(Long tagId, String content) {
        HealthTag healthTag = new HealthTag();
        healthTag.setId(tagId);
        healthTag.setContent(content);
        healthTag.setCreatedAt(LocalDateTime.now());
        return healthTag;
    }


}
