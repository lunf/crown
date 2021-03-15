package org.crown.common.enums;

/**
 * user status
 *
 * @author Crown
 */
public enum UserStatus {
    OK("0", "normal"), DISABLE("1", "deactivated");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
