package net.onefree.aiphone.bean;

import java.io.Serializable;

/**
 * Created by admin on 2015/1/5.
 */
public class Bean implements Serializable {
    private String objectId;
    private String createAt;
    private String updateAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
