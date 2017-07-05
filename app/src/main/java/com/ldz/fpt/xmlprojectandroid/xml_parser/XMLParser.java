package com.ldz.fpt.xmlprojectandroid.xml_parser;

import android.util.Log;

import com.ldz.fpt.xmlprojectandroid.model.BaCangModel;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.LoModel;
import com.ldz.fpt.xmlprojectandroid.model.LoXien2Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien3Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien4Model;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by linhdq on 7/4/17.
 */

public class XMLParser {
    //response of user model
    private static final String ITEM_USER = "user";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FULLNAME = "fullName";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String ROLE = "role";
    private static final String IS_SUCCESS = "success";
    //response of de model
    private static final String ITEM_DE = "de";
    private static final String NUMBER = "number";
    private static final String PRICE = "price";
    private static final String DATE = "date";
    //response of Ba cang model
    private static final String ITEM_BA_CANG = "ba_cang";
    //response of Lo model
    private static final String ITEM_LO = "lo";
    private static final String POINT = "point";
    //response of Lo xien 2 model
    private static final String ITEM_LO_XIEN_2 = "lo_xien_2";
    private static final String NUMBER_1 = "number1";
    private static final String NUMBER_2 = "number2";
    //response of Lo xien 3 model
    private static final String ITEM_LO_XIEN_3 = "lo_xien_3";
    private static final String NUMBER_3 = "number3";
    //response of Lo xien 4 model
    private static final String ITEM_LO_XIEN_4 = "lo_xien_4";
    private static final String NUMBER_4 = "number4";
    //response model
    private static final String ITEM_RESPONSE = "response";
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";

    private static XMLParser inst;

    public static XMLParser getInst() {
        if (inst == null) {
            inst = new XMLParser();
        }
        return inst;
    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public List<User> getAllAdmins(String xml) {
        List<User> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_USER);
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String username = getValue(e, USERNAME);
            String fullName = getValue(e, FULLNAME);
            String phone = getValue(e, PHONENUMBER);
            int role = -1;
            try {
                role = Integer.parseInt(getValue(e, ROLE));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            list.add(new User(username, "", fullName, phone, role, 0, false));
        }
        return list;
    }

    public User getUserModel(String xml) {
        User user = null;
        Document doc = getDomElement(xml);
        Element element = doc.getDocumentElement();
        if (element != null) {
            String username = getValue(element, USERNAME);
            String password = getValue(element, PASSWORD);
            String fullName = getValue(element, FULLNAME);
            String phoneNumber = getValue(element, PHONENUMBER);
            int role = -1;
            try {
                role = Integer.parseInt(getValue(element, ROLE));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            boolean success = getValue(element, IS_SUCCESS).equalsIgnoreCase("true") ? true : false;
            user = new User(username, password, fullName, phoneNumber, role, 1, success);
        }
        return user;
    }

    public List<DeModel> getListDeModel(String xml) {
        List<DeModel> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_DE);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number = Integer.parseInt(getValue(element, NUMBER));
            int price = Integer.parseInt(getValue(element, PRICE));
            String date = getValue(element, DATE);
            list.add(new DeModel(username, number, price, date));
        }

        return list;
    }

    public List<BaCangModel> getListBaCangModel(String xml) {
        List<BaCangModel> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_BA_CANG);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number = Integer.parseInt(getValue(element, NUMBER));
            int price = Integer.parseInt(getValue(element, PRICE));
            String date = getValue(element, DATE);
            list.add(new BaCangModel(username, number, price, date));
        }

        return list;
    }

    public List<LoModel> getListLoModel(String xml) {
        List<LoModel> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_LO);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number = Integer.parseInt(getValue(element, NUMBER));
            float point = Float.parseFloat(getValue(element, POINT));
            String date = getValue(element, DATE);
            list.add(new LoModel(username, number, point, date));
        }

        return list;
    }

    public List<LoXien2Model> getListLoXien2Model(String xml) {
        List<LoXien2Model> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_LO_XIEN_2);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number1 = Integer.parseInt(getValue(element, NUMBER_1));
            int number2 = Integer.parseInt(getValue(element, NUMBER_2));
            float point = Float.parseFloat(getValue(element, POINT));
            String date = getValue(element, DATE);
            list.add(new LoXien2Model(username, number1, number2, point, date));
        }

        return list;
    }

    public List<LoXien3Model> getListLoXien3Model(String xml) {
        List<LoXien3Model> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_LO_XIEN_3);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number1 = Integer.parseInt(getValue(element, NUMBER_1));
            int number2 = Integer.parseInt(getValue(element, NUMBER_2));
            int number3 = Integer.parseInt(getValue(element, NUMBER_3));
            float point = Float.parseFloat(getValue(element, POINT));
            String date = getValue(element, DATE);
            list.add(new LoXien3Model(username, number1, number2, number3, point, date));
        }

        return list;
    }

    public List<LoXien4Model> getListLoXien4Model(String xml) {
        List<LoXien4Model> list = new ArrayList<>();
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(ITEM_LO_XIEN_4);
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            String username = getValue(element, USERNAME);
            int number1 = Integer.parseInt(getValue(element, NUMBER_1));
            int number2 = Integer.parseInt(getValue(element, NUMBER_2));
            int number3 = Integer.parseInt(getValue(element, NUMBER_3));
            int number4 = Integer.parseInt(getValue(element, NUMBER_4));
            float point = Float.parseFloat(getValue(element, POINT));
            String date = getValue(element, DATE);
            list.add(new LoXien4Model(username, number1, number2, number3, number4, point, date));
        }

        return list;
    }

    public ResponseModel getResponseModel(String xml) {
        ResponseModel model = null;
        Document doc = getDomElement(xml);
        Element element = doc.getDocumentElement();
        if (element != null) {
            String message = getValue(element, MESSAGE);
            boolean status = getValue(element, STATUS).equalsIgnoreCase("true") ? true : false;
            model = new ResponseModel(message, status);
        }
        return model;
    }
}
