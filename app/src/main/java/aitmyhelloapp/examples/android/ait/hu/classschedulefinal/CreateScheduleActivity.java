package aitmyhelloapp.examples.android.ait.hu.classschedulefinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.Schedule.Schedule;


public class CreateScheduleActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        final Spinner spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);

        final EditText etClassName = (EditText) findViewById(R.id.etClassName);

        //this is scary... Do not know what is going to happen...
        try {
            etClassName.setText(getIntent().getStringExtra("class_name"));
            spinnerTime.setSelection(getIntent().getIntExtra("class_time",0));
        } finally{

        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                Schedule newS = new Schedule(Schedule.TimeSchedule.fromInt(spinnerTime.getSelectedItemPosition())
                        ,etClassName.getText().toString(),getIntent().getStringExtra("day_code"));
                intentResult.putExtra(getString(R.string.KEY_VALUE),
                        newS);
                setResult(RESULT_OK,intentResult);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
