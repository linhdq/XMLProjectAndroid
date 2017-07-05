package com.ldz.fpt.xmlprojectandroid.network.model;

import android.util.Xml;

import com.ldz.fpt.xmlprojectandroid.util.Constant;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linhdq on 7/4/17.
 */

public class XmlRequestForm {
    private String username;
    private String date;

    public XmlRequestForm() {
    }

    public XmlRequestForm(String username, String date) {
        this.username = username;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "requestData");
            serializer.startTag("", "username");
            serializer.text(this.username);
            serializer.endTag("", "username");
            serializer.startTag("", "date");
            serializer.text(this.date);
            serializer.endTag("", "date");
            serializer.endTag("", "requestData");
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
