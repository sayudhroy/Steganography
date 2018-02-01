package com.steganography;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.*;


/**
 * Created by Sayudh on 3/25/2017.
 */
public class Stego {


    public void embed(String msg, String path) {
        Log.e("imagePath", ""+path);
        String dirName = Environment.getExternalStorageDirectory().getPath()+ "/output.jpg";
        Log.e("outputPath", ""+dirName);

        FileInputStream fin = null;
        FileOutputStream fout=null;
        try {

            File fp=new File(dirName);
            fp.createNewFile();
            Log.e("outputPath", ""+fp.getAbsolutePath());
             fout= new FileOutputStream(fp);
            fin = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            Log.e("FNF","catch block 1");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOEXCEPtion","catch block 2"+e.getMessage());
            e.printStackTrace();
        }

        int i, n = -1, arrImg[], arrMsg[], len;
        msg += '\0';
        len = msg.length() * 8;
        char imgCh, msgCh;
        try {

            while ((i = fin.read()) != -1) {
                n++;
                imgCh = ((char) i);
                if (n >= (25*1024) && n < (25*1024) + len) {
                    msgCh = msg.charAt((n - (25*1024)) / 8);
                    arrMsg = toBits(msgCh);
                    arrImg = toBits(imgCh);
                    arrImg[7] = n % 8 == 0 ? arrMsg[7] : arrMsg[8 - (n % 8) - 1];
                    imgCh = toCharacter(arrImg);
                }
                fout.write(imgCh);
            }
            fin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String extract(String path)throws IOException {
        FileInputStream fin = new FileInputStream(path);
        String msg = "";
        int i, n = -1, arrImg[], arrMsg[] = new int[8];
        char imgCh, msgCh;
        try {

            while ((i = fin.read()) != -1) {
                n++;
                if (n >= (25*1024)) {
                    imgCh = ((char) i);
                    arrImg = toBits(imgCh);
                    arrMsg[8 - (n % 8) - 1] = arrImg[7];
                    if ((n + 1) % 8 == 0) {
                        msgCh = toCharacter(arrMsg);
                        if ((int) msgCh == 0)
                            break;
                        msg += msgCh;
                    }
                }
            }
            fin.close();
            System.out.print("\nMessage successfully extracted.");
        } catch (Exception e) {
            System.out.println(e);
        }
        return msg;
    }

    public String encrypt(String text, String key){
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            res += (char) ((text.charAt(i) + key.charAt(j)) % 256);
            j = ++j % key.length();
        }
        return res;
    }
    private static int[] toBits(char ch) {
        int arr[] = { 0, 0, 0, 0, 0, 0, 0, 0 }, no = (int) ch, len = 8;
        while (no > 0) {
            arr[--len] = no % 2;
            no /= 2;
        }
        return arr;
    }
    public String decrypt() {
        String text=Data.extractedMsg, key=Data.key;
        Log.e("Decrypt Function Call: ", ""+text+"  abc  "+key);
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            res += (char) ((text.charAt(i) - key.charAt(j)) % 256);
            j = ++j % key.length();
        }
        return res;
    }

    private static char toCharacter(int arr[]) {
        int len, num = 0;
        for (len = 0; len < 8; len++)
            num += arr[len] * Math.pow(2, 8 - len - 1);
        return (char) num;
    }
}