package com.caioketo.gerenciadordeestudos.utils.authorization;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 03/11/2014.
 */
public class ParseComServerAuthenticate implements ServerAuthenticate{
	public static final String APP_ID = "ApmtYacAdlwGWlnryM0sKjUSZ39U7osMzoVBseRL";
	public static final String REST_API = "Md1ckfPYjp8DkfbZ07uayEdhtdPVEXkaSNitsNsz";

    @Override
    public com.caioketo.gerenciadordeestudos.utils.authorization.User userSignUp(String name, String email, String pass, String authType) throws Exception {

        String url = "https://api.parse.com/1/users";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders()) {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("Content-Type", "application/json");

        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        HttpEntity entity = new StringEntity(user);
        httpPost.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user[" + error.code + "] - " + error.error);
            }

            User createdUser = new Gson().fromJson(responseString, User.class);

            return createdUser;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public com.caioketo.gerenciadordeestudos.utils.authorization.User userSignIn(String user, String pass, String authType) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/login";

        String query = null;
        try {
            query = String.format("%s=%s&%s=%s", "username", URLEncoder.encode(user, "UTF-8"), "password", pass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += "?" + query;

        HttpGet httpGet = new HttpGet(url);

        for (Header header : getAppParseComHeaders()) {
            httpGet.addHeader(header);
        }

        HttpParams params = new BasicHttpParams();
        params.setParameter("username", user);
        params.setParameter("password", pass);
        httpGet.setParams(params);
//        httpGet.getParams().setParameter("username", user).setParameter("password", pass);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error signing-in [" + error.code + "] - " + error.error);
            }

            User loggedUser = new Gson().fromJson(responseString, User.class);
            return loggedUser;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static List<Header> getAppParseComHeaders() {
        List<Header> ret = new ArrayList<Header>();
        ret.add(new BasicHeader("X-Parse-Application-Id", APP_ID));
        ret.add(new BasicHeader("X-Parse-REST-API-Key", REST_API));
        return ret;
    }


    public class ParseComError implements Serializable {
        public int code;
        public String error;
    }
}