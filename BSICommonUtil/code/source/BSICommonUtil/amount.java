package BSICommonUtil;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.math.RoundingMode;
import java.text.DecimalFormat;
import com.ibm.icu.math.BigDecimal;
// --- <<IS-END-IMPORTS>> ---

public final class amount

{
	// ---( internal utility methods )---

	final static amount _instance = new amount();

	static amount _newInstance() { return new amount(); }

	static amount _cast(Object o) { return (amount)o; }

	// ---( server methods )---




	public static final void amountFormatter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(amountFormatter)>> ---
		// @sigtype java 3.5
		// [i] field:0:required amountInput
		// [o] field:0:required value
		IDataCursor idc = pipeline.getCursor();
		
		String amountInput = IDataUtil.getString(idc, "amountInput");
		String value = null;
		if (amountInput != null) {
			if (amountInput.startsWith("-")) {
				String calcAmount = amountInput.substring(1);
				value = "-"+convertAmount(calcAmount);
			}
			else if (amountInput.endsWith("-")) {
				String calcAmount = amountInput.substring(0, amountInput.length()-1);
				value = convertAmount(calcAmount)+"-";
			}
			else {
				value = convertAmount(amountInput);
			}
			IDataUtil.put(idc, "value", value);
		}
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void convertDoc (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertDoc)>> ---
		// @sigtype java 3.5
		// [i] object:0:required nik
		// [o] field:0:required value
		IDataCursor IDC = pipeline.getCursor();
		
		Double object = (Double) IDataUtil.get(IDC, "nik");
		
		DecimalFormat df = new DecimalFormat("#");
		
		df.setRoundingMode(RoundingMode.DOWN);
		
		String format = df.format(object);
		
		IDataUtil.put(IDC, "value", format);
		
		IDC.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void currencyAmountFormatter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(currencyAmountFormatter)>> ---
		// @sigtype java 3.5
		// [i] field:0:required amountInput
		// [o] field:0:required currency
		// [o] field:0:required value
		IDataCursor idc = pipeline.getCursor();
		
		String amountInput = IDataUtil.getString(idc, "amountInput");
		String value = null;
		if (amountInput != null) {
			String currency = amountInput.substring(0, 3);
			String calcAmount = amountInput.substring(3);
			value = convertAmount(calcAmount);
			IDataUtil.put(idc, "currency", currency);
			IDataUtil.put(idc, "value", value);
		}
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static String convertAmount(String amount) {
		String newAmount = amount.replace(",", ""); // can contains .00 or not
		
		//convert to big decimal
		BigDecimal dec = new BigDecimal(newAmount);
		
		//prepare decimal formatter
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.DOWN);
		 
		//format
		String value= df.format(dec);
		return value;
	}
	// --- <<IS-END-SHARED>> ---
}

