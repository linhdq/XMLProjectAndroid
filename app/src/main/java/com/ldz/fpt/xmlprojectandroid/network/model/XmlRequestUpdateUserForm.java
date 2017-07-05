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

public class XmlRequestUpdateUserForm {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;

    public XmlRequestUpdateUserForm() {
    }

    public XmlRequestUpdateUserForm(String username, String password, String fullName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "user");
            serializer.startTag("", "username");
            serializer.text(this.username);
            serializer.endTag("", "username");
            serializer.startTag("", "password");
            serializer.text(this.password);
            serializer.endTag("", "password");
            serializer.startTag("", "fullName");
            serializer.text(this.fullName);
            serializer.endTag("", "fullName");
            serializer.startTag("", "phoneNumber");
            serializer.text(this.phoneNumber);
            serializer.endTag("", "phoneNumber");
            serializer.endTag("", "user");
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
