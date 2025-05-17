package com.erez_p.tashtit.ACTIVITIES;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.helper.AlertUtil;
import com.erez_p.helper.BitMapHelper;
import com.erez_p.helper.Global;
import com.erez_p.model.TripPicture;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.TripPictureViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

public class Add_Photo_Activity extends BaseActivity {
    private String tripId;
    private ImageButton ivPhoto;
    private ImageButton cancelButton;
    private Button confirmButton;
    private EditText descriptionEditText;
    private TripPictureViewModel viewModel;
    private String photo;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    Boolean bol=false;
    int count=0;
    private ActivityResultLauncher<Void> cameraLanucher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setViewModel();
        setLaunchers();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        tripId = getIntent().getStringExtra("tripId");
        ivPhoto = findViewById(R.id.imagePreview);
        cancelButton = findViewById(R.id.btnReturn);
        confirmButton = findViewById(R.id.btnConfirm);
        descriptionEditText = findViewById(R.id.etPhotoDescription);
    }

    @Override
    protected void setListeners() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(Add_Photo_Activity.this)
                        .setTitle("Picture")
                        .setMessage("Take a picture")
                        .setPositiveButton( "Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkCameraPermissionAndLaunch();
                            }
                        })
                        .setNegativeButton( "Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryLauncher.launch(galleryIntent);
                            }
                        })
                        .setNeutralButton( "Cancel", null)
                        .setCancelable(true)
                        .show();
        }

    });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo!=null) {
                    viewModel.add(new TripPicture(tripId, photo,descriptionEditText.getText().toString() ));
                    finish();
                }
            }
        });
    }
    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(TripPictureViewModel.class);
    }
    private void checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            cameraLanucher.launch(null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraLanucher.launch(null);
            } else {
                Toast.makeText(this, "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setLaunchers() {
        cameraLanucher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap o) {
                        if (o != null) {
                            picture = o;
                            ivPhoto.setImageBitmap(picture);
                            // Save bitmap to file and get path
                            photo = BitMapHelper.encodeTobase64(picture);
                        }
                    }
                }
        );

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK && o.getData() != null){
                            Uri imageUri = o.getData().getData();
                            try {
                                picture = uriToBitmap(imageUri);
                                ivPhoto.setImageBitmap(picture);
                                // Save bitmap to file and get path
                                photo = BitMapHelper.encodeTobase64(picture);
                            }
                            catch (Exception ex) {
                                Toast.makeText(Add_Photo_Activity.this, "Error loading image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private Bitmap uriToBitmap(Uri uri) throws IOException {  // 1 usage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
        } else {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        }
    }

}