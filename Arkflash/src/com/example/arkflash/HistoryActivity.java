package com.example.arkflash;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.arkslash.R;

public class HistoryActivity extends SherlockActivity
{

	ArcflashDataSource dataSource;

	// UI elements

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.history_activity);
		dataSource = new ArcflashDataSource(this);
		dataSource.open();
		instantiate();
		super.onCreate(savedInstanceState);
	}

	private void instantiate()
	{
		TextView resultTitle = (TextView) findViewById(R.id.resultTitle);
		TextView lineVoltage = (TextView) findViewById(R.id.history_lineVoltage);
		TextView transformerKva = (TextView) findViewById(R.id.history_transformerKva);
		TextView eqipmentType = (TextView) findViewById(R.id.history_equipmentType);
		TextView transformerZ = (TextView) findViewById(R.id.history_transformerZ);
		TextView faultToleranceTime = (TextView) findViewById(R.id.history_faultToleranceTime);
		TextView grounding = (TextView) findViewById(R.id.history_grounding);
		TextView incidentEnergy = (TextView) findViewById(R.id.history_incidentEnergy);
		TextView ea18 = (TextView) findViewById(R.id.history_ea18View);
		TextView ea12 = (TextView) findViewById(R.id.history_ea12View);

		List<arcflashResult> result = dataSource.getAllResults();
		arcflashResult resultItem = result.get(0);
		resultTitle.setText(resultItem.getTitle());
		lineVoltage.setText("lineVoltage : " + resultItem.getLineVoltage());
		transformerKva.setText("transformer (KVA) : " + resultItem.getTransformerKva());
		eqipmentType.setText("Equipment Type : " + resultItem.getEquipmentType());
		transformerZ.setText("Transformer (Z) : " + resultItem.getTransformerZ());
		faultToleranceTime.setText("Fault Tolerance Time (t) : " + resultItem.getFaultToleranceTime());
		grounding.setText("grounding : " + resultItem.getGrounding());
		incidentEnergy.setText("incident Energy : " + resultItem.getIncidentEnergy());
		ea18.setText("ea18 : " + resultItem.getEa18());
		ea12.setText("ea12 : " + resultItem.getEa12());

	}

}
