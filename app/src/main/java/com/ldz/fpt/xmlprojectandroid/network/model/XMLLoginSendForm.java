package com.ldz.fpt.xmlprojectandroid.network.model;

import android.util.Xml;

import com.ldz.fpt.xmlprojectandroid.util.Constant;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linhdq on 6/30/17.
 */

public class XMLLoginSendForm {
    private String username;
    private String password;

    public XMLLoginSendForm(String username, String password) {
        this.username = username;
        this.password = password;
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
