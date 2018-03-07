package br.com.contato.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.contato.modelo.CameraResultado;

public final class Util {

    public enum CameraOptions {
        CAMERA_ONLY, GALLERY_ONLY, GALLERY_CAMERA, GET_IMAGE_CODE;
    }

    public static void preencherBase64(String base64, ImageView imageView) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
    }

    /*
    Exemplo de uso
     @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        CameraResultado = Utils.getImageCameraOrGallery(data, this);

        if (CameraResultado != null) {
            image.setImageBitmap(CameraResultado.getPhotoBitmap());
        }

    }

     */

    public static CameraResultado getImageCameraOrGallery(Intent data, Context context) {

        CameraResultado result = null;
        Bitmap myBitmap = null;

        if (data != null ) {

            if (data.getExtras() != null) {
                myBitmap = data.getExtras().getParcelable("data");
            }

            if (myBitmap == null) {

                if (data.getData() == null)
                    return null;

                Cursor cursor = context.getContentResolver()
                        .query(data.getData(),
                                new String[]{android.provider.MediaStore.Images.ImageColumns.DATA},
                                null, null, null);
                cursor.moveToFirst();
                myBitmap = BitmapFactory.decodeFile(cursor.getString(0));
                cursor.close();
            }

            if (myBitmap == null) {

                if (data.getData() == null)
                    return null;

                try {
                    InputStream is = context.getContentResolver().openInputStream(data.getData());
                    myBitmap = BitmapFactory.decodeStream(is, null, null);
                    is.close();
                } catch (Exception e) {
                }
            }

            if (myBitmap != null) {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);

                result = new CameraResultado();
                result.setFotoBase64(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP));
                result.setFotoBitmap(myBitmap);

            }
        }

        return result;
    }


    public static boolean validate(Activity activity, int requestCode, String... permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            boolean nok = ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
            if (nok) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        ActivityCompat.requestPermissions(activity, newPermissions, requestCode);

        return false;
    }

    public static void openPhoto(CameraOptions options, Activity activity) {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Continuar usando:");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        if (options == CameraOptions.CAMERA_ONLY) {
            activity.startActivityForResult(cameraIntent, CameraOptions.GET_IMAGE_CODE.ordinal());
            return;
        }
        if (options == CameraOptions.GALLERY_ONLY) {
            activity.startActivityForResult(galleryIntent, CameraOptions.GET_IMAGE_CODE.ordinal());
            return;
        }
        activity.startActivityForResult(chooserIntent, CameraOptions.GET_IMAGE_CODE.ordinal());
        return;
    }
}
