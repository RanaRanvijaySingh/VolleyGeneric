package com.volleydemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

	private final String TAG = "MainActivity";
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView)findViewById(R.id.textView);
	}

	public void onClickObjectResponseButton(View view) {
        String tag = "json_obj_req";
        String url = "http://api.androidhive.info/volley/person_object.json";
        ServerSupport serverSupport = ServerSupport.getInstance(this);
        VolleyRequest volleyRequest = new VolleyRequest(Method.GET,url,tag, UserModel.class);
		volleyRequest.setUpProgressDialog(0, "Loading ... ", false);
        serverSupport.makeApiCall(volleyRequest, new ApiCallListener() {
            @Override
            public void onSuccess(String response, Object responseObject) {
                UserModel userModel = (UserModel)responseObject;
                mTextView.setText(userModel.getName());
            }

            @Override
            public void onError(String message) {
				mTextView.setText(message);
            }
        });
    }

	public void onClickArrayResponseButton(View view) {
		String url = "http://www.earrn.com/api/users/featured";
		String tag = "json_array_req";
		ServerSupport serverSupport = ServerSupport.getInstance(this);
		VolleyRequest volleyRequest = new VolleyRequest(Method.GET,url,tag, new ArrayList<UserListModel>().getClass());
		volleyRequest.setUpProgressDialog(0,"Loading ... ",false);
		serverSupport.makeApiCall(volleyRequest, new ApiCallListener() {
			@Override
			public void onSuccess(String response, Object responseObject) {
				ArrayList<UserListModel> listModels = (ArrayList<UserListModel>) responseObject;
				mTextView.setText(""+listModels.size());
			}

			@Override
			public void onError(String message) {
				mTextView.setText(message);
			}
		});
	}

	public void onClickStringResponseButton(View view) {
		String  tag = "string_req";
		String url = "http://api.androidhive.info/volley/string_response.html";
		ServerSupport serverSupport = ServerSupport.getInstance(this);
		VolleyRequest volleyRequest = new VolleyRequest(Method.GET,url,tag, String.class);
		volleyRequest.setUpProgressDialog(0,"Loading ... ",false);
		serverSupport.makeApiCall(volleyRequest, new ApiCallListener() {
			@Override
			public void onSuccess(String response, Object responseObject) {
				String userModel = (String) responseObject;
				mTextView.setText(userModel);
			}

			@Override
			public void onError(String message) {
				mTextView.setText(message);
			}
		});
	}

	public void onClickPostRequest(View view) {
		String  tag = "post_req";
		String url = "http://testing.microsave.net/apis/library_search.json";
		ServerSupport serverSupport = ServerSupport.getInstance(this);
		VolleyRequest volleyRequest = new VolleyRequest(Method.POST,url,tag, UserModelPost.class);
		volleyRequest.setParams(getParams());
		volleyRequest.setUpProgressDialog(0,"Loading ... ",false);
		serverSupport.makeApiCall(volleyRequest, new ApiCallListener() {
			@Override
			public void onSuccess(String response, Object responseObject) {
				UserModelPost userModel = (UserModelPost) responseObject;
				mTextView.setText(userModel.getResponse().getMessage());
			}

			@Override
			public void onError(String message) {
				mTextView.setText(message);
			}
		});
	}

	private Map<String, String> getParams() {
		Map<String,String> params = new HashMap<String, String>();
		params.put("page","0");
		params.put("display_tab","3");
		params.put("device_type","android");
		params.put("topic","Digital Financial Services");
		params.put("search_type","normal");
		return params;
	}
}
