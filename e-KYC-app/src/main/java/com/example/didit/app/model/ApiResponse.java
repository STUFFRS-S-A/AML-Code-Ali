package com.example.didit.app.model;

import com.example.didit.app.util.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> extends ResponseDTO {
    private T data;

    public ApiResponse(String statusCode, String Msg, String exceptionMsg, T data) {
        this.data = data;
        this.setStatus(statusCode);
        this.setMessage(Msg);
        this.setExceptionMsg(exceptionMsg);
    }

    public static <T> ApiResponse<T> success(T data) {
        var res = new ApiResponse<T>();
        res.setData(data);
        res.setStatus(ResponseCodes.Success);
        res.setMessage(ResponseCodes.getMessage(ResponseCodes.Success));
        return res;
    }

    public static <T> ApiResponse<T> success() {
        var res = new ApiResponse<T>();
        res.setStatus(ResponseCodes.Success);
        res.setMessage(ResponseCodes.getMessage(ResponseCodes.Success));
        return res;
    }

    public static <T> ApiResponse<T> fail(String statusCode, String exceptionMsg, Object... args) {
        var res = new ApiResponse<T>();
        res.setStatus(statusCode);
        res.setMessage(ResponseCodes.getMessage(statusCode, args));
        res.setExceptionMsg(exceptionMsg);
        return res;
    }
    public static <T> ApiResponse<T> fail(String statusCode, Object... args) {
        var res = new ApiResponse<T>();
        res.setStatus(statusCode);
        res.setMessage(ResponseCodes.getMessage(statusCode, args));
        return res;
    }
}
