package com.technojamtask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_WISHNAME1 = "com.technojamtask.EXTRA1";
    public static final String EXTRA_WISHNAME2 = "com.technojamtask.EXTRA2";
    public static final String EXTRA_WISH = "com.technojamtask.WISH";
    public static final String EXTRA_URI = "com.technojamtask.URI";
    public static final int PICK_IMAGE = 100;


    Uri imageUri = Uri.EMPTY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.mainactivity);
        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(!Uri.EMPTY.equals(imageUri))
                openCard();
                else

                    showSnackBar();

            }

            private void showSnackBar(){
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "No image selected, use default?", Snackbar.LENGTH_LONG);
                snackbar.setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCard();
                    }
                });
                snackbar.show();
            }
        });

        Button gal = findViewById(R.id.gallery);
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openAbout();
            }
        });


    }

    public void closeKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView img = findViewById(R.id.card);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }


    public void openCard() {

        TextInputEditText from = findViewById(R.id.fromName);
        String fromVal = from.getText().toString();
        TextInputEditText to = findViewById(R.id.toName);
        String toVal = to.getText().toString();
        TextInputEditText wish = findViewById(R.id.getWish);
        String wishVal = wish.getText().toString();

        if (TextUtils.isEmpty(fromVal) || TextUtils.isEmpty(toVal)){
            if (TextUtils.isEmpty(fromVal) && TextUtils.isEmpty(toVal)){
                from.setError("This field is required");
                to.setError("This field is required");
            }
            else if(TextUtils.isEmpty(fromVal))
            from.setError("This field is required");
            else if(TextUtils.isEmpty(toVal))
                to.setError("This field is required");

        }else {
            Intent intent = new Intent(this, CardActivity.class);

            intent.putExtra(EXTRA_WISHNAME1, toVal);
            intent.putExtra(EXTRA_WISHNAME2, fromVal);
            intent.putExtra(EXTRA_WISH, wishVal);
            intent.putExtra(EXTRA_URI, imageUri.toString());
            startActivity(intent);
        }
    }

    public void openAbout(){

        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }




}
