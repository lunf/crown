package org.crown.project.demo.domain;

import java.util.Date;

import org.crown.common.annotation.Excel;
import org.crown.common.annotation.Excel.Type;
import org.crown.common.utils.DateUtils;
import org.crown.framework.web.domain.BaseEntity;

public class UserOperateModel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private int userId;

    @Excel(name = "user ID")
    private String userCode;

    @Excel(name = "user name")
    private String userName;

    @Excel(name = "user sex", readConverterExp = "0=male, 1=female, 2=unknown")
    private String userSex;

    @Excel(name = "user phone number")
    private String userPhone;

    @Excel(name = "user email address")
    private String userEmail;

    @Excel(name = "user balance")
    private double userBalance;

    @Excel(name = "user status", readConverterExp = "0=normal, 1=disabled")
    private String status;

    @Excel(name = "time created", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date createTime;

    public UserOperateModel() {

    }

    public UserOperateModel(int userId, String userCode, String userName, String userSex, String userPhone,
                            String userEmail, double userBalance, String status) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtils.getNowDate();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}