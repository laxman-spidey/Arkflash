package com.example.arkflash;

import com.example.arkslash.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class saveDialogActivity extends Activity implements OnClickListener
{
	EditText title;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.prompt_save_dialog);

		Button saveButton = (Button) findViewById(R.id.dialogSaveButton);
		Button cancelButton = (Button) findViewById(R.id.dialogCancelButton);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		title = (EditText) findViewById(R.id.titleEditText);
	}

	@Override
	public void onClick(View v)
	{

		Intent intent = new Intent();
		if (v.getId() == R.id.dialogSaveButton)
		{
			if (validate())
			{
				intent.putExtra(getResources().getString(R.string.TAG_TITLE_STRING), title.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		}
		else if (v.getId() == R.id.dialogCancelButton)
		{
			setResult(RESULT_CANCELED);
			finish();
		}

	}

	private boolean validate()
	{
		if (title.getText().toString().equals(""))
		{
			title.setHint("Please enter title");
			title.setHintTextColor(Color.RED);
			return false;
		}
		return true;
	}

}
