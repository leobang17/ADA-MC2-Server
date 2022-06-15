package com.appledeveloperacademy.MC2Server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

//@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
//    UNAUTHORIZED_MEMBER(NOT_FOUND, "해당 유저코드를 가진 유저가 없습니다"),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

//    USERCODE_DUPLICATED()
    ;

    private final HttpStatus status;
    private final String content;
}
