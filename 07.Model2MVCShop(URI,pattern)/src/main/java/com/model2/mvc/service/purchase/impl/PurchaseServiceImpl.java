package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;

	public PurchaseServiceImpl() {
	}

	public PurchaseServiceImpl(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	@Override
	public Purchase addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
		return purchase;
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {// 구매정보 상세 조회를 위한
																// 비즈니스 수행.
		// TODO Auto-generated method stub
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {// 구매목록보기를
		// TODO Auto-generated method stub
		return purchaseDao.getPurchaseList(search, buyerId);
	}

//	@Override
/*	public Map<String, Object> getSaleList(Search search) throws Exception {// 판매목록보기를
																			// 위한
																			// 비즈니스
																			// 수행.
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public void updatePurchase(Purchase purchase) throws Exception {// 구매정보 수정을
																	// 위한 비즈니스
																	// 수행.
		// TODO Auto-generated method stub
		purchaseDao.updatePurchase(purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {// 구매상태 코드
																	// 수정을 위한
																	// 비즈니스 수행.
		// TODO Auto-generated method stub
		purchaseDao.updateTranCode(purchase);

	}

}
