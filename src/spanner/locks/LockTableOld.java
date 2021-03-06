package spanner.locks;

import java.util.HashMap;

public class LockTableOld {

	private HashMap<String, String> lockTable;
	
	public LockTableOld()
	{
		lockTable = new HashMap<String, String>();
	}
	
	
	public boolean acquireLock(String row, String uid)
	{
		if(lockTable.containsKey(row) ){
			if( lockTable.get(row).equalsIgnoreCase(uid)){
				printLocks();
				return true;
			}
			else{
				printLocks();
				return false;
			}
		}
		else
		{
			lockTable.put(row, uid);
			printLocks();
			return true;
		}
		
	}
	
	public boolean acquireReadLockIfNot(String row, String uid)
	{
		if(lockTable.containsKey(row) )
			if( lockTable.get(row).equalsIgnoreCase(uid))
				return true;
			else
				return false;
		else
		{
			lockTable.put(row, uid);
			return true;
		}
	}
	
	public void printLocks()
	{
		System.out.println("Total keys in lock table "+lockTable.size());
		for(String key : lockTable.keySet())
			System.out.println("Lock for Key "+key +" acquired by "+lockTable.get(key));
	}
	
	public void releaseLock(String row, String uid)
	{
		
		/*if(!lockTable.containsKey(row))
			throw new IllegalStateException("No read lock found for the row");*/
		
		if(lockTable.containsKey(row) && !lockTable.get(row).equalsIgnoreCase(uid)){
		//	System.out.println("Expected lock value "+uid);
			throw new IllegalStateException("Read lock for the row is acquired by a different transaction "+lockTable.get(row));
		}
		else
			lockTable.remove(row);
	}
	
	
}
