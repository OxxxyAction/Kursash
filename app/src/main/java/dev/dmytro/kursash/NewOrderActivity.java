package dev.dmytro.kursash;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.Order;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;

public class NewOrderActivity extends Activity {
    EditText description;
    RetrofitService service;
    Context ctx;
    ProgressDialog pd;
    ImageView imgView;

    final String TAG = "Profile";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CODE_PICK_IMAGE = 2;
    static final int MEDIA_TYPE_IMAGE = 1;
    static final String IMAGE_DIRECTORY_NAME = "SMP";
    static final String MIME_TYPE = "image/*";
    private Uri fileUri;
    TypedFile typedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);

        description = (EditText) findViewById(R.id.edt_neworder_description);
        imgView = (ImageView) findViewById(R.id.imgView_neworder);
        ctx = this;
        service = new RetrofitService();

        findViewById(R.id.imgView_neworder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(NewOrderActivity.this).create();
                alert.setTitle("Фото");
                alert.setButton2("Выбрать из галерии", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureFromGalleryIntent();
                    }
                });
                alert.setButton("Сделать фото", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                    }
                });
                alert.show();
            }
        });


        findViewById(R.id.btn_back_neworder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_neworder_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typedImage != null) {
                    pd = ProgressDialog.show(ctx, "", "Ваш запрос обрабатывается");
                    Order order = new Order();
                    order.setId(RetrofitService.id);
                    order.setDescription(description.getText().toString());
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date today = Calendar.getInstance().getTime();
                    order.setDate(df.format(today));

                    service.sendNewOrder(order, typedImage, new Callback<CustomResponse>() {
                        @Override
                        public void success(CustomResponse customResponse, Response response) {
                            if (customResponse.isSuccess()) {
                                Toast.makeText(ctx, "Заказ успешно отправлен", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                                finish();
                            } else {
                                pd.dismiss();
                                Toast.makeText(ctx, "Ошибка соединения, попробуйте снова", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            pd.dismiss();
                            if (error.getMessage() != null)
                                Log.d("Retrofit", error.getMessage());
                            else if (error.getResponse() != null) {
                                String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                                Log.d("Retrofit", json.toString());
                            } else {
                                finish();
                                Toast.makeText(ctx, "Не удалось загрузить заказ, попробуйте позже.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ctx, "Выбрите файл. Нажмите на изображение вверху экрана", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
    }

    private void dispatchTakePictureFromGalleryIntent() {
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);*/
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE_PICK_IMAGE);

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK) {

                    try {

                        BitmapFactory.Options options = new BitmapFactory.Options();

                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;

                        final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                                options);

                        imgView.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                /*Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgView.setImageBitmap(imageBitmap);*/
                    try{

                        Log.d(TAG, "Camera fileUri " + fileUri.getPath());
                        typedImage = new TypedFile(MIME_TYPE, new File(fileUri.getPath()));
                    }catch (Exception e){
                        Log.d(TAG,"catch" + e.getMessage());
                    }

                }
                break;
            case REQUEST_CODE_PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();//imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(TAG, "File from cursor" + filePath);
                    Bitmap imageBitmap = BitmapFactory.decodeFile(filePath);
                    imgView.setImageBitmap(imageBitmap);
                    try{

                        typedImage = new TypedFile(MIME_TYPE, new File(filePath));
                    }catch (Exception e){
                        Log.d(TAG,"catch");
                    }

                }
                break;
        }
    }
}
