package edu.coloradomesa.myproject;

import java.util.Vector;

/**
 * Created by Dakota on 10/17/2017.
 */

public class CT3 {
    public String name = "CT3";
    String label = "";
    public Vector<CT4> CT4s = new Vector<CT4>();
    public boolean InUse;


    public CT3(String _name)
    {
        name = _name;
        InUse = true;
    }

    public CT3(boolean _InUse)
    {
        InUse = _InUse;
    }

    public String PrintCT3()
    {
        label += name + "\n";
        for (int i = 0; i < CT4s.size(); i++)
        {
            label += CT4s.elementAt(i).PrintCT4();
            CT4s.elementAt(i).CleanLabel();
        }
        /*foreach (CT4 a in CT4s)
        {
            label += a.PrintCT4();
            a.CleanLabel();
        }*/
        return label;
    }

    public void CleanLabel()
    {
        label = "";
    }
}
