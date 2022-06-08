package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public String createUser(@RequestBody final CreateUserReq createUserReq) {
        return "createUser";
    }

    @GetMapping("/{usercode}")
    public String getUser(@PathVariable final String usercode) {
        return "getUser";
    }

    static class CreateUserReq {
        private String username;
    }
}
