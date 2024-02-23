package com.scalar.labs.coe;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Put;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;

import java.util.UUID;

public class InsertRecord {

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
			String comment = UUID.randomUUID().toString();

			transaction = manager.start();

			transaction.put(
					Put.newBuilder().namespace(NAME_SPACE_POSTGRES).table(SAMPLE_TABLE).partitionKey(Key.ofInt("id", sampleId))
							.textValue("comment", comment).build());
			transaction.put(
					Put.newBuilder().namespace(NAME_SPACE_MYSQL).table(SAMPLE_TABLE).partitionKey(Key.ofInt("id", sampleId))
							.textValue("comment", comment).build());

			transaction.commit();
			
    		System.out.println("Records were inserted");


		} catch (Exception e) {
			if (transaction != null) {
				transaction.abort();
			}
			throw e;
		}
	}
}
