package tr.com.nuritiras.kullanicibilgileri;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import tr.com.nuritiras.kullanicibilgileri.databinding.ActivityKayitBinding;

public class KayitActivity extends AppCompatActivity {

    ActivityKayitBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    Kullanici kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityKayitBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

    }

    public void onClickKaydet(View view) {
        String textIsim=binding.editKayitTextIsim.getText().toString().trim();
        String textEmail=binding.editKayitTextEmailAddress.getText().toString().trim();
        String textSifre=binding.editKayitTextPassword.getText().toString();
        String textSifreTekrar=binding.editKayitTextRePassword.getText().toString();

        if(!textIsim.isEmpty()) {
            if (!textEmail.isEmpty()) {
                if (!textSifre.isEmpty()) {
                    if (!textSifreTekrar.isEmpty()) {
                        if (textSifre.equals(textSifreTekrar)) {

                            auth.createUserWithEmailAndPassword(textEmail,textSifre)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            binding.textViewDurum.setTextColor(Color.parseColor("#0000FF"));
                                            binding.textViewDurum.setText("Kullanıcı oluşturuldu.");
                                            user=auth.getCurrentUser();
                                            if(user !=null){
                                                kullanici=new Kullanici(user.getUid(),textIsim,textEmail,textSifre);
                                                firestore.collection("Kullanıcılar").document(user.getUid())
                                                        .set(kullanici)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(KayitActivity.this, "Başarı ile kayıt oldunuz.", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                    startActivity(new Intent(KayitActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                                                }  else binding.textViewDurum.setText(task.getException().getMessage());
                                                            }
                                                        });
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.textViewDurum.setText("Kullanıcı oluşturulamadı.\n"+ e.getLocalizedMessage());
                                        }
                                    });

                        } else binding.textViewDurum.setText("Şifreler uyuşmuyor.");
                    } else binding.textViewDurum.setText("Lütfen geçerli bir şifre belirleyiniz.");
                } else binding.textViewDurum.setText("Lütfen geçerli bir şifre belirleyiniz.");
            } else binding.textViewDurum.setText("Lütfen geçerli bir E-Posta adresi giriniz.");
        } else binding.textViewDurum.setText("Lütfen geçerli bir isim bilgisi giriniz.");
    }
}