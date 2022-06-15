package com.appledeveloperacademy.MC2Server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자  요청은 인증이 필요하다. 서버는 로그인이 필요한 페이지에 대해 이 요청을 제공할 수 있다.
    상태 코드 이름이 권한 없음(Unauthorized)으로 되어 있지만 실제 뜻은 인증 안됨(Unauthenticated)에 더 가깝다*/
    UNAUTHENTICATED_MEMBER(UNAUTHORIZED, "유저가 없습니다."),


    /* 403 FORBIDDEN : 서버가 요청을 거부하고 있다. 예를 들자면, 사용자가 리소스에 대한 필요 권한을 갖고 있지 않다. (401은 인증 실패, 403은 인가 실패라고 볼 수 있음) */
    UNAUTHORIZED_MEMBER(FORBIDDEN, "권한이 없습니다."),


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */



    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 서버는 PUT 요청과 충돌하는 PUT 요청에 대한 응답으로 이 코드를 요청 간 차이점 목록과 함께 표시해야 한다. */
    USERCODE_DUPLICATED(CONFLICT, "유저 코드가 중복됩니다."),
    USER_ALREADY_PARTICIPATING(CONFLICT, "유저가 이미 방에 참여하고 있습니다."),
    ;

    private final HttpStatus status;
    private final String content;
}
