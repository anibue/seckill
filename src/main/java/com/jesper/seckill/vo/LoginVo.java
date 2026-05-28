package com.jesper.seckill.vo;

import com.jesper.seckill.annotation.IsMobile;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    
    @NotBlank(message = "手机号不能为空")
    @IsMobile
    private String mobile;
    
    @NotBlank(message = "密码不能为空")
    private String password;
}
