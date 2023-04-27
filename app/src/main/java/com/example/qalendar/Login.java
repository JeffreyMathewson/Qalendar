package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentSender;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;

public class Login extends AppCompatActivity {

    // Part 1.
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    // Indicates that a method declaration is intended to override a method declaration in a supertype.
    // If a method is annotated with this annotation type compilers are required to generate an error
        // message unless at least one of the following conditions hold:
            // The method does override or implement a method declared in a supertype.
            // The method has a signature that is override-equivalent to that of any public method declared in Object.
    @Override
    // I am going to import the Nullable class with the .firebase parameter.
        // I hope I don't mess anything up.
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                /*.setPasswordRequestOptions(PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()) */
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))   // qalendar-6e694
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        // ...
        BeginSignInRequest signUpRequest;
        oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>()) {

             @Override
                public void onSuccess(BeginSignInResult result) {
                    try {
                        startIntentSenderForResult(
                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                            null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    }
                }
            })
            .addOnFailureListener(this, new OnFailureListener()) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d(TAG, e.getLocalizedMessage());
                }
            };
    }


    // Part 2
    /*oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>()) {
        @Override
        public void onSuccess(BeginSignInResult result) {
            try {
                startIntentSenderForResult(
                        result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                        null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
            }
        }
    })
            .addOnFailureListener(this, new OnFailureListener()) {
        @Override
        public void onFailure(@NonNull Exception e) {
            // No saved credentials found. Launch the One Tap sign-up flow, or
            // do nothing and continue presenting the signed-out UI.
            Log.d(TAG, e.getLocalizedMessage());
        }
    };*/


    // Part 3
    // ...
    /*private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    // ...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.");
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.");
                    }
                } catch (ApiException e) {
                    // ...
                }
                break;
        }
    }*/
    // ...
}