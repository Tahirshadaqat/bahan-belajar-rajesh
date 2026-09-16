package BSICommonLogging;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
// --- <<IS-END-IMPORTS>> ---

public final class kafka

{
	// ---( internal utility methods )---

	final static kafka _instance = new kafka();

	static kafka _newInstance() { return new kafka(); }

	static kafka _cast(Object o) { return (kafka)o; }

	// ---( server methods )---




	public static final void doLogging (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(doLogging)>> ---
		// @sigtype java 3.5
		// [i] field:0:required topic
		// [i] field:0:required message
		IDataCursor idc = pipeline.getCursor();
		String topic = IDataUtil.getString(idc, "topic");
		String message = IDataUtil.getString(idc, "message");
				
		Job job = new Job(producer, topic, message);
		executor.submit(job);
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getLoggerProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getLoggerProperties)>> ---
		// @sigtype java 3.5
		// [o] field:0:required BOOTSTRAP_SERVER
		// [o] field:0:required N_CORE_THREAD_POOL
		// [o] field:0:required N_MAX_THREAD_POOL
		// [o] field:0:required KEEP_ALIVE_TIME
		// [o] field:0:required MAX_QUEUE
		// [o] field:0:required producer
		// [o] field:0:required executor
		IDataCursor idc = pipeline.getCursor();
		
		IDataUtil.put(idc, "BOOTSTRAP_SERVER", BOOTSTRAP_SERVER);
		IDataUtil.put(idc, "N_CORE_THREAD_POOL", N_CORE_THREAD_POOL);
		IDataUtil.put(idc, "N_MAX_THREAD_POOL", N_MAX_THREAD_POOL);
		IDataUtil.put(idc, "KEEP_ALIVE_TIME", KEEP_ALIVE_TIME);
		IDataUtil.put(idc, "MAX_QUEUE", MAX_QUEUE);
		 
		IDataUtil.put(idc, "producer", producer);
		IDataUtil.put(idc, "executor", executor);
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void initLogger (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(initLogger)>> ---
		// @sigtype java 3.5
		// [i] field:0:required kafkaServer
		// [i] field:0:required nCoreThreadPool
		// [i] field:0:required nMaxThreadPool
		// [i] field:0:required keepAliveTime
		// [i] field:0:required maxQueue
		IDataCursor idc = pipeline.getCursor();
		
		String kafkaServer = IDataUtil.getString(idc, "kafkaServer");
		String nCoreThreadPool = IDataUtil.getString(idc, "nCoreThreadPool");
		String nMaxThreadPool = IDataUtil.getString(idc, "nMaxThreadPool");
		String keepAliveTime = IDataUtil.getString(idc, "keepAliveTime");
		String maxQueue = IDataUtil.getString(idc, "maxQueue");
		
		// update from config
		BOOTSTRAP_SERVER = kafkaServer;
		N_CORE_THREAD_POOL = nCoreThreadPool;
		N_MAX_THREAD_POOL = nMaxThreadPool;
		KEEP_ALIVE_TIME = keepAliveTime;
		MAX_QUEUE = maxQueue;
		
		executor = initExecutorService();
		producer = initKafkaProducer();
		
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void passwordXMLmasker (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(passwordXMLmasker)>> ---
		// @sigtype java 3.5
		// [i] field:0:required searchString1
		// [i] field:0:required searchString2
		// [i] field:0:required inString
		// [i] field:0:required replace
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	searchString1 = IDataUtil.getString( pipelineCursor, "searchString1" );
			String	searchString2 = IDataUtil.getString( pipelineCursor, "searchString2" );
			String	inString = IDataUtil.getString( pipelineCursor, "inString" );
			String	replace = IDataUtil.getString( pipelineCursor, "replace" );
		pipelineCursor.destroy();
		String result = inString;
		try{
		Integer string1Size = searchString1.length();
		String a = inString;
		a = a.replace(a.substring(a.indexOf(searchString1) + string1Size, a.lastIndexOf(searchString2)), replace);
		result = a;
		}catch (Exception ex) {
			
		}
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void shutdownLogger (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(shutdownLogger)>> ---
		// @sigtype java 3.5
		if (producer != null)
			producer.close();
		if (executor != null)
			executor.shutdown();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static String BOOTSTRAP_SERVER = null;
	private static String N_CORE_THREAD_POOL = "10";
	private static String N_MAX_THREAD_POOL = "50";
	private static String KEEP_ALIVE_TIME = "1000";
	private static String MAX_QUEUE = "1000";
	
	private static KafkaProducer<String, String> producer = null;
	private static ExecutorService executor = null;
	
	private static ExecutorService initExecutorService() {
		BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<Runnable>(Integer.parseInt(MAX_QUEUE));
		if (executor == null) {
			int N_CORE_POOL = Integer.parseInt(N_CORE_THREAD_POOL);
			int N_MAX_POOL = Integer.parseInt(N_MAX_THREAD_POOL);
			long N_KEEP_ALIVE_TIME = Long.valueOf(KEEP_ALIVE_TIME).longValue();
			executor = new ThreadPoolExecutor(N_CORE_POOL, N_MAX_POOL, N_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, QUEUE);
			System.out.println("[INFO][KAFKA_LOGGER] executor : " + executor + " started with"
					+ " CORE : " + N_CORE_POOL 
					+ " MAX : " + N_MAX_POOL 
					+ " KEEP_ALIVE : " + N_KEEP_ALIVE_TIME 
					+ " MAX_QUEUE : " + MAX_QUEUE
			);
			return executor;
		}
		return null;
	}
		
	private static KafkaProducer<String, String> initKafkaProducer(){
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG, "3000");
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		System.out.println("[INFO][KAFKA_LOGGER] producer : " + producer + " started with props : " + props);
		return producer;
	}
	
	private static class Job implements Runnable {
		private KafkaProducer<String, String> producer;
		private String topic;
		private String message;
	
		public Job(KafkaProducer<String, String> producer, String topic, String message) {
			this.producer = producer;
			this.topic = topic;
			this.message = message;
		}
	
		private void produceMessage(KafkaProducer<String, String> producer, String topic, String message) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
			producer.send(record);
		}
	
		@Override
		public void run() {
			produceMessage(this.producer, this.topic, this.message);
		}
	}
	// --- <<IS-END-SHARED>> ---
}

