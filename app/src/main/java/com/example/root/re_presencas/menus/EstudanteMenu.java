package com.example.root.re_presencas.menus;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.estudante_activities._a.activities.EstPresenca;
import com.example.root.re_presencas.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EstudanteMenu extends AppCompatActivity {

    private Button btnGo;
    private ImageView imgPerfilEst;
    private TextView tvNomeEstudantePerfil, tvUpload;
    private Intent intent;
    private static final int CHOOSE_IMAGE = 1;
    public static final String EST_LOGADO = "est_logado";
    private Uri imgUri;
    private StorageReference mStorageRef;
    private DatabaseReference raiz;
    private StorageTask mUploadTask;
    private String[] extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudante_menu);
        intent = getIntent();
        imgPerfilEst = findViewById(R.id.img_foto_perfil_estudante);
        tvNomeEstudantePerfil = findViewById(R.id.tv_nome_estudante_perfil);
        tvUpload = findViewById(R.id.tv_upload);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        raiz = FirebaseDatabase.getInstance().getReference();
        extras = intent.getStringArrayExtra(LoginActivity.EST_LOGADO);

        chooseImage();
        uploadClick();


        this.start();
    }

    private void chooseImage() {
        imgPerfilEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void uploadClick() {
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(EstudanteMenu.this, "Upload is in progress", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.with(this).load(imgUri).into(imgPerfilEst);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imgUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
            mUploadTask = fileReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // Tem que pegar o estudante logado e fazer um update do campo foto e colocar a url
                    Toast.makeText(EstudanteMenu.this, "Upload successfully", Toast.LENGTH_SHORT).show();
                    String urlFoto = Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString();
                    String idLogado = extras[0];
                    raiz.child("estudante").child(idLogado).child("foto").setValue(urlFoto);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Falha na foto", e.toString());
                }

            });
        } else {
            Toast.makeText(this, "Nenhum ficheiro foi selecionado", Toast.LENGTH_SHORT).show();
        }
    }

    private void start() {
        btnGo = findViewById(R.id.btn_go_estudante);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EstudanteMenu.this, EstPresenca.class);
                intent2.putExtra(EST_LOGADO, extras);
                startActivity(intent2);
            }
        });
    }
}
