package com.scalar.labs.coe;

import com.scalar.db.service.TransactionFactory;
import com.scalar.db.api.DistributedTransactionAdmin;



public class DropCoorinator {

	public static void main(String[] args) {
		String properties = "database.properties";

        try
        {
    		TransactionFactory factory = TransactionFactory.create(properties);
    		DistributedTransactionAdmin admin = factory.getTransactionAdmin();
    		admin.createCoordinatorTables(true);
        	
    		System.out.println("Coordinator table was Created");


        }
        catch (Exception ex)
        {

        	ex.printStackTrace();

        }

	}
}
