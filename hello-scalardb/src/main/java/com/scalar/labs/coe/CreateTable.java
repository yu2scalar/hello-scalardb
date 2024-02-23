package com.scalar.labs.coe;

import com.scalar.db.api.TableMetadata;
import com.scalar.db.io.DataType;
import com.scalar.db.service.TransactionFactory;

import com.scalar.db.api.DistributedTransactionAdmin;



public class CreateTable {

	private static final String NAME_SPACE_POSTGRES = "nsj_postgres";
	private static final String NAME_SPACE_MYSQL = "nsj_mysql";
	private static final String SAMPLE_TABLE = "table";

	public static void main(String[] args) {
		String properties = "database.properties";

        try
        {
    		TransactionFactory factory = TransactionFactory.create(properties);
    		DistributedTransactionAdmin admin = factory.getTransactionAdmin();
    		admin.createCoordinatorTables(true);
            admin.createNamespace(NAME_SPACE_POSTGRES, true);
    		admin.createNamespace(NAME_SPACE_MYSQL, true);
    		System.out.println("Name Spaces were created");
    		
    		TableMetadata  sample =
                TableMetadata.newBuilder()
    	            .addColumn("id", DataType.INT)
    	            .addColumn("comment", DataType.TEXT)
                    .addPartitionKey("id")
                    .build();
    		
        	admin.createTable(NAME_SPACE_POSTGRES, SAMPLE_TABLE, sample);
        	admin.createTable(NAME_SPACE_MYSQL, SAMPLE_TABLE, sample);
        	
    		System.out.println("Tables were Created");


        }
        catch (Exception ex)
        {

        	ex.printStackTrace();

        }

	}
}
