package joss.polinema.ahmadrizaldi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    Button register;
    Button login;
    EditText email;
    EditText password;

    String emailS, passwordS;

    private FirebaseAuth mAuth;
    String TAG = "Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button)findViewById(R.id.Daftarrgs);
//        login = (Button)findViewById(R.id.kelogin);
        email = (EditText)findViewById(R.id.emailrgs);
        password = (EditText)findViewById(R.id.passwordrgs);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menset nilai dari string email dengan nilai yang ada pada edittext email
                emailS = email.getText().toString().trim();
                //menset nilai dari string password dengan nilai yang ada pada edittext password
                passwordS = password.getText().toString().trim();

                //Memanggil method signnin
                signUp(emailS, passwordS);
            }
        });


    }

    private void updateUI(FirebaseUser currentUser){
        //Melakukan perpindahan halaman setelah uodate UI ke main activity
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);

    }

    private void signUp(String email, String password){
        //Melakukan register user ke firebase dengan menggunakan firebase auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
