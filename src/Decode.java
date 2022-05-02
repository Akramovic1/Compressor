import java.io.*;
import java.util.HashMap;

public class Decode {
    static HashMap<String, String> map = new HashMap<>();
    public static void Decompress(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        String line = br.readLine();
        String str = "";
        String[] codes = line.split(" ");
        for (int i = 0; i < codes.length; i++){
            String[] x = codes[i].split("=");
            map.put(x[1], x[0]);
        }
        int counter = Integer.parseInt(br.readLine());
        br.close();
        int temp = file.getPath().lastIndexOf(file.getName());
        String newFile = file.getPath().substring(0, temp) + "extracted." + file.getPath().substring(temp);
        newFile = newFile.substring(0,newFile.length() - 3);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
        while(bis.read()!='\n');	while(bis.read()!='\n');
        int i = 0;
        int count = 0;
        while ((i = bis.read()) != -1){
            String formStr = Integer.toBinaryString(i);
            String format = ("00000000"+formStr).substring(formStr.length());
            for(char c : format.toCharArray()){
                str += c;
                if (count < counter && map.containsKey(str)){
                    String[] a = map.get(str).split(":");
                    str = "";
                    bos.write(Integer.parseInt(a[0]));
                    count++;
                }
            }
        }
        bis.close();
        bos.close();
    }
}
