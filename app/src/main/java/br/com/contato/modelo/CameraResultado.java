package br.com.contato.modelo;

import android.graphics.Bitmap;

public class CameraResultado {

    private String fotoBase64;
    private Bitmap fotoBitmap;

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public Bitmap getFotoBitmap() {
        return fotoBitmap;
    }

    public void setFotoBitmap(Bitmap fotoBitmap) {
        this.fotoBitmap = fotoBitmap;
    }
}
