package app.m.ophthalmicuser.profile.viewModels;


import android.app.ProgressDialog;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.rpc.Code;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;


public class ProfileViewModels extends BaseViewModel {
    private UserData userData;
    public Uri filePath;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public ProfileViewModels() {
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userData = new UserData();
        setUserData(UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData());
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void updateProfileImage() {
        if (filePath != null) {

//            // Code for showing progressDialog while uploading
//            ProgressDialog progressDialog
//                    = new ProgressDialog(MyApplication.getInstance());
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
            accessLoadingBar(View.VISIBLE);
            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "profile/"
                                    + auth.getCurrentUser().getUid());
            final UploadTask uploadTask = ref.putFile(filePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    setReturnedMessage(e.getMessage());
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Log.e("then", "then: " + task.getResult().toString());
                                updateUerImage(task.getResult().toString());
                            } else {
                                setReturnedMessage(task.getException().getMessage());
                                getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                            }
                        }
                    });
                }
            });
        }
    }

    private void updateUerImage(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            //create hashMap to store name and image of user
            Map<String, Object> user_Map = new HashMap<>();
            user_Map.put("image", imgUrl);
            //create collection (users table ) and insert user id and Map
            db.collection("Users").document(auth.getCurrentUser().getUid()).update(user_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        accessLoadingBar(View.GONE);
                        setReturnedMessage("Image Updated Successfully");
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_SUCCESS);
                        getUserData().setImage(imgUrl);
                        UserPreferenceHelper.getInstance(MyApplication.getInstance()).userLogin(getUserData());
                    } else {
                        setReturnedMessage(task.getException().getMessage());
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                    }
                    accessLoadingBar(View.GONE);

                }
            });
        }
    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);
    }

    public void selectImage() {
        getClicksMutableLiveData().setValue(Codes.SELECT_PROFILE_IMAGE);
    }

    public void toUpdateAuth() {
        getClicksMutableLiveData().setValue(Codes.UPDATE_AUTH);
    }

    public void toUpdateData() {
        getClicksMutableLiveData().setValue(Codes.UPDATE_DATA);
    }

    @BindingAdapter({"userImage"})
    public static void loadImage(ImageView view, String countryImage) {
        if (!TextUtils.isEmpty(countryImage))
            Picasso.get().load(countryImage).placeholder(R.color.overlayBackground).into(view);
    }
}
