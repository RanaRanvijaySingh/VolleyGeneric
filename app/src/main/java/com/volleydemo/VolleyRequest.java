package com.volleydemo;


import java.util.Map;

public class VolleyRequest {
    private boolean showProgressDialog = false;
    private int requestType;
    private String url;
    private String tag;
    private Map<String,String> params;
    private byte[] body;
    private Map<String,String> header;
    private Class<?> responseClass;
    private int pdCustomViewId = 0;
    private String pdMessage;
    private boolean pdIsCancelable = true;

    /**
     * Constructor to initialize class
     * @param requestType int Method.GET or Method.POST
     * @param url String
     * @param tag String
     * @param responseClass Class<?>
     */
    public VolleyRequest(int requestType,String url,String tag,Class<?> responseClass){
        this.requestType = requestType;
        this.url = url;
        this.tag = tag;
        this.responseClass = responseClass;
    }

    /**
     * Function to setup progress dialog based on given params.
     * @param customViewId int custom view id (if present) else -1
     * @param message String message to be displayed
     * @param isCancelable boolean true or false.
     */
    public void setUpProgressDialog(int customViewId, String message, boolean isCancelable) {
        this.pdMessage = message;
        this.pdCustomViewId = customViewId;
        this.pdIsCancelable = isCancelable;
        this.showProgressDialog = true;
    }

    public int getPdCustomViewId() {
        return pdCustomViewId;
    }

    public void setPdCustomViewId(int pdCustomViewId) {
        this.pdCustomViewId = pdCustomViewId;
    }

    public String getPdMessage() {
        return pdMessage;
    }

    public void setPdMessage(String pdMessage) {
        this.pdMessage = pdMessage;
    }

    public boolean isPdIsCancelable() {
        return pdIsCancelable;
    }

    public void setPdIsCancelable(boolean pdIsCancelable) {
        this.pdIsCancelable = pdIsCancelable;
    }

    public Class<?> getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class<?> responseClass) {
        this.responseClass = responseClass;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Map<String,String> getHeader() {
        return header;
    }

    public void setHeader(Map<String,String> header) {
        this.header = header;
    }

    public boolean showPregressDialog() {
        return this.showProgressDialog;
    }
}