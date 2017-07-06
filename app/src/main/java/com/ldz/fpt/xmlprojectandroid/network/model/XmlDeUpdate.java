package com.ldz.fpt.xmlprojectandroid.network.model;

import android.util.Xml;

import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.util.Constant;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linhdq on 7/6/17.
 */

public class XmlDeUpdate {
    private List<DeModel> listDe;

    public XmlDeUpdate(List<DeModel> listDe) {
        this.listDe = listDe;
    }

    private String toXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "list_de");
            for (DeModel deModel : this.listDe) {
                serializer.startTag("", "de");
                serializer.startTag("", "username");
                serializer.text(deModel.getUsername());
                serializer.endTag("", "username");
                serializer.startTag("", "number");
                serializer.text(String.valueOf(deModel.getNumber()));
                serializer.endTag("", "number");
                serializer.startTag("", "price");
                serializer.text(String.valueOf(deModel.getPrice()));
                serializer.endTag("", "price");
                serializer.startTag("", "date");
                serializer.text(deModel.getDate());
                serializer.endTag("", "date");
                serializer.endTag("", "de");
            }
            serializer.endTag("", "list_de");
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
