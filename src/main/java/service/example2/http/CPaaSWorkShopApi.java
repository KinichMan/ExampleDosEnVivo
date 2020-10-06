/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.example2.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import service.example2.util.Constants_CPaaSWorkShopApi;

/**
 *
 * @author umansilla
 */
public class CPaaSWorkShopApi {

    public JSONObject obtenerUsuarioPorNumeroDeCuenta(String numeroDeCuenta) throws IOException {
        final String URI = "https://" + Constants_CPaaSWorkShopApi.FQDN
                + "/services/AAADEVCPaaSWorkShopAPI/ws/usuarios/" + numeroDeCuenta + "/numerodecuenta?cuenta="
                + Constants_CPaaSWorkShopApi.CUENTA;

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet getMethod = new HttpGet(URI);
        final HttpResponse response = client.execute(getMethod);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return new JSONObject(result.toString());
    }

    public JSONObject crearNuevoPinRandomAUsuarioPorNumeroDeCuenta(String numeroDeCuenta) throws IOException {
        final String URI = "https://" + Constants_CPaaSWorkShopApi.FQDN
                + "/services/AAADEVCPaaSWorkShopAPI/ws/usuarios/" + numeroDeCuenta + "/randomPIN";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut putMethod = new HttpPut(URI);

        putMethod.addHeader("Authorization", "Bearer " + Constants_CPaaSWorkShopApi.TOKEN);
        putMethod.addHeader("Content-Type", "application/json");
        final HttpResponse response = client.execute(putMethod);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return new JSONObject(result.toString());
    }

    public JSONObject validarElPinRandomPorNumeroDeCuenta(String numeroDeCuenta, String digitos) throws IOException {
        final String URI = "https://" + Constants_CPaaSWorkShopApi.FQDN
                + "/services/AAADEVCPaaSWorkShopAPI/ws/usuarios/" + numeroDeCuenta + "/randomPIN";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost postMethod = new HttpPost(URI);
        JSONObject jsonPayLoad = new JSONObject().put("pinRandom", digitos);
        postMethod.addHeader("Authorization", "Bearer " + Constants_CPaaSWorkShopApi.TOKEN);
        postMethod.addHeader("Content-Type", "application/json");
        final StringEntity payload = new StringEntity(jsonPayLoad.toString(), StandardCharsets.ISO_8859_1);
        postMethod.setEntity(payload);
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
