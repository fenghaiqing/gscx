

/**
 * SyncContractBudget.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */

    package cn.migu.income.webservices;

    /*
     *  SyncContractBudget java interface
     */

    public interface SyncContractBudget {
          

        /**
          * Auto generated method signature
          * 
                    * @param findContractBudget0
                
         */

         
                     public cn.migu.income.webservices.FindContractBudgetResponse findContractBudget(

                        cn.migu.income.webservices.FindContractBudget findContractBudget0)
                        throws java.rmi.RemoteException
             ;

        

        public void startfindContractBudget(

            cn.migu.income.webservices.FindContractBudget findContractBudget0,

            final cn.migu.income.webservices.SyncContractBudgetCallbackHandler callback)

            throws java.rmi.RemoteException;

       }
    