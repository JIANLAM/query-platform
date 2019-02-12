package com.zt.queryplatform.entity;

import javax.persistence.*;

/**
 * 角色实体类
 * created by linzj on 2018/12/19
 **/

@Entity
@Table(name ="t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 角色代码
     */
    @Column(name = "role_code")
    private String roleCode;
    /**
     * 图书馆id
     */
    @Column(name = "lib_id")
    private Long libId;
    /**
     * 机构类别
     */
    @Column(name = "org_type")
    private String orgType;
    /**
     * 创建人id
     */
    @Column(name = "create_by")
    private Long createBy;
    /**
     * 创建人姓名
     */
    @Column(name = "create_name")
    private String createName;
    /**
     * 创建人账号
     */
    @Column(name = "create_account")
    private String createAccount;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 1启用，0禁用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 所属系统
     * @return
     */
    @Column(name = "belong_system")
    private String belongSystem;

    @Column(name = "lib_name")
    private String libName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getLibId() {
        return libId;
    }

    public void setLibId(Long libId) {
        this.libId = libId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}
