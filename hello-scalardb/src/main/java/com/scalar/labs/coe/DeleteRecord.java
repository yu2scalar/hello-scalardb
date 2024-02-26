package com.scalar.labs.coe;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Get;
import com.scalar.db.api.Delete;
import com.scalar.db.api.Result;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;

import java.util.Optional;

public class DeleteRecord {

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
			

			Delete  deleteP =
					Delete.newBuilder()
				        .namespace(NAME_SPACE_POSTGRES)
				        .table(SAMPLE_TABLE)
				        .partitionKey(partitionKey)
				        .build();
			Delete  deleteM =
					Delete.newBuilder()
				        .namespace(NAME_SPACE_MYSQL)
				        .table(SAMPLE_TABLE)
				        .partitionKey(partitionKey)
				        .build();
			
			transaction.delete(deleteP);
			transaction.delete(deleteM);
					
					
			transaction.commit();
			
    		System.out.println("Records were deleted.");



		} catch (Exception e) {
        	e.printStackTrace();

			if (transaction != null) {
				transaction.abort();
			}
			throw e;
		}
	}
}
