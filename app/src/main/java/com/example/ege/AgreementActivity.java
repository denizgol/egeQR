package com.example.ege;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AgreementActivity extends AppCompatActivity {
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kullanıcı tercihini kontrol et
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isAccepted = preferences.getBoolean("accepted", false);

        if (isAccepted) {
            // Kullanıcı daha önce kabul ettiyse, doğrudan MainActivity'ye yönlendirin
            startActivity(new Intent(this, MainActivity.class));
            finish(); // AgreementActivity'yi kapatın
        } else {
            // Kullanıcı daha önce kabul etmemişse, Sözleşme Kabulü ekranını gösterin
            setContentView(R.layout.activity_agreement);

            acceptButton = findViewById(R.id.acceptButton);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Kabulu kabul ettiğinizde MainActivity'ye geçiş yapın
                    startActivity(new Intent(AgreementActivity.this, MainActivity.class));

                    // Kullanıcı tercihini kaydet
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("accepted", true);
                    editor.apply();

                    finish(); // Bu aktiviteyi kapatın, geri dönüş engellenir.
                }
            });
        }
    }
}
