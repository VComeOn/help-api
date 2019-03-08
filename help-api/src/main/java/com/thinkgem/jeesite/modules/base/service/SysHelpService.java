/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import javax.annotation.Resource;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.entity.SysHelp;
import com.thinkgem.jeesite.modules.base.dao.SysHelpDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 帮助中心Service
 * @author wcf
 * @version 2018-01-02
 */
@Service
@Transactional(readOnly = true)
public class SysHelpService extends CrudService<SysHelpDao, SysHelp> {
	@Resource
	private SysHelpDao dao;

	public Grid list(SysHelp sysHelp, GridParam param){
		Grid grid = new Grid(param);
		sysHelp.setGrid(grid);

		List<SysHelp> list = dao.findList(sysHelp);

		//Pattern pattern = Pattern.compile("<img src=\"");
		for(SysHelp help : list){
			if(StringUtils.isNotBlank(help.getContent())){
				//Matcher matcher = pattern.matcher(help.getContent());
				//help.setContent(matcher.replaceAll("<img src=\""));
				help.setContent(help.getContent().replace("<img src=\"", "<img src=\"" + Global.baseUrl + ""));
			}
		}

		grid.setRows(list);
		return grid;
	}
}