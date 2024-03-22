package com.example.quiz1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button proses;
    private TextInputEditText pelanggan, kode, jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pelanggan = findViewById(R.id.pelanggan);
        kode = findViewById(R.id.kodebarang);
        jumlah = findViewById(R.id.jumlahbarang);
        radioGroup = findViewById(R.id.radio);
        proses = findViewById(R.id.proses);

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesTransaksi();
            }
        });
    }

    private void prosesTransaksi() {
        String Nama = pelanggan.getText().toString();
        String Kode = kode.getText().toString();
        int Jumlah = Integer.parseInt(jumlah.getText().toString());

        long harga = getHarga(Kode);
        if (harga == -1) {
            Toast.makeText(MainActivity.this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        long total = harga * Jumlah;
        long DiskonMember = hitungDiskonMember(total);
        long DiskonHarga = hitungDiskonHarga(total);


        long jumlahBayar = total- DiskonHarga - DiskonMember;

        Intent intent = new Intent(MainActivity.this, DetailBarang.class);
        intent.putExtra("Nama Pelanggan", Nama);
        intent.putExtra("Kode Barang", Kode);
        intent.putExtra("Nama Barang", getNamaBarang(Kode));
        intent.putExtra("Harga", harga);
        intent.putExtra("Jumlah Barang", Jumlah);
        intent.putExtra("Total Harga", total);
        intent.putExtra("Diskon Harga", DiskonHarga);
        intent.putExtra("Diskon Member", DiskonMember);
        intent.putExtra("Jumlah Bayar", jumlahBayar);
        startActivity(intent);
    }

    private long getHarga(String Kode) {
        switch (Kode) {
            case "MP3":
                return 28999999;
            case "AV4":
                return 9150999;
            case "AA5":
                return 9999999;
            default:
                return -1;
        }
    }

    private String getNamaBarang(String Kode) {
        switch (Kode) {
            case "MP3":
                return "Macbook Pro M3";
            case "AV4":
                return "Asus VivoBook 14";
            case "AA5":
                return "Acer Aspire 5";
            default:
                return "";
        }
    }

    private long hitungDiskonHarga(long total) {
        if (total > 10000000) {
            return 100000;
        }
        return 0;
    }

    private long hitungDiskonMember(long total) {
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String membership = radioButton.getText().toString();
        switch (membership) {
            case "Gold":
                return (long) (total * 0.1);
            case "Silver":
                return (long) (total * 0.05);
            case "Biasa":
                return (long) (total * 0.02);
            default:
                return 0;
        }
    }
}
