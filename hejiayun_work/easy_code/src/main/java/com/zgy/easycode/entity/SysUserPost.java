package com.zgy.easycode.entity;

import java.io.Serializable;

/**
 * 用户与岗位关联表(SysUserPost)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class SysUserPost implements Serializable {
    private static final long serialVersionUID = -97214290981670138L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 岗位ID
     */
    private Long postId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}

