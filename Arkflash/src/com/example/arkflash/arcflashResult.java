package com.example.arkflash;

import com.example.arkslash.R;

import android.content.res.Resources;

public class arcflashResult
{

	private int id;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	private String title;
	private double lineVoltage;
	private double transformerKva;
	private int equipmentType;
	private double transformerZ;
	private double faultToleranceTime;
	private int grounding;
	private double incidentEnergy;
	private double ea18;
	private double ea12;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public double getIncidentEnergy()
	{
		return incidentEnergy;
	}

	public void setIncidentEnergy(double incidentEnergy)
	{
		this.incidentEnergy = incidentEnergy;
	}

	public double getEa18()
	{
		return ea18;
	}

	public void setEa18(double ea18)
	{
		this.ea18 = ea18;
	}

	public double getEa12()
	{
		return ea12;
	}

	public void setEa12(double ea12)
	{
		this.ea12 = ea12;
	}

	public double getLineVoltage()
	{
		return lineVoltage;
	}

	public void setLineVoltage(double lineVoltage)
	{
		this.lineVoltage = lineVoltage;
	}

	public double getTransformerKva()
	{
		return transformerKva;
	}

	public void setTransformerKva(double transformerKva)
	{
		this.transformerKva = transformerKva;
	}

	public int getEquipmentType()
	{
		return equipmentType;
	}

	public String getEquipmenTypeString()
	{
		switch (equipmentType)
		{
		case 1:
			return Resources.getSystem().getString(R.string.open_air);

		case 2:
			return Resources.getSystem().getString(R.string.switch_gears);

		case 3:
			return Resources.getSystem().getString(R.string.mcs_panels);

		case 4:
			return Resources.getSystem().getString(R.string.cables);

		default:
			return null;

		}
	}

	public void setEquipmentType(int equipmentType)
	{
		this.equipmentType = equipmentType;
	}

	public double getTransformerZ()
	{
		return transformerZ;
	}

	public void setTransformerZ(double transformerZ)
	{
		this.transformerZ = transformerZ;
	}

	public double getFaultToleranceTime()
	{
		return faultToleranceTime;
	}

	public void setFaultToleranceTime(double faultToleranceTime)
	{
		this.faultToleranceTime = faultToleranceTime;
	}

	public int getGrounding()
	{
		return grounding;
	}

	public String getGroundingString()
	{
		switch (grounding)
		{
		case 0:
			return Resources.getSystem().getString(R.string.grounded);
		case 1:
			return Resources.getSystem().getString(R.string.not_grounded);

		default:
			return null;
		}
	}

	public void setGrounding(int grounding)
	{
		this.grounding = grounding;
	}

}
