package com.zgy.easycode.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 房间 (HjyRoom)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class HjyRoom implements Serializable {
    private static final long serialVersionUID = -40899990898334913L;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 楼栋id
     */
    private Long buildingId;
    /**
     * 单元id
     */
    private Long unitId;
    /**
     * 楼层
     */
    private Integer roomLevel;
    /**
     * 房间编号
     */
    private String roomCode;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房屋建筑面积
     */
    private Double roomAcreage;
    /**
     * 算费系数
     */
    private Double roomCost;
    /**
     * 房屋状态
     */
    private String roomStatus;
    /**
     * 是否商铺
     */
    private String roomIsShop;
    /**
     * 是否商品房
     */
    private String roomSCommercialHouse;
    /**
     * 房屋户型
     */
    private String roomHouseType;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Integer getRoomLevel() {
        return roomLevel;
    }

    public void setRoomLevel(Integer roomLevel) {
        this.roomLevel = roomLevel;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getRoomAcreage() {
        return roomAcreage;
    }

    public void setRoomAcreage(Double roomAcreage) {
        this.roomAcreage = roomAcreage;
    }

    public Double getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(Double roomCost) {
        this.roomCost = roomCost;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomIsShop() {
        return roomIsShop;
    }

    public void setRoomIsShop(String roomIsShop) {
        this.roomIsShop = roomIsShop;
    }

    public String getRoomSCommercialHouse() {
        return roomSCommercialHouse;
    }

    public void setRoomSCommercialHouse(String roomSCommercialHouse) {
        this.roomSCommercialHouse = roomSCommercialHouse;
    }

    public String getRoomHouseType() {
        return roomHouseType;
    }

    public void setRoomHouseType(String roomHouseType) {
        this.roomHouseType = roomHouseType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

