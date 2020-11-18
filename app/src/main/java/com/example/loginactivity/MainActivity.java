package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

//    TextView alert;
    TextView alert1;
    CountryCodePicker ccp;
    EditText inputNumber;
    Button btnGenerate;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String Fullnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        alert=findViewById(R.id.alert);
        alert1=findViewById(R.id.alert1);
        ccp=findViewById(R.id.ccp);
        inputNumber=findViewById(R.id.inputNumber);
        btnGenerate=findViewById(R.id.btnGenerate);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
//        alert.setVisibility(View.GONE);
        alert1.setVisibility(View.GONE);
        mAuth=FirebaseAuth.getInstance();

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            private String number;

            @Override
            public void onClick(View view) {
               number = inputNumber.getText().toString();
               if (number.length()==0)
               {
                   Toast.makeText(MainActivity.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   String fullName="+"+ccp.getFullNumber()+number;
                   AttemptAuth(fullName);

               }

            }

            private void AttemptAuth(String fullName) {
                Fullnumber=fullName;
                progressBar.setVisibility(View.VISIBLE);
                alert1.setText("Please Wait");
                alert1.setVisibility(View.VISIBLE);


                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(Fullnumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
          private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {


                    Object onVerificationCompleted;
                    Log.d("TAG", "onVerificationCompleted:" + credential);

                    signInWithPhoneAuthCredential(credential);
                }

               @Override
                public void onVerificationFailed(FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w("TAG", "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        // ...
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    // Show a message and update the UI
                    // ...
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                        @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Intent intent=new Intent(MainActivity.this,OTPActivity.class);
                    String name;
                    intent.putExtra("verificationId",verificationId);
                    intent.putExtra("number", Fullnumber);
                    Log.d("TAG", "onCodeSent:" + verificationId);

                    // Save verification ID and resending token so we can use them later
                   // mVerificationId = verificationId;
                    //mResendToken = token;

                    // ...
                }
            };
            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithCredential:success");
                                    SendToHomeActivity();
                                    FirebaseUser user = task.getResult().getUser();
                                    // ...
                                } else {
                                    // Sign in failed, display a message and update the UI
                                    Log.w("TAG", "signInWithCredential:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        // The verification code entered was invalid
                                    }
                                }
                            }
                            private void SendToHomeActivity() {
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

            }
        });
    }
}