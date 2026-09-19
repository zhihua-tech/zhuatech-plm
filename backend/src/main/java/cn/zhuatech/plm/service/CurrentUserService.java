/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;
import cn.zhuatech.plm.common.BusinessException;import cn.zhuatech.plm.model.UserAccount;import cn.zhuatech.plm.repository.UserRepository;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.stereotype.Service;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class CurrentUserService {private final UserRepository users;/**
                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                              */
public CurrentUserService(UserRepository users){this.users=users;}/**
                                                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                */
public UserAccount get(){String username=SecurityContextHolder.getContext().getAuthentication().getName();return users.findByUsername(username).orElseThrow(()->new BusinessException("当前用户不存在"));}}
