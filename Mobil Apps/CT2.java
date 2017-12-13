package edu.coloradomesa.myproject;

import java.util.Vector;

/**
 * Created by Dakota on 10/17/2017.
 */

public class CT2 {
        public String name = "CT2";
        String label = "";
        public Vector<CT3>CT3s = new Vector<CT3>();
        public boolean InUse;


        public CT2(String _name)
        {
            name = _name;
            InUse = true;
        }

        public CT2(boolean _InUse)
        {
            InUse = _InUse;
        }

        public String PrintCT2()
        {
            label += name + "\n";
            for (int i = 0; i < CT3s.size(); i++)
            {
                label += CT3s.elementAt(i).PrintCT3();
                CT3s.elementAt(i).CleanLabel();
            }
            /*foreach (CT3 a in CT3s)
            {
                label += a.PrintCT3();
                a.CleanLabel();
            }*/
            return label;
        }

        public void CleanLabel()
        {
            label = "";
        }
}
