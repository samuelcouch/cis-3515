package edu.temple.checkerboard;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tud23264.lab3.R;


public class myFragment2 extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;
    private OnFragmentInteractionListener mListener;
    String[] numbers;


    // TODO: Rename and change types and number of parameters


    public myFragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_fragment2, container, false);
        gridView = (GridView)v.findViewById(R.id.myGridView);
        numbers = getActivity().getResources().getStringArray(R.array.numbers);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            int n = args.getInt("num");
            gridView.setAdapter(new CustomAdapterClass(n * n, getActivity(), this.numbers));
            gridView.setNumColumns(n);
        }


    }

    public void newNumber(int n){

        gridView.setAdapter(new CustomAdapterClass(n * n, getActivity(), this.numbers));
        gridView.setNumColumns(n);
    }
    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
