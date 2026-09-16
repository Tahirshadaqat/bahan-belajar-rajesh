package BSICommonUtil;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class http

{
	// ---( internal utility methods )---

	final static http _instance = new http();

	static http _newInstance() { return new http(); }

	static http _cast(Object o) { return (http)o; }

	// ---( server methods )---




	public static final void httpWithProxy (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(httpWithProxy)>> ---
		// @sigtype java 3.5
		// [i] field:0:required url
		// [i] field:0:required method
		// [i] field:0:required timeout
		// [i] record:0:required httpHeaders
		// [i] field:0:required bodyRequest
		// [i] field:0:required proxyAddress
		// [i] field:0:required proxyPort
		// [i] field:0:required proxyUsername
		// [i] field:0:required proxyPassword
		// [o] record:0:required header
		// [o] - record:0:required lines
		// [o] -- field:0:required Accept
		// [o] -- field:0:required Content-Type
		// [o] -- field:0:required Content-Length
		// [o] - object:0:required status
		// [o] - field:0:required statusMessage
		// [o] - field:0:required statusLine
		// [o] record:0:required body
		// [o] - field:0:required string
		IDataCursor cursor = pipeline.getCursor();
		CloseableHttpClient client = null; 
		    
		try {
			String url = IDataUtil.getString(cursor, "url");
			//debugLog("[URL]: " + url);
			String method = IDataUtil.getString(cursor, "method");
			IData headers = IDataUtil.getIData(cursor, "httpHeaders");
			String bodyRequest = IDataUtil.getString(cursor, "bodyRequest");
			String sTO = IDataUtil.getString(cursor, "timeout"); // get the timeout variable
			int timeout = 0;
			if (sTO != null) {
				timeout = Integer.parseInt(sTO);
			} 
		 
			client = doGetHttpClient(cursor);
			
			HttpRequestBase request = new HttpPost();
			if (method.equalsIgnoreCase("post")) {
				request = constructPostRequest(url, headers, bodyRequest);
			}
			else if (method.equalsIgnoreCase("get")) {
				request = constructGetRequest(url, headers);
			}
			
			if (timeout > 0) {
				RequestConfig.Builder requestConfig = RequestConfig.custom();
				requestConfig.setConnectTimeout(timeout);
				requestConfig.setConnectionRequestTimeout(timeout);
				requestConfig.setSocketTimeout(timeout);
				request.setConfig(requestConfig.build());
			}
		
			HttpHost host = getHttpHost(url);
			CloseableHttpResponse response = client.execute(host, request);
			
			try {
		    	HttpEntity entity = response.getEntity();
		    	IData dHeader = createHeader(response);
		
		    	String sEntity = EntityUtils.toString(entity);
		    	IDataUtil.put(cursor, "header", dHeader);
		    	IDataUtil.put(cursor, "body", IDataFactory.create(new Object[][]{
		    		new Object[]{"string", sEntity}
		    	}));
			} finally {
				try {
					response.close(); // close silently
					client.close();
				} catch (Exception ignore) {}
			}
		} catch (IOException e) {
			debugLog("[httpClientWithProxy] " + e.getMessage());
			e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new ServiceException(e);
		}
		
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void httpWithProxyBypassSSL (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(httpWithProxyBypassSSL)>> ---
		// @sigtype java 3.5
		// [i] field:0:required url
		// [i] field:0:required method
		// [i] field:0:required timeout
		// [i] record:0:required httpHeaders
		// [i] field:0:required bodyRequest
		// [i] field:0:required proxyAddress
		// [i] field:0:required proxyPort
		// [i] field:0:required proxyUsername
		// [i] field:0:required proxyPassword
		// [o] record:0:required header
		// [o] - record:0:required lines
		// [o] -- field:0:required Accept
		// [o] -- field:0:required Content-Type
		// [o] -- field:0:required Content-Length
		// [o] - object:0:required status
		// [o] - field:0:required statusMessage
		// [o] - field:0:required statusLine
		// [o] record:0:required body
		// [o] - field:0:required string
		//  --- <<IS-START(httpClientWithProxyBypassSSL)>> ---
				// @sigtype java 3.5 
				// [i] field:0:required url
				// [i] field:0:required method {"get","post"}
				// [i] field:0:required timeout
				// [i] record:0:optional httpHeaders
				// [i] field:0:required bodyRequest
				// [i] field:0:required proxyAddress
				// [i] field:0:required proxyPort
				// [i] field:0:optional proxyUsername
				// [i] field:0:optional proxyPassword
				// [o] record:0:required header
				// [o] - record:0:required lines
				// [o] -- field:0:required Accept
				// [o] -- field:0:required Content-Type
				// [o] -- field:0:required Content-Length
				// [o] - object:0:required status
				// [o] - field:0:required statusMessage
				// [o] - field:0:required statusLine
				// [o] record:0:required body
				// [o] - field:0:required string
				IDataCursor cursor = pipeline.getCursor();
				CloseableHttpClient client = null;
				 
				try {
					String url = IDataUtil.getString(cursor, "url");
					//debugLog("[URL]: " + url);
					String method = IDataUtil.getString(cursor, "method");
					IData headers = IDataUtil.getIData(cursor, "httpHeaders");
					String bodyRequest = IDataUtil.getString(cursor, "bodyRequest");
					String sTO = IDataUtil.getString(cursor, "timeout"); // get the timeout variable
					int timeout = 0;
					if (sTO != null) {
						timeout = Integer.parseInt(sTO);
					}
				
					//SINGLETON_SHARED_CLIENT_BYPASS_SSL = doGetHttpClientBypassSSL(cursor);
					client = doGetHttpClientBypassSSL(cursor);
					
					HttpRequestBase request = new HttpPost();
					if (method.equalsIgnoreCase("post")) {
						request = constructPostRequest(url, headers, bodyRequest);
					}
					else if (method.equalsIgnoreCase("get")) {
						request = constructGetRequest(url, headers);
					}
					
					if (timeout > 0) {
						RequestConfig.Builder requestConfig = RequestConfig.custom();
						requestConfig.setConnectTimeout(timeout);
						requestConfig.setConnectionRequestTimeout(timeout);
						requestConfig.setSocketTimeout(timeout);
						request.setConfig(requestConfig.build());
					}
				
					HttpHost host = getHttpHost(url);
					//CloseableHttpResponse response = SINGLETON_SHARED_CLIENT_BYPASS_SSL.execute(HOST, request);
					CloseableHttpResponse response = client.execute(host, request);
					//HOST = null;
					
					try {
				    	HttpEntity entity = response.getEntity();
				    	IData dHeader = createHeader(response);
				
				    	String sEntity = EntityUtils.toString(entity);
				    	IDataUtil.put(cursor, "header", dHeader);
				    	IDataUtil.put(cursor, "body", IDataFactory.create(new Object[][]{
				    		new Object[]{"string", sEntity}
				    	}));
					} finally {
						try {
							response.close(); // close silently
							client.close();
						} catch (Exception ignore) {}
					}
				} catch (IOException e) {
					debugLog("[httpClientWithProxy] " + e.getMessage());
					e.printStackTrace();
					try {
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					throw new ServiceException(e.getMessage());
				}
				
				cursor.destroy();
				// --- <<IS-END>> ---
		
		                
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static final Object LOCK = new Object();
	private static volatile CloseableHttpClient SINGLETON_SHARED_CLIENT;
	private static volatile CloseableHttpClient SINGLETON_SHARED_CLIENT_BYPASS_SSL;
	private static volatile int DEFAULT_TIMEOUT = 10 * 1000; // 10 seconds
	private static HttpHost HOST = null;
	
	private static HttpHost getHttpHost(String url) throws MalformedURLException {
		URL u = new URL(url);
		String address = u.getHost();
		int port = u.getPort() == -1 ? u.getDefaultPort() : u.getPort();
		String protocol = u.getProtocol();
		HttpHost host = new HttpHost(address, port, protocol);
		return host;
	}
	
	public static HttpRequestBase constructPostRequest(String url, IData headers, String body) {
		HttpPost httpPost = new HttpPost(url);
		// set all header
		IDataCursor cHeader = headers.getCursor();
		while (cHeader.next()) {
			httpPost.addHeader(cHeader.getKey(), (String) cHeader.getValue());	
		}
		cHeader.destroy();
		try {
			HttpEntity entity = new StringEntity(body);
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			debugLog(e.getMessage());
		}
		return httpPost;
	}
	
	public static HttpRequestBase constructGetRequest(String url, IData headers) {
		HttpGet httpGet = new HttpGet(url);
	
		// set all header
		IDataCursor cHeader = headers.getCursor();
		while (cHeader.next()) {
			httpGet.addHeader(cHeader.getKey(), (String) cHeader.getValue());
		}
		cHeader.destroy();
	
		return httpGet;
	}
	
	public static IData createHeader(HttpResponse response) {
		Header[] allHeaders = response.getAllHeaders();
		IData dLines = IDataFactory.create();
		IDataCursor cLines = dLines.getCursor();
		for (Header header : allHeaders) {
			IDataUtil.put(cLines, header.getName(), header.getValue());
		}
		cLines.destroy();
		StatusLine statusLine = response.getStatusLine();
		IData dHeader = IDataFactory.create(new Object[][] { 
				new Object[] { "lines", dLines },
				new Object[] { "status", statusLine.getStatusCode() },
				new Object[] { "statusMessage", statusLine.getReasonPhrase() },
				new Object[] { "statusLine", response.getStatusLine().toString() } });
		return dHeader;
	}
	
	protected static CloseableHttpClient getHttpClient(IDataCursor cursor) throws ServiceException {
		CloseableHttpClient client = SINGLETON_SHARED_CLIENT;
		if (client == null) {
			synchronized (LOCK) {
				if (SINGLETON_SHARED_CLIENT == null) {
					SINGLETON_SHARED_CLIENT = client = doGetHttpClient(cursor);
				}
			}
		}
		return client;
	}
	
	
	
	protected static CloseableHttpClient getHttpClientBypassSSL(IDataCursor cursor) throws ServiceException {
		CloseableHttpClient client = SINGLETON_SHARED_CLIENT_BYPASS_SSL;
		if (client == null) {
			synchronized (LOCK) {
				if (SINGLETON_SHARED_CLIENT_BYPASS_SSL == null) {
					SINGLETON_SHARED_CLIENT_BYPASS_SSL = client = doGetHttpClientBypassSSL(cursor);
				}
			}
		}
		return client;
	}
	
	private static CloseableHttpClient doGetHttpClient(IDataCursor cursor) {
		try {
			return internalGetHttpClient(cursor);
		} catch (ServiceException e) {
			ServerAPI.logError(e);
			return null;
		}
	}
	
	private static CloseableHttpClient doGetHttpClientBypassSSL(IDataCursor cursor) {
		try {
			return internalGetHttpClientBypassSSL(cursor);
		} catch (ServiceException e) {
			ServerAPI.logError(e);
			return null;
		} catch (KeyManagementException e) {
			ServerAPI.logError(e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			ServerAPI.logError(e);
			return null;
		} catch (KeyStoreException e) {
			ServerAPI.logError(e);
			return null;
		}
	}
	
	protected static CloseableHttpClient internalGetHttpClient(IDataCursor cursor) throws ServiceException {
		HttpClientBuilder clientBuilder = HttpClients.custom();
		int timeout = DEFAULT_TIMEOUT;
		if (timeout > 0) {
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
			clientBuilder.setDefaultRequestConfig(config);
		}
	
		ConnectionKeepAliveStrategy keepAliveStrategy = getConnectionKeepAliveStrategy();
		setupProxy(cursor, clientBuilder);
		CloseableHttpClient httpClient = clientBuilder.setKeepAliveStrategy(keepAliveStrategy).build();
		System.out.println(cursor);
		System.out.println(clientBuilder);
		debugLog("[Cursor]: "+ cursor + " , " + "[clientBuilter]: " + clientBuilder);
		debugLog("[httpClient]:"+httpClient);
		return httpClient;
	}
	
	protected static CloseableHttpClient internalGetHttpClientBypassSSL(IDataCursor cursor) throws ServiceException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		HttpClientBuilder clientBuilder = HttpClients.custom();
		int timeout = DEFAULT_TIMEOUT;
		if (timeout > 0) {
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
			clientBuilder.setDefaultRequestConfig(config);
		}
	
		ConnectionKeepAliveStrategy keepAliveStrategy = getConnectionKeepAliveStrategy();
		setupProxy(cursor, clientBuilder);
		CloseableHttpClient httpClient = clientBuilder.setKeepAliveStrategy(keepAliveStrategy)
				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.build();
		return httpClient;
	}
	
	/**
	 * Setup proxy
	 * 
	 * @param 	IDataCursor
	 *          HttpClientBuilder
	 */
	protected static void setupProxy(IDataCursor cursor, HttpClientBuilder httpClientBuilder) {
		String sProxyAddress = IDataUtil.getString(cursor, "proxyAddress");
		//String strUsr = IDataUtil.getString(cursor, "proxyUsername");
		//String strPas = IDataUtil.getString(cursor, "proxyPassword");
		sProxyAddress = sProxyAddress.trim();
		
		debugLog("[Proxy Address]: " + sProxyAddress);
		if (sProxyAddress != null && !("").equals(sProxyAddress) && !("0").equals(sProxyAddress)) {
			int proxyPort = IDataUtil.getInt(cursor, "proxyPort", 80);
			HttpHost proxy = new HttpHost(sProxyAddress, proxyPort);
			debugLog("[ProxyAddress]: "+ sProxyAddress + " , " + "[proxyPort]: " + proxyPort);
			
			/**Uncomment jika proxy menggunakan user/pass */
			/*if (strUsr != null && strPas != null) {
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(strUsr, strPas));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
			}*/
			
			httpClientBuilder.setProxy(proxy);
		} else {
			httpClientBuilder.setProxy(null);
		}
	
		cursor.destroy();
	}
	
	private static ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
		ConnectionKeepAliveStrategy strategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return (long) 5 * 1000;
			}
		};
		return strategy;
	}
	
	public static void debugLog(Object text) {
	    JournalLogger.log(JournalLogger.INFO, JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, text);
	}
	// --- <<IS-END-SHARED>> ---
}

