package com.ldz.fpt.xmlprojectandroid.network.model;

import android.util.Xml;

import com.ldz.fpt.xmlprojectandroid.util.Constant;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ZYuTernity on 06/07/2017.
 */

public class XmlCreateAccountForm {
    private String accountRequest;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;

    public XmlCreateAccountForm(String accountRequest, String username, String password, String fullName, String phoneNumber) {
        this.accountRequest = accountRequest;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "NewUser");
            serializer.startTag("", "requestByUser");
            serializer.text(this.accountRequest);
            serializer.endTag("", "requestByUser");
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
            serializer.endTag("", "NewUser");
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
