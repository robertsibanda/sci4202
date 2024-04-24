package com.robert.sci4202.comm;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.net.URL;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;

public class RPCRequests {

    public static String url = "http://10.42.0.1:5005";

    public static ServerResult sendRequest(String method, Map params
    ) throws Exception {

        URL serverURL = new URL(url);

        SecureRandom secureRandom = new SecureRandom();

        JSONRPC2Response jsonrpc2Response;
        Session jsonrpc2Session = new Session(serverURL);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method,
                Collections.singletonList(params), secureRandom.nextInt());

        jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request);
        ServerResult serverResult = new ServerResult(jsonrpc2Response);
        return serverResult;

    }
}
