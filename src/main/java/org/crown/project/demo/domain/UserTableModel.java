package org.crown.project.demo.domain;

import java.util.Date;

import org.crown.common.utils.DateUtils;
import org.crown.framework.web.domain.BaseEntity;

public class UserTableModel extends BaseEntity {

    /**
     * User ID
     */
    private int userId;

    /**
     * User code
     */
    private String userCode;

    /**
     * User name
     */
    private String userName;

    /**
     * User sex
     */
    private String userSex;

    /**
     * User phone number
     */
    private String userPhone;

    /**
     * User email address
     */
    private String userEmail;

    /**
     * User balance
     */
    private double userBalance;

    /**
     * User status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Time created
     */
    private Date createTime;

    public UserTableModel() {

    }

    public UserTableModel(int userId, String userCode, String userName, String userSex, String userPhone,
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