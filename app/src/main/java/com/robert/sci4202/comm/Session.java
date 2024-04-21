package com.robert.sci4202.comm;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionOptions;
import com.thetransactioncompany.jsonrpc2.client.RawResponseInspector;

import net.minidev.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import static com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session.createTrustAllSocketFactory;

public class Session {

    private URL url;
    private JSONRPC2SessionOptions options;
    private ConnectionConfigurator connectionConfigurator;
    private RawResponseInspector responseInspector;
    private CookieManager cookieManager;
    private static final SSLSocketFactory trustAllSocketFactory =
            createTrustAllSocketFactory();

    public Session(URL url) {
        if (!url.getProtocol().equalsIgnoreCase("http") && !url.getProtocol().equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("The URL protocol must be" +
                    " HTTP or HTTPS");
        } else {
            this.url = url;
            this.options = new JSONRPC2SessionOptions();
            this.connectionConfigurator = null;
        }
    }

    protected static RawResponse parse(HttpURLConnection connection) throws IOException {
        String encoding = connection.getContentEncoding();
        String line = null;
        System.out.println("line : " + line);
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

    private RawResponse readRawResponse(URLConnection con) throws JSONRPC2SessionException {
        RawResponse rawResponse;
        System.out.println("Reading raw response now ");
        try {
            rawResponse = RawResponse.parse((HttpURLConnection) con);
        } catch (IOException var6) {
            throw new JSONRPC2SessionException("Network exception: " + var6.getMessage(), 1, var6);
        }


        if (this.options.acceptCookies()) {
            if (this.cookieManager == null) {
                this.cookieManager =
                        new CookieManager((CookieStore) null,
                                CookiePolicy.ACCEPT_ALL);
            }

            try {
                this.cookieManager.put(con.getURL().toURI(),
                        rawResponse.getHeaderFields());
            } catch (URISyntaxException var4) {
                throw new JSONRPC2SessionException("Network exception: " + var4.getMessage(), 1, var4);
            } catch (IOException var5) {
                throw new JSONRPC2SessionException("I/O exception: " + var5.getMessage(), 1, var5);
            }
        }

        System.out.println("Returning response now");
        //System.out.println( "Raw response  : " + new JSONObject
        // (Integer.parseInt(rawResponse.getContent())));

        return rawResponse;
    }

    private URLConnection createURLConnection() throws JSONRPC2SessionException {
        URLConnection con;
        try {
            if (this.options.getProxy() != null) {
                con = this.url.openConnection(this.options.getProxy());
            } else {
                con = this.url.openConnection();
            }
        } catch (IOException var3) {
            throw new JSONRPC2SessionException("Network exception: " + var3.getMessage(), 1, var3);
        }

        con.setConnectTimeout(this.options.getConnectTimeout());
        con.setReadTimeout(this.options.getReadTimeout());
        this.applyHeaders(con);
        con.setDoOutput(true);
        if (con instanceof HttpsURLConnection && this.options.trustsAllCerts()) {
            if (trustAllSocketFactory == null) {
                closeStreams(con);
                throw new JSONRPC2SessionException("Couldn't obtain " +
                        "trust-all SSL socket factory");
            }

            ((HttpsURLConnection) con).setSSLSocketFactory(trustAllSocketFactory);
        }

        if (this.connectionConfigurator != null) {
            this.connectionConfigurator.configure((HttpURLConnection) con);
        }

        return con;
    }

    private void applyHeaders(URLConnection con) {
        con.setRequestProperty("Accept-Charset", "UTF-8");
        if (this.options.getRequestContentType() != null) {
            con.setRequestProperty("Content-Type",
                    this.options.getRequestContentType());
        }

        if (this.options.getOrigin() != null) {
            con.setRequestProperty("Origin", this.options.getOrigin());
        }

        if (this.options.enableCompression()) {
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
        }

        if (this.options.acceptCookies()) {
            StringBuilder buf = new StringBuilder();

            HttpCookie cookie;
            for (Iterator var3 = this.getCookies().iterator(); var3.hasNext(); buf.append(cookie.toString())) {
                cookie = (HttpCookie) var3.next();
                if (buf.length() > 0) {
                    buf.append("; ");
                }
            }

            con.setRequestProperty("Cookie", buf.toString());
        }

    }

    public List<HttpCookie> getCookies() {
        return this.cookieManager == null ? Collections.emptyList() :
                this.cookieManager.getCookieStore().getCookies();
    }


    public ConnectionConfigurator getConnectionConfigurator() {
        return this.connectionConfigurator;
    }

    public void setConnectionConfigurator(ConnectionConfigurator connectionConfigurator) {
        this.connectionConfigurator = connectionConfigurator;
    }

    public RawResponseInspector getRawResponseInspector() {
        return this.responseInspector;
    }

    public void setRawResponseInspector(RawResponseInspector responseInspector) {
        this.responseInspector = responseInspector;
    }

    private static void postString(URLConnection con, String data) throws JSONRPC2SessionException {
        try {
            OutputStreamWriter wr =
                    new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            wr.write(data);
            wr.flush();
            wr.close();
        } catch (IOException var3) {
            throw new JSONRPC2SessionException("Network exception: " + var3.getMessage(), 1, var3);
        }
    }

    private static void closeStreams(URLConnection con) {
        if (con != null) {
            try {
                if (con.getInputStream() != null) {
                    con.getInputStream().close();
                }
            } catch (Exception var4) {
            }

            try {
                if (con.getOutputStream() != null) {
                    con.getOutputStream().close();
                }
            } catch (Exception var3) {
            }

            try {
                HttpURLConnection httpCon = (HttpURLConnection) con;
                if (httpCon.getErrorStream() != null) {
                    httpCon.getErrorStream().close();
                }
            } catch (Exception var2) {
            }

        }
    }

    public JSONRPC2Response send(JSONRPC2Request request) throws JSONRPC2SessionException {
        URLConnection con = this.createURLConnection();

        RawResponse rawResponse;
        //System.out.println("Sending now");
        try {
            postString(con, request.toString());
            rawResponse = this.readRawResponse(con);
        } finally {
            closeStreams(con);
        }

        String contentType = rawResponse.getContentType();

        if (!this.options.isAllowedResponseContentType(contentType)) {
            String msg;
            if (contentType == null) {
                msg = "Missing Content-Type header in the HTTP response";
            } else {
                msg = "Unexpected \"" + contentType + "\" content type " +
                        "of the HTTP response";
            }

            throw new JSONRPC2SessionException(msg, 2);
        } else {
            JSONRPC2Response response;

            JSONObject jsonObject = new JSONObject();
            //jsonObject.put(rawResponse.getContent())
            response = new JSONRPC2Response(rawResponse.getContent(),
                    request.getID());


            Object reqID = request.getID();
            Object resID = response.getID();
            if (reqID != null && resID != null && reqID.toString().equals(resID.toString()) || reqID == null && resID == null || !response.indicatesSuccess() && (response.getError().getCode() == -32700 || response.getError().getCode() == -32600 || response.getError().getCode() == -32603)) {
                return response;
            } else {
                throw new JSONRPC2SessionException("Invalid JSON-RPC 2.0" +
                        " response: ID mismatch: Returned " + resID + "," +
                        " expected " + reqID, 3);
            }
        }
    }

    public void send(JSONRPC2Notification notification) throws JSONRPC2SessionException {
        URLConnection con = this.createURLConnection();

        try {
            postString(con, notification.toString());
        } finally {
            closeStreams(con);
        }

    }
}