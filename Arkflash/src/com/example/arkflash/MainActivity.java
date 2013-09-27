package com.example.arkflash;

import com.actionbarsherlock.app.SherlockActivity;

import android.R.menu;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.arkslash.R;

public class MainActivity extends SherlockActivity implements OnClickListener
{

	public static final int SAVE_DIALOG_REQUEST = 10;
	public static String LOG_TAG = "tag";
	// dummy inputs
	double V = 0.48; // Line Voltage
	double faultClearanceTime = 0.167;
	double kVa = 750;
	double z = 5.52;
	boolean grounded = true;
	int option = 1;

	// conditions

	double K = -0.097;//
	double k1 = -0.555;
	double k2 = -0.113;
	double cf = 1.5;
	double X = 1.473;
	double G = 32; // Conductor Gap

	// results
	double incidentEnergy;
	double ea18;
	double ea12;
	// views
	private EditText lineVoltageV_view;
	private RadioGroup optionGroup;
	private EditText transformerKVA_view;
	private EditText transformerZ_view;
	private EditText faultClearanceTimeT_view;
	private RadioGroup groundedGroup;
	private Button submitButton;
	private Button saveButton;
	private ImageButton closeButton;
	TextView incidentEnergyView;
	TextView ea18View;
	TextView ea12View;
	RelativeLayout resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_test);

		instantiate();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_history)
		{

		}
		return super.onOptionsItemSelected(item);
	}

	public void instantiate()
	{

		lineVoltageV_view = (EditText) findViewById(R.id.lineVotageV);
		optionGroup = (RadioGroup) findViewById(R.id.optionGroup);
		transformerKVA_view = (EditText) findViewById(R.id.transformerkVA);
		transformerZ_view = (EditText) findViewById(R.id.transformerZ);
		faultClearanceTimeT_view = (EditText) findViewById(R.id.faultClearanceTimeT);
		groundedGroup = (RadioGroup) findViewById(R.id.groundedGroup);
		submitButton = (Button) findViewById(R.id.submitButton);
		saveButton = (Button) findViewById(R.id.saveButton);
		closeButton = (ImageButton) findViewById(R.id.closeButton);

		incidentEnergyView = (TextView) findViewById(R.id.incidentEnergyView);
		ea18View = (TextView) findViewById(R.id.ea18View);
		ea12View = (TextView) findViewById(R.id.ea12View);
		resultView = (RelativeLayout) findViewById(R.id.resultView);

		submitButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		closeButton.setOnClickListener(this);
		// /test
		lineVoltageV_view.setText("6");
		transformerKVA_view.setText("650");

		// /test

		lineVoltageV_view.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
			{

				if (!isEmpty(lineVoltageV_view))
				{
					try
					{
						V = Double.valueOf(lineVoltageV_view.getText().toString());
						if (V > 1 && V <= 15)
						{
							RadioButton mcButton = (RadioButton) findViewById(R.id.mcPanels);
							mcButton.setEnabled(false);
						}
						else
						{
							RadioButton mcButton = (RadioButton) findViewById(R.id.mcPanels);
							mcButton.setEnabled(true);
						}
					}
					catch (NumberFormatException e)
					{

					}

				}
				else
				{
					RadioButton mcButton = (RadioButton) findViewById(R.id.mcPanels);
					mcButton.setEnabled(true);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
			{
			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
			}
		});

	}

	@Override
	public void onClick(View view)
	{
		Log.i(LOG_TAG, "onClick()");
		if (view.getId() == R.id.submitButton)
		{
			if (validate() == true)
			{
				option = getOptionValue();
				grounded = getGroudedValue();
				Log.i(LOG_TAG, "v = " + V + ", option = " + option + ", t = " + faultClearanceTime + ", kVa = " + kVa + ", z = " + z + ", grounded = " + grounded);
				Formulae formula = new Formulae(V, option, faultClearanceTime, kVa, z, grounded);
				incidentEnergy = formula.incidentEnergy();
				ea18 = formula.eaAt18();
				ea12 = formula.eaAt12();
				incidentEnergyView.setText(" incident Energy = " + incidentEnergy);
				ea18View.setText(" ea18 = " + ea18);
				ea12View.setText(" ea12 = " + ea12);
				resultView.setVisibility(View.VISIBLE);
			}
		}
		else if (view.getId() == R.id.saveButton)
		{
			showSaveDialog();
		}
		else if (view.getId() == R.id.closeButton)
		{
			resultView.setVisibility(View.GONE);
		}

	}

	private void showSaveDialog()
	{
		Intent intent = new Intent(this, saveDialogActivity.class);
		startActivityForResult(intent, SAVE_DIALOG_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == SAVE_DIALOG_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				String title = data.getStringExtra(getResources().getString(R.string.TAG_TITLE_STRING));
				Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
				saveValuestoDatabase(title);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void saveValuestoDatabase(String title)
	{
		ArcflashDataSource dataSource = new ArcflashDataSource(getApplicationContext());
		dataSource.open();
		int grounding;
		if (grounded)
			grounding = 1;
		else
			grounding = 0;
		dataSource.createResult(title, V, kVa, option, z, faultClearanceTime, grounding, incidentEnergy, ea18, ea12);
		dataSource.close();

	}

	public boolean validate()
	{

		boolean validity = true;
		// /////////////////////
		if (!isEmpty(lineVoltageV_view))
		{

			V = Double.valueOf(lineVoltageV_view.getText().toString());
			if (V >= 0.208 && V <= 15)
			{
				;
			}
			else
			{
				validity = false;
				setError(lineVoltageV_view, "value must be >=0.208 and <=15");
			}
		}
		else
		{
			validity = false;
			setError(lineVoltageV_view, "Field cannot be Empty.");
		}
		// ///////////////////
		if (isEmpty(transformerKVA_view))
		{
			validity = false;
			setError(transformerKVA_view, "Field cannot be Empty.");
		}
		else
		{
			kVa = Double.valueOf(transformerKVA_view.getText().toString());
		}
		// ///////////////////
		if (isEmpty(transformerZ_view))
		{
			z = 5;
		}
		else
		{
			z = Double.valueOf(transformerZ_view.getText().toString());
		}

		// ///////////////////
		if (isEmpty(faultClearanceTimeT_view))
		{
			faultClearanceTime = 0.167;
		}
		else
		{
			faultClearanceTime = Double.valueOf(faultClearanceTimeT_view.getText().toString());
		}
		// ///////////////////

		option = 1;
		grounded = true;
		return validity;
	}

	public boolean isEmpty(EditText field)
	{

		String string = field.getText().toString();
		if (string.equals(""))
		{

			return true;
		}

		return false;
	}

	public void setError(EditText field, String errorMsg)
	{
		field.setError(errorMsg);

	}

	private boolean getGroudedValue()
	{
		int checkedId = groundedGroup.getCheckedRadioButtonId();
		if (checkedId == R.id.grounded)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private int getOptionValue()
	{
		int checkedId = optionGroup.getCheckedRadioButtonId();
		switch (checkedId)
		{
		case R.id.openArea:
			return 1;
		case R.id.switchGears:
			return 2;
		case R.id.mcPanels:
			return 3;
		case R.id.cables:
			return 4;

		default:
			return 0;

		}

	}

}
