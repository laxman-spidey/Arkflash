package com.antzview.arkflash;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ArcflashDataSource
{
	private String LOG_TAG = "tag";

	private SQLiteDatabase database;
	private ArcflashSQLiteHelper dbHelper;
	private String[] allColumns =
	{ArcflashSQLiteHelper.RESULT_ID, ArcflashSQLiteHelper.RESULT_TITLE, ArcflashSQLiteHelper.LINE_VOLTAGE, ArcflashSQLiteHelper.TRANSFORMER_KVA, ArcflashSQLiteHelper.EQUIPMENT_TYPE, ArcflashSQLiteHelper.TRANSFORMER_Z, ArcflashSQLiteHelper.FAULT_TOLERANCE_TIME, ArcflashSQLiteHelper.GROUNDING, ArcflashSQLiteHelper.INCIDENT_ENERGY, ArcflashSQLiteHelper.EA18, ArcflashSQLiteHelper.EA12 };

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

		Log.i(LOG_TAG, values.toString());
		long insertId = database.insert(ArcflashSQLiteHelper.TABLE_RESULT, null, values);
		Log.i(LOG_TAG, "id = " + insertId);
		Cursor cursor = database.query(ArcflashSQLiteHelper.TABLE_RESULT, allColumns, ArcflashSQLiteHelper.RESULT_ID + " = " + insertId, null, null, null, null);
		Log.i(LOG_TAG, "cursor object" + cursor.toString());
		cursor.moveToFirst();
		arcflashResult newResult = cursorToObject(cursor);
		cursor.close();
		return newResult;
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
		Log.i(LOG_TAG, "cursor object" + cursor.getColumnNames().length);
		String values[] = cursor.getColumnNames();
		for (int i = 0; i < values.length; i++)
		{
			Log.i(LOG_TAG, "column-" + i + " : " + values[i]);
		}
		
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
		int i = 0;
		result.setId(cursor.getInt(i++));
		Log.i(LOG_TAG, "id: " + result.getId());
		result.setTitle(cursor.getString(i++));
		Log.i(LOG_TAG, "title : " + result.getTitle());
		result.setLineVoltage(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getLineVoltage());
		result.setTransformerKva(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getTransformerKva());
		result.setEquipmentType(cursor.getInt(i++));
		Log.i(LOG_TAG, "" + result.getEquipmentType());
		result.setTransformerZ(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getTransformerZ());
		result.setFaultToleranceTime(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getFaultToleranceTime());
		result.setGrounding(cursor.getInt(i++));
		Log.i(LOG_TAG, "" + result.getGrounding());
		result.setIncidentEnergy(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getIncidentEnergy());
		result.setEa18(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getEa18());
		result.setEa12(cursor.getDouble(i++));
		Log.i(LOG_TAG, "" + result.getEa12());
		return result;
	}

}
