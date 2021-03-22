package org.crown.project.demo.domain;

public class UserFormModel {

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
     * User phone number
     */
    private String userPhone;

    public UserFormModel() {

    }

    public UserFormModel(int userId, String userCode, String userName, String userPhone) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPhone = userPhone;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
