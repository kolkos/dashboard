package nl.kolkos.dashboard.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.*;


import org.apache.commons.codec.binary.Base64;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.configurations.DomoticzConfiguration;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.SubDeviceType;

/**
 * This class is used to talk to domoticz
 * @author antonvanderkolk
 *
 */
@Service
public class DomoticzCommunicator {
	@Autowired
	private DomoticzConfiguration domoticzConfiguration;
	
	private String username;
	private String password;
	private String baseUrl;
	
	static {
	    disableSslVerification();
	}
	
	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}
	
	private void loadSettings() {
		// build the url
		String protocol = domoticzConfiguration.getProtocol();
		String host = domoticzConfiguration.getHost();
		String port = domoticzConfiguration.getPort();
		String page = "json.htm?";
		
		baseUrl = String.format("%s://%s:%s/%s", protocol, host, port, page);
		
		// finally get the username and password
		username = domoticzConfiguration.getUser();
		password = domoticzConfiguration.getPass();
		
	}
	
	public String getDataFromServer(String path) {
		this.loadSettings();
		StringBuilder sb = new StringBuilder();
		try {
            URL url = new URL(baseUrl + path);
            URLConnection urlConnection = setUsernamePassword(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
 
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public JSONArray createJSONArray(String response) throws JSONException {
		JSONArray jsonArray = new JSONArray(response);
		return jsonArray;
	}
	
	public JSONObject createJSONObject(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		return jsonObject;
	}
	
	private URLConnection setUsernamePassword(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = username + ":" + password;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return urlConnection;
    }
	
	
}
