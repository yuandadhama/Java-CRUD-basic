// import java library
import java.io.IOException;
import java.util.Scanner;

// import CRUD library
import CRUD.Utility;
import CRUD.Operation;

public class Main {
   public static void main(String[] args) throws IOException {
      Scanner terminalInput = new Scanner(System.in);
      String pilihanUser;
      boolean isLanjutkan = true;

      while (isLanjutkan) {
         Utility.clearScreen();
         System.out.println("Database Perpustakaan\n");
         System.out.println("1. Lihat seluruh buku");
         System.out.println("2. Cari data buku");
         System.out.println("3. Tambah data buku");
         System.out.println("4. Ubah data buku");
         System.out.println("5. Hapus data buku");

         System.out.print("\n\nPilihan anda: ");
         pilihanUser = terminalInput.next();

         switch (pilihanUser) {
            case "1" -> {
               System.out.println("\n=================");
               System.out.println("LIST SELURUH BUKU");
               System.out.println("=================");

               // tampilkan data
               Operation.tampilkanData();
            }
            case "2" -> {
               System.out.println("\n==========");
               System.out.println("CARI BUKU");
               System.out.println("==========");

               // cari data
               Operation.cariData();
            }
            case "3" -> {
               System.out.println("\n================");
               System.out.println("TAMBAH DATA BUKU");
               System.out.println("================");

               // tambah data
               Operation.tambahData();
               Operation.tampilkanData();
            }
            case "4" -> {
               System.out.println("\n===============");
               System.out.println("UBAH DATA BUKU");
               System.out.println("===============");

               // ubah data
               Operation.updateData();
            }
            case "5" -> {
               System.out.println("\n================");
               System.out.println("HAPUS DATA BUKU");
               System.out.println("================");

               // hapus data
               Operation.deleteData();
            }
            default -> {
               System.err.println("Input Tidak Valid");
               System.err.println("Silahkan pilih [1-5]");
            }
         }
         isLanjutkan = Utility.getYesOrNo("Apakah anda ingin melanjutkan");
      }
   }
}