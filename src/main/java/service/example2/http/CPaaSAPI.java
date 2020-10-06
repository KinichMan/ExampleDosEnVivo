/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.example2.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import service.example2.util.Constants_CPaaSApi;

/**
 *
 * @author umansilla
 */
public class CPaaSAPI {

    public JSONObject getCarrierLookUpPorTelefono(String telefono) throws IOException {
        final String URI = "https://api.zang.io/v2/Accounts/" + Constants_CPaaSApi.ACCOUNT_SID + "/Lookups/Carrier.json";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost postMethod = new HttpPost(URI);

        postMethod.addHeader("Authorization", Constants_CPaaSApi.CPAAS_TOKEN_AUTH_BASIC);
        postMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("PhoneNumber", telefono));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.ISO_8859_1);

        postMethod.setEntity(entity);
        final HttpResponse response = client.execute(postMethod);

        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return new JSONObject(result.toString());
    }

    public JSONObject sendSMSPinRandom(String pinRandom, String to) throws IOException {
        final String URI = "https://api.zang.io/v2/Accounts/" + Constants_CPaaSApi.ACCOUNT_SID + "/SMS/Messages.json";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost postMethod = new HttpPost(URI);

        postMethod.addHeader("Authorization", Constants_CPaaSApi.CPAAS_TOKEN_AUTH_BASIC);
        postMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("From", Constants_CPaaSApi.FROM_SEND_SMS_NUMBER));
        formparams.add(new BasicNameValuePair("To", to));
        formparams.add(new BasicNameValuePair("Body", pinRandom));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.ISO_8859_1);

        postMethod.setEntity(entity);
        final HttpResponse response = client.execute(postMethod);

        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return new JSONObject(result.toString());
    }

}
