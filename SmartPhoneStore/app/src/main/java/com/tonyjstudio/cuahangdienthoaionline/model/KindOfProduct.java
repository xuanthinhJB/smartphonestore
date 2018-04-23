package com.tonyjstudio.cuahangdienthoaionline.model;

public class KindOfProduct {
    public int id;
    public String kindName;
    public String kindImage;

    public KindOfProduct() {
    }

    public KindOfProduct(int id, String kindName, String kindImage) {
        this.id = id;
        this.kindName = kindName;
        this.kindImage = kindImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindImage() {
        return kindImage;
    }

    public void setKindImage(String kindImage) {
        this.kindImage = kindImage;
    }
}
