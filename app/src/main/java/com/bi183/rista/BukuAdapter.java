package com.bi183.rista;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {

    private Context context;
    private ArrayList<Buku> dataBuku;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy");

    public BukuAdapter(Context context, ArrayList<Buku> dataBuku) {
        this.context = context;
        this.dataBuku = dataBuku;
    }

    @NonNull
    @Override
    public BukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_buku, parent, false);
        return new BukuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewHolder holder, int position) {
        Buku tempBuku = dataBuku.get(position);
        holder.idBuku = tempBuku.getIdBuku();
        holder.tvJudul.setText(tempBuku.getJudul());
        holder.tvHeadline.setText(tempBuku.getSinopsis());
        holder.terbit = sdFormat.format(tempBuku.getTerbit());
        holder.gambar = tempBuku.getGambar();
        holder.genre = tempBuku.getGenre();
        holder.penulis = tempBuku.getPenulis();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgBuku.setImageBitmap(bitmap);
            holder.imgBuku.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return dataBuku.size();
    }

    public class BukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ImageView imgBuku;
        private TextView tvJudul, tvHeadline;
        private int idBuku;
        private String terbit, gambar, genre, penulis;

        public BukuViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBuku = itemView.findViewById(R.id.iv_buku);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHeadline = itemView.findViewById(R.id.tv_headline);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent bukaBuku = new Intent(context, TampilActivity.class);
            bukaBuku.putExtra("ID", idBuku);
            bukaBuku.putExtra("JUDUL", tvJudul.getText().toString());
            bukaBuku.putExtra("TERBIT", terbit);
            bukaBuku.putExtra("GAMBAR", gambar);
            bukaBuku.putExtra("GENRE", genre);
            bukaBuku.putExtra("PENULIS", penulis);
            bukaBuku.putExtra("SINOPSIS", tvHeadline.getText().toString());
            context.startActivity(bukaBuku);

        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idBuku);
            bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
            bukaInput.putExtra("TERBIT", terbit);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("GENRE", genre);
            bukaInput.putExtra("PENULIS", penulis);
            bukaInput.putExtra("SINOPSIS", tvHeadline.getText().toString());
            context.startActivity(bukaInput);
            return true;
        }
    }
}
