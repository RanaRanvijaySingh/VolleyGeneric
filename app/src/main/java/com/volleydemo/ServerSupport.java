package com.volleydemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.Map;

public class ServerSupport {

    private final String TAG = "Server Support";
    public static ServerSupport mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private ServerSupport(){
    }

    public static synchronized ServerSupport getInstance(Context context){
        if (mInstance == null){
            mInstance = new ServerSupport();
        }
        mContext = context;
        return mInstance;
    }

    public void makeApiCall(final VolleyRequest volleyRequest,final ApiCallListener listener) {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        final ProgressDialog progressDialog = getProgressDialogFrom(volleyRequest);
        int requestType = volleyRequest.getRequestType();
        String url = volleyRequest.getUrl();
        Log.d(TAG, url);
        if (isConnected) {
            if (progressDialog != null) {
                progressDialog.show();
            }
            StringRequest jsonObjReq = new StringRequest(requestType, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    onResponseReceived(response, listener, progressDialog, volleyRequest.getResponseClass());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                    onErrorReceived(error, progressDialog);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = volleyRequest.getParams();
                    if (params != null && params.size() > 0) {
                        return params;
                    }
                    Log.d(TAG, String.valueOf(params));
                    return super.getParams();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = volleyRequest.getHeader();
                    if (header != null && header.size() > 0) {
                        return header;
                    }
                    Log.d(TAG, String.valueOf(header));
                    return super.getHeaders();
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    byte[] body = volleyRequest.getBody();
                    if (body != null && body.length > 0) {
                        return body;
                    }
                    Log.d(TAG, String.valueOf(body));
                    return super.getBody();
                }
            };
            addToRequestQueue(jsonObjReq, volleyRequest.getTag());
        }else {
            Toast.makeText(mContext,"No connection!g Please check your network.",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function is called on getting response from server.
     * @param response String
     * @param listener ApiCallListener
     * @param progressDialog ProgressDialog
     * @param responseClass Class<?>
     */
    private void onResponseReceived(String response, ApiCallListener listener, ProgressDialog progressDialog, Class<?> responseClass) {
        try {
            Object responseObject = new Gson().fromJson(response, responseClass);
            listener.onSuccess(response, responseObject);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }catch (JsonParseException e){
            /** This exception will only happen if the Gson is unable to parse response. */
            progressDialog.dismiss();
            String serverErrorMessage = "Incorrect server response.";
            e.printStackTrace();
            Toast.makeText(mContext,serverErrorMessage,Toast.LENGTH_SHORT).show();
            listener.onError(serverErrorMessage);
        }
    }

    /**
     * Function is called on getting error from server.
     * @param error VolleyError
     * @param progressDialog ProgressDialog
     */
    private void onErrorReceived(VolleyError error,ProgressDialog progressDialog) {
        Toast.makeText(mContext,"Server not responding",Toast.LENGTH_SHORT).show();
        VolleyLog.d(TAG, "Error: " + error.getMessage());
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * Function to create progress dialog.
     * @param volleyRequest VolleyRequest class
     * @return ProgressDialog
     */
    private ProgressDialog getProgressDialogFrom(VolleyRequest volleyRequest) {
        ProgressDialog progressDialog = null;
        try{
            if (volleyRequest.showPregressDialog()) {
                progressDialog = new ProgressDialog(mContext);
                if (volleyRequest.getPdMessage() != null){
                    progressDialog.setMessage(volleyRequest.getPdMessage());
                }
                progressDialog.setCancelable(volleyRequest.isPdIsCancelable());
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return progressDialog;
    }

    /**
     * Function to get request queue
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null && mContext != null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /**
     * Function to add to request queue
     * @param req Request<>
     * @param tag String
     */
    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Function to cancel request
     * @param tag Object
     */
    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
