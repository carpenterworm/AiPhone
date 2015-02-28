package net.onefree.aiphone.bean;

/**
 * Created by admin on 2015/1/5.
 */
public class Devices extends Bean {

    public static String T_NAME = "UserDevices";
    //厂商
    private String brand;
    //型号
    private String model;
    private String modelName;
    private String osVersion;
    private String devicesId;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(String devicesId) {
        this.devicesId = devicesId;
    }
}
