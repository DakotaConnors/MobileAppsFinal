package edu.coloradomesa.myproject;

import java.util.Vector;

/**
 * Created by Dakota on 10/17/2017.
 */

public class CT1 {
    public String name = "CT1";
    String label = "";
    public String ProjectName = "";
    public int ProjectLevel;
    public Vector<CT2> CT2s = new Vector<CT2>();
    public boolean InUse;

    public CT1(String _name)
    {
        ProjectName = _name;
        InUse = true;
    }

    public CT1(boolean _InUse)
    {
        InUse = _InUse;
    }

    public String PrintCT2s()
    {
        label += name + "\n";

        for (int i = 0; i < CT2s.size(); i++)
        {
            label += CT2s.elementAt(i).PrintCT2();
            CT2s.elementAt(i).CleanLabel();
        }
        /*foreach (CT2 a in CT2s)
        {
            label += a.PrintCT2();
            a.CleanLabel();
        }*/
        return label;
    }

    public void CleanLabel()
    {
        label = "";
    }
}
