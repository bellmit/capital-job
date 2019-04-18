package com.yixin.alixjob.service.mortageswitch.factory;

public enum MortageSwitchEnum {

    NewCarService("1","newCarMortageSwitch"),OldCarService("2","oldCarMortageSwitch"),ResourceCarService("3","resourceCarMortageSwitch");
    private String key;
    private String value;

    MortageSwitchEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (MortageSwitchEnum c : MortageSwitchEnum.values()) {
            if (c.getKey() == key) {
                return c.value;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
