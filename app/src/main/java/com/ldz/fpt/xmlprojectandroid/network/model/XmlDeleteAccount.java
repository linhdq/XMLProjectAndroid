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

public class XmlDeleteAccount {
    private String userNameRequest;
    private String userNameDelete;

    public XmlDeleteAccount(String userNameRequest, String userNameDelete) {
        this.userNameRequest = userNameRequest;
        this.userNameDelete = userNameDelete;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "delete_request");
            serializer.startTag("", "username_request");
            serializer.text(this.userNameRequest);
            serializer.endTag("", "username_request");
            serializer.startTag("", "username_delete");
            serializer.text(this.userNameDelete);
            serializer.endTag("", "username_delete");
            serializer.endTag("", "delete_request");
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
