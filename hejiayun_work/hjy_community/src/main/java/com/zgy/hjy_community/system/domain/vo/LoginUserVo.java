package com.zgy.hjy_community.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * @author roxanne_waar
 * @date 2024/2/5 20:51
 * @description LoginUserVo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserVo implements Serializable {
    @Transient
    private static final long serialVersionUID = -8146829933809938728L;
    String username;
    String password;
    String code;
    String uuid;
}
