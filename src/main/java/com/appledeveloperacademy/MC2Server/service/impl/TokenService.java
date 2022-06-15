package com.appledeveloperacademy.MC2Server.service.impl;


import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;

    public Long authenticate(String authorizationHeader) {
        String token = splitter(authorizationHeader);
        List<Member> user = userRepository.findByUsercode(token);

        if (user.size() == 0) {
            throw new RuntimeException();
        } else if (user.size() > 1) {
            throw new RuntimeException("Usercode duplicated");
        }

        return user.get(0).getId();
    }

    private String splitter(String token) {
        return token.split(" ")[1];
    }
}
