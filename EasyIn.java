//Assignment 2 Credit Union
//Student Name: Noreen Doyle
//Student Number: B00123558

import javax.swing.*;

public abstract class EasyIn
{
	static String s = new String();
	static byte[] b = new byte[512];
	static int bytesRead = 0;


	public static String getString(String message, boolean required)
	{
		String result;

		while(true)
		{
			result = JOptionPane.showInputDialog(null, message);

			if(required && (result == null || result.isEmpty()))
				JOptionPane.showMessageDialog(null, "Input required.");
			else
				break;
		}

		return result;
	}

	public static int getInt(String message)
	{
		int result = 0;

		while(true)
		{
			try
			{
				result = Integer.parseInt(JOptionPane.showInputDialog(null, message));
				break;
			}
			catch(Exception error)
			{
				JOptionPane.showMessageDialog(null, "Enter a numerical value.");
			}
		}

		return result;
	}


	public static double getDouble(String message)
	{
		double result = 0;

		while(true)
		{
			try
			{
				result = Double.parseDouble(JOptionPane.showInputDialog(null, message));
				break;
			}
			catch(Exception error)
			{
				JOptionPane.showMessageDialog(null, "Enter a numerical value.");
			}
		}

		return result;
	}
}
