package com.antzview.arkflash;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.antzview.arkslash.R;

public class HistoryListAdapter extends ArrayAdapter<arcflashResult>
{

	private Context context;
	private List<arcflashResult> resultList;
	ArcflashDataSource dataSource;
	TextView msgView;

	public HistoryListAdapter(Context context, List<arcflashResult> resultList, ArcflashDataSource dataSource, TextView msgView)
	{
		super(context, R.layout.history_card);
		this.context = context;
		this.resultList = resultList;
		this.dataSource = dataSource;
		this.msgView = msgView;

	}

	@Override
	public arcflashResult getItem(int position)
	{
		return resultList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.history_card, parent, false);

		TextView resultTitle = (TextView) rowView.findViewById(R.id.resultTitle);
		TextView lineVoltage = (TextView) rowView.findViewById(R.id.history_lineVoltage);
		TextView transformerKva = (TextView) rowView.findViewById(R.id.history_transformerKva);
		TextView eqipmentType = (TextView) rowView.findViewById(R.id.history_equipmentType);
		TextView transformerZ = (TextView) rowView.findViewById(R.id.history_transformerZ);
		TextView faultToleranceTime = (TextView) rowView.findViewById(R.id.history_faultToleranceTime);
		TextView grounding = (TextView) rowView.findViewById(R.id.history_grounding);
		TextView incidentEnergy = (TextView) rowView.findViewById(R.id.history_incidentEnergy);
		TextView ea18 = (TextView) rowView.findViewById(R.id.history_ea18View);
		TextView ea12 = (TextView) rowView.findViewById(R.id.history_ea12View);
		ImageButton closeButton = (ImageButton) rowView.findViewById(R.id.history_closeButton);

		arcflashResult resultItem = resultList.get(position);
		setCloseButtonOnclickListener(closeButton, resultItem);

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

		return rowView;
	}

	private void setCloseButtonOnclickListener(ImageButton closeButton, arcflashResult result)
	{
		final arcflashResult resultItem = result;
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view)
			{
				Log.i("tag", "clicked");
				resultList.remove(resultItem);
				HistoryListAdapter.this.remove(resultItem);
				HistoryListAdapter.this.notifyDataSetChanged();
				dataSource.deleteResult(resultItem);
				
				Log.i("tag", "size = " + resultList.size());
				if (resultList.size() <= 0)
				{
					msgView.setText("No data Saved.");
					msgView.setVisibility(View.VISIBLE);
				}
			}
		});

	}

	@Override
	public int getCount()
	{

		return resultList.size();
	}

	@Override
	public void remove(arcflashResult object)
	{
		resultList.remove(object);
		super.remove(object);
	}

}
