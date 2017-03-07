package com.jfinal.weixin.sdk.msg.in.card;

import com.jfinal.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 核销事件推送
 * @author L.cm
<xml>
  <ToUserName> <![CDATA[gh_fc0a06a20993]]> </ToUserName>
  <FromUserName> <![CDATA[oZI8Fj040-be6rlDohc6gkoPOQTQ]]> </FromUserName>
  <CreateTime>1472549042</CreateTime>
  <MsgType> <![CDATA[event]]> </MsgType>
  <Event> <![CDATA[user_consume_card]]> </Event>
  <CardId> <![CDATA[pZI8Fj8y-E8hpvho2d1ZvpGwQBvA]]> </CardId>
  <UserCardCode> <![CDATA[452998530302]]> </UserCardCode>
  <ConsumeSource> <![CDATA[FROM_API]]> </ConsumeSource>
  <LocationName> <![CDATA[]]> </LocationName>
  <StaffOpenId> <![CDATA[oZ********nJ3bPJu_Rtjkw4c]]> </StaffOpenId>
  <VerifyCode> <![CDATA[]]> </VerifyCode>
  <RemarkAmount> <![CDATA[]]> </RemarkAmount>
  <OuterStr> <![CDATA[xxxxx]]> </OuterStr>
</xml>
 */
@SuppressWarnings("serial")
public class InUserConsumeCardEvent extends EventInMsg {
	public static final String EVENT = "user_consume_card";
	
	public InUserConsumeCardEvent(String toUserName, String fromUserName, Integer createTime) {
		super(toUserName, fromUserName, createTime, EVENT);
	}
	
	//卡券ID。
	private String cardId;
	//卡券Code码。
	private String userCardCode;
	//核销来源。支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
	private String consumeSource;
	//门店名称，当前卡券核销的门店名称（只有通过自助核销和买单核销时才会出现该字段）
	private String locationName;
	//核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）
	private String staffOpenId;
	//自助核销时，用户输入的验证码
	private String verifyCode;
	//自助核销时，用户输入的备注金额
	private String remarkAmount;
	//开发者发起核销时传入的自定义参数，用于进行核销渠道统计
	private String outerStr;

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
	public String getConsumeSource() {
		return consumeSource;
	}
	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getStaffOpenId() {
		return staffOpenId;
	}
	public void setStaffOpenId(String staffOpenId) {
		this.staffOpenId = staffOpenId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getRemarkAmount() {
		return remarkAmount;
	}
	public void setRemarkAmount(String remarkAmount) {
		this.remarkAmount = remarkAmount;
	}
	public String getOuterStr() {
		return outerStr;
	}
	public void setOuterStr(String outerStr) {
		this.outerStr = outerStr;
	}
}