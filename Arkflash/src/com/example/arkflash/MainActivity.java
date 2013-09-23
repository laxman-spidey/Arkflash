package com.example.arkflash;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.example.arkslash.R;

public class MainActivity extends SherlockActivity implements OnClickListener
{

	public static String LOG_TAG = "tag";
	// dummy inputs
	double V = 0.48; // Line Voltage
	double faultClearanceTime = 0.167;
	double kVa = 750;
	double z = 5.52;
	boolean grounded = true;
	double option = 1;

	// conditions

	double K = -0.097;//
	double k1 = -0.555;
	double k2 = -0.113;
	double cf = 1.5;
	double X = 1.473;
	double G = 32; // Conductor Gap

	// views
	private EditText lineVoltageV_view;
	private RadioGroup optionGroup;
	private EditText transformerKVA_view;
	private EditText transformerZ_view;
	private EditText faultClearanceTimeT_view;
	private RadioGroup groundedGroup;
	private Button submitButton;
	TextView resultTextView;
	LinearLayout resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_test);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		resultView = (LinearLayout) findViewById(R.id.resultView);
		instantiate();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
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

		submitButton.setOnClickListener(this);

		///test
		lineVoltageV_view.setText("6");
		transformerKVA_view.setText("650");
		
		///test
		
		
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
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View arg0)
	{
		Log.i(LOG_TAG, "onClick()");
		if (validate() == true)
		{
			option = getOptionValue();
			grounded = getGroudedValue();
			Log.i(LOG_TAG, "v = " + V + ", option = " + option + ", t = " + faultClearanceTime + ", kVa = " + kVa + ", z = " + z + ", grounded = " + grounded);
			Formulae formula = new Formulae(V, option, faultClearanceTime, kVa, z, grounded);
			resultTextView.setText(" incident Energy = " + formula.incidentEnergy() + "\n ea18 = " + formula.eaAt18() + "\n ea12 = " + formula.eaAt12() + formula.getTestValues());

			resultView.setVisibility(View.VISIBLE);
		}

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
		field.setText("");
		field.setHint(errorMsg);
		field.setHintTextColor(Color.RED);

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

	private double getOptionValue()
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
