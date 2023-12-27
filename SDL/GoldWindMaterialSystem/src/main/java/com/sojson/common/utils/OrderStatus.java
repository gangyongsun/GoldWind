package com.sojson.common.utils;

public enum OrderStatus {
	UNPAY("未付款", 1), PAY("已付款", 2), TO_BE_PURCHASE("待采购", 3), PURCHASING("采购中", 4), PURCHASE_OVER("采购完成", 5), UNSHIP("待发货", 6), SHIPPED("已发货", 7), TRADE_SUCCESS("交易成功", 8), TRADE_CLOSED("交易成功", 9);

	private String name;
	private int index;

	private OrderStatus(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
