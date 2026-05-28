package com.jesper.seckill.service;

import com.jesper.seckill.entity.User;
import com.jesper.seckill.vo.LoginVo;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    
    User getById(long id);
    
    boolean updatePassword(String token, long id, String formPass);
    
    String login(HttpServletResponse response, LoginVo loginVo);
    
    void addCookie(HttpServletResponse response, String token, User user);
    
    User getByToken(HttpServletResponse response, String token);
}
