package com.robert.sci4202.comm;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class RawResponse {
    public Map<String, List<String>> headers;
    public int statusCode;
    public String statusMessage;
    public int contentLength;
    public String contentType;
    public String contentEncoding;
    public String content;

    public RawResponse() {
    }

    protected static RawResponse parse(HttpURLConnection connection) throws IOException {
        String encoding = connection.getContentEncoding();

        Object is;
        try {
            if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                is = new GZIPInputStream(connection.getInputStream());
            } else if (encoding != null && encoding.equalsIgnoreCase(
                    "deflate")) {
                is = new InflaterInputStream(connection.getInputStream()
                        , new Inflater(true));
            } else {
                is = connection.getInputStream();
            }
        } catch (IOException var7) {
            if (connection.getErrorStream() == null) {
                throw var7;
            }

            is = connection.getErrorStream();
        }

        StringBuilder responseText = new StringBuilder();
        BufferedReader input =
                new BufferedReader(new InputStreamReader((InputStream) is, "UTF-8"));

        String line;
        while ((line = input.readLine()) != null) {
            responseText.append(line);
            responseText.append(System.getProperty("line.separator"));
        }

        input.close();
        RawResponse response = new RawResponse();
        response.content = responseText.toString();
        response.statusCode = connection.getResponseCode();
        response.statusMessage = connection.getResponseMessage();
        response.headers = connection.getHeaderFields();
        response.contentLength = connection.getContentLength();
        response.contentType = connection.getContentType();
        response.contentEncoding = encoding;
        return response;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public String getContent() {
        return this.content;
    }

    public Map<String, List<String>> getHeaderFields() {
        return this.headers;
    }

    public String getHeaderField(String name) {
        List<String> values = (List) this.headers.get(name);
        return values != null && values.size() > 0 ?
                (String) values.get(0) : null;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }
}
