import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Encode {
    static HashMap<String, String> mapSC = new HashMap<>();
    static PriorityQueue<Node> priorityQueue;
    static long sizeOfNewFile;
    static Node root;
    static int counter = 0;
    public static void Compress(File file, int n) throws IOException {
        String str = "";
        FileInputStream fis;
        BufferedOutputStream bos;
        byte[] bytes = new byte[(int) file.length()];
        HashMap<String, Integer> map = new HashMap<>();
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
            for(int i = 0; i < bytes.length; i++){
                byte[] arr = Arrays.copyOfRange(bytes, i, Math.min(bytes.length,i+n));
                String chunk = "";
                int count = 0 ;
                while(count < arr.length) {
                    chunk += String.valueOf(arr[count]);
                    if (count < arr.length - 1)
                        chunk += ":";
                    count++;
                }
                if (!map.containsKey(chunk)) {
                    map.put(chunk, 1);
                } else
                    map.put(chunk,map.get(chunk)+1);
            }
        } catch (Exception e) {
            System.out.println("ERROR! This File is not found please check file path and try again");
        }
        priorityQueue = new PriorityQueue<>();
        for (String st : map.keySet()) {
            Node node = new Node(st, map.get(st));
            counter++;
            priorityQueue.add(node);
        }
        map.clear();
        makePrefixTable();
        String codeToSym = mapSC.toString();
        codeToSym = codeToSym.substring(1, codeToSym.length() - 1);
        codeToSym = codeToSym.replace(", ", " ");
        String header = "";
        header += codeToSym + '\n' + root.getFreq() + '\n';
        int temp = file.getPath().lastIndexOf(file.getName());
        String newFile = file.getPath().substring(0, temp)  + file.getPath().substring(temp) + ".hc";
        bos = new BufferedOutputStream(new FileOutputStream(newFile));
        for (int i = 0; i < header.length(); i++){
            char c = header.charAt(i);
            bos.write(c);
        }
        for (int i = 0; i < bytes.length; i++) {
            byte[] arr = Arrays.copyOfRange(bytes, i, Math.min(bytes.length, i + n));
            String chunk = "";
            int count = 0;
            while (count < arr.length) {
                chunk += String.valueOf(arr[count]);
                if (count < arr.length - 1) {
                    chunk += ":";
                }
                count++;
            }
            for (char c : mapSC.get(chunk).toCharArray()) {
                str += c;
                if (str.length() == 8) {
                    int x = Integer.parseInt(str, 2);
                    bos.write(x);
                    str = "";
                }
            }
        }
        if(str != ""){
            String tmp = str + "00000000";
            tmp = tmp.substring(0,8);
            int x = Integer.parseInt(tmp, 2);
            bos.write(x);
        }
        mapSC.clear();
        sizeOfNewFile = Files.size(Path.of(newFile));
        bos.close();
    }
    public static void makePrefixTable(){
        int count = 0;
        while(count < counter - 1){
            Node leftChild = priorityQueue.poll();
            Node rightChild = priorityQueue.poll();
            leftChild.setCode("0");
            rightChild.setCode("1");
            Node node = new Node(leftChild.getSym() + rightChild.getSym(),leftChild.getFreq() + rightChild.getFreq(),leftChild, rightChild);
            priorityQueue.add(node);
            count++;
        }
        root = priorityQueue.peek();
        prefixCodes(root, "");
        codesFormulation(root);
    }
    public static void prefixCodes(Node node, String code){
        if(node.getLeftChild()!=null) {
            node.getLeftChild().setCode(node.getCode() + "0");
            prefixCodes(node.getLeftChild(), node.getLeftChild().getCode());
            node.getRightChild().setCode(node.getCode() + "1");
            prefixCodes(node.getRightChild(), node.getRightChild().getCode());
        }
    }
    public static void codesFormulation(Node node){
        if(node.getLeftChild() != null)
            codesFormulation(node.getLeftChild());
        if(node.getRightChild() != null)
            codesFormulation(node.getRightChild());
        if(node.getLeftChild() == null && node.getRightChild() == null){
            mapSC.put(node.getSym(), node.getCode());
        }
    }
}
