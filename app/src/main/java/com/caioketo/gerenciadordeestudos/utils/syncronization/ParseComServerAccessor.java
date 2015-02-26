package com.caioketo.gerenciadordeestudos.utils.syncronization;

import android.util.Log;

import com.caioketo.gerenciadordeestudos.classes.Aula;
import com.caioketo.gerenciadordeestudos.classes.Base;
import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.classes.Prova;
import com.caioketo.gerenciadordeestudos.utils.authorization.ParseComServerAuthenticate;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 06/11/2014.
 */
import static com.caioketo.gerenciadordeestudos.utils.authorization.ParseComServerAuthenticate.getAppParseComHeaders;

public class ParseComServerAccessor {
    public static final int MATERIA_TYPE = 0;
    public static final int AULA_TYPE = 1;
    public static final int PROVA_TYPE = 2;

    public String getUrl(int type) {
        switch (type) {
            case MATERIA_TYPE:
                return "https://api.parse.com/1/classes/materia";
            case AULA_TYPE:
                return "https://api.parse.com/1/classes/aula";
            case PROVA_TYPE:
                return "https://api.parse.com/1/classes/prova";
            default:
                return "";
        }
    }

    public <T extends Base> List<T> parse(int type, String responseString) {
        switch (type) {
            case MATERIA_TYPE:
                return (List<T>)(new Gson().fromJson(responseString, Materias.class)).results;
            case AULA_TYPE:
                return (List<T>)(new Gson().fromJson(responseString, Aulas.class)).results;
            case PROVA_TYPE:
                return (List<T>)(new Gson().fromJson(responseString, Provas.class)).results;
            default:
                return new ArrayList<T>();
        }
    }

    public <T extends Base> List<T> get(String auth, int type) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = getUrl(type);//"https://api.parse.com/1/classes/aula";

        HttpGet httpGet = new HttpGet(url);
        for (Header header : getAppParseComHeaders()) {
            httpGet.addHeader(header);
        }
        httpGet.addHeader("X-Parse-Session-Token", auth); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires

        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error retrieving ["+error.code+"] - " + error.error);
            }

            return parse(type, responseString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<T>();
    }


    public <T extends Base> void put(String authtoken, String userId, T toAdd, int type) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = getUrl(type);

        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders()) {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("X-Parse-Session-Token", authtoken); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires
        httpPost.addHeader("Content-Type", "application/json");

        JSONObject sendObj = toAdd.toJson();//getJSON(type, toAdd);

        // Creating ACL JSON object for the current user
        JSONObject acl = new JSONObject();
        JSONObject aclEveryone = new JSONObject();
        JSONObject aclMe = new JSONObject();
        aclMe.put("read", true);
        aclMe.put("write", true);
        acl.put(userId, aclMe);
        acl.put("*", aclEveryone);
        sendObj.put("ACL", acl);

        String request = sendObj.toString();
        httpPost.setEntity(new StringEntity(request, "UTF-8"));

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error posting tv shows [" + error.code + "] - " + error.error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Prova> getProvas(String auth) throws Exception {
        return get(auth, PROVA_TYPE);
    }

    public void putProva(String authtoken, String userId, Prova provaToAdd) throws Exception {
        put(authtoken, userId, provaToAdd, PROVA_TYPE);
    }

    public List<Aula> getAulas(String auth) throws Exception {
        return get(auth, AULA_TYPE);
        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/classes/aula";

        HttpGet httpGet = new HttpGet(url);
        for (Header header : getAppParseComHeaders()) {
            httpGet.addHeader(header);
        }
        httpGet.addHeader("X-Parse-Session-Token", auth); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires

        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error retrieving tv shows ["+error.code+"] - " + error.error);
            }

            Aulas aulas = new Gson().fromJson(responseString, Aulas.class);
            return aulas.results;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Aula>();*/
    }

    public void putAula(String authtoken, String userId, Aula aulaToAdd) throws Exception {
        put(authtoken, userId, aulaToAdd, AULA_TYPE);
        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/classes/aula";

        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders()) {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("X-Parse-Session-Token", authtoken); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires
        httpPost.addHeader("Content-Type", "application/json");

        JSONObject aula = new JSONObject();
        aula.put("materia_id", aulaToAdd.getMateria().getId());
        aula.put("duracao", aulaToAdd.getDuracao());
        aula.put("diaDaSemana", aulaToAdd.getDiaDaSemana());
        aula.put("horaInicio", aulaToAdd.getHoraInicio());
        aula.put("minutoInicio", aulaToAdd.getMinutoInicio());

        // Creating ACL JSON object for the current user
        JSONObject acl = new JSONObject();
        JSONObject aclEveryone = new JSONObject();
        JSONObject aclMe = new JSONObject();
        aclMe.put("read", true);
        aclMe.put("write", true);
        acl.put(userId, aclMe);
        acl.put("*", aclEveryone);
        aula.put("ACL", acl);

        String request = aula.toString();
        httpPost.setEntity(new StringEntity(request,"UTF-8"));

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error posting tv shows [" + error.code + "] - " + error.error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }




    public List<Materia> getMaterias(String auth) throws Exception {
        return get(auth, MATERIA_TYPE);
        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/classes/materia";

        HttpGet httpGet = new HttpGet(url);
        for (Header header : getAppParseComHeaders()) {
            httpGet.addHeader(header);
        }
        httpGet.addHeader("X-Parse-Session-Token", auth); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires

        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error retrieving tv shows ["+error.code+"] - " + error.error);
            }

            Materias materias = new Gson().fromJson(responseString, Materias.class);
            return materias.results;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Materia>();*/
    }

    public void putMateria(String authtoken, String userId, Materia materiaToAdd) throws Exception {
        put(authtoken, userId, materiaToAdd, MATERIA_TYPE);
        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/classes/materia";

        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders()) {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("X-Parse-Session-Token", authtoken); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires
        httpPost.addHeader("Content-Type", "application/json");

        JSONObject materia = new JSONObject();
        materia.put("descricao", materiaToAdd.getDescricao());
        materia.put("calculoMedia", materiaToAdd.getCalculoMedia());
        materia.put("cor", materiaToAdd.getCor());

        // Creating ACL JSON object for the current user
        JSONObject acl = new JSONObject();
        JSONObject aclEveryone = new JSONObject();
        JSONObject aclMe = new JSONObject();
        aclMe.put("read", true);
        aclMe.put("write", true);
        acl.put(userId, aclMe);
        acl.put("*", aclEveryone);
        materia.put("ACL", acl);

        String request = materia.toString();
        httpPost.setEntity(new StringEntity(request,"UTF-8"));

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
                throw new Exception("Error posting tv shows [" + error.code + "] - " + error.error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }




    private class Materias implements Serializable {
        List<Materia> results;
    }

    private class Aulas implements Serializable {
        List<Aula> results;
    }

    private class Provas implements Serializable {
        List<Prova> results;
    }
}
