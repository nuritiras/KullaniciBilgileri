package tr.com.nuritiras.kullanicibilgileri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import tr.com.nuritiras.kullanicibilgileri.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    Kullanici kullanici;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        firestore.collection("Kullanıcılar").document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            binding.textViewUserId.setText("ID : " + documentSnapshot.getData().get("kullaniciId"));
                            binding.textViewUserName.setText("İsim : " + documentSnapshot.getData().get("kullaniciIsmi"));
                            binding.textViewUserEmail.setText("E-Posta : " + documentSnapshot.getData().get("kullaniciEmail"));
                            binding.textViewUserPassword.setText("Parola : " + documentSnapshot.getData().get("kullaniciParola"));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Hata oluştu" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, GirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void onClickKayitEkrani(View view) {
        finish();
        startActivity(new Intent(MainActivity.this, KayitActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void onClickGirisEkrani(View view) {
        finish();
        startActivity(new Intent(MainActivity.this, GirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}