package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.MemberRoomIntermediate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Album extends MemberRoomIntermediate {
    @Id @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    @Column(name = "upload_url")
    private String uploadUrl;

    @Column(name = "store_url")
    private String storeUrl;
}
