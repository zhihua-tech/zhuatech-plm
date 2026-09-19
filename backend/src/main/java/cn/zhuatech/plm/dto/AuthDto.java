/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.dto;
import cn.zhuatech.plm.model.UserAccount;import jakarta.validation.constraints.NotBlank;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public final class AuthDto {/**
                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                             */
private AuthDto(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record LoginRequest(@NotBlank(message="请输入账号") String username,@NotBlank(message="请输入密码") String password){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record UserView(String username,String displayName,String role){/**
                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                            */
public static UserView from(UserAccount u){return new UserView(u.getUsername(),u.getDisplayName(),u.getRole().name());}}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record LoginResponse(String token,UserView user){}
}
