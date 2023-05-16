package com.example.qalendar;

import static com.example.qalendar.CalendarUtils.daysInMonthArray;
import static com.example.qalendar.CalendarUtils.monthYearFromDate;
import static com.example.qalendar.Notifications.sendNotification;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qalendar.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
  
    //<editor-fold desc="Permission initialization">
    private static final int NOTIFICATION_PERMISSION_CODE = 1;
    private static final int PERMISSION_REQUEST_NOTIFICATION = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    //</editor-fold>

    SignInClient oneTapClient;
    BeginSignInRequest signInRequest;
    ImageView google_img;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    FirebaseFirestore firestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();
        //<editor-fold desc="Permission Requesting at runtime">
        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher, as an instance variable.
        ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // Permission is granted. Continue the action or workflow in your
                        // app.
                    } else {
                        // Explain to the user that the feature is unavailable because the
                        // feature requires a permission that the user has denied. At the
                        // same time, respect the user's decision. Don't link to system
                        // settings in an effort to convince the user to change their
                        // decision.
                        requestNotificationPermission();
                    }
                });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest
                        .PasswordRequestOptions.builder()
                        .setSupported(true).build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .setAutoSelectEnabled(true)
                .build();


        google_img = findViewById(R.id.google);
        firestore = FirebaseFirestore.getInstance();

        //configure google sign in
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        google_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                SignIn();
            }
        });

        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold desc="Notification Permission Request Method">
    private void requestNotificationPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification Permissions Required");
        builder.setMessage("Qalender requires notification permissions to reach its full potential. Please enable notifications in your settings, then re-launch the app.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ask for notification permission again
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    //</editor-fold>


    //<editor-fold desc="OnItemClick Action">
    @Override
    public void OnItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
            sendNotification(this, "TEST NOTIFICATION", "This has been a test of the notification system");

    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    
    private void checkUser() {
        //if user is already signed in, take them to Profile Activity class
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG, "checkUser: Already logged in");
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }
    //</editor-fold>

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                HomeActivity();
                //firebaseAuthWithGoogleAccount(account);

            }
            catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

//    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
//        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Log.d(TAG, "OnSuccess: Logged In");
//
//                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//                        String uid = firebaseUser.getUid();
//                        String email = firebaseUser.getEmail();
//
//                        Log.d(TAG, "OnSuccess: Email: "+email);
//                        Log.d(TAG, "OnSuccess: UID: "+uid);
//
//                        if (authResult.getAdditionalUserInfo().isNewUser()){
//                            Log.d(TAG, "OnSuccess: Account Created...\n"+email);
//                            Toast.makeText(MainActivity.this, "Account Created...\n"+email, Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Log.d(TAG, "OnSuccess: Existing User...\n: "+email);
//                            Toast.makeText(MainActivity.this, "Existing User...\n"+email, Toast.LENGTH_SHORT).show();
//                        }
//                        googleLoginAction();
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "OnFailure: Loggin Failed "+e.getMessage());
//                    }
//                });
//    }
//
//
//    public void googleLoginAction(){
//        startActivity(new Intent(this, ProfileActivity.class));
//    }


}