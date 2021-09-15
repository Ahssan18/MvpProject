package uk.co.falcona.mvpproject.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.co.falcona.mvpproject.SignUp.SignUpPresenter;

public class LoginPresenter {
    private String TAG = "LoginPresenter";
    private SignUpPresenter.View view;
    private Context context;
    private FirebaseAuth mAuth;

    public LoginPresenter(SignUpPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email, String password) {
        view.showProgressbar(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            view.showProgressbar(false);
                            view.success("");
                        } else {
                            view.showProgressbar(false);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            view.Failure(task.getException());

                        }
                    }
                });
    }


}
