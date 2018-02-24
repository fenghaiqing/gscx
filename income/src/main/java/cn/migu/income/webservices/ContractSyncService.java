
/**
 * ContractSyncService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.5  Built on : May 06, 2017 (03:45:26 BST)
 */

package cn.migu.income.webservices;

/*
 *  ContractSyncService java interface
 */

public interface ContractSyncService {

	/**
	 * Auto generated method signature
	 * 
	 * @param getContractList0
	 * 
	 */

	public cn.migu.income.webservices.GetContractListResponse getContractList(

			cn.migu.income.webservices.GetContractList getContractList0) throws java.rmi.RemoteException;

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @param getContractList0
	 * 
	 */
	public void startgetContractList(

			cn.migu.income.webservices.GetContractList getContractList0,

			final cn.migu.income.webservices.ContractSyncServiceCallbackHandler callback)

			throws java.rmi.RemoteException;

	//
}
