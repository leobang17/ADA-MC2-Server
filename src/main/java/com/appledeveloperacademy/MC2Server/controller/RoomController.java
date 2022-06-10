package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/rooms")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // List all rooms managed by a user
    @GetMapping
    public ResponseEntity<ListedResult<ParticipatingRoomDto>> getAllRooms() {
        Long userId = 1L;
        List<ParticipatingRoomDto> rooms = roomService.findAllParticipatingRooms(userId);
        return ResponseEntity.ok(new ListedResult<>(rooms));
    }

    // Create a new room
    @PostMapping
    public String createRoom(@RequestBody CatInfoDto catInfoDto) {
        return "createRoom";
    }

    // Check if invitation code exists to specific room.
    @GetMapping("/{roomId}/invitation-codes")
    public ResponseEntity<InvitationCodeDto> checkInvitation(@PathVariable final Long roomId) {
        InvitationCodeDto invitation = roomService.findInvitationCodeByRoomId(roomId);
        return ResponseEntity.ok(invitation);
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

    @GetMapping("/{roomId}/cats/{catId}")
    public ResponseEntity<CatInfoDto> getCatProfile(@PathVariable final Long roomId, @PathVariable final Long catId) {
        Cat findCat = roomService.findCatByCatId(catId);
        return ResponseEntity.ok(new CatInfoDto(findCat));
    }

//    static class CatInfoDto {
//        private String name;
//        private String gender;
//        private boolean neutralization;
//        private int age;
//        private String type;
//        private List<String> health;
//        private String imgUrl;
//    }
}
