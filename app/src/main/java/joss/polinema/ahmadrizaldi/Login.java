package joss.polinema.ahmadrizaldi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    //membuat variabel button
    Button register;
    Button login;
    LoginButton loginFacebook;
    SignInButton loginGoogle;

    //membuat variabel edittext
    EditText email;
    EditText password;

    //inisialisasi variable yang berisi data string email dan password dari register
    String emailS, passwordS;
    String TAG = "Message";

    //variable global untuk authentication
    private FirebaseAuth mAuth;
    //inisialisasi callbcakmanager yang berfungsi sebagai bridge antara aplikasi dengan facebook
    CallbackManager callbackManager;

    //inisialisasi googleSignInClient
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //menngisi variabel dengan atribut di xml
        register = (Button)findViewById(R.id.keregister);
        login = (Button)findViewById(R.id.logins);
        email = (EditText)findViewById(R.id.emaillgn);
        password = (EditText)findViewById(R.id.passwordlgn);
        loginFacebook = (LoginButton) findViewById(R.id.loginFacebook);
        loginGoogle = (SignInButton) findViewById(R.id.loginGoogle);

        mAuth = FirebaseAuth.getInstance();

        //memberikan event ke button register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        //Memberikan Event ke button login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Login");
                progressDialog.show();
                //menset variabel email dengan edittext email
                emailS = email.getText().toString().trim();
                //menset variabel password dengan edittext password
                passwordS = password.getText().toString().trim();

                //Memanggil method signin
                signin(emailS, passwordS);

            }
        });

//        printHashKey();

        //Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        //izin untuk mengakses email dari fecabook
        loginFacebook.setReadPermissions("email");
        //memberikan event ke button loginFacebook
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInFacebook();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        //memberikan event ke button loginGoogle
        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

    }

    //method untuk mengintegrasikan login dengan google
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    //method jika proses di loginFacebook berhasil maka proses ke method handleFacebookAccessToken dijalankan
    private void signInFacebook() {
        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //mendapatkan token akses untuk pengguna yang sedang login dan
    //ditukarkan dengan kredensial Firebase,
    //lalu dilakukan autentikasi dengan Firebase menggunakan kredensial Firebase:
    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //memberikan hasil dari activity ke facebook SDK
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode,resultCode,data);

        //untuk login google
        //hasil yang dikembalikan dari intent yang berasal dari GoogleSignInApi
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                //Jika google sign in berhasil akan dilakukan
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    //untuk mendapatkan token ID dari objek GoogleSignInAccount dan
    //ditukarkan dengan kredensial Firebase, dan dilakukan autentikasi dengan Firebase menggunakan kredensial Firebase yang didapatkan
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    //untuk mendapatkan hashkey aplikasi yang digunakan pada facebook developer
    private void printHashKey() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("joss.polinema.ahmadrizaldi", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures)
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.e("KEYHASH", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }

        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //Menset user yang berhasil login ke variabel
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//            updateUI(currentUser);
    }

    //untuk mengganti interface ketika login berhasil dilakukan menuju activity selanjutnya
    private void updateUI(FirebaseUser currentUser){
        //Melakukan perpindahan halaman setelah uodate UI ke main activity

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    //untuk proses login menggunakan email dan password firebase
    private void signin(String emails, String passwords){
        //melakukan signin dengan email dan oassword
        mAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //Menset user yang berhasil login ke variabel
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //menampilkan pesan dengan toast
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
