package com.sojson.common.utils;

public enum PaymentType {
	PAY_ONLINE("线上付款", 1), PAY_ON_DELIVERY("货到付款", 2), NO_PAY("无需支付", 3);

	private String name;
	private int index;

	private PaymentType(String name, int index) {
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
