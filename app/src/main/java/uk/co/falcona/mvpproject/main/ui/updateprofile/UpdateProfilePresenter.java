package uk.co.falcona.mvpproject.main.ui.updateprofile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import uk.co.falcona.mvpproject.Helper;

public class UpdateProfilePresenter {
    private String TAG = "UpdateProfilePresenter";
    View view;
    Context context;
    StorageReference storageRef;
    Profile profile;
    private DatabaseReference mDatabase;
// ...

    public UpdateProfilePresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        storageRef = FirebaseStorage.getInstance().getReference();
        profile = new Profile();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void getDataFromDb() {
        view.showProgress(true);
        try {
            Log.e(TAG, "getDataFromDb =>path" + mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()
            ));
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()
            ).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    view.showProgress(false);
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    view.dataFromServer(profile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    view.showProgress(false);
                    Log.e(TAG, "getDataFromDb => " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void submitData(String name, String email, String phone) {

        if (!name.isEmpty()) {
            if (!email.isEmpty()) {
                if (!phone.isEmpty()) {
                    view.showProgress(true);
                    profile.setPic(Helper.getData(context,Helper.image));
                    profile.setEmail(email);
                    profile.setName(name);
                    profile.setPhone(phone);
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile)
                            .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    view.showProgress(false);
                                    task.isSuccessful();
                                    {
                                        Helper.setData(context, name, Helper.name);
                                        Helper.setData(context, email, Helper.email);
                                        Helper.setData(context, phone, Helper.phone);
                                        view.success("Profile Updated Successfully");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            view.showProgress(false);
                            view.Failure(e);
                        }
                    });
                } else {
                    Exception e = new Exception("phone is empty!");
                    view.Failure(e);
                }
            } else {
                Exception e = new Exception("email is empty!");
                view.Failure(e);
            }
        } else {
            Exception e = new Exception("Name is empty!");
            view.Failure(e);
        }
    }

    void ProfileUpdated(Bitmap bitmap) {
        view.showProgress(true);
        if (bitmap != null) {
            int name = new Random().nextInt(1000000);
            StorageReference mountainImagesRef = storageRef.child("images/" + name + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mountainImagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    view.Failure(exception);
                    view.showProgress(false);
                    Log.e(TAG, "UploadTask=> onFailure" + exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    view.showProgress(false);
                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e(TAG, "UploadTask=> onSuccess" + uri);
                            profile.setPic(uri.toString());
                            Helper.setData(context, uri.toString(), Helper.image);
                            view.setProfile(uri.toString());
                        }
                    });
                }
            });
        } else {
            Exception exception = new Exception("Image cannot be empty");
            view.Failure(exception);
        }
    }

    interface View {
        void success(String message);

        void Failure(Exception e);

        void showProgress(Boolean b);

        void setProfile(String bitmap);

        void dataFromServer(Profile profile);
    }
}
