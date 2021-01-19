//Assignment 2 Credit Union
//Student Name: Noreen Doyle
//Student Number: B00123558

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class Main extends JFrame
{
	private JList<String> accountsList;
	private CreditUnion credit;


	public Main()
	{

		this.setTitle("Noreens'Credit Union");
		this.setSize(500, 300);
		this.setLayout(new BorderLayout());

		// close window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		this.setLocationRelativeTo(null);

		// Initialize the interface for viewing accounts
		this.accountsList = new JList<String>(new DefaultListModel<String>());
		JScrollPane scrollPane = new JScrollPane(this.accountsList);
		this.getContentPane().add(BorderLayout.CENTER, scrollPane);

		JMenuBar menuBar = new JMenuBar();
		JMenu adminOptions = new JMenu("Create Account");
		JMenu accountOptions = new JMenu("Options");

		// create the options for the administrator
		JMenuItem openAccountMenuItem = new JMenuItem("Create Account");
		openAccountMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { openAccountMenuItemClicked(); }});

		JMenuItem exitMenuItem = new JMenuItem("Save and exit");
		exitMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { exitMenuItemClicked(); }});

		adminOptions.add(openAccountMenuItem);
		adminOptions.add(exitMenuItem);

		// create the options for the account
		JMenuItem closeAccountMenuItem = new JMenuItem("Close Account");
		closeAccountMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { closeAccountMenuItemClicked(); }});

		JMenuItem makeLodgementAccountMenuItem = new JMenuItem("Lodge");
		makeLodgementAccountMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { makeLodgementAccountMenuItemClicked(); }});

		JMenuItem makeWithdrawalAccountMenuItem = new JMenuItem("Withdraw");
		makeWithdrawalAccountMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { makeWithdrawalAccountMenuItemClicked(); }});

		JMenuItem requestOverDraftMenuItem = new JMenuItem("Overdraft");
		requestOverDraftMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { requestOverDraftMenuItemClicked(); }});

		accountOptions.add(closeAccountMenuItem);
		accountOptions.add(makeLodgementAccountMenuItem);
		accountOptions.add(makeWithdrawalAccountMenuItem);
		accountOptions.add(requestOverDraftMenuItem);

		menuBar.add(adminOptions);
		menuBar.add(accountOptions);

		this.setJMenuBar(menuBar);

		this.credit = new CreditUnion();

		this.loadAccounts();
	}

//load account
	private void loadAccounts()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
			String line = reader.readLine();

			while(line != null)
			{
				String[] tokens = line.split("<>");


				Account account = new Account(Integer.parseInt(tokens[0]), tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
				this.credit.addAccount(account);


				line = reader.readLine();
			}

			reader.close();

			this.reloadAccountsList();
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}
	}

//overdraft
	private void requestOverDraftMenuItemClicked()
	{
		int number = this.getSelectedAccount();

		if(number == -1)
			return;


		double amount = 0;

		while(true)
		{
			amount = EasyIn.getDouble("Please enter an overdraft amount");

			if(amount > 0)
				break;
			else
				JOptionPane.showMessageDialog(null, "Please enter positive number");
		}

		this.credit.requestOverdraft(number, amount);
		JOptionPane.showMessageDialog(null, "Overdraft set");
		this.reloadAccountsList();
	}

//withdraw
	private void makeWithdrawalAccountMenuItemClicked()
	{
		int number = this.getSelectedAccount();

		if(number == -1)
			return;


		double amount = 0;

		while(true)
		{
			amount = EasyIn.getDouble("How much would you like to withdraw?");

			if(amount > 0)
				break;
			else
				JOptionPane.showMessageDialog(null, "Please enter positive number");
		}

		if(this.credit.makeWithdrawal(number, amount))
		{
			JOptionPane.showMessageDialog(null, "Withdrawal complete");
			this.reloadAccountsList();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Cannot withdraw that amount. It is above the overdraft limit. please try again.");
		}
	}


	private int getSelectedAccount()
	{

		if(this.accountsList.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(null, "Select an account.");
			return -1;
		}


		return Integer.parseInt(this.accountsList.getSelectedValue().split("\\-")[0].trim());
	}

//lodge
	private void makeLodgementAccountMenuItemClicked()
	{
		int number = this.getSelectedAccount();

		if(number == -1)
			return;


		double amount = 0;

		while(true)
		{
			amount = EasyIn.getDouble("How much would you like to lodge?");

			if(amount > 0)
				break;
			else
				JOptionPane.showMessageDialog(null, "Please enter positive number");
		}

		this.credit.makeLodgement(number, amount);

		JOptionPane.showMessageDialog(null, "Lodgement complete");
		this.reloadAccountsList();
	}

//close account
	private void closeAccountMenuItemClicked()
	{
		int number = this.getSelectedAccount();


		if(number != -1 && JOptionPane.showConfirmDialog(null, "Please Confirm") == JOptionPane.YES_OPTION)
		{
			if(this.credit.closeAccount(number))
			{
				JOptionPane.showMessageDialog(null, "Account Closed");
				this.reloadAccountsList();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Account balance should be 0 before closing");
			}
		}
	}


	private void exitMenuItemClicked()
	{

		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter("data.txt"));
			ArrayList<Account>accounts = this.credit.getAccounts();

			for(int i = 0; i < accounts.size(); i++)
			{
				Account account = accounts.get(i);
				writer.println(account.getNumber() + "<>" + account.name() + "<>" + account.balance() + "<>" + account.overdraft());
			}

			writer.flush();
			writer.close();
		}
		catch(Exception error)
		{
			System.err.println(error);
		}

		System.exit(0);
	}


	private void reloadAccountsList()
	{
		((DefaultListModel<String>)this.accountsList.getModel()).removeAllElements();

		ArrayList<Account> accounts = this.credit.getAccounts();

		for(int i = 0; i < accounts.size(); i++)
			((DefaultListModel<String>)this.accountsList.getModel()).addElement(accounts.get(i).toString());

		this.repaint();
		this.revalidate();
	}

//new account
	private void openAccountMenuItemClicked()
	{

		int number = 0;

		while(true)
		{
			number = EasyIn.getInt("Please enter a new account number");

			if(this.credit.isAccountNumberUnique(number))
				break;

			JOptionPane.showMessageDialog(null, "That account number is already in use. Please use another number");
		}

		String name = EasyIn.getString("Please enter first name", true);

		//add
		this.credit.addNewAccount(number, name);
		JOptionPane.showMessageDialog(null, "Account created");

		this.reloadAccountsList();
	}


	public static void main(String[] args)
	{
		// start the window program
		Main main = new Main();
		main.setVisible(true);
	}
}
