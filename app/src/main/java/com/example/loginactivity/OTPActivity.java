package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPActivity extends AppCompatActivity {
    TextView alert;
    PinView pinView;
    Button btnverify;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        alert=findViewById(R.id.alert);
        pinView=findViewById(R.id.pinView);
        btnverify=findViewById(R.id.btnVerify);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        alert.setVisibility(View.GONE);
        mAuth=FirebaseAuth.getInstance();
        String verificationId = getIntent().getStringExtra("verificationId");
        String number = getIntent().getStringExtra("number");
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, pinView.getText().toString());
               signInWithPhoneAuthCredential(credential);
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            SendToHomeActivity();

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void SendToHomeActivity() {
        Intent intent=new Intent(OTPActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
};