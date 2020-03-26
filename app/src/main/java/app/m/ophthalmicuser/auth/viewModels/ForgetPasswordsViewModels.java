package app.m.ophthalmicuser.auth.viewModels;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.Code;

import app.m.ophthalmicuser.auth.model.ForgetPassword;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;


public class ForgetPasswordsViewModels extends BaseViewModel {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ForgetPassword forgetPassword;

    public ForgetPasswordsViewModels() {
        forgetPassword = new ForgetPassword();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public ForgetPassword getForgetPassword() {
        return forgetPassword;
    }

    public void forgetPasswordAction() {
        if (getForgetPassword().getEmail() != null) {
            accessLoadingBar(View.VISIBLE);
            mAuth.sendPasswordResetEmail(getForgetPassword().getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            accessLoadingBar(View.GONE);
                            if (task.isSuccessful()) {
                                setReturnedMessage("Please check your mail");
                                toBack();
                            }
                        }
                    });
        }
    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);

    }
}
