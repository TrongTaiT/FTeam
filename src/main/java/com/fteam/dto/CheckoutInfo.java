package com.fteam.dto;

import java.util.Calendar;
import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutInfo {

	private float productCost;
	private float productTotal;
	private float shippingCost;
	private float paymentTotal;
	private int deliverDays;
	
	@Getter(AccessLevel.NONE)
	private Date deliverDate;

	public Date getDeliverDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, this.deliverDays);
		
		return calendar.getTime();
	}
	
}
