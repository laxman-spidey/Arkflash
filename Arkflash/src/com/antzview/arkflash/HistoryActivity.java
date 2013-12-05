package com.antzview.arkflash;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.antzview.arkslash.R;

public class HistoryActivity extends SherlockActivity
{

	ArcflashDataSource dataSource;
	TextView msgView;

	// UI elements

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTitle("History");
		setContentView(R.layout.history_activity);
		msgView = (TextView) findViewById(R.id.history_infoMsg);

		dataSource = new ArcflashDataSource(this);
		dataSource.open();
		// instantiate();
		makeList();
		// dataSource.close();
		super.onCreate(savedInstanceState);
	}

	private void makeList()
	{
		List<arcflashResult> resultList = dataSource.getAllResults();
		ListView historyListView = (ListView) findViewById(R.id.history_list);
		HistoryListAdapter adapter = new HistoryListAdapter(getApplicationContext(), resultList, dataSource, msgView);
		historyListView.setAdapter(adapter);
		if (resultList.size() <= 0)
		{
			msgView.setText("No data Saved.");
			msgView.setVisibility(View.VISIBLE);
		}

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
		eqipmentType.setText("Equipment Type : " + resultItem.getEquipmentTypeString());
		transformerZ.setText("Transformer (Z) : " + resultItem.getTransformerZ());
		faultToleranceTime.setText("Fault Tolerance Time (t) : " + resultItem.getFaultToleranceTime());
		grounding.setText("Grounding : " + resultItem.getGroundingString());
		incidentEnergy.setText("incident Energy : " + resultItem.getIncidentEnergy());
		ea18.setText("ea18 : " + resultItem.getEa18());
		ea12.setText("ea12 : " + resultItem.getEa12());

	}

	@Override
	public void onDestroy()
	{
		dataSource.close();
		super.onDestroy();
	}

}
