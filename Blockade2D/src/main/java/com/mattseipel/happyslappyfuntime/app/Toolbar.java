package com.mattseipel.happyslappyfuntime.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class Toolbar extends Fragment {
    private RadioButton brickBTN;
    private RadioButton concreteBTN;
    private RadioButton electricBTN;

    private boolean brickSelected = false;
    private boolean concreteSelected = false;
    private boolean electricSelected = false;

    public Toolbar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        brickBTN = (RadioButton)getActivity().findViewById(R.id.brickBTN);
        concreteBTN = (RadioButton)getActivity().findViewById(R.id.concreteBTN);
        electricBTN = (RadioButton)getActivity().findViewById(R.id.electricBTN);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toolbar, container, false);
    }


    public boolean isBrickSelected() {
        return brickSelected;
    }

    public void setBrickSelected(boolean brickSelected) {
        this.brickSelected = brickSelected;
    }

    public boolean isConcreteSelected() {
        return concreteSelected;
    }

    public void setConcreteSelected(boolean concreteSelected) {
        this.concreteSelected = concreteSelected;
    }

    public boolean isElectricSelected() {
        return electricSelected;
    }

    public void setElectricSelected(boolean electricSelected) {
        this.electricSelected = electricSelected;
    }
}
