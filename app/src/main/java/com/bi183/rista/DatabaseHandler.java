package com.bi183.rista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_buku";
    private final static String TABLE_BUKU = "t_buku";
    private final static String KEY_ID_BUKU = "ID_buku";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TERBIT = "Terbit";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_SINOPSIS= "Sinopsis";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " +  TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TERBIT + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_SINOPSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataBuku.getJudul());
        cv.put(KEY_TERBIT, sdFormat.format(dataBuku.getTerbit()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());


        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataBuku.getJudul());
        cv.put(KEY_TERBIT, sdFormat.format(dataBuku.getTerbit()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());

        db.insert(TABLE_BUKU, null, cv);

    }

    public void editBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataBuku.getJudul());
        cv.put(KEY_TERBIT, sdFormat.format(dataBuku.getTerbit()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());


        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku(int idBuku) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }
        return dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db) {
        int idBuku = 0;
        Date tempDate = new Date();

        //menambah data Buku 1
        try{
            tempDate = sdFormat.parse("2014");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Buku buku1 = new Buku(
                idBuku,
                "Dia Adalah Dilanku Tahun 1990",
                tempDate,
                storeImageFile(R.drawable.buku1),
                "Romance",
                "Pidi Baiq",
                "Dilan (Iqbaal Ramadhan), panglima tempur sebuah geng motor di Bandung awal 90-an,\n" +
                        "menjalin hubungan dengan seorang siswi baru dari Jakarta bernama Milea (Vanesha Prescilla). Dilan selalu bahagia saat bersama Milea, namun teman-teman geng motor merasa Dilan makin menjauh dari kelompoknya karena Milea. \n" +
                        "Terjadi peristiwa yang mengerikan, salah satu anggota mereka, Akew (Gusti Rayhan), meninggal akibat dikeroyok oleh sekelompok orang. Peristiwa itu membuat Milea khawatir akan keselamatan Dilan.\n" +
                        "Milea membuat keputusan untuk berpisah dengan Dilan sebagai peringatan agar Dilan menjauh dari geng motor. Peristiwa Akew menyeret Dilan ke pihak berwajib bersama teman-temannya..\n" +
                        "Perpisahan yang tadinya hanya gertakan Milea menjadi perpisahan yang berlangsung lama sampai mereka lulus kuliah dan dewasa.\n" +
                        "Mereka berdua masih membawa perasaan yang sama saat mereka kembali bertemu di reuni,\n" +
                        "namun masing-masing saat itu sudah memiliki pasangan.\n"
        );

        tambahBuku(buku1, db);
        idBuku++;

        // Data Buku ke 2
        try{
            tempDate = sdFormat.parse("2007");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Buku buku2 = new Buku(
                idBuku,
                "5 CM",
                tempDate,
                storeImageFile(R.drawable.buku2),
                "Persahabatan",
                "Donny Dhirgantoro",
                "Buku 5 cm ini mengisahkan tentang persahabatan lima orang anak muda yang menjalin persahabatan sekitar tujuh tahun, mereka diantaranya mempunyai nama Arial, Riani, Zafran, Ian, dan Genta.\n" +
                        "\n" +
                        "Mereka ialah sahabat yang kompak, mempunyai obsesi dan mimpi masing-masing, mereka tidak jarang kali pergi bareng dan ketemu masing-masing saat. Karena jenuh bertemu masing-masing hari, kesudahannya mereka mengejar titik bosan dengan kegiatan yang tidak jarang kali mereka lakukan bareng dan mereka menyimpulkan untuk tidak saling berkomunikasi sekitar tiga bulan.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "Dalam masa “berpisah tersebut”, mereka tidak diperkenankan mengerjakan komunikasi dalam format apapun. Dalam kurun 3 bulan tersebutlah, mereka ditempa dengan urusan baru. Dengan rasa rindu yang saling menyilang. Tentang figur Riani yang menyukai salah satu sahabatnya zafran. Tentang Zafran yang merindui dinda adik Arial, sahabatnya sendiri. Tentang Genta yang memilih mengagumi Riani dengan diam.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "Selama tiga bulan berpisah itulah terjadi tidak sedikit hal yang menjadikan hati mereka lebih kaya dari sebelumnya. Pertemuan sesudah tiga bulan yang sarat dengan rasa kangen kesudahannya terjadi dan dirayakan dengan suatu perjalanan ‘reuni’ mereka dengan memanjat gunung tertinggi di Pulau Jawa, Mahameru.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "“Biarkan kepercayaan kamu, 5 centimeter menggantung mengapung di depan kamu, hanya kaki yang bakal berjalan lebih jauh dari biasanya, tangan yang akan melakukan lebih tidak sedikit dari biasanya, mata yang bakal menatap lebih lama dari biasanya, leher yang bakal lebih sering menyaksikan ke atas. Lapisan tekad yang seribu kali lebih keras dari baja, hati yang bakal bekerja lebih keras dari seringkali serta mulut yang akan tidak jarang kali berdoa. percaya pada 5 centimeter di depan kening kamu”."
        );

        tambahBuku(buku2, db);
        idBuku++;

        // Tambah buku 3

        try{
            tempDate = sdFormat.parse("2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Buku buku3 = new Buku(
                idBuku,
                "Rumah Tanpa Jendela",
                tempDate,
                storeImageFile(R.drawable.buku3),
                "Drama, Musikal",
                "Asma Nadia",
                "Rara tinggal di sebuah rumah kecil yang terbuat dari papan triplek bekas di daerah pemukiman kumuh, bersama nenek dan ayahnya. Mengetahui bahwa gadis kecilnya sangat ingin memiliki jendela di rumahnya, Raga (ayah Rara), selalu berupaya mencari jendela bekas, " +
                        "berharap ada orang yang membuangnya di tempat sampah saat ia memulung. " +
                        "Untuk membantu perekonomian keluarga,  Rara bekerja sebagai pengamen dan ojek payung pada saat hujan. " +
                        "Suatu ketika, Rara yang pada saat itu sedang mengojek payung, tiba-tiba terserempet oleh sebuah mobil. Untungnya si pemilik mobil bertanggung jawab atas kecelakaan tersebut.\n" +
                        "\n" +
                        "Aldo adalah anak dari pemilik mobil yang menyerempet Rara. " +
                        "Aldo merupakan anak lelaki yang memiliki keterbelakangan mental. " +
                        "Aldo berasal dari keluarga kaya, semua anggota keluarga menyayangi dan dapat menerima kekurangan Aldo kecuali kakak perempuannya. " +
                        "Semenjak kecelakaan tersebut, Aldo dan Rara menjadi akrab. " +
                        "Rara kerap bermain ke rumah Aldo bersama teman-teman Rara yang lain. " +
                        "Melihat itu semua kakak perempuan Aldo semakin membencinya.  " +
                        "Akhirnya Aldo pergi dari rumah karena melihat kakaknya yang mengatakan bahwa dia malu memiliki adik seperti Aldo pada saat pesta ulang tahunnya yang ke 17. Pada saat yang sama, rumah Rara kebakaran, ayah dan neneknya dalam keadaan koma, padahal pada saat itu ayah Rara berhasil mendapatkan jendela bekas untuk rumahnya. Aldo pun memutuskan untuk pergi ke Rumah sakit menemui Rara, namun karna keluarganya mencari sampai rumah sakit, Aldo pun pergi meninggalkan Rumah Sakit dan Rara pun mengikutinya."

        );
        tambahBuku(buku3, db);


    }

}