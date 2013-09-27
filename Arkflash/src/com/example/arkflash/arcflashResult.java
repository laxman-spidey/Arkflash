package com.example.arkflash;

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
	public int grounding;
	public double incidentEnergy;
	public double ea18;
	public double ea12;

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

	public void setGrounding(int grounding)
	{
		this.grounding = grounding;
	}

}
