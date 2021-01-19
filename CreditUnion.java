//Assignment 2 Credit Union
//Student Name: Noreen Doyle
//Student Number: B00123558

import java.util.*;

public class CreditUnion {
	private ArrayList<Account> accounts;

	public CreditUnion()
	{
		this.accounts = new ArrayList<Account>();
	}


	public boolean isAccountNumberUnique(int number)
	{
		boolean result = true;

		for(int i = 0; i < this.accounts.size(); i++)
		{
			if(this.accounts.get(i).getNumber() == number)
			{
				result = false;
				break;
			}
		}

		return result;
	}

//add account
	public Account addNewAccount(int number, String name)
	{
		Account account = new Account(number, name);
		this.accounts.add(account);

		return account;
	}


	public void addAccount(Account account)
	{
		this.accounts.add(account);
	}


	public ArrayList<Account> getAccounts()
	{
		return this.accounts;
	}

//lodge
	public void makeLodgement(int number, double amount)
	{
		Account account = this.findAccount(number);
		account.lodgement(amount);
	}

//withdraw
	public boolean makeWithdrawal(int number, double amount)
	{
		Account account = this.findAccount(number);
		return account.withdrawal(amount);
	}

//overdraft
	public void requestOverdraft(int number, double amount)
	{
		Account account = this.findAccount(number);
		account.setNewOverDraft(amount);
	}

//close account
	public boolean closeAccount(int number)
	{
		Account account = this.findAccount(number);

		if(account.balance() != 0)
			return false;

		this.accounts.remove(account);
		return true;
	}

//account info
	private Account findAccount(int number)
	{
		for(int i = 0; i < this.accounts.size(); i++)
			if(this.accounts.get(i).getNumber() == number)
				return this.accounts.get(i);

		return null;
	}
}
