package com.appledeveloperacademy.MC2Server.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/rooms")
@RestController
public class roomController {

    // List all rooms managed by a user
    @GetMapping
    public String getAllRooms() {
        return "getAllRooms";
    }

    // Create a new room
    @PostMapping
    public String createRoom(@RequestBody CatInfoDto catInfoDto) {
        return "createRoom";
    }

    // Check if invitation code exists to specific room.
    @GetMapping("/{roomId}/invitation-codes")
    public String checkInvitation(@PathVariable final Long roomId) {
        return "checkInvitation";
    }

    // Create invitation codes for a particular room
    @PostMapping("/{roomId}/invitation-codes")
    public String createInvitation(@PathVariable final Long roomId) {
        return "createInvitation";
    }

    // Add new user to an existing room
    @PostMapping("/{roomId}/users")
    public String participateRoom(
            @PathVariable final Long roomId,
            @RequestBody final CatInfoDto catInfoDto) {
        return "participateRoom";
    }

    // Merge a room to another room
    @PostMapping("/{roomId}/rooms")
    public String mergeRoom(@PathVariable final Long roomId, @RequestParam(name = "roomId") final Long mergingRoomId) {
        return "mergeRoom";
    }

    static class CatInfoDto {
        private String name;
        private String gender;
        private boolean neutralization;
        private int age;
        private String type;
        private List<String> health;
        private String imgUrl;
    }

}
