package uk.co.falcona.mvpproject.main.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostsPresenter {
    View view;
    private String TAG="PostsPresenter";
    Context context;
    private List<Posts> posts;
    DatabaseReference reference;


    public PostsPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference();
        posts = new ArrayList<>();
    }

    void loadData() {
        view.showProgress(true);
        reference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                        Posts post = childSnapshot.getValue(Posts.class);
                        posts.add(post);
                        view.success(posts);
                        view.showProgress(false);
                        Log.e(TAG,"loadData => onDataChange"+post.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e(TAG,"loadData => onFailure"+error.getMessage());
                Exception exception=new Exception(error.getMessage());
                view.Failure(exception);
                view.showProgress(false);
            }
        });
        /*reference.child("posts").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.e(TAG,"loadData => onSuccess"+dataSnapshot);
                Posts post = dataSnapshot.getValue(Posts.class);
                posts.add(post);
                view.success(posts);
                view.showProgress(false);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG,"loadData => onFailure"+e.getMessage());
                view.Failure(e);
                view.showProgress(false);
            }
        });*/
    }

    interface View {
        void showProgress(Boolean b);

        void success(List<Posts> list);

        void Failure(Exception e);
    }
}
