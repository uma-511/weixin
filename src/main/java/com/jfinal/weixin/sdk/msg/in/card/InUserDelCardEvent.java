package com.jfinal.weixin.sdk.msg.in.card;

import com.jfinal.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 删除事件推送
 */
@SuppressWarnings("serial")
public class InUserDelCardEvent extends EventInMsg {
	public static final String EVENT = "user_del_card";

	private String cardId;
	private String userCardCode;

	public InUserDelCardEvent(String toUserName, String fromUserName, Integer createTime) {
		super(toUserName, fromUserName, createTime, EVENT);
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
}