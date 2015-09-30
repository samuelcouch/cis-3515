package edu.temple.checkerboard;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.view.View.OnClickListener;

import com.example.tud23264.lab3.R;


public class myFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private onClickButtonListener mListener;
    public interface onClickButtonListener {
        public void onNumberSelected(int number);
    }


    public myFragment1() {
        // Required empty public constructor


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String[] numbers = {"2", "4", "6", "8","10"};


        View v = inflater.inflate(R.layout.fragment_my_fragment1, container, false);
        final Spinner spin = (Spinner)v.findViewById(R.id.mySpinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, numbers);
        spin.setAdapter(stringArrayAdapter);

        Button myButton = (Button)v.findViewById(R.id.myButton);
        myButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spin.getSelectedItem().toString();
                mListener.onNumberSelected(Integer.parseInt(text));
            }


            });


        return v;
    }



    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        }
    }*/



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onClickButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
