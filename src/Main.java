import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        String path = scanner.nextLine();
//        String choice = args[0];
//        String path = args[1];
        File file = new File(path);
        long elapsedTime = 0;
        long start = 0;
        long end = 0;
        start = System.currentTimeMillis();
        if(choice.equals("c")){
            String chunkSize = scanner.nextLine();
//            String chunkSize = args[2];
            Encode.Compress(file, Integer.parseInt(chunkSize));
            float size = (float) Encode.sizeOfNewFile / (float) Files.size(Path.of(path));
            System.out.println("Compression Ratio : " + size);
        } else if (choice.equals("d")){
            Decode.Decompress(file);
        } else {
            System.out.println("ERROR!");
            return;
        }
        end = System.currentTimeMillis();
        elapsedTime = end - start;
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int minutes = (int) ((elapsedTime / 1000) / 60);
        System.out.println("Time taken: " + minutes + " Minutes" + " , " + seconds + " Seconds");
    }
}
