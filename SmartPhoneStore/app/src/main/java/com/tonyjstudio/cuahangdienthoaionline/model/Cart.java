package com.tonyjstudio.cuahangdienthoaionline.model;

public class Cart  {
    public int idSP;
    public String nameSP;
    public long priceSP;
    public String imageSP;
    public int slSP;

    public Cart() {
    }

    public Cart(int idSP, String nameSP, long priceSP, String imageSP, int slSP) {
        this.idSP = idSP;
        this.nameSP = nameSP;
        this.priceSP = priceSP;
        this.imageSP = imageSP;
        this.slSP = slSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getNameSP() {
        return nameSP;
    }

    public void setNameSP(String nameSP) {
        this.nameSP = nameSP;
    }

    public long getPriceSP() {
        return priceSP;
    }

    public void setPriceSP(long priceSP) {
        this.priceSP = priceSP;
    }

    public String getImageSP() {
        return imageSP;
    }

    public void setImageSP(String imageSP) {
        this.imageSP = imageSP;
    }

    public int getSlSP() {
        return slSP;
    }

    public void setSlSP(int slSP) {
        this.slSP = slSP;
    }
}
