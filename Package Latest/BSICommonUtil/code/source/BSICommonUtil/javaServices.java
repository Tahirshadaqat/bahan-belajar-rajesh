package BSICommonUtil;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.ibm.icu.impl.duration.TimeUnit;
import com.wm.util.DebugOptions;
import com.softwareag.util.IDataArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
// --- <<IS-END-IMPORTS>> ---

public final class javaServices

{
	// ---( internal utility methods )---

	final static javaServices _instance = new javaServices();

	static javaServices _newInstance() { return new javaServices(); }

	static javaServices _cast(Object o) { return (javaServices)o; }

	// ---( server methods )---




	public static final void debugStatus (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(debugStatus)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [o] field:0:required debugStatus
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
		pipelineCursor.destroy();
		String debugStatus;
		Object debugInfo = Service.getDebug(serviceName);
		
		// Check if debugging is enabled
		if (debugInfo != null && debugInfo instanceof DebugOptions) {
		    DebugOptions debugOptions = (DebugOptions) debugInfo;
		
		    // Check the specific pipeline debug options
		    debugStatus = "Pipeline Debugging is enabled for " + serviceName;
		
		    // Check debug options
		    if (debugOptions.isSavePipeline()) {
		        debugStatus += "\nSave Pipeline option is enabled.";
		    }
		    if (debugOptions.isRestorePipelineOverride()) {
		        debugStatus += "\nRestore Pipeline (Override) option is enabled.";
		    }
		    if (debugOptions.isRestorePipelineMerge()) {
		        debugStatus += "\nRestore Pipeline (Merge) option is enabled.";
		    }
		} else {
		    debugStatus = "Pipeline Debugging is disabled for " + serviceName;
		}
		
		    // Print the debug status to the server log
		    System.out.println(debugStatus);
		
		    // Set the output variable
		    IDataCursor pipelineCursor2 = pipeline.getCursor();
		    pipelineCursor.insertAfter("debugStatus", debugStatus);
		    pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateSHA256 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateSHA256)>> ---
		// @sigtype java 3.5
		// [i] field:0:required stringToSign
		// [o] field:0:required value
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	stringToSign = IDataUtil.getString( pipelineCursor, "stringToSign" );
		pipelineCursor.destroy();
		String value = "";  
		try{
			value = DigestUtils.sha256Hex(stringToSign);
		} catch (Exception ex) {
		}
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "value", value );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateSHA256_1 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateSHA256_1)>> ---
		// @sigtype java 3.5
		// [i] field:0:required channelId
		// [i] field:0:required partnerReff
		// [i] field:0:required sercretKey
		// [o] field:0:required value
		// [o] field:0:required stringToSign
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	channelId = IDataUtil.getString( pipelineCursor, "channelId" );
		String	partnerReff = IDataUtil.getString( pipelineCursor, "partnerReff" );
		String	sercretKey = IDataUtil.getString( pipelineCursor, "sercretKey" );
		pipelineCursor.destroy();
		String stringToSign = partnerReff+":"+channelId+sercretKey;
		String value = "";  
		try{
			value = DigestUtils.sha256Hex(stringToSign);
		//			HmacUtils hm1 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey);
		//			value = hm1.hmacHex(stringToSign.getBytes());
		} catch (Exception ex) {
		}
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "value", value );
		IDataUtil.put( pipelineCursor_1, "stringToSign", stringToSign );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateSHA256_incomingNgva (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateSHA256_incomingNgva)>> ---
		// @sigtype java 3.5
		// [i] field:0:required channelId
		// [i] field:0:required apiKey
		// [o] field:0:required value
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	channelId = IDataUtil.getString( pipelineCursor, "channelId" );
			String	apiKey = IDataUtil.getString( pipelineCursor, "apiKey" );
		pipelineCursor.destroy();
		String stringToSign = channelId+apiKey;
		String value = "";  
		try{
			value = DigestUtils.sha256Hex(stringToSign);
		} catch (Exception ex) {
			value = ex.getMessage().toString();
		}
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "value", value );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void getCurrentTimeMillis (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCurrentTimeMillis)>> ---
		// @sigtype java 3.5
		// [o] object:0:required millis
		ValuesEmulator.put(pipeline, "millis", System.currentTimeMillis());
		// --- <<IS-END>> ---

                
	}



	public static final void isContains (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isContains)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string
		// [i] field:0:required stringToCheck
		// [o] field:0:required isContains
		IDataCursor idc = pipeline.getCursor();
		String string = IDataUtil.getString(idc, "string");
		String stringToCheck = IDataUtil.getString(idc, "stringToCheck");
		
		boolean contains = false;
		if (string != null)
			contains = string.contains(stringToCheck);
		
		IDataUtil.put(idc, "isContains", String.valueOf(contains));
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void objectOrListToList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(objectOrListToList)>> ---
		// @sigtype java 3.5
		// [i] record:0:required objectToCheck
		// [i] field:0:required listName
		// [o] record:1:required list
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String	listName = IDataUtil.getString( pipeline.getCursor(), "listName" );
		pipelineCursor.destroy();
		
		
		IData objectToCheck = (IData) ValuesEmulator.get(pipeline, "objectToCheck");
		Object object = ValuesEmulator.get(objectToCheck, listName);
		
		IData[] list = null;
		
		if (object instanceof IData) {
			// single object
			list = new IData[1];
			list[0] = (IData) object;
		}
		else if (object instanceof IData[]) {
			list = (IData[]) object;
		}
		
		ValuesEmulator.put(pipeline, "list", list);
			
		// --- <<IS-END>> ---

                
	}



	public static final void testThrowException (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(testThrowException)>> ---
		// @sigtype java 3.5
		throw new ServiceException("Exception message");
		// --- <<IS-END>> ---

                
	}



	public static final void unpadleft (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(unpadleft)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inString
		// [i] field:0:required unpadString
		// [o] field:0:required value
		IDataCursor pipelineCursor = pipeline.getCursor();
		String inString = IDataUtil.getString( pipelineCursor, "inString" );
		String unpadString = IDataUtil.getString( pipelineCursor, "unpadString");
		pipelineCursor.destroy();
		 
		String value = unPadLeft(inString, unpadString.charAt(0));
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "value", value );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static String unPadLeft(String s, char c) {
		int fill = 0;
		int end = s.length();
		if (end == 0) {
			return s;
		}
		while ((fill < end) && (s.charAt(fill) == c)) {
			fill++;
		}
		return fill < end ? s.substring(fill, end) : s.substring(fill - 1, end);
	}
	// --- <<IS-END-SHARED>> ---
}

