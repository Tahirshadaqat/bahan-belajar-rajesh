package BSICommonUtil.object;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.logging.Level;
import java.util.logging.Logger;
// --- <<IS-END-IMPORTS>> ---

public final class atomic

{
	// ---( internal utility methods )---

	final static atomic _instance = new atomic();

	static atomic _newInstance() { return new atomic(); }

	static atomic _cast(Object o) { return (atomic)o; }

	// ---( server methods )---




	public static final void objectToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(objectToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [i] field:0:required type
		// [o] field:0:required string
		IDataCursor idc = pipeline.getCursor();
		String string = "";
		Object object = IDataUtil.get(idc, "object");
		String type = IDataUtil.getString(idc,"type");
		if (object != null){
			if (object instanceof IData){
				if(type != null && type.equals("xml"))
					string = xmlDocToJsonString((IData)object);
				else
					string = jsonDocToJsonString((IData)object, "false");
			} else if(object instanceof String){
				string = (object.toString()).replace("\n", "").replace("\r", "");
			}
		}			
		IDataUtil.put(idc, "string", string);
		idc.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static Logger logger = Logger.getLogger(atomic.class.getName());
	public static String jsonDocToJsonString(IData inputDoc, String prettyPrint){
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
	
		// document
		IDataUtil.put( inputCursor, "document", inputDoc );
		IDataUtil.put( inputCursor, "prettyPrint", prettyPrint);
		inputCursor.destroy();
	
		// output
		IData 	output = IDataFactory.create();
		try {
			output = Service.doInvoke( "pub.json", "documentToJSONString", input );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.INFO, e.toString());
		} 
		IDataCursor outputCursor = output.getCursor();
		String payloadString = IDataUtil.getString( outputCursor, "jsonString" );
		outputCursor.destroy();
		return payloadString;
	}
	
	public static String xmlDocToJsonString(IData inputDoc){
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
	
		// document
		IDataUtil.put( inputCursor, "document", inputDoc );
		inputCursor.destroy();
	
		// output
		IData 	output = IDataFactory.create();
		try {
			output = Service.doInvoke( "pub.xml", "documentToXMLString", input );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.INFO, e.toString());
		} 
		IDataCursor outputCursor = output.getCursor();
		String payloadString = IDataUtil.getString( outputCursor, "xmldata" );
		outputCursor.destroy();
		return payloadString;
	}
	// --- <<IS-END-SHARED>> ---
}

