package edu.coloradomesa.myproject;

import java.util.Vector;

/**
 * Created by Dakota on 10/17/2017.
 */

public class CT4 {
    public String name = "CT4";
    public String label = "";
    public Vector<Task> Tasks = new Vector<Task>();
    public boolean InUse;


    public CT4(String _name)
    {
        name = _name;
        InUse = true;
    }

    public CT4(boolean _InUse)
    {
        InUse = _InUse;
    }

    public String PrintCT4()
    {
        label += " -" + name + "\n";
        for (int i = 0; i < Tasks.size(); i++)
        {
            label += Tasks.elementAt(i).PrintTask();
        }
        /*
        foreach (Task a in Tasks)
        {
            label += a.PrintTask();
        }*/
        return label;
    }

    public void CleanLabel()
    {
        label = "";
    }
}
