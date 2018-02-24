

/**
 * SynTaxInvoiceInfoSrv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.5  Built on : May 06, 2017 (03:45:26 BST)
 */

    package cn.migu.income.webservices;

    /*
     *  SynTaxInvoiceInfoSrv java interface
     */

    public interface SynTaxInvoiceInfoSrv {
          

        /**
          * Auto generated method signature
          * 
                    * @param getTaxInvoiceInfo0
                
         */

         
                     public cn.migu.income.webservices.GetTaxInvoiceInfoResponse getTaxInvoiceInfo(

                    		 cn.migu.income.webservices.GetTaxInvoiceInfo getTaxInvoiceInfo0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getTaxInvoiceInfo0
            
          */
        public void startgetTaxInvoiceInfo(

        		cn.migu.income.webservices.GetTaxInvoiceInfo getTaxInvoiceInfo0,

            final cn.migu.income.webservices.SynTaxInvoiceInfoSrvCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    