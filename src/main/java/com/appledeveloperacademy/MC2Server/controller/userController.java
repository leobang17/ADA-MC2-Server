package com.appledeveloperacademy.MC2Server.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class userController {

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
