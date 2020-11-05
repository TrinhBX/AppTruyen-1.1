package com.example.apptruyen.truyentranh.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TruyenTranh implements Serializable {
    private String tenTruyen, tenChap, linkAnh, id;

    /*
    {
    "tenTruyen":"",
    "tenChap":"",
    "linkAnh":""
    }
    */
    public TruyenTranh(){

    }
    public TruyenTranh(JSONObject o) throws JSONException {
        id = o.getString("idTruyen");
        tenTruyen = o.getString("tenTruyen");
        tenChap = o.getString("tenChap");
        linkAnh = o.getString("linkAnh");
    }
    public TruyenTranh(String tenTruyen, String tenChap, String linkAnh) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        this.linkAnh = linkAnh;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
