package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.dto.HealthTagDto;
import com.appledeveloperacademy.MC2Server.dto.UserInfoDto;
import com.appledeveloperacademy.MC2Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody final CreateUserReq createUserReq) {
        return ResponseEntity.created(URI.create("/users/")).build();
    }

    @GetMapping("/{usercode}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable final String usercode) {
        UserInfoDto findUser = userService.findUserByUserCode(usercode);
        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @GetMapping("/health-tags")
    public ResponseEntity<ListedResult<HealthTagDto>> getCustomHealthTags() {
        final Long userId = 0L;
        List<HealthTag> tags = userService.findHealthTagsByUserId(userId);
        List<HealthTagDto> collect = tags.stream().map(HealthTagDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new ListedResult<>(collect));
    }
    
    static class CreateUserReq {
        private String username;
    }
}
