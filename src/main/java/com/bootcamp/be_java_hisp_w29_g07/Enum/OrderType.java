package com.bootcamp.be_java_hisp_w29_g07.Enum;

/**
 * The enum Order type.
 */
public enum OrderType {
    ASC("name_asc"),
    DESC("name_desc");

    private final String orderType;

    OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }
}
