package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.dto.HealthTagDto;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateUserReq;
import com.appledeveloperacademy.MC2Server.service.UserService;
import com.appledeveloperacademy.MC2Server.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(@Qualifier(value = "userServiceV1") UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody final CreateUserReq createUserReq) {
        Member user = userService.createUser(createUserReq);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(UserInfoDto.build(user));
    }

    @GetMapping("/{usercode}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable final String usercode) {
        Member findUser = userService.findUserByUserCode(usercode);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoDto.build(findUser));
    }

    @GetMapping("/health-tags")
    public ResponseEntity<ListedResult<HealthTagDto>> getCustomHealthTags(
            @RequestHeader(value = "Authorization") String usercode
    ) {
        final Long userId = tokenService.authenticate(usercode);
        List<HealthTag> tags = userService.findHealthTagsByUserId(userId);
        List<HealthTagDto> collect = tags.stream().map(HealthTagDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new ListedResult<>(collect));
    }
}
