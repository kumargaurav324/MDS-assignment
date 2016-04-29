package example.metadesignassignment.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import example.metadesignassignment.R;
import example.metadesignassignment.database.Employee_Database;
import example.metadesignassignment.database.Profile_Data;
import example.metadesignassignment.database.Use_Database;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editText_address;
    EditText editText_number;
    EditText editText_designation;
    EditText editText_depart;
    EditText editText_income;
    private boolean validation=false;
    private String username;
    private String Id;
    Use_Database use_database;
    int count;
    String address;
    String number;
    String designation;
    String depart;
    String income;



    //private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");

        editText_address=(EditText)view.findViewById(R.id.address);
        editText_number=(EditText)view.findViewById(R.id.number);
        editText_designation=(EditText)view.findViewById(R.id.designation);
        editText_depart=(EditText)view.findViewById(R.id.depart);
        editText_income=(EditText)view.findViewById(R.id.income);

        Button button_submit=(Button)view.findViewById(R.id.submit);
        button_submit.setOnClickListener(this);

        Button button_logout=(Button)view.findViewById(R.id.logout);
        button_logout.setOnClickListener(this);

        use_database=new Use_Database(getActivity());

        load_data();

        return view;
    }
    private void load_data()
    {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username","");
        Id=sharedPreferences.getString("id","");

        Cursor cursor=use_database.getdata_table2(Id);
        count=cursor.getCount();
        cursor.moveToNext();
        if(count==1)
        {
            address= cursor.getString(cursor.getColumnIndex(Employee_Database.COLUMN_NAME_ADDRESS));
            number= cursor.getString(cursor.getColumnIndex(Employee_Database.COLUMN_NAME_PHONE));
            designation= cursor.getString(cursor.getColumnIndex(Employee_Database.COLUMN_NAME_DESIGNATION));
            depart= cursor.getString(cursor.getColumnIndex(Employee_Database.COLUMN_NAME_DEPARTMENT));
            income= cursor.getString(cursor.getColumnIndex(Employee_Database.COLUMN_NAME_INCOME));

            editText_address.setText(address);
            editText_number.setText(number);
            editText_designation.setText(designation);
            editText_depart.setText(depart);
            editText_income.setText(income);

        }


    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit:
                validation=validate_data();
                if(validation==true)
                {
                    if(save_data())
                    {
                        Snackbar.make(getView(), "Profile successfully saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(getView(), "Profile not saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }
                break;
            case R.id.logout:

                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();

                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,new SignIn()).commit();
        }
    }
    private boolean validate_data()
    {
        address=editText_address.getText().toString();
        number=editText_number.getText().toString();
        designation=editText_designation.getText().toString();
        depart=editText_depart.getText().toString();
        income=editText_income.getText().toString();
        if (address.equals(""))
        {
            Snackbar.make(getView(), "Address is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (number.equals(""))
        {
            Snackbar.make(getView(), "Number is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (number.length()<10)
        {
            Snackbar.make(getView(), "Enter valid number", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (designation.equals(""))
        {
            Snackbar.make(getView(), "Designation is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (depart.equals(""))
        {
            Snackbar.make(getView(), "Department is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (income.equals(""))
        {
            Snackbar.make(getView(), "Income is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else
        {
            return true;
        }

    }

    private boolean save_data()
    {
        Profile_Data profile_data=new Profile_Data();
        profile_data.setId(Id);
        profile_data.setAddress(address);
        profile_data.setPhone(number);
        profile_data.setDesignation(designation);
        profile_data.setDepartment(depart);
        profile_data.setIncome(income);

        if (count==1)
        {
            int a=use_database.update_table2(Id,profile_data);
            if(a==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            long a=use_database.save_table2(profile_data);
            if(a>0)
            {
                return true;
            }
            else
            {
                return false;
            }



        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
    }
}
