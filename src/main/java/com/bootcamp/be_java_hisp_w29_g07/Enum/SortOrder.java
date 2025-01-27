package com.bootcamp.be_java_hisp_w29_g07.Enum;

public enum SortOrder {
    DATE_ASC("date_asc"),
    DATE_DESC("date_desc");

    private final String sortOrder;

    SortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }
}
