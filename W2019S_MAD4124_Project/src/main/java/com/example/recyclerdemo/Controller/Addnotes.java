package com.example.recyclerdemo.Controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerdemo.Database.DatabaseHelper;
import com.example.recyclerdemo.Modal.Note;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class Addnotes extends AppCompatActivity {
int cid=0;
private EditText title;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
private EditText ndetails;
    private DatabaseHelper db;
    private Button btnaddimage;
    private ImageView addimageview;
    private int editnotes;
    private NoteDetails editnodtemodal;
    private double lati;
    private double longi;
    Bitmap bitmap;
    private String fulladd;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    String img_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);
        getSupportActionBar().setTitle("MyNotes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Bundle bundle = getIntent().getExtras();
        cid=bundle.getInt("cid");
        editnotes=bundle.getInt("editnotes") ;
        System.out.println(cid);
        db = new DatabaseHelper(this);
        title= (EditText) findViewById(R.id.txttitle);
        ndetails= (EditText) findViewById(R.id.txtnodedetails);
        btnaddimage= (Button) findViewById(R.id.addimage);
        addimageview= (ImageView) findViewById(R.id.addimage_view);
        if(editnotes==0)
        {
            getSupportActionBar().setSubtitle("Add_Notes");
        }else
        {
            getSupportActionBar().setSubtitle("Modify_Notes");
            editnodtemodal=db.getNotedetails(editnotes);
            title.setText(editnodtemodal.getNotetitle());
            ndetails.setText(editnodtemodal.getNotedetails());
            addimageview.setImageBitmap(StringToBitMap(editnodtemodal.getNoteimage()));
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
      //  addimageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            btnaddimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPictureDialog();
                }
            });
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnotemenu1, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;

            case R.id.addnotes1:
                if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(ndetails.getText().toString()) ) {
                    Toast.makeText(Addnotes.this, "Enter All Field First", Toast.LENGTH_LONG).show();
                }else {

                    if (editnotes == 0) {
                        if (addimageview.getDrawable() == null) {
                            img_str = "NULL";
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) addimageview.getDrawable()).getBitmap();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                            byte[] byte_arr = stream.toByteArray();
                            img_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);


                        }

                        getLocation();
                        db.insertNotedetails(Integer.toString(cid), title.getText().toString(), ndetails.getText().toString(), img_str,lati,longi,fulladd);

                        title.setText("");
                        ndetails.setText("");
                        AlertDialog alertDialog = new AlertDialog.Builder(Addnotes.this).create();
                        alertDialog.setTitle("SUCESS");
                        alertDialog.setMessage("Saved Sucessfully");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //dialog.dismiss();
                                        Intent it = new Intent(Addnotes.this, NotesDetails.class);
                                        Bundle bundle = new Bundle();

//Add your data to bundle
                                        bundle.putInt("categoryid", cid);

//Add the bundle to the intent
                                        it.putExtras(bundle);
                                        startActivity(it);
                                    }
                                });
                        alertDialog.show();

                        return true;
                    }
                else
            {

                if (addimageview.getDrawable() == null) {
                    img_str = "NULL";

                } else {

                    try {
                        bitmap = ((BitmapDrawable) addimageview.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                        byte[] byte_arr = stream.toByteArray();
                        img_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    }catch(Exception e){
                        img_str = "NULL";
                    }


                }
                getLocation();
                     NoteDetails enote=new NoteDetails();
                            enote.setId(editnotes);
                            enote.setNotedetails(ndetails.getText().toString());
                            enote.setNotetitle(title.getText().toString());
                            enote.setNoteimage(img_str);
                            enote.setFulldaaress(fulladd);
                            enote.setLatitude(lati);
                            enote.setLongitude(longi);

                       db.updateNotedetails(enote);
                AlertDialog alertDialog = new AlertDialog.Builder(Addnotes.this).create();
                alertDialog.setTitle("SUCESS");
                alertDialog.setMessage("Saved Sucessfully");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.dismiss();
                                Intent it = new Intent(Addnotes.this, NotesDetails.class);
                                Bundle bundle = new Bundle();

//Add your data to bundle
                                bundle.putInt("categoryid", Integer.parseInt(editnodtemodal.getCategory()));

//Add the bundle to the intent
                                it.putExtras(bundle);
                                startActivity(it);
                            }
                        });
                alertDialog.show();

            }}
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,22);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Addnotes.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    addimageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Addnotes.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == 22) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            addimageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(Addnotes.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
       myBitmap.createScaledBitmap(myBitmap,(int)(myBitmap.getWidth()*0.8), (int)(myBitmap.getHeight()*0.8), true);

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                });

}
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 110) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera();
            }
        }}


    private void getLocation() {
        DecimalFormat df = new DecimalFormat("#.######");
        if (ActivityCompat.checkSelfPermission(Addnotes.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Addnotes.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Addnotes.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

          //  Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        /*   if (location2 != null) {
                double latti = Double.parseDouble(df.format(location2.getLatitude()));
                double longi = Double.parseDouble(df.format(location2.getLongitude()));
                setAddress(latti, longi);
            } else */ if (location1 != null) {
                double latti = Double.parseDouble(df.format(location1.getLatitude()));
                double longi = Double.parseDouble(df.format(location1.getLongitude()));
                setAddress(latti, longi);
            } else if (location != null) {
                double latti = Double.parseDouble(df.format(location.getLatitude()));
                double longi = Double.parseDouble(df.format(location.getLongitude()));
                setAddress(latti, longi);
            } else {
                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setAddress(Double latitude, Double longitude) {

        Geocoder geocoder;
        List<Address>  addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {
            Log.d("max", " " + addresses.get(0).getMaxAddressLineIndex());

            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
             String postalcode=addresses.get(0).getPostalCode();
            fulladd = city+", "+state+", "+country+","+postalcode;
            lati=latitude;
            longi=longitude;
            Log.d("fulladd",fulladd);
        }
    }
}
