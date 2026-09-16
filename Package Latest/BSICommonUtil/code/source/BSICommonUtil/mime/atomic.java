package BSICommonUtil.mime;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Arrays;
import java.util.HashMap;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class atomic

{
	// ---( internal utility methods )---

	final static atomic _instance = new atomic();

	static atomic _newInstance() { return new atomic(); }

	static atomic _cast(Object o) { return (atomic)o; }

	// ---( server methods )---




	public static final void constructWmDocument (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(constructWmDocument)>> ---
		// @sigtype java 3.5
		// [i] field:1:required keyString
		// [i] field:1:required valueString
		// [o] record:0:required document
		IDataMap pl=new IDataMap(pipeline);
		IData docContent=new IDataFactory().create();		
		IDataMap docContentMap=new IDataMap(docContent);
		  
		String[] keyString=pl.getAsStringArray("keyString");
		String[] valueString=pl.getAsStringArray("valueString");
		
		for(int i=0;i<keyString.length;i++){
			docContentMap.put(keyString[i], valueString[i]);
		}
		 				
		pl.put("documentContent", docContent);
						
				
		// --- <<IS-END>> ---

                
	}
}

