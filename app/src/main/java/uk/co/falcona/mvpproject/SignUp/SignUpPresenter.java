package uk.co.falcona.mvpproject.SignUp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.co.falcona.mvpproject.SignUp.models.User;

public class SignUpPresenter {
    public String TAG = "MainActivityPresenter";
    View view;
    User user;
    private Context context;
    private FirebaseAuth mAuth;

    public SignUpPresenter(View view, Context context) {
        this.view = view;
        user = new User();
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            view.success("");
            Log.e(TAG, "currentUser =>" + currentUser.getEmail());
        }
    }

    public void createAccount(User data) {
        boolean valid = validateUser(data);
        if (valid) {
            view.showProgressbar(true);
            mAuth.createUserWithEmailAndPassword(data.getEmail(), data.getPassword())
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                view.showProgressbar(false);
                                view.success("account created successfully");
                            } else {
                                view.showProgressbar(false);
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                view.Failure(task.getException());
                            }
                        }
                    });
        }


    }

    private boolean validateUser(User data) {
        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            if (data.getPassword() != null  && !data.getPassword().isEmpty()) {
                return true;
            } else {
                Exception exception = new Exception("password is empty ");
                view.Failure(exception);
                return false;
            }
        } else {
            Exception exception = new Exception("email is empty ");
            view.Failure(exception);
            return false;
        }
    }

    public interface View {
        void success(String date);

        void Failure(Exception e);

        void showProgressbar(boolean progress);
    }
}
