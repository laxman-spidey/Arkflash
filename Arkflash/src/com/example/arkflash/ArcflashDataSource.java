package com.example.arkflash;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ArcflashDataSource
{
	private SQLiteDatabase database;
	private ArcflashSQLiteHelper dbHelper;
	private String[] allColumns =
	{ ArcflashSQLiteHelper.LINE_VOLTAGE, ArcflashSQLiteHelper.TRANSFORMER_KVA, ArcflashSQLiteHelper.EQUIPMENT_TYPE, ArcflashSQLiteHelper.TRANSFORMER_Z, ArcflashSQLiteHelper.FAULT_TOLERANCE_TIME, ArcflashSQLiteHelper.GROUNDING };

	public ArcflashDataSource(Context context)
	{
		dbHelper = new ArcflashSQLiteHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public void createResult(arcflashResult result)
	{
		createResult(result.getLineVoltage(), result.getTransformerKva(), result.getEquipmentType(), result.getTransformerZ(), result.getFaultToleranceTime(), result.getGrounding(), result.getIncidentEnergy(), result.getEa18(), result.getEa12());
	}

	public arcflashResult createResult(double lineVoltage, double transformerKva, int equipmentType, double transformerZ, double faultToleranceTime, int grounding, double incidentEnergy, double ea18, double ea12)
	{
		ContentValues values = new ContentValues();
		values.put(ArcflashSQLiteHelper.LINE_VOLTAGE, lineVoltage);
		values.put(ArcflashSQLiteHelper.TRANSFORMER_KVA, transformerKva);
		values.put(ArcflashSQLiteHelper.EQUIPMENT_TYPE, equipmentType);
		values.put(ArcflashSQLiteHelper.TRANSFORMER_Z, transformerZ);
		values.put(ArcflashSQLiteHelper.FAULT_TOLERANCE_TIME, faultToleranceTime);
		values.put(ArcflashSQLiteHelper.GROUNDING, grounding);
		values.put(ArcflashSQLiteHelper.INCIDENT_ENERGY, incidentEnergy);
		values.put(ArcflashSQLiteHelper.EA18, ea18);
		values.put(ArcflashSQLiteHelper.EA12, ea12);

		long insertId = database.insert(ArcflashSQLiteHelper.TABLE_RESULT, null, values);
		Cursor cursor = database.query(ArcflashSQLiteHelper.TABLE_RESULT, allColumns, ArcflashSQLiteHelper.RESULT_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		arcflashResult newComment = cursorToObject(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteResult(arcflashResult result)
	{
		long id = result.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(ArcflashSQLiteHelper.TABLE_RESULT, ArcflashSQLiteHelper.RESULT_ID + " = " + id, null);
	}

	public List<arcflashResult> getAllResults()
	{
		List<arcflashResult> results = new ArrayList<arcflashResult>();

		Cursor cursor = database.query(ArcflashSQLiteHelper.TABLE_RESULT, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			arcflashResult result = cursorToObject(cursor);
			results.add(result);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return results;
	}

	private arcflashResult cursorToObject(Cursor cursor)
	{
		arcflashResult result = new arcflashResult();
		result.setId(cursor.getInt(0));
		result.setLineVoltage(cursor.getDouble(1));
		result.setTransformerKva(cursor.getDouble(2));
		result.setEquipmentType(cursor.getInt(3));
		result.setTransformerZ(cursor.getDouble(4));
		result.setFaultToleranceTime(cursor.getDouble(5));
		result.setGrounding(cursor.getInt(6));
		result.setIncidentEnergy(cursor.getDouble(7));
		result.setEa18(cursor.getDouble(8));
		result.setEa12(cursor.getDouble(9));
		return result;
	}

}
