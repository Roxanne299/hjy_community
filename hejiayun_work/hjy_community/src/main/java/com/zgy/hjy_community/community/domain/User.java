package com.zgy.hjy_community.community.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author roxanne_waar
 * @date 2024/1/16 15:05
 * @description User
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    @NotNull
    private String username;
    private String password;
}
