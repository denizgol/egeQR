package com.example.ege;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ege.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Button scan_btn;
    private TextView textView;
    private ImageView imageView;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    public AppBarConfiguration mAppBarConfiguration;
    private PreviewView previewView;

    public class AgreementActivity extends AppCompatActivity {
        private Button acceptButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_agreement);

            acceptButton = findViewById(R.id.acceptButton);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Kullanıcı sözleşmeyi kabul ettiğinde, ana uygulamaya geçiş yapın.
                    startActivity(new Intent(AgreementActivity.this, MainActivity.class));
                    finish(); // Bu aktiviteyi kapatın, geri dönüş engellenir.
                }
            });
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//home buton ve menu butonu


        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                String url = "";

                if (itemId == R.id.bir) {
                    url = "https://dent.ege.edu.tr/tr-5252/agiz__dis_ve_cene_cerrahisi_anabilim_dali.html";
                } else if (itemId == R.id.iki) {
                    url = "https://dent.ege.edu.tr/tr-5249/agiz__dis_ve_cene_radyoloji_anabilim_dali___.html";
                } else if (itemId == R.id.üç) {
                    url = "https://dent.ege.edu.tr/tr-5253/endodonti_anabilim_dali.html";
                } else if (itemId == R.id.dört) {
                    url = "https://dent.ege.edu.tr/tr-5255/ortodonti_anabilim_dali.html";
                } else if (itemId == R.id.beş) {
                    url = "https://dent.ege.edu.tr/tr-5256/pedodonti_anabilim_dali.html";
                } else if (itemId == R.id.altı) {
                    url = "https://dent.ege.edu.tr/tr-5257/periodontoloji_anabilim_dali.html";
                } else if (itemId == R.id.yedi) {
                    url = "https://dent.ege.edu.tr/tr-5259/protetik_dis_tedavisi_anabilim_dali.html";
                } else if (itemId == R.id.sekiz) {
                    url = "https://dent.ege.edu.tr/tr-5261/restoratif_dis_tedavisi_anabilim_dali.html";
                } else if (itemId == R.id.nav_home) {
                    drawer.closeDrawer(GravityCompat.START);
                    textView3.setText("Scan QR Code");
                    textView4.setText("Place qr code inside the frame to scan please");
                    textView5.setText("avoid shake to get result quickliy");



                    return true;

            }


                if (!url.isEmpty()) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Snackbar.make(getWindow().getDecorView(), "Tarayıcı bulunamadı", Snackbar.LENGTH_SHORT).show();
                    }
                    return true;
                }

                return false;
            }
        });


        scan_btn = findViewById(R.id.button2);
        textView = findViewById(R.id.text);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        imageView = findViewById(R.id.imageView2);
        webView = findViewById(R.id.webView);

        // WebView ayarlarını yapılandırın
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // YouTube videolarının tam ekran oynatılabilmesi için WebChromeClient'i ayarlayın
        webView.setWebChromeClient(new WebChromeClient());

        // WebViewClient'i ayarlayarak yalnızca youtube.com içindeki URL'leri açmasını sağlayın
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("youtube.com")) {
                    // YouTube URL'si ise WebView'de aç
                    view.loadUrl(url);
                }
                return true;
            }
        });


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this)
                        .setOrientationLocked(true)
                        .setCaptureActivity(CaptureActivity.class)
                        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                        .initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                // YouTube video URL'sini "embed" ile başlatarak WebView'de açın
                if (isYouTubeUrl(contents)) {
                    webView.loadUrl("https://www.youtube.com/embed/" + getYouTubeVideoId(contents));
                    textView.setVisibility(View.GONE); // TextView'i GÖSTER
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                    textView5.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE); // WebView'i göster
                    //imageView.setVisibility((View.GONE));
                  //  textView.setText(contents);
                } else {
                    textView3.setText(contents); // YouTube video URL'si değilse TextView içinde göster
                    textView.setVisibility(View.VISIBLE); // TextView'i göster
                    webView.setVisibility(View.GONE); // WebView'i gizle
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.GONE);
                    textView5.setVisibility(View.GONE);
                    //   imageView.setVisibility((View.GONE));
                }
            }
        }
    }


    // YouTube video URL'si kontrolü için yardımcı bir yöntem
    private boolean isYouTubeUrl(String url) {
        return url.contains("youtube.com");
    }

    // YouTube video URL'sinden video kimliğini almak için yardımcı bir yöntem
    private String getYouTubeVideoId(String url) {
        String videoId = "";
        if (url.contains("v=")) {
            int startIndex = url.indexOf("v=") + 2;
            int endIndex = url.indexOf("&", startIndex);
            if (endIndex == -1) {
                videoId = url.substring(startIndex);
            } else {
                videoId = url.substring(startIndex, endIndex);
            }
        }
        return videoId;
    }


    //menu butonu
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }


    //ayarlar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    private void startCamera(PreviewView previewView) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider, PreviewView previewView) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }
}