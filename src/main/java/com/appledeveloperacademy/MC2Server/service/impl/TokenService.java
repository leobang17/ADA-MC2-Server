package com.appledeveloperacademy.MC2Server.service.impl;


import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.exception.CustomException;
import com.appledeveloperacademy.MC2Server.exception.ErrorCode;
import com.appledeveloperacademy.MC2Server.repository.RoomRepository;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public Long authenticate(String authorizationHeader) {
        String token = splitter(authorizationHeader);
        List<Member> user = userRepository.findByUsercode(token);

        if (user.size() == 0) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        } else if (user.size() > 1) {
            throw new CustomException(ErrorCode.USERCODE_DUPLICATED);
        }

        return user.get(0).getId();
    }

    public Long authenticateAndAuthorize(String authorizationHeader, Long roomId) {
        String token = splitter(authorizationHeader);
        List<Member> user = userRepository.findByUsercode(token);

        if (user.size() == 0) {
            throw new CustomException(ErrorCode.UNAUTHENTICATED_MEMBER);
        } else if (user.size() > 1) {
            throw new CustomException(ErrorCode.USERCODE_DUPLICATED);
        }

        final Long userId = user.get(0).getId();

        try {
            roomRepository.checkAuthority(userId, roomId);
        } catch (EmptyResultDataAccessException e) {
            // User가 room에 대한 권한이 없음.
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        } catch (IncorrectResultSizeDataAccessException e) {
            // User가 room을 중복으로 참여하고 있음.
            throw new CustomException(ErrorCode.USER_ALREADY_PARTICIPATING);
        }
        return userId;
    }

    private String splitter(String token) {
        return token.split(" ")[1];
    }
}
