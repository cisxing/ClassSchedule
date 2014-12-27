package aitmyhelloapp.examples.android.ait.hu.classschedulefinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.R;
import aitmyhelloapp.examples.android.ait.hu.classschedulefinal.Schedule.Schedule;

/**
 * Created by xinyunxing on 11/19/2014.
 */
public class ScheduleAdapter extends BaseAdapter {

    private Context context;
    private List<Schedule> classSchedule;

    public ScheduleAdapter(Context context, List<Schedule> classSchedule) {
        this.context = context;
        this.classSchedule = classSchedule;
    }

    public void addSchedule(Schedule Item) {
        classSchedule.add(Item);
    }

    public void removeSchedule(int index) {
        classSchedule.remove(index);
    }
    @Override
    public int getCount() {
        return classSchedule.size();
    }

    @Override
    public Object getItem(int position) {
        return classSchedule.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Schedule> getClassSchedule() {
        return classSchedule;
    }



    static class ViewHolder {
        TextView tvTime;
        TextView tvClassName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v== null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_schedule, null);
            ViewHolder holder = new ViewHolder();
            holder.tvTime = (TextView) v.findViewById(R.id.tvTime);
            holder.tvClassName= (TextView) v.findViewById(R.id.tvClassName);
            v.setTag(holder);
        }

        final Schedule schedule = classSchedule.get(position);
        if(schedule != null)
        {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.tvClassName.setText(schedule.getClassName());
            holder.tvTime.setText(schedule.getTimeSchedule().getStringValue());
        }
        return v;
    }
}
