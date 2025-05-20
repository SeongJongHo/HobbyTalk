package com.jongho.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
*
* BaseResponseEntity는 API의 응답을 위한 커스텀클래스 입니다.
* @JsonInclude(JsonInclude.Include.NON_NULL)을 이용하여 null값인 필드는 제외하고 JSON으로 변환합니다.
*
*/
@Schema(description = "기본 응답 데이터 구조")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@RequiredArgsConstructor
public class BaseResponseEntity<T> {
    @Schema(description = "결과 코드 (예: 200은 성공, 400은 클라이언트 오류)", example = "200")
    private int code;
    private HttpStatus status;
    @Schema(description = "결과 메시지", example = "OK")
    private String message;
    @Schema(description = "반환 데이터")
    private T data;

    public BaseResponseEntity(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.code = status.value();
        this.status = status;
    }

    public BaseResponseEntity(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.code = status.value();
    }

    public static<T> ResponseEntity<BaseResponseEntity<T>> ok(T data, String message) {

        BaseResponseEntity<T> baseResponseEntity = new BaseResponseEntity<>(data, message, HttpStatus.OK);

        return ResponseEntity.status(baseResponseEntity.getStatus()).body(baseResponseEntity);
    }

    public static ResponseEntity<BaseResponseEntity<?>> ok(String message) {
        BaseResponseEntity<?> baseResponseEntity = new BaseResponseEntity<>(message, HttpStatus.OK);

        return ResponseEntity.status(baseResponseEntity.getStatus()).body(baseResponseEntity);

    }

    public static<T> ResponseEntity<BaseResponseEntity<T>> create(T data, String message) {
        BaseResponseEntity<T> baseResponseEntity = new BaseResponseEntity<>(data, message, HttpStatus.CREATED);

        return ResponseEntity.status(baseResponseEntity.getStatus()).body(baseResponseEntity);
    }

    public static ResponseEntity<BaseResponseEntity<?>> create(String message) {
        BaseResponseEntity<?> baseResponseEntity = new BaseResponseEntity<>(message, HttpStatus.CREATED);

        return ResponseEntity.status(baseResponseEntity.getStatus()).body(baseResponseEntity);
    }

    public static ResponseEntity<BaseResponseEntity<?>> fail(HttpStatus httpStatus, String message) {
        BaseResponseEntity<?> baseResponseEntity = new BaseResponseEntity<>(message, httpStatus);

        return ResponseEntity.status(baseResponseEntity.getStatus()).body(baseResponseEntity);
    }
}
