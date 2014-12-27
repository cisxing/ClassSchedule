package aitmyhelloapp.examples.android.ait.hu.classschedulefinal;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.Schedule.Schedule;
import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.adapter.ScheduleAdapter;
import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.dummy.DummyContent;

/**
 * A fragment representing a single Day detail screen.
 * This fragment is either contained in a {@link DayListActivity}
 * in two-pane mode (on tablets) or a {@link DayDetailActivity}
 * on handsets.
 */
public class DayDetailFragment extends ListFragment {

    public static final int REQUEST_NEW_CLASS_CODE = 100;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final int CONTEXT_ACTION_DELETE = 10;
    private static final int CONTEXT_ACTION_EDIT = 11;
    private static final String DAY_CODE = "day_code";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        List<Schedule> scheduleList2 = Schedule.find(Schedule.class, "day = ?",mItem.id);

        //I am trying this. Someone said that be careful with this because this may result in null if the fragment is detached
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity().getApplicationContext(), scheduleList2);
        //set the adapter of the list
        setListAdapter(adapter);
        //enable the option Menu
        setHasOptionsMenu(true);



    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.day_detail_action,menu);
        //can only register here because in the onCreate function the content view is not yet created
        registerForContextMenu(getListView());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //need to find the id and change it, probably a button that is touched
        if (id == R.id.action_new_class) {
            Intent i = new Intent();
            i.putExtra(DAY_CODE,mItem.id);
            //trying this out, may not work. I am getting the activity from this fragment
            i.setClass(this.getActivity(), CreateScheduleActivity.class);
            startActivityForResult(i, REQUEST_NEW_CLASS_CODE);

            return true;
        }
        if(id==R.id.action_new_deleteAll) {
            showConfirmation("You are deleting all classes!");
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            while (!getListAdapter().isEmpty()) {
                Schedule schedule = (Schedule) getListAdapter().getItem(0);
                schedule.delete();
                ((ScheduleAdapter) getListAdapter()).removeSchedule(0);
            }

            ((ScheduleAdapter)getListAdapter()).notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean booked = false;
        switch (resultCode) {
            case Activity.RESULT_OK:
                //what does this serializable do? Very confusing
                Schedule schedule = (Schedule) data.getSerializableExtra("KEY_PLACE");
               for(int i=0; i<getListAdapter().getCount();i++)
               {

                   Schedule current = (Schedule) getListAdapter().getItem(i);
                   if (schedule.getTimeSchedule().getValue()==current.getTimeSchedule().getValue())
                   {
                       booked = true;
                       showMessage("This time is already blocked, please find a different time or edit the existing class");
                       break;
                   }
               }
                if(!booked) {
                    schedule.save();

                    showConfirmation("Class: "+schedule.getClassName()+" is added!");
                    ((ScheduleAdapter) getListAdapter()).addSchedule((Schedule) data.getSerializableExtra("KEY_PLACE"));
                    ((ScheduleAdapter) getListAdapter()).notifyDataSetChanged();
                }
                //Toast.makeText(this, "Item added to the shopping list!", Toast.LENGTH_LONG).show();
                break;
            case Activity.RESULT_CANCELED:
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                break;

       }




    }

    private void showMessage(String msg){
        //display dialog fragment with the message
        ErrorMessageFragment dialog = new ErrorMessageFragment();
        Bundle b = new Bundle();
        b.putString(ErrorMessageFragment.KEY_MSG,msg);
        dialog.setArguments(b);
        dialog.show(getFragmentManager(),ErrorMessageFragment.TAG);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Menu");
        menu.add(0, CONTEXT_ACTION_DELETE, 0, "Delete");
        menu.add(0,CONTEXT_ACTION_EDIT,0,"Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_ACTION_DELETE) {
            showConfirmation("You deleted this class");
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Schedule schedule = (Schedule) getListAdapter().getItem(info.position);
            //would this freak out????
            schedule.delete();
            ((ScheduleAdapter) getListAdapter()).removeSchedule(info.position);
            ((ScheduleAdapter) getListAdapter()).notifyDataSetChanged();
        }

        else if (item.getItemId() == CONTEXT_ACTION_EDIT) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Schedule schedule = (Schedule) getListAdapter().getItem(info.position);
            Intent i = new Intent();
            i.putExtra(DAY_CODE,mItem.id);
            i.putExtra("class_name",schedule.getClassName());
            i.putExtra("class_time",schedule.getTimeSchedule().getValue());
            //trying this out, may not work. I am getting the activity from this fragment
            i.setClass(this.getActivity(), CreateScheduleActivity.class);
            startActivityForResult(i, REQUEST_NEW_CLASS_CODE);
            schedule.delete();
            ((ScheduleAdapter) getListAdapter()).removeSchedule(info.position);
            ((ScheduleAdapter) getListAdapter()).notifyDataSetChanged();


        }else {
            return false;
        }
        return true;
    }


    private void showConfirmation(String msg){
        //display dialog fragment with the message
        ConfirmationFragment dialog = new ConfirmationFragment();
        Bundle b = new Bundle();
        b.putString(ConfirmationFragment.KEY_MSG,msg);
        dialog.setArguments(b);
        dialog.show(getFragmentManager(),ConfirmationFragment.TAG);

    }
}
