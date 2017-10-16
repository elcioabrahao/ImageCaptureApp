package arquidesis.usjt.br.imagecaptureapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.mukesh.image_processing.ImageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageInputHelper.ImageActionListener{

    private ImageInputHelper imageInputHelper;
    private ImageView imagem1;
    private Button btn1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkAndRequestPermissions();

        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

        imagem1=(ImageView)findViewById(R.id.imagem1);
        btn1 = (Button)findViewById(R.id.bt1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int network = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        //int myLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.LOCATION_HARDWARE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (network != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    public void capturaDaGaleria(View view){
        imageInputHelper.selectImageFromGallery();
    }

    public void capturaDaCamera(View view){
        imageInputHelper.takePhotoWithCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 400, 300, 4, 3);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 400, 300, 4, 3);
    }

    public void onRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        ImageProcessor imageProcessor = new ImageProcessor();

        BitmapDrawable drawable = (BitmapDrawable) imagem1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        switch (view.getId()) {
            case R.id.radio01:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.doInvert(bitmap));
                break;
            case R.id.radio02:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.doGreyScale(bitmap));
                break;
            case R.id.radio03:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applyGaussianBlur(bitmap));
                break;
            case R.id.radio04:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.createShadow(bitmap));
                break;
            case R.id.radio05:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applyMeanRemoval(bitmap));
                break;
            case R.id.radio06:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.emboss(bitmap));
                break;
            case R.id.radio07:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.engrave(bitmap));
                break;
            case R.id.radio08:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applyFleaEffect(bitmap));
                break;
            case R.id.radio09:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applyBlackFilter(bitmap));
                break;
            case R.id.radio10:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applySnowEffect(bitmap));
                break;
            case R.id.radio11:
                if (checked)
                    imagem1.setImageBitmap(imageProcessor.applyReflection(bitmap));
                break;
            default :
                break;
        }
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            imagem1.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
