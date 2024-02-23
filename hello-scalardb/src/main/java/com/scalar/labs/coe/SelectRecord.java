package com.scalar.labs.coe;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Get;
import com.scalar.db.api.Result;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;

import java.util.Optional;

public class SelectRecord {

	private static final String NAME_SPACE_POSTGRES = "nsj_postgres";
	private static final String NAME_SPACE_MYSQL = "nsj_mysql";
	private static final String SAMPLE_TABLE = "table";

	
	public static void main(String[] args) throws Exception {
		String properties = "database.properties";
		DistributedTransactionManager manager;
		DistributedTransaction transaction = null;
		
		try {
			TransactionFactory factory = TransactionFactory.create(properties);
			manager = factory.getTransactionManager();			
			
			int sampleId = 1;

			Key partitionKey = Key.ofInt("id", sampleId);

			transaction = manager.start();
			

			Get getP =
				    Get.newBuilder()
				        .namespace(NAME_SPACE_POSTGRES)
				        .table(SAMPLE_TABLE)
				        .partitionKey(partitionKey)
				        .projections("id", "comment")
				        .build();
			Get getM =
				    Get.newBuilder()
				        .namespace(NAME_SPACE_MYSQL)
				        .table(SAMPLE_TABLE)
				        .partitionKey(partitionKey)
				        .projections("id", "comment")
				        .build();
			
			Optional<Result> resultP = transaction.get(getP);
			Optional<Result> resultM = transaction.get(getM);
					
					
			transaction.commit();
			
    		System.out.println(NAME_SPACE_POSTGRES + ": id:" + String.valueOf(resultP.get().getInt("id"))+ "/comment:" + resultP.get().getText("comment").toString());
    		System.out.println(NAME_SPACE_MYSQL + ": id:" + String.valueOf(resultM.get().getInt("id"))+ "/comment:" + resultM.get().getText("comment").toString());


		} catch (Exception e) {
        	e.printStackTrace();

			if (transaction != null) {
				transaction.abort();
			}
			throw e;
		}
	}
}
