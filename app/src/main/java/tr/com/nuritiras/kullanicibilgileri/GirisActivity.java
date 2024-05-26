package tr.com.nuritiras.kullanicibilgileri;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import tr.com.nuritiras.kullanicibilgileri.databinding.ActivityGirisBinding;

public class GirisActivity extends AppCompatActivity {

    ActivityGirisBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    Kullanici kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGirisBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        if(user !=null) {
            finish();
            startActivity(new Intent(GirisActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

    }

    public void onClickGirisYap(View view) {
        String textEmail=binding.editGirisTextEmailAddress.getText().toString().trim();
        String textSifre=binding.editGirisTextPassword.getText().toString();

        if(!textEmail.isEmpty()) {
            if (!textSifre.isEmpty()) {

                auth.signInWithEmailAndPassword(textEmail,textSifre)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(GirisActivity.this, "Başarı ile giriş yaptınız.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(GirisActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                binding.girisDurum.setText("Giriş başarısız\n"+ e.getLocalizedMessage());
                            }
                        });

            } else binding.girisDurum.setText("Lütfen geçerli bir şifre belirleyiniz.");
        } else binding.girisDurum.setText("Lütfen geçerli bir E-Posta adresi giriniz");

    }

    public void onClickKayitOl(View view) {
        startActivity(new Intent(GirisActivity.this, KayitActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

}