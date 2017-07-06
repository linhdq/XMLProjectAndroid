package com.ldz.fpt.xmlprojectandroid.model;

import android.util.Xml;

import com.ldz.fpt.xmlprojectandroid.util.Constant;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linhdq on 7/6/17.
 */

public class Price {
    private String username;
    private int dePrice;
    private int baCangPrice;
    private int loNhanPrice;
    private int loTraPrice;
    private int loXien2NhanPrice;
    private int loXien2TraPrice;
    private int loXien3NhanPrice;
    private int loXien3TraPrice;
    private int loXien4NhanPrice;
    private int loXien4TraPrice;

    public Price() {
    }

    public Price(int dePrice, int baCangPrice, int loNhanPrice, int loTraPrice, int loXien2NhanPrice, int loXien2TraPrice, int loXien3NhanPrice, int loXien3TraPrice, int loXien4NhanPrice, int loXien4TraPrice) {
        this.dePrice = dePrice;
        this.baCangPrice = baCangPrice;
        this.loNhanPrice = loNhanPrice;
        this.loTraPrice = loTraPrice;
        this.loXien2NhanPrice = loXien2NhanPrice;
        this.loXien2TraPrice = loXien2TraPrice;
        this.loXien3NhanPrice = loXien3NhanPrice;
        this.loXien3TraPrice = loXien3TraPrice;
        this.loXien4NhanPrice = loXien4NhanPrice;
        this.loXien4TraPrice = loXien4TraPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDePrice() {
        return dePrice;
    }

    public void setDePrice(int dePrice) {
        this.dePrice = dePrice;
    }

    public int getBaCangPrice() {
        return baCangPrice;
    }

    public void setBaCangPrice(int baCangPrice) {
        this.baCangPrice = baCangPrice;
    }

    public int getLoNhanPrice() {
        return loNhanPrice;
    }

    public void setLoNhanPrice(int loNhanPrice) {
        this.loNhanPrice = loNhanPrice;
    }

    public int getLoTraPrice() {
        return loTraPrice;
    }

    public void setLoTraPrice(int loTraPrice) {
        this.loTraPrice = loTraPrice;
    }

    public int getLoXien2NhanPrice() {
        return loXien2NhanPrice;
    }

    public void setLoXien2NhanPrice(int loXien2NhanPrice) {
        this.loXien2NhanPrice = loXien2NhanPrice;
    }

    public int getLoXien2TraPrice() {
        return loXien2TraPrice;
    }

    public void setLoXien2TraPrice(int loXien2TraPrice) {
        this.loXien2TraPrice = loXien2TraPrice;
    }

    public int getLoXien3NhanPrice() {
        return loXien3NhanPrice;
    }

    public void setLoXien3NhanPrice(int loXien3NhanPrice) {
        this.loXien3NhanPrice = loXien3NhanPrice;
    }

    public int getLoXien3TraPrice() {
        return loXien3TraPrice;
    }

    public void setLoXien3TraPrice(int loXien3TraPrice) {
        this.loXien3TraPrice = loXien3TraPrice;
    }

    public int getLoXien4NhanPrice() {
        return loXien4NhanPrice;
    }

    public void setLoXien4NhanPrice(int loXien4NhanPrice) {
        this.loXien4NhanPrice = loXien4NhanPrice;
    }

    public int getLoXien4TraPrice() {
        return loXien4TraPrice;
    }

    public void setLoXien4TraPrice(int loXien4TraPrice) {
        this.loXien4TraPrice = loXien4TraPrice;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "price");
            serializer.startTag("", "username");
            serializer.text(this.username);
            serializer.endTag("", "username");
            serializer.startTag("", "de_price");
            serializer.text(String.valueOf(this.dePrice));
            serializer.endTag("", "de_price");

            serializer.startTag("", "ba_cang_price");
            serializer.text(String.valueOf(this.baCangPrice));
            serializer.endTag("", "ba_cang_price");

            serializer.startTag("", "lo_nhan_price");
            serializer.text(String.valueOf(this.loNhanPrice));
            serializer.endTag("", "lo_nhan_price");

            serializer.startTag("", "lo_tra_price");
            serializer.text(String.valueOf(this.loTraPrice));
            serializer.endTag("", "lo_tra_price");

            serializer.startTag("", "lo_xien_2_nhan_price");
            serializer.text(String.valueOf(this.loXien2NhanPrice));
            serializer.endTag("", "lo_xien_2_nhan_price");

            serializer.startTag("", "lo_xien_2_tra_price");
            serializer.text(String.valueOf(this.loXien2TraPrice));
            serializer.endTag("", "lo_xien_2_tra_price");

            serializer.startTag("", "lo_xien_3_nhan_price");
            serializer.text(String.valueOf(this.loXien3NhanPrice));
            serializer.endTag("", "lo_xien_3_nhan_price");

            serializer.startTag("", "lo_xien_3_tra_price");
            serializer.text(String.valueOf(this.loXien3TraPrice));
            serializer.endTag("", "lo_xien_3_tra_price");

            serializer.startTag("", "lo_xien_4_nhan_price");
            serializer.text(String.valueOf(this.loXien4NhanPrice));
            serializer.endTag("", "lo_xien_4_nhan_price");

            serializer.startTag("", "lo_xien_4_tra_price");
            serializer.text(String.valueOf(this.loXien4TraPrice));
            serializer.endTag("", "lo_xien_4_tra_price");

            serializer.endTag("", "price");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RequestBody getRequestBody() {
        RequestBody data = RequestBody.create(MediaType.parse(Constant.APPLICATION_XML), toXml());
        return data;
    }
}
