package com.scalar.labs.coe;

import com.scalar.db.service.TransactionFactory;

import com.scalar.db.api.DistributedTransactionAdmin;


public class DropTable {
	private static final String NAME_SPACE_POSTGRES = "nsj_postgres";
	private static final String NAME_SPACE_MYSQL = "nsj_mysql";
	private static final String SAMPLE_TABLE = "table";

	public static void main(String[] args) {
		String properties = "database.properties";

        try
        {
    		TransactionFactory factory = TransactionFactory.create(properties);
    		DistributedTransactionAdmin admin = factory.getTransactionAdmin();

        	admin.dropTable(NAME_SPACE_POSTGRES, SAMPLE_TABLE);
        	admin.dropTable(NAME_SPACE_MYSQL, SAMPLE_TABLE);
    		System.out.println("Tables were Dropped" );
    		
            admin.dropNamespace(NAME_SPACE_POSTGRES, true);
            admin.dropNamespace(NAME_SPACE_MYSQL, true);
            admin.dropCoordinatorTables(true);
    		System.out.println("Namaspace were Dropped" );
    		
        }
        catch (Exception ex)
        {

        	ex.printStackTrace();

        }
	}
}
