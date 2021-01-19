//Assignment 2 Credit Union
//Student Name: Noreen Doyle
//Student Number: B00123558

import java.text.*;

public class Account
{
	private int number;
	private String name;
	private double balance;
	private double overdraftLimit;


	public Account(int number, String name)
	{
		this.number = number;
		this.name = name;
		this.balance = 0;
		this.overdraftLimit = 0;
	}
	
//Load account
	public Account(int number, String name, double balance, double overdraftLimit)
	{
		this.number = number;
		this.name = name;
		this.balance = balance;
		this.overdraftLimit = overdraftLimit;
	}
//shows the details of the account
	public String name()
	{
		return this.name;
	}

	public int getNumber()
	{
		return this.number;
	}

//lodgement
	public void lodgement(double amount)
	{
		this.balance += amount;
	}

//withdraw
	public boolean withdrawal(double amount)
	{
		if(this.balance - amount >= this.overdraftLimit)
		{
			this.balance -= amount;
			return true;
		}

		return false;
	}

//overdraft
	public double overdraft()
	{
		return this.overdraftLimit;
	}

//overdraft limit
	public void setNewOverDraft(double amount)
	{
		this.overdraftLimit = -amount;
	}

//balance
	public double balance()
	{
		return this.balance;
	}

	public String toString()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return this.number + " - " + this.name + " [Balance: " + formatter.format(this.balance) + "][Limit: " + formatter.format(this.overdraftLimit) + "]";
	}
}
