package com.example.falling.leyi.internet.Analysis;

import com.example.falling.leyi.dao.EverydayInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by falling on 2015/11/16.
 */
public class Sentence extends DefaultHandler {
    public EverydayInfo Sen = null;
    private String tagName = null;
    private String content=null;
    private String dateline=null;
    private String note=null;
    private String translation=null;
    private String picture=null;

    public Sentence(){
        Sen = new EverydayInfo();
    }
    public EverydayInfo getSen(){
        return Sen;
    }



    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        if (length <= 0)
            return;
        for (int i = start; i < start + length; i++)
        {
            if (ch[i] == '\n')
                return;
        }

        //去除莫名其妙的换行！

        String str = new String(ch, start, length);
        if (tagName .equals( "content"))
        {
            Sen.setContent(str);
        }
        else if (tagName == "note")
        {
           Sen.setNote(str);
        }
        else if (tagName == "translation")
        {
           Sen.setTranslation(str);
        }
        else if (tagName == "picture2")
        {
          Sen.setPicture(str);
        }
        else if (tagName == "dateline")
        {
            Sen.setDateline(str);
        }

    }


    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
        tagName = null;


    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException
    {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        tagName = localName;
    }

    @Override
    public void endDocument() throws SAXException
    {
        // TODO Auto-generated method stub
        super.endDocument();

    }
}
