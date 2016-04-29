package example.metadesignassignment.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import example.metadesignassignment.R;
import example.metadesignassignment.database.Employee_Database;
import example.metadesignassignment.database.Use_Database;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignIn.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignIn extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editText_username;
    EditText editText_password;
    private boolean validation=false;
    private boolean authentication=false;

    Use_Database use_database;

    //private OnFragmentInteractionListener mListener;

    public SignIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignIn.
     */
    // TODO: Rename and change types and number of parameters
    public static SignIn newInstance(String param1, String param2) {
        SignIn fragment = new SignIn();
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
        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("SignIn");

        Button button_signin=(Button)view.findViewById(R.id.signin);
        Button button_signup=(Button)view.findViewById(R.id.signup);
        editText_username=(EditText)view.findViewById(R.id.username);
        editText_password=(EditText)view.findViewById(R.id.password);

        button_signin.setOnClickListener(this);
        button_signup.setOnClickListener(this);

        use_database=new Use_Database(getActivity());

        return view;
    }
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();

        switch (v.getId())
        {
            case R.id.signin:
                ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setMessage("Signing in ...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);

                validation=validate_data();
                if(validation==true)
                {
                    authentication=authenticate();
                    if (authentication==true)
                    {
                        progress.dismiss();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,new Profile()).commit();

                    }
                    else
                    {
                        Snackbar.make(getView(), "Username or password invalid", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

                break;
            case R.id.signup:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,new SignUp(),"signup").addToBackStack("signup").commit();

                break;
        }

    }
    private boolean validate_data()
    {

        String username=editText_username.getText().toString();
        String password=editText_password.getText().toString();
        if(username.equals(""))
        {
            Snackbar.make(getView(), "Enter username", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if(password.equals(""))
        {
            Snackbar.make(getView(), "Enter password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;

        }
        else
        {
            return true;
        }
    }

    private boolean authenticate()
    {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        String username=editText_username.getText().toString();
        String password=editText_password.getText().toString();
        if (username.equals(password)) {
            Cursor c = use_database.getdata_table1(username);
            if (c.getCount() == 1) {
                c.moveToNext();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("id", c.getString(c.getColumnIndex(Employee_Database.COLUMN_NAME_ID)));
                editor.commit();

                return true;
            } else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
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
        toolbar.setTitle("SignIn");
    }
}
