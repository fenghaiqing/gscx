package cn.migu.income.dao;

import cn.migu.income.pojo.TMiguContract;
import java.util.List;
import java.util.Map;


public interface TMiguContractMapper {

	int checkDataExists(TMiguContract tMiguContract);

	int saveContract(TMiguContract tMiguContract);

	int queryTotal(Map<String, Object> param);

	List<TMiguContract> queryAllAssociatedContract(Map<String, Object> param);

	int cancelAssociatedContract(List<TMiguContract> list);
}