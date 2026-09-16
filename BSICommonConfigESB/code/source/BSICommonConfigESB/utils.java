package BSICommonConfigESB;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void decryptAES128 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(decryptAES128)>> ---
		// @sigtype java 3.5
		// [i] field:0:required encrypted
		// [o] field:0:required string
		IDataCursor idc = pipeline.getCursor();
		String encrypted = IDataUtil.getString(idc, "encrypted");
		
		try {
			SecretKeySpec sskey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, sskey);
			
			byte[] decryptedByte = c.doFinal(Hex.decodeHex(encrypted));
			String string = new String(decryptedByte);
			IDataUtil.put(idc, "string", string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void encryptAES128 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(encryptAES128)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string
		// [o] field:0:required encrypted
		IDataCursor idc = pipeline.getCursor();
		String string = IDataUtil.getString(idc, "string");
		
		try {
			SecretKeySpec sskey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, sskey);
			
			byte[] encryptedByte = c.doFinal(string.getBytes());
			String encrypted = Hex.encodeHexString(encryptedByte);
			IDataUtil.put(idc, "encrypted", encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}
}

