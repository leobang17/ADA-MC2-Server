package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import com.appledeveloperacademy.MC2Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return userRepository.findByUsercode(usercode);
    }

    @Override
    public List<HealthTag> findHealthTagsByUserId(Long userId) {
        return userRepository.listHealthTagsById(userId);
    }
}
