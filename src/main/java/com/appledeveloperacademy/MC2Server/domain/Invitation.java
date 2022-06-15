package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationLog;
import com.appledeveloperacademy.MC2Server.domain.superclass.CreationModificationLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Invitation extends CreationLog {
    @Id @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(unique = true)
    private String code;

    private boolean fulfilled;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }

    public Invitation(String code) {
        this.code = code;
        this.fulfilled = false;
        this.expiredAt = LocalDateTime.now().plusMinutes(1);
    }

    public static String generateCode() {
        Random random = new Random();
        String randomCode = "";
        for (int i = 0; i < 6; i++) {
            randomCode += Integer.toString(random.nextInt(10));
        }
        return randomCode;
    }
}
