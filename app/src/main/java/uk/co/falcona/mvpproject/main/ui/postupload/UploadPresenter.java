package uk.co.falcona.mvpproject.main.ui.postupload;

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
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.UUID;

import uk.co.falcona.mvpproject.Helper;
import uk.co.falcona.mvpproject.SignUp.SignUpPresenter;
import uk.co.falcona.mvpproject.main.ui.home.Posts;

public class UploadPresenter {
    private String TAG = "UploadPresenter";
    SignUpPresenter.View view;
    FirebaseAuth auth;
    Context context;
    private DatabaseReference mDatabase;
    StorageReference storageRef;

    public UploadPresenter(SignUpPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void updateData(String title, Bitmap bitmap) {
        if (bitmap != null) {
            if (!title.isEmpty()) {
                int name = new Random().nextInt(1000000);
                StorageReference mountainImagesRef = storageRef.child("posts/" + name + ".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        view.Failure(exception);
                        view.showProgressbar(false);
                        Log.e(TAG, "UploadTask=> onFailure" + exception.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        view.showProgressbar(false);
                        mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e(TAG, "UploadTask=> onSuccess" + uri);
                                uploadPost(uri.toString(), title);
                            }
                        });
                    }
                });
            } else {
                Exception e = new Exception("Title is required");
                view.Failure(e);
            }
        } else {
            Exception e = new Exception("Image is required");
            view.Failure(e);
        }
    }

    private void uploadPost(String toString, String title) {
        Posts posts = new Posts();
        posts.setImage(toString);
        posts.setTitle(title);
        posts.setName(Helper.getData(context, Helper.name));
        posts.setProfile(Helper.getData(context, Helper.image));
        posts.setId(String.valueOf(UUID.randomUUID()));
        view.showProgressbar(true);
        mDatabase.child("posts").push().setValue(posts)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        view.showProgressbar(false);
                        task.isSuccessful();
                        {
                            view.success("Profile Updated Successfully");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                view.showProgressbar(false);
                view.Failure(e);
            }
        });
    }
}
