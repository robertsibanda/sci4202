package com.robert.sci4202.comm;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class RPCRequests {
    public static ServerResult sendRequest(String method, Map params,
                                           String url) throws Exception {

        URL serverURL = new URL(url);

        JSONRPC2Response jsonrpc2Response;
        Session jsonrpc2Session = new Session(serverURL);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method,
                Collections.singletonList(params), 10);

        jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request);
        ServerResult serverResult = new ServerResult(jsonrpc2Response);
        return serverResult;

    }
}
