package com.bootcamp.be_java_hisp_w29_g07.Enum;

public enum OrderType {
    ASC("name_asc"),
    DESC("name_desc"),
    DATE_ASC("date_asc"),
    DATE_DESC("date_desc");

    private final String orderType;

    OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }
}
