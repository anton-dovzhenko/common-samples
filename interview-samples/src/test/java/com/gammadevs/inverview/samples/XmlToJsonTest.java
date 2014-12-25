package com.gammadevs.inverview.samples;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class XmlToJsonTest {

    @Test
    public void testConvertNestedNodes() throws Exception {
        XmlToJson converter = new XmlToJson();
        String json = converter.convert(new ByteArrayInputStream("<node1><node2></node2></node1>".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("{\"node2\": {}}", json);
    }

    @Test
    public void testConvertAttributes() throws Exception {
        XmlToJson converter = new XmlToJson();
        String json = converter.convert(new ByteArrayInputStream("<node1><node2 attr1=\"1\" attr2=\"2\"></node2></node1>".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("{\"node2\": {\"attr1\": \"1\",\"attr2\": \"2\",}}", json);
    }

    @Test
    public void testConvertInnerText() throws Exception {
        XmlToJson converter = new XmlToJson();
        String json = converter.convert(new ByteArrayInputStream("<node1><node2>innerText</node2></node1>".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("{\"node2\": {_text: \"innerText\"}}", json);
    }

    @Test
    public void testConvertInnerTextAndAttributes() throws Exception {
        XmlToJson converter = new XmlToJson();
        String json = converter.convert(new ByteArrayInputStream("<node1><node2 attr1=\"1\" attr2=\"2\">innerText</node2></node1>".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("{\"node2\": {\"attr1\": \"1\",\"attr2\": \"2\",_text: \"innerText\"}}", json);
    }

    @Test
    public void testConvertNestedNodesAndInnerTextAndAttributes() throws Exception {
        XmlToJson converter = new XmlToJson();
        String json = converter.convert(new ByteArrayInputStream(("<node1><node2 attr1=\"1\" attr2=\"2\"><node3 attr1=\"nested1\" attr2=\"nested2\">" +
                "innerText</node3></node2></node1>").getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("{\"node2\": {\"attr1\": \"1\",\"attr2\": \"2\",\"node3\": {\"attr1\": \"nested1\",\"attr2\": \"nested2\",_text: \"innerText\"}}}", json);
    }

}