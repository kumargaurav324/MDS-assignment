package example.metadesignassignment.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import example.metadesignassignment.Id_Generator;
import example.metadesignassignment.R;
import example.metadesignassignment.database.Employee_Database;
import example.metadesignassignment.database.SignUp_Data;
import example.metadesignassignment.database.Use_Database;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editText_first_name;
    EditText editText_last_name;
    EditText editText_email;
    private boolean validation=false;
    private boolean authentication=false;
    Use_Database use_database;


    //private OnFragmentInteractionListener mListener;

    public SignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
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
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("SignUp");

        editText_first_name=(EditText)view.findViewById(R.id.first_name);
        editText_last_name=(EditText)view.findViewById(R.id.last_name);
        editText_email=(EditText)view.findViewById(R.id.email);

        Button button_signup=(Button)view.findViewById(R.id.signup);
        button_signup.setOnClickListener(this);

        use_database=new Use_Database(getActivity());

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();

        switch (v.getId())
        {
            case R.id.signup:
                validation=validate_data();
                if(validation==true)
                {
                    authentication=authenticate();
                    if (authentication==true)
                    {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,new Profile()).commit();

                    }
                    else
                    {
                        Snackbar.make(getView(), "User already exists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
        }

    }
    private boolean validate_data()
    {
        String first_name=editText_first_name.getText().toString();
        String last_name=editText_last_name.getText().toString();
        String email=editText_email.getText().toString();
        if(first_name.equals(""))
        {
            Snackbar.make(getView(), "Enter first name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if(last_name.equals(""))
        {
            Snackbar.make(getView(), "Enter last name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if(email.equals(""))
        {
            Snackbar.make(getView(), "Enter email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else
        {
            if(isValidEmail(email))
            {
                return true;
            }
            else
            {
                Snackbar.make(getView(), "Enter valid email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return false;
            }

        }

    }
    public final boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private boolean authenticate()
    {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);

        String first_name=editText_first_name.getText().toString();
        String last_name=editText_last_name.getText().toString();
        String email=editText_email.getText().toString();
        Cursor c=use_database.getdata_table1(email);
        if(c.getCount()==1)
        {
            return false;
        }
        else
        {
            Id_Generator id_generator=new Id_Generator(getActivity());
            String id=id_generator.getId();

            SignUp_Data signUp_data=new SignUp_Data();
            signUp_data.setFirst_name(first_name);
            signUp_data.setLast_name(last_name);
            signUp_data.setEmail(email);
            signUp_data.setId(id);

            long a=use_database.save_table1(signUp_data);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("username",email);
            editor.putString("id",id);
            editor.commit();

            if(a<0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
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
        toolbar.setTitle("SignUp");
    }
}
