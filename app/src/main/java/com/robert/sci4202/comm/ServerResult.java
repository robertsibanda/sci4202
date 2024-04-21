package com.robert.sci4202.comm;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerResult {

    private final JSONRPC2Response result;

    public ServerResult(JSONRPC2Response result) {
        this.result = result;
    }

    public JSONObject getResult() throws JSONException {
        System.out.println("response : " + result.getResult());

        JSONObject jsonObject = new JSONObject(result.toString());
        JSONObject object = new JSONObject(jsonObject.getString("result"));
        return object.getJSONObject("result");
    }
}
