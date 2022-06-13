package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.dto.request.CreateUserReq;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import com.appledeveloperacademy.MC2Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Qualifier(value = "userServiceV1")
public class UserServiceV1 implements UserService {
    private final UserRepository userRepository;

    @Override
    public Member findUserByUserCode(String usercode) {
        // Result가 1개 일 때
        // Result가 1개 이상일 때
        return userRepository.findByUsercode(usercode).get(0);
    }

    @Override
    public List<HealthTag> findHealthTagsByUserId(Long userId) {
        return userRepository.listHealthTagsById(userId);
    }

    @Override
    @Transactional
    public Long createUser(CreateUserReq createUserReq) {
        String usercode;

        do {
            usercode = UUID.randomUUID().toString();
        } while (userRepository.findByUsercode(usercode).size() > 0);

        Member member = new Member(createUserReq.getUsername(), usercode);

        userRepository.save(member);

        return member.getId();
    }
}
