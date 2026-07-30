/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.common;
import java.time.OffsetDateTime;
public record ApiResponse<T>(boolean success,String message,T data,OffsetDateTime timestamp){
    public static <T> ApiResponse<T> ok(T data){return new ApiResponse<>(true,"success",data,OffsetDateTime.now());}
    public static <T> ApiResponse<T> ok(String message,T data){return new ApiResponse<>(true,message,data,OffsetDateTime.now());}
    public static ApiResponse<Void> fail(String message){return new ApiResponse<>(false,message,null,OffsetDateTime.now());}
}
