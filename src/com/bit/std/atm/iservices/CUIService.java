package com.bit.std.atm.iservices;

import java.util.Scanner;

import com.bit.std.atm.services.AuthenticationService;
import com.bit.std.atm.services.BankingService;

public class CUIService implements  ICUIService{
	private Integer choice=null;
	private Integer pin=null;
	private Integer amount=null;
	private Scanner sc = new Scanner(System.in);
	private IBankingService bankingService=new BankingService();
	private IAuthenticationService authenticationService= new AuthenticationService();
	@Override
	public void showMenu() {
		while(true)
		{
			System.out.println("1.Deposit\n2.Withdrawl\n3.Balance\n4.PIN change\n5.Exit");
			userChoice();
		}
			
	}
	private void userChoice()
	{
		System.out.println("\nEnter Choice: ");
		choice = sc.nextInt();
		performUserOperation();
	}
	private void performUserOperation()
	{
		switch(choice)
		{
		case 1:
			deposit();
			break;
		case 2:
			withdrawl();
			break;
		case 3:
			balance();
			break;
		case 4:
			pinChange();
			break;
		case 5:
			System.out.println("...Terminating ATM...");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
			break;
		}
	}
	private void deposit()
	{
		readAmount();
		if(validateAmount())
		{
			if(bankingService.deposit(amount)) System.out.println("Deposited Successfully");
			else System.out.println("Not Deposited");
		}
		else System.out.println("Invalid Amount Entered!");
	}
	private void balance()
	{
		if(authenticateUser())
		{
			System.out.println("Balance is: "+bankingService.balance());
		}
	}
	private void pinChange()
	{
		if(authenticateUser())
		{
			readPin();
			if(authenticationService.resetPin(pin))
			{
				System.out.println("PIN changed");
			}
			else
				System.out.println("PIN Not Changed");
		}
	}
	private void withdrawl()
	{
		if(authenticateUser())
		{
			readAmount();
			if(validateAmount())
			{
				if(bankingService.balance()>=amount)
				{
					if(bankingService.withdrawl(amount))
					System.out.println("Amount Deducted");
					else System.out.println("Amount Not Deducted");
				}
				else System.out.println("Insufficient Balance");
			}
			else System.out.println("Invalid Amount Entered!");
		}
	}
	private void readAmount()
	{
		System.out.println("\nEnter Amount: ");
		amount=sc.nextInt();
	}
	private boolean validateAmount()
	{
		return(amount%100)==0?true:false;
	}
	private void readPin()
	{
		System.out.println("\nEnter Pin: ");
		pin=sc.nextInt();
	}
	private boolean authenticateUser()
	{
		boolean isAuthenticated=true;
		Integer attempt=1;
		while(attempt<=3)
		{
			readPin();
			isAuthenticated=authenticationService.isAuthenticated(pin);
			if(isAuthenticated) break;
			attempt++;
		}
		if(attempt>3)
		{
			System.out.println("User Blocked\n...Terminating ATM...");
			System.exit(0);
		}
		return isAuthenticated;
		
	}
}
