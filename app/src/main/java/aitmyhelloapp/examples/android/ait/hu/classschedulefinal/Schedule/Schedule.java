package aitmyhelloapp.examples.android.ait.hu.classschedulefinal.Schedule;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by xinyunxing on 11/19/2014.
 */
public class Schedule extends SugarRecord<Schedule> implements Serializable {

    public enum TimeSchedule{
        first("9:00-11:00",0),
        second("11:00-13:00",1),
        third("14:00-16:00",2),
        fourth("16:00-18:00",3);

        private String StringValue;
        private int value;


        private TimeSchedule(String toString, int value1){
            StringValue = toString;
            value = value1;
        }

        public static TimeSchedule fromInt(int value) {
            for (TimeSchedule c : TimeSchedule.values()) {
                if (c.value == value) {
                    return c;
                }
            }
            return first;
        }

        public int getValue(){return value;}

        public String getStringValue() {
            return StringValue;
        }
    }

    private TimeSchedule timeSchedule;
    private String className;
    private String day= "1";
    //private long id;


    //constructor
    public Schedule(TimeSchedule timeSchedule,String className, String day)
    {
        this.timeSchedule = timeSchedule;
        this.className= className;
        this.day= day;
    }

    //empty constructor for sugar orm
    public Schedule()
    {

    }
    //some setter and getter methods
    public TimeSchedule getTimeSchedule() {
        return timeSchedule;
    }

    public String getClassName() {
        return className;
    }



    public void setTimeSchedule(TimeSchedule timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}
