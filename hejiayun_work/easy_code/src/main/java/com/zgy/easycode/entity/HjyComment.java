package com.zgy.easycode.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 评论表(HjyComment)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class HjyComment implements Serializable {
    private static final long serialVersionUID = 870108739262397498L;
    /**
     * id
     */
    private Long commentId;
    /**
     * 创建人者
     */
    private String createBy;
    /**
     * 更新者ID
     */
    private Long updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 删除状态0默认1删除
     */
    private Integer delFlag;
    /**
     * 社区互动ID
     */
    private Long interactionId;
    /**
     * 创建人ID
     */
    private Long userId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 划属Id
     */
    private Long rootId;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(Long interactionId) {
        this.interactionId = interactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

}

