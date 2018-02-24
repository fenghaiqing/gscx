package cn.migu.income.dao;

import cn.migu.income.pojo.TMiguIncomeBilling;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * User:lushengpeng
 * Date:2017/8/28
 * Time:15:40
 */
public interface TMiguIncomeBillingMapper {
    /**
     * 获取指定条件的报账条目数
     *
     * @param params
     * @return
     */
    Integer getIncomeBillingCount(Map<String, Object> params);

    /**
     * 获取指定条目数据
     *
     * @param params
     * @return
     */
    List<TMiguIncomeBilling> getIncomeBillingList(Map<String, Object> params);

    int insertBilling(TMiguIncomeBilling tMiguIncomeBilling);
    
   int  updateInvoiceStatus(TMiguIncomeBilling tMiguIncomeBilling);
   
   List<TMiguIncomeBilling>  selectUnBillSuccess();

   List<TMiguIncomeBilling> queryAllBill(Map<String, Object> param);

   int deleteBilling(String billingKey);

   int selectUnBilling(String mergeId, String cycle);  
}
