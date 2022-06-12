package com.fteam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fteam.dto.CheckoutInfo;
import com.fteam.model.CartItem;

@Service
public class CheckoutService {

	public CheckoutInfo prepareCheckout(List<CartItem> cartItems) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();

		float productCost = calculateProductCost(cartItems);
		float productTotal = calculateProductTotal(cartItems);
		float shippingCost = calculateShippingCost(productTotal);
		
		checkoutInfo.setProductCost(productCost);
		checkoutInfo.setProductTotal(productTotal);
		checkoutInfo.setDeliverDays(5);
		checkoutInfo.setShippingCost(shippingCost);

		return checkoutInfo;
	}

	private float calculateShippingCost(float productTotal) {
		if (productTotal < 300000) {
            return 30000;
        }		
		
		return 0;
	}

	private float calculateProductTotal(List<CartItem> cartItems) {
		float total = 0.0f;

		for (CartItem item : cartItems) {
			total += item.getQuantity() * item.getProduct().getRealPrice();
		}

		return total;
	}

	private float calculateProductCost(List<CartItem> cartItems) {
		float cost = 0.0f;

		for (CartItem item : cartItems) {
			cost += item.getQuantity() * item.getProduct().getRealPrice();
		}

		return cost;
	}
	
	

}
