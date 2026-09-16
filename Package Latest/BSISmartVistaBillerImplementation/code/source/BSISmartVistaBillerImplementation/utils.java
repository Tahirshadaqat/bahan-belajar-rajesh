package BSISmartVistaBillerImplementation;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void customNumericFormat (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(customNumericFormat)>> ---
		// @sigtype java 3.5
		// [i] field:0:required num1
		// [o] field:0:required result
		IDataCursor idc = pipeline.getCursor();
		String num1 = IDataUtil.getString(idc, "num1");
		
		double a = Double.parseDouble(num1);
		
		
		String d = String.format("%,d.%02d", a);
		
		String result = String.valueOf(d);
		
		IDataUtil.put(idc, "result", result);
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void customNumericFormat_1 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(customNumericFormat_1)>> ---
		// @sigtype java 3.5
		// [i] field:0:required num1
		// [o] field:0:required result
		IDataCursor idc = pipeline.getCursor();
		String num1 = IDataUtil.getString(idc, "num1");
		String first = "";
		String last2 = "";
		String output = "";
		
		first = num1.substring(0, num1.length() - 2);
		last2 = num1.substring(num1.length() - 2);
		Double a = Double.parseDouble(first);
		NumberFormat formatter = new DecimalFormat("#,###"); 
		String b = formatter.format(a);
		output = "Rp. " + b + "." + last2;
		
		IDataUtil.put(idc, "result", output);
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}
}

