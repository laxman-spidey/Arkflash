package com.antzview.arkflash;

import android.util.Log;

public class Formulae
{

	// dummy inputs
	private double V; // Line Voltage
	private double faultClearanceTime;
	private double kVa;
	private double z;
	private int grounded;
	private double option;
	// conditions

	public static String LOG_TAG = "tag";
	private double K;//
	private double k1;
	private double k2;
	private double cf;
	private double X;
	private double G; // Conductor Gap

	public Formulae(double V, double option, double faultClearanceTime, double KVa, double z, boolean grounded)
	{
		this.V = V;
		this.faultClearanceTime = faultClearanceTime;
		this.kVa = KVa;
		this.z = z;
		if (grounded)
			this.grounded = 1;
		else
			this.grounded = 2;

		K = getK(option);
		k1 = getK1(option);
		k2 = getK2(this.grounded);
		cf = getCf(V);
		G = getG(V, option);
		X = getX(V, option);
		Log.i(LOG_TAG, "K = " + K + ", k1 = " + k1 + ", k2 = " + k2 + ", cf = " + cf + ", G = " + G + ", X = " + X);
		Log.i(LOG_TAG, "lbf : " + lbf());
		Log.i(LOG_TAG, "LogLa : " + logLa(lbf()));
		Log.i(LOG_TAG, "arc currrent : " + arcCurrent());
		Log.i(LOG_TAG, "LogEa : " + logEa());
	}

	public String getTestValues()
	{
		String res = "\n\n" + "K = " + K + ", k1 = " + k1 + ", k2 = " + k2 + ", cf = " + cf + ", G = " + G + ", X = " + X;
		res += "\nlbf : " + lbf();
		res += "\nLogLa : " + logLa(lbf());
		res += "\narc currrent : " + arcCurrent();
		res += "\nLogEa : " + logEa();
		return res;
	}

	public double getCf(double V)
	{
		if (V <= 1)
			return 1.5;
		else if (V > 1)
			return 1;
		else
			return 0;
	}

	public double getG(double V, double option)
	{
		if (V > 0.208 && V <= 1)
		{
			if (option == 1)
				return 25;
			else if (option == 2)
				return 32;
			else if (option == 3)
				return 25;
			else if (option == 4)
				return 13;
		}
		else if (V > 1 && V <= 5)
		{
			if (option == 1)
				return 102;
			else if (option == 2)
				return 45;
			else if (option == 4)
				return 13;
		}
		else if (V > 5 && V < 15)
		{
			if (option == 1)
				return 70;
			else if (option == 2)
				return 153;
			else if (option == 4)
				return 13;
		}
		return 0;
	}

	public double getX(double V, double option)
	{
		if (V > 0.208 && V <= 1)
		{
			if (option == 1)
				return 2;
			else if (option == 2)
				return 1.473;
			else if (option == 3)
				return 1.641;
			else if (option == 4)
				return 2;
		}
		else if (V > 1 && V <= 15)
		{
			if (option == 1)
				return 2;
			else if (option == 2)
				return 0.973;
			else if (option == 4)
				return 2;
		}

		return 0;
	}

	public double getK(double option)
	{
		if (option == 1)
			return -0.153;

		else
			return -0.097;
	}

	public double getK1(double option)
	{
		if (option == 1)
			return -0.792;
		else
			return -0.555;
	}

	public double getK2(int grounded)
	{
		if (grounded == 1)
			return -0.113;
		return 0;
	}

	public double lbf()
	{
		return (kVa * 100 / (z * 1.732 * V * 1000));
	}

	public double logLa(double lbf)
	{

		return K + (0.662 * Math.log10(lbf)) + (0.0966 * V) + (0.000526 * G) + (0.5588 * V * Math.log10(lbf)) - 0.00304 * G * Math.log10(lbf);
	}

	public double arcCurrent()
	{
		double logLa = logLa(lbf());
		return Math.pow(10, logLa);
	}

	public double logEa()
	{
		double logLa = logLa(lbf());
		return k1 + k2 + (1.081 * logLa) + (0.0011 * G);
	}

	public double incidentEnergy()
	{
		return 0.24 * Math.pow(10, logEa());
	}

	public double eaAt18()
	{
		cf = getCf(V);
		return 4.184 * cf * incidentEnergy() * (faultClearanceTime / 0.2) * (Math.pow(610, X) / Math.pow(18 * 25.4, X));
	}

	public double eaAt12()
	{
		cf = getCf(V);
		return 4.184 * cf * incidentEnergy() * (faultClearanceTime / 0.2) * (Math.pow(610, X) / Math.pow(12 * 25.4, X));

	}

	public void BMI()
	{
		double weight = 1;
		double height = 1;
		double BMI = weight / (height * height);// Body-Mass Index
	}
}
