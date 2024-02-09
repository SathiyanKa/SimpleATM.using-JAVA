package com.bit.std.atm;

import com.bit.std.atm.iservices.CUIService;
import com.bit.std.atm.iservices.ICUIService;

public class ATM {

	public static void main(String[] args) {
		System.out.println("...Welcome to BIT Bank...");
		ICUIService cuiService = new CUIService();
		cuiService.showMenu();
	}

}
