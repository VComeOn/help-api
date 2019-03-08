package com.thinkgem.jeesite.modules.openApi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
public class BillDeliveryListModel extends BaseModel{

    @NotNull
    @Size(min = 1,max = 100)
    private List<BillDeliveryModel> list;

    public List<BillDeliveryModel> getList() {
        return list;
    }

    public void setList(List<BillDeliveryModel> list) {
        this.list = list;
    }
}
