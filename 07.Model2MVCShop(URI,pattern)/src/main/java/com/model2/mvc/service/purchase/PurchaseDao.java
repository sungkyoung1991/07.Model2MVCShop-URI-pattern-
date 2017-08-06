package com.model2.mvc.service.purchase;

import java.sql.SQLException;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {

	public Purchase findPurchase(int tranNo) throws Exception;

	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception;

	//public Map<Search, String> getSaleList(Search search);

	public void insertPurchase(Purchase purchase) throws Exception;

	public void updatePurchase(Purchase purchase) throws SQLException;

	public void updateTranCode(Purchase purchase) throws Exception;

	//private String makeCurrentPageSql(String sql, Search search);
	
	public int getTotalCount(Search search) throws Exception ;

}
