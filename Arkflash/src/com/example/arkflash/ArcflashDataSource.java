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
	{ ArcflashSQLiteHelper.RESULT_TITLE, ArcflashSQLiteHelper.LINE_VOLTAGE, ArcflashSQLiteHelper.TRANSFORMER_KVA, ArcflashSQLiteHelper.EQUIPMENT_TYPE, ArcflashSQLiteHelper.TRANSFORMER_Z, ArcflashSQLiteHelper.FAULT_TOLERANCE_TIME, ArcflashSQLiteHelper.GROUNDING, ArcflashSQLiteHelper.INCIDENT_ENERGY, ArcflashSQLiteHelper.EA18, ArcflashSQLiteHelper.EA12 };

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
		createResult(result.getTitle(), result.getLineVoltage(), result.getTransformerKva(), result.getEquipmentType(), result.getTransformerZ(), result.getFaultToleranceTime(), result.getGrounding(), result.getIncidentEnergy(), result.getEa18(), result.getEa12());
	}

	public arcflashResult createResult(String title, double lineVoltage, double transformerKva, int equipmentType, double transformerZ, double faultToleranceTime, int grounding, double incidentEnergy, double ea18, double ea12)
	{
		ContentValues values = new ContentValues();
		values.put(ArcflashSQLiteHelper.RESULT_TITLE, title);
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
		int i=0;
		result.setId(cursor.getInt(i++));
		result.setTitle(cursor.getString(i++));
		result.setLineVoltage(cursor.getDouble(i++));
		result.setTransformerKva(cursor.getDouble(i++));
		result.setEquipmentType(cursor.getInt(i++));
		result.setTransformerZ(cursor.getDouble(i++));
		result.setFaultToleranceTime(cursor.getDouble(i++));
		result.setGrounding(cursor.getInt(i++));
		result.setIncidentEnergy(cursor.getDouble(i++));
		result.setEa18(cursor.getDouble(i++));
		result.setEa12(cursor.getDouble(9));
		return result;
	}

}
