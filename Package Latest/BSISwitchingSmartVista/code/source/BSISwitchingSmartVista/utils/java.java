package BSISwitchingSmartVista.utils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.List;
import com.wm.lang.ns.NSName;
// --- <<IS-END-IMPORTS>> ---

public final class java

{
	// ---( internal utility methods )---

	final static java _instance = new java();

	static java _newInstance() { return new java(); }

	static java _cast(Object o) { return (java)o; }

	// ---( server methods )---




	public static final void generateIndihomePaymentExtradataList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateIndihomePaymentExtradataList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required bit48extraDataList
		// [i] field:0:required count
		// [o] field:0:required responseCode
		// [o] field:0:required responseMessage
		// [o] record:1:required extraDataList
		// [o] - field:0:required billReference
		// [o] - field:0:required billAmount
		// [o] - field:0:required name
		// [o] - field:0:required npwp
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	bit48 = IDataUtil.getString( pipelineCursor, "bit48extraDataList" );
			String	countString = IDataUtil.getString( pipelineCursor, "count" );
			String responseCode = "99";
			String responseMessage = "Error generate extradata list";
		pipelineCursor.destroy();
		int chunkSize = 69;
		
		Integer	count = 1;
		try {
			count = Integer.valueOf(countString);
		} catch (Exception e) {
			//let count keep value = 1
		}
		IData[]	extraDataList = new IData[count];
		int x=0;
		try{
		    for (int i = 0; i < bit48.length(); i += chunkSize) {
				extraDataList[x] = IDataFactory.create();
				IDataCursor extraDataListCursor = extraDataList[x].getCursor();
				String extraDataRaw = bit48.substring(i, Math.min(bit48.length(), i + chunkSize)) ;
				String billReference = extraDataRaw.substring(0,11).trim();
				String billAmount = extraDataRaw.substring(11,23).trim();
				String name = extraDataRaw.substring(23,53).trim();
				String npwp = extraDataRaw.substring(53).trim();
				IDataUtil.put( extraDataListCursor, "billReference", billReference);
				IDataUtil.put( extraDataListCursor, "billAmount", billAmount);
				IDataUtil.put( extraDataListCursor, "name", name);
				IDataUtil.put( extraDataListCursor, "npwp", npwp);
				extraDataListCursor.destroy();
				x++;
		    }
			responseCode = "00";
			responseMessage = "Success";
		}catch (Exception e) {
			responseCode = "99";
			responseMessage = "String too long to be parsed";
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		// extraDataList
		
		IDataUtil.put( pipelineCursor_1, "responseCode", responseCode );
		IDataUtil.put( pipelineCursor_1, "responseMessage", responseMessage );
		IDataUtil.put( pipelineCursor_1, "extraDataList", extraDataList );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateTelkomPayExtradataList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateTelkomPayExtradataList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required bit48extraDataList
		// [i] field:0:required count
		// [o] field:0:required responseCode
		// [o] field:0:required responseMessage
		// [o] record:1:required extraDataList
		// [o] - field:0:required billReference
		// [o] - field:0:required billAmount
		// [o] - field:0:required name
		// [o] - field:0:required npwp
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	bit48 = IDataUtil.getString( pipelineCursor, "bit48extraDataList" );
			String	countString = IDataUtil.getString( pipelineCursor, "count" );
			String responseCode = "99";
			String responseMessage = "Error generate extradata list";
		pipelineCursor.destroy();
		int chunkSize = 69;
		
		Integer	count = 1;
		try {
			count = Integer.valueOf(countString);
		} catch (Exception e) {
			//let count keep value = 1
		}
		IData[]	extraDataList = new IData[count];
		int x=0;
		try{
		    for (int i = 0; i < bit48.length(); i += chunkSize) {
				extraDataList[x] = IDataFactory.create();
				IDataCursor extraDataListCursor = extraDataList[x].getCursor();
				String extraDataRaw = bit48.substring(i, Math.min(bit48.length(), i + chunkSize)) ;
				String billReference = extraDataRaw.substring(0,11).trim();
				String billAmount = extraDataRaw.substring(11,23).trim();
				String name = extraDataRaw.substring(23,53).trim();
				String npwp = extraDataRaw.substring(53).trim();
				IDataUtil.put( extraDataListCursor, "billReference", billReference);
				IDataUtil.put( extraDataListCursor, "billAmount", billAmount);
				IDataUtil.put( extraDataListCursor, "name", name);
				IDataUtil.put( extraDataListCursor, "npwp", npwp);
				extraDataListCursor.destroy();
				x++;
		    }
			responseCode = "00";
			responseMessage = "Success";
		}catch (Exception e) {
			responseCode = "99";
			responseMessage = "String too long to be parsed";
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		// extraDataList
		
		IDataUtil.put( pipelineCursor_1, "responseCode", responseCode );
		IDataUtil.put( pipelineCursor_1, "responseMessage", responseMessage );
		IDataUtil.put( pipelineCursor_1, "extraDataList", extraDataList );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void getSvRrnSequenceNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSvRrnSequenceNumber)>> ---
		// @sigtype java 3.5
		// [o] field:0:required svRrnTraceNumber
		try {
			synchronized(lockSvRrnTraceNumber) {
				if (maxSeqSvRrnTraceNumber <= svRrnTraceNumber) {  
					IData d = Service.doInvoke(nsSeqSvRrnTraceNumber, IDataFactory.create());
					IDataCursor c1 = d.getCursor();
					IData traceNumberB24Output = IDataUtil.getIData(c1, "sv_rrn_nextvalOutput");
					c1.destroy();
					c1 = traceNumberB24Output.getCursor();
					IData[] results  = IDataUtil.getIDataArray(c1, "results");
					c1.destroy();
					c1 = results[0].getCursor();
					String seqTraceNumber = IDataUtil.getString(c1, "seqReferenceNum");
					c1.destroy();
					
					svRrnTraceNumber = Integer.parseInt(seqTraceNumber);
					maxSeqSvRrnTraceNumber = svRrnTraceNumber + svRrn_increment_by;
				} 
			}
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "svRrnTraceNumber", String.valueOf(svRrnTraceNumber++));
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static final int svRrn_increment_by = 1000;
	public static final NSName nsSeqSvRrnTraceNumber = NSName.create("BSISwitchingSmartVista.adapter", "sv_rrn_nextval");
	public static final Object lockSvRrnTraceNumber = new Object();
	public static volatile int svRrnTraceNumber = 0;
	public static volatile int maxSeqSvRrnTraceNumber = -1;
	// --- <<IS-END-SHARED>> ---
}

