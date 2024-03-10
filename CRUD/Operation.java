package CRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operation {
    public static void updateData() throws IOException {
        // ambil data original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List Buku");
        tampilkanData();

        // ambil user input / pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukkan nomor buku yang akan di update: ");
        int updateNum = terminalInput.nextInt();

        // tampilkan data yang ingin di update
        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null) {
            entryCounts++;

            StringTokenizer st = new StringTokenizer(data, ",");

            // tampilkan entryCounts == updateNUm
            if (updateNum == entryCounts) {
                System.out.println("\n Data yang ingin and update adalah: ");
                System.out.println("=======================================");
                System.out.println("Referensi          : " + st.nextToken());
                System.out.println("Tahun              : " + st.nextToken());
                System.out.println("Penulis            : " + st.nextToken());
                System.out.println("Penerbit           : " + st.nextToken());
                System.out.println("Judul              : " + st.nextToken());

                // update data

                // mengambil input dari data user
                String[] fieldData = { "tahun", "penulis", "penerbit", "judul" };
                String[] tempData = new String[4];

                // refresh token
                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.getYesOrNo("apakah anda ingin merubah nama " + fieldData[i]);

                    originalData = st.nextToken();
                    if (isUpdate) {
                        // user input
                        if (fieldData[i].equalsIgnoreCase("tahun")) {
                            System.out.print("Masukkan tahun terbit, format=(YYYY): ");
                            tempData[i] = Utility.ambilTahun();
                        } else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
                            tempData[i] = terminalInput.nextLine();
                        }
                    } else {
                        tempData[i] = originalData;
                    }
                }

                // tampilkan data baru ke layar
                st = new StringTokenizer(data, ",");
                st.nextToken();
                System.out.println("\nData baru anda adalah: ");
                System.out.println("=======================================");
                System.out.println("Tahun              : " + st.nextToken() + " ---> " + tempData[0]);
                System.out.println("Penulis            : " + st.nextToken() + " ---> " + tempData[1]);
                System.out.println("Penerbit           : " + st.nextToken() + " ---> " + tempData[2]);
                System.out.println("Judul              : " + st.nextToken() + " ---> " + tempData[3]);

                boolean isUpdate = Utility.getYesOrNo("Apakah anda yakin ingin mengupdate data tersebut?");
                if (isUpdate) {
                    // cek data baru di database
                    boolean isExist = Utility.cekBukuDiDatabase(tempData, false);

                    if (isExist) {
                        System.err.println("data buku sudah ada di database");
                    } else {
                        // format data baru ke dalam database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        // membuat primary key
                        long nomorEntry = Utility.ambilEntryPerTahun(penulis, tahun) + 1;

                        String penulisTanpaSpasi = penulis.replaceAll("\\s", "");
                        String primaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;

                        // tulis data ke database
                        bufferedOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                    }

                } else {
                    bufferedOutput.write(data);
                }
            } else {
                // copy data
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();
            data = bufferedInput.readLine();
        }

        // menulis data ke file
        bufferedOutput.flush();

        // delete original database
        database.delete();

        // rename file tempDB menjadi database
        tempDB.renameTo(database);
    }

    public static void deleteData() throws IOException {
        // ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // buat database sementara
        File tempDB = new File("tempDB.text");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List buku");
        tampilkanData();

        // ambil user input untuk delete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        // looping untuk membaca tiap data baris dan skip data yang akan di delete
        int entryCounts = 0;
        boolean isFound = false;

        String data = bufferedInput.readLine();

        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");

            // tampilkan data yang ingin dihapus
            if (deleteNum == entryCounts) {
                System.out.println("\nData yang ingin anda hapus adalah: ");
                System.out.println("======================================");
                System.out.println("Referensi       : " + st.nextToken());
                System.out.println("Tahun           : " + st.nextToken());
                System.out.println("Penulis         : " + st.nextToken());
                System.out.println("Penerbit        : " + st.nextToken());
                System.out.println("Judul           : " + st.nextToken());

                isDelete = Utility.getYesOrNo("Apakah anda yakin akan menghapus");
                isFound = true;

            }

            if (isDelete) {
                // skip pindahkan data dari original ke sementara
                System.out.println("Data berhasil dihapus");
            } else {
                // pindahkan data dari original ke sementara
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }

        if (!isFound) {
            System.err.println("Buku tidak ditemukan");
        }

        // menulis data ke file
        bufferedOutput.flush();

        // menghapus file original
        database.delete();

        // rename file sementara menjadi database
        tempDB.renameTo(database);
    }

    public static void tambahData() throws IOException {
        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);
  
        // mengambil input dari user
        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;
  
        System.out.print("Masukkan nama penulis: ");
        penulis = terminalInput.nextLine();
  
        System.out.print("Masukkan judul buku: ");
        judul = terminalInput.nextLine();
  
        System.out.print("Masukkan nama penerbit: ");
        penerbit = terminalInput.nextLine();
  
        System.out.print("Masukkan tahun terbit, format=(YYYY): ");
        tahun = Utility.ambilTahun();
  
        // cek buku di database
        String[] keywords = { tahun + "," + penulis + "," + penerbit + "," + judul };
        boolean isExist = Utility.cekBukuDiDatabase(keywords, false);
  
        // menulis buku di database
        if (!isExist) {
           // fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
           long nomorEntry = Utility.ambilEntryPerTahun(penulis, tahun) + 1;
  
           String penulisTanpaSpasi = penulis.replaceAll("\\s", "");
           String primaryKey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;
           System.out.println("\nData yang anda masukkan adalah");
           System.out.println("=====================================");
           System.out.println("Primary key  : " + primaryKey);
           System.out.println("Tahun Terbit : " + tahun);
           System.out.println("Penulis      : " + penulis);
           System.out.println("Judul        : " + judul);
           System.out.println("Penerbit     : " + penerbit);
  
           boolean isTambah = Utility.getYesOrNo("Apakah anda ingin menambah data tersebut?");
  
           if (isTambah) {
              bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
              bufferOutput.newLine();
              bufferOutput.flush();
           }
  
        } else {
           System.out.println("Buku yang anda akan masukkan sudah tersedia di database!");
           Utility.cekBukuDiDatabase(keywords, true);
        }
  
        bufferOutput.close();
     }
  
    public static void tampilkanData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            return;
        }
        String data = bufferInput.readLine();
        int nomorData = 0;

        System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
        System.out.println(
                "=================================================================================================");

        while (data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%s   ", stringToken.nextToken());
            System.out.print("\n");
            data = bufferInput.readLine();
        }

        System.out.println(
                "=================================================================================================");
    }

    public static void cariData() throws IOException {

        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e) {
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            return;
        }

        // ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan Keywords Buku: ");
        String cariString = terminalInput.nextLine();

        String[] keywords = cariString.split("\\s");

        // cek keyword di database
        Utility.cekBukuDiDatabase(keywords, true);
    }

}
