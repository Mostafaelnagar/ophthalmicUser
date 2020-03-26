package app.m.ophthalmicuser.base;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import app.m.ophthalmicuser.BaseActivity;
import app.m.ophthalmicuser.MainActivity;
import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.constantsutils.Codes;


public class MovementManager {

    public static void pickImage(final Context context) {
        String choiceString[] = new String[]{"Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Select image from");
        dialog.setItems(choiceString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) {
                            intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        }
//                        else {
//                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        }
                        ((AppCompatActivity) context).startActivityForResult(Intent.createChooser(intent, "Select profile picture"), Codes.FILE_TYPE_IMAGE);
                    }
                }).show();

    }

    //---------Fragments----------//
    private static final int CONTAINER_ID = R.id.fl_home_container;

    public static void popAllFragments(Context context) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public static void addFragment(Context context, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(CONTAINER_ID, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }

    public static void removeFragment(Context context, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().remove(fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }

    public static void addHomeFragment(Context context, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fl_home_container, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }


    public static void replaceHomeFragment(Context context, int layout, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(layout, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }

    public static void replaceAuthFragment(Context context, int layout, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(layout, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }


    public static void popLastFragment(Context context) {
        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
    }


    //-----------Activities-----------------//

    public static void startBaseActivity(Context context, int page) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        context.startActivity(intent);
        ((Activity) context).finishAffinity();
    }

    public static void startActivity(Context context, int page) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        context.startActivity(intent);
    }

    public static void startActivityWithBundle(Context context, int page, int from) {
        Log.e("startActivityWithBundle", "startActivityWithBundle: " + from);
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        intent.putExtra(Params.BUNDLE, from);
        context.startActivity(intent);
    }

    public static void startActivityWithObject(Context context, int page, PassingObject object) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        intent.putExtra(Params.BUNDLE, new Gson().toJson(object));
        context.startActivity(intent);
    }


    public static void startMainActivity(Context context, int page) {
        Log.e("startMainActivity", "startMainActivity: ");
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        context.startActivity(intent);
        ((Activity) context).finishAffinity();

    }

    public static void startWhatsApp(Context context, String phone) {
        PackageManager pm = context.getPackageManager();
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent waIntent = new Intent(Intent.ACTION_SEND, uri);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            context.startActivity(waIntent);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public static void startWebPage(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);


    }

//    public static void MultiImagePicker(Context context, int maxImg) {
//        FishBun.with(((Activity) context))
//                .setImageAdapter(new PicassoAdapter())
//                .setMaxCount(maxImg)
//                .setMinCount(1)
//                .setPickerSpanCount(5)
//                .setActionBarColor(context.getResources().getColor(R.color.white), context.getResources().getColor(R.color.colorPrimaryDark), true)
//                .setActionBarTitleColor(context.getResources().getColor(R.color.colorPrimaryDark))
//                .setAlbumSpanCount(1, 3)
//                .setButtonInAlbumActivity(false)
//                .setCamera(true)
//                .exceptGif(true)
//                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close))
//                .setDoneButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_selected))
//                .setAllDoneButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_selected))
//                .setActionBarTitle(context.getString(R.string.app_name))
//                .textOnNothingSelected(context.getString(R.string.minSelectedImage))
//                .textOnImagesSelectionLimitReached(context.getString(R.string.maxSelectedImage))
//                .startAlbum();
//
//
//    }

//    public static void addNewView(View rootView, Context context, Uri imgUri) {
//        Log.i("addNewView", "addNewView: " + imgUri);
//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = vi.inflate(R.layout.selected_image_view, null);
//
//        // insert into main view
//        FrameLayout parentLayout = rootView.findViewById(R.id.parentLayout);
//        parentLayout.addView(v, parentLayout.getChildCount());
//        // fill in any details dynamically here
//        FrameLayout childLayout = (FrameLayout) v.findViewById(R.id.childLayout);
//        int idx = parentLayout.indexOfChild(childLayout);
//        childLayout.setTag(Integer.toString(idx));
//        ImageView newImage = v.findViewById(R.id.newImage);
//        newImage.setImageURI(imgUri);
//
////        v.findViewById(R.id.removeSelected).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                parentLayout.removeView(v);
////                String index = (String) v.getTag();
////                selectedList.remove(Integer.parseInt(index));
////            }
////        });
//    }


    public static void restart(Context context) {
        ((Activity) context).finish();
        context.startActivity(new Intent(context, BaseActivity.class));
    }

    public static void mapNavigate(String lat, String lng, Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d" +
                        "&daddr=" +
                        lat +
                        "," + lng +
                        "&hl=zh&t=m&dirflg=d"));


        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        ((Activity) context).startActivityForResult(intent, 1);
    }

    public static void loggout(Context context) {
        UserPreferenceHelper.getInstance(context).loggout();

        ((Activity) context).finishAffinity();
        startActivity(context, Codes.SPLASH);
    }
}
