/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.BillDeliveryCommentDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliveryComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 运单评价Service
 * @author wcf
 * @version 2018-02-06
 */
@Service
@Transactional(readOnly = true)
public class BillDeliveryCommentService extends CrudService<BillDeliveryCommentDao, BillDeliveryComment> {
	@Resource
	private BillDeliveryCommentDao dao;
	
}