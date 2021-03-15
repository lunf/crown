package org.crown.common.enums;

public enum OnlineStatus {
    /**
     * user status
     */
    on_line("Online"), off_line("Offline");
    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}