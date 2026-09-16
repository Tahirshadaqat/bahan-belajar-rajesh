package BSIPaymentEmoneyImplementation;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void HMAC_SHA512 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(HMAC_SHA512)>> ---
		// @sigtype java 3.5
		// [i] field:0:required httpMethod
		// [i] field:0:required endpointUrl
		// [i] field:0:required accessToken
		// [i] field:0:required requestBody
		// [i] field:0:required timestamp
		// [i] field:0:required secretKey
		// [o] field:0:required generatedSignature
		//pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	httpMethod = IDataUtil.getString( pipelineCursor, "httpMethod" );
		String	endpointUrl = IDataUtil.getString( pipelineCursor, "endpointUrl" );
		String	accessToken = IDataUtil.getString( pipelineCursor, "accessToken" );
		String	requestBody = IDataUtil.getString( pipelineCursor, "requestBody" );
		String	timestamp = IDataUtil.getString( pipelineCursor, "timestamp" );
		String	secretKey = IDataUtil.getString( pipelineCursor, "secretKey" );
		pipelineCursor.destroy();
		
		//String hexString = DigestUtils.sha256Hex(requestBody.getBytes()).toLowerCase();
		String stringToSign = httpMethod+":"+endpointUrl+":"+accessToken+":"+requestBody+":"+timestamp;
		String generatedSignature ="";
		
		try{
			Mac mac = Mac.getInstance("HmacSHA512");
			SecretKeySpec secretKeySpec = new SecretKeySpec (secretKey.getBytes(StandardCharsets.UTF_8),"HmacSHA512");
			mac.init(secretKeySpec);
			byte[] macData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
		//			generatedSignature = new String (macData, StandardCharsets.UTF_8);
			BigInteger hash = new BigInteger(1, macData);
		    generatedSignature = hash.toString(16);
		    if ((generatedSignature.length() % 2) != 0) {
		    	generatedSignature = "0" + generatedSignature;
		    }
		}
		catch(Exception e){
			generatedSignature = e.getMessage();
		}
		
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "generatedSignature", generatedSignature );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void generateSignature (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateSignature)>> ---
		// @sigtype java 3.5
		// [i] field:0:required apiSecretKey
		// [i] field:0:required requestJsonString
		// [i] field:0:required clientSecret
		// [o] field:0:required jwtValue
		IDataCursor pipelineCursor = pipeline.getCursor();
		String apiSecretKey = IDataUtil.getString(pipelineCursor, "apiSecretKey");
		String requestJsonString = IDataUtil.getString(pipelineCursor, "requestJsonString");
		String clientSecret = IDataUtil.getString(pipelineCursor, "clientSecret");
		pipelineCursor.destroy();
		
		//		HmacUtils hm1 = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, clientSecret);
		//		
		//		String jwtValue = hm1.hmacHex(apiSecretKey.concat("|")
		//				.concat(requestJsonString));
		String jwtValue="";
		
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put(pipelineCursor_1, "jwtValue", jwtValue);
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void signatureBmri (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(signatureBmri)>> ---
		// @sigtype java 3.5
		// [i] field:0:required stringToSign
		// [i] field:0:required keystoreFile
		// [i] field:0:required keystorePassword
		// [i] field:0:required keyAlias
		// [i] field:0:required keyPassword
		// [i] field:0:required algorithm
		// [o] field:0:required signature
		IDataCursor pipelineCursor = pipeline.getCursor();
		String stringToSign = IDataUtil.getString( pipelineCursor, "stringToSign" );
		String keystoreFile = IDataUtil.getString( pipelineCursor, "keystoreFile" );
		String keystorePassword = IDataUtil.getString( pipelineCursor, "keystorePassword" );
		String keyAlias = IDataUtil.getString( pipelineCursor, "keyAlias" );
		String keyPassword = IDataUtil.getString( pipelineCursor, "keyPassword" );
		String algorithm = IDataUtil.getString( pipelineCursor, "algorithm" );
		
		pipelineCursor.destroy();
		
		
		String signature = sign(keystoreFile,keystorePassword,keyAlias,keyPassword,algorithm, stringToSign);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "signature", signature );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void signatureXbmri (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(signatureXbmri)>> ---
		// @sigtype java 3.5
		// [i] field:0:required endpointUrl
		// [i] field:0:required accessToken
		// [i] field:0:required requestBody
		// [i] field:0:required timestamp
		// [i] field:0:required clientSecret
		// [o] field:0:required signature
		// [o] field:0:required stringToSign
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	endpointUrl = IDataUtil.getString( pipelineCursor, "endpointUrl" );
			String	accessToken = IDataUtil.getString( pipelineCursor, "accessToken" );
			String	requestBody = IDataUtil.getString( pipelineCursor, "requestBody" );
			String	timestamp = IDataUtil.getString( pipelineCursor, "timestamp" );
			String	clientSecret = IDataUtil.getString( pipelineCursor, "clientSecret" );
		pipelineCursor.destroy();		
		
		String stringToSignBody = requestBody;
		
		String signatureBody = DigestUtils.sha256Hex(stringToSignBody);
		
		
		String stringToSign = "POST"+":"+endpointUrl.trim()+":"+accessToken.trim()+":"+signatureBody+":"+timestamp.trim();
		
		// Return Base64
		byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, clientSecret).hmac(stringToSign);
		String signature= Base64.getEncoder().encodeToString(hmac);
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "signature", signature );
		IDataUtil.put( pipelineCursor_1, "stringToSign", stringToSign );
		
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static FileInputStream fis = null;
	
	public static String sign(String keystore, String keystorePassword, String keyAlias, String keyPassword, String algorithm, String message) {
		
		String signature = null;
	//		String keystoreFile = "/home/userapi/src/bmri/bsi.jimport javax.crypto.Mac;
		
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType()); 
			 fis = new FileInputStream(keystore); 
			 ks.load(fis,keystorePassword.toCharArray()); 
			 KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry)ks.getEntry (keyAlias, new KeyStore.PasswordProtection(keyPassword.toCharArray ()));
			 RSAPrivateKey pvKey = (RSAPrivateKey)pkEntry.getPrivateKey(); 
			 Signature sign = Signature.getInstance(algorithm);
			 sign.initSign(pvKey);
			 byte[] byteMessage = message.getBytes();
			 sign.update(byteMessage, 0, byteMessage.length); 
			 byte[] byteSignature = sign.sign();
			 signature = new String(Base64.getEncoder().encode(byteSignature));
		} catch(Exception ex) {
			ex.printStackTrace(); 
		}
		
		return signature;
	}
	// --- <<IS-END-SHARED>> ---
}

