package edu.coloradomesa.myproject;

/**
 * Created by Dakota on 10/17/2017.
 */

public class Task {
    public String name = "TASK0";
    public boolean done = false;
    public String year = "0000", month = "00", day = "00";
    public boolean daily = false;

    public Task(String _name)
    {
        name = _name;
    }

    public void DoTask()
    {
        if (!done) done = true;
        else done = false;
    }

    public void SetDueDate(String _year, String _month, String _day)
    {
        year = _year;
        month = _month;
        day = _day;
    }

    public String PrintTask()
    {
        return name + "\n";
    }
}
