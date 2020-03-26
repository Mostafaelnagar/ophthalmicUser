package app.m.ophthalmicuser.auth.viewModels;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.rpc.Code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.m.ophthalmicuser.auth.model.LoginRequest;
import app.m.ophthalmicuser.auth.model.RegisterRequest;
import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;


public class AuthViewModels extends BaseViewModel {
    LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public AuthViewModels() {
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    public void login() {
        getClicksMutableLiveData().setValue(Codes.CHECK_ERRORS);
    }

    public void loginAction() {
        accessLoadingBar(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(getLoginRequest().getEmail(), getLoginRequest().getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    accessLoadingBar(View.GONE);
                    setReturnedMessage(task.getException().getMessage());
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                } else {
                    final String user_Id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> token_Map = new HashMap<>();
                    token_Map.put("token", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getToken());
                    db.collection("Users").document(user_Id).update(token_Map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getUserData(user_Id);
                                }
                            });

                }
            }
        });
    }

    private void getUserData(String user_Id) {
        db.collection("Users").document(user_Id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserData userData = new UserData();
                        Map<String, Object> objectMap = new HashMap<>();
                        objectMap.putAll(document.getData());
                        for (Map.Entry me : objectMap.entrySet()) {
                            String key = me.getKey().toString();
                            String value = me.getValue().toString();
                            if (key.equals("user_name")) {
                                userData.setUser_name(value);
                            }
                            if (key.equals("image")) {
                                userData.setImage(value);
                            }
                            if (key.equals("type")) {
                                userData.setType(value);
                            }
                            if (key.equals("phone")) {
                                userData.setPhone(value);
                            }
                        }
                        userData.setId(user_Id);
                        userData.setEmail(getLoginRequest().getEmail());
                        UserPreferenceHelper.getInstance(MyApplication.getInstance()).userLogin(userData);
                        accessLoadingBar(View.GONE);
                        getClicksMutableLiveData().setValue(Codes.HOME_SCREEN);

                    } else {
                        setReturnedMessage("User Does not exists");
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                    }
                } else {
                    setReturnedMessage(task.getException().getMessage());
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                }
            }
        });
    }

    public void signUp() {
        getClicksMutableLiveData().setValue(Codes.CHECK_ERRORS);
    }

    public void signUpAction() {
        accessLoadingBar(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(getRegisterRequest().getEmail(), getRegisterRequest().getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String user_Id = mAuth.getCurrentUser().getUid();
                    addToUsers(user_Id);
                } else {
                    setReturnedMessage(task.getException().getMessage());
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                    accessLoadingBar(View.GONE);
                }
            }
        });
    }

    private void addToUsers(String user_Id) {
        Map<String, String> doctor_map = new HashMap<>();
        doctor_map.put("user_name", getRegisterRequest().getName());
        doctor_map.put("type", "0");
        doctor_map.put("phone", getRegisterRequest().getPhone());
        doctor_map.put("image", "");
        doctor_map.put("token", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getToken());
        db.collection("Users").document(user_Id).set(doctor_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    setReturnedMessage("Logged In");
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_SUCCESS);
                    getClicksMutableLiveData().setValue(Codes.HOME_SCREEN);
                    UserPreferenceHelper.getInstance(MyApplication.getInstance()).userLogin(new UserData(
                            getRegisterRequest().getName(), getRegisterRequest().getPhone(), getRegisterRequest().getEmail(),
                            mAuth.getCurrentUser().getUid(), "1", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getToken(), ""));
                } else {
                    setReturnedMessage(task.getException().getMessage());
                    getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                }
                accessLoadingBar(View.GONE);
            }
        });
    }

    public void toForget() {
        getClicksMutableLiveData().setValue(Codes.SEND_CODE_SCREEN);
    }

    public void toSignUp() {
        getClicksMutableLiveData().setValue(Codes.REGISTER_SCREEN);
    }

}
