package joss.polinema.ahmadrizaldi.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import joss.polinema.ahmadrizaldi.Exit;
import joss.polinema.ahmadrizaldi.Login;
import joss.polinema.ahmadrizaldi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    String emailT = " ";

    public UserFragment() {
        // Required empty public constructor
    }

    public void setData(String email){
        this.emailT = email;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView email = (TextView)view.findViewById(R.id.US_email);
        TextView poin = (TextView)view.findViewById(R.id.US_poinUser);
        Button exita = (Button)view.findViewById(R.id.US_Exit);

        email.setText(emailT);

        exita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Exit.class);
                getActivity().finish();
                startActivity(intent);


            }
        });



        return view;

    }

}
