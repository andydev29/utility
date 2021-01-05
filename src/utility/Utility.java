/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Andres Martinez
 */
public class Utility {

    /**
     * @param args the command line arguments
     */

    public static void copy2ClipBoard(String text) {

        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            wrDataText("Utility","copy2clip done");
        } catch (Exception e) {
            wrDataText("Utility", "error: " + e);
        }

    }
    
    public static String paste2ClipBoard() {
        String copiado;
        try {
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            copiado = (String) t.getTransferData(DataFlavor.stringFlavor);
            return copiado;
        } catch (Exception e) {
            wrDataText("Utility", "error: " + e);
            return null;
        }
    }
    
    public static void wrDataText(String theClass, String data) {
        Date objDate = new Date();
        SimpleDateFormat objHora = new SimpleDateFormat("hhmmss");
        SimpleDateFormat objFecha = new SimpleDateFormat("MMdd");

        String SHORA = String.valueOf(objHora.format(objDate));
        String SFECHA = String.valueOf(objFecha.format(objDate));
        String date = SFECHA;
        String time = SHORA;
        try {
            String carpeta = System.getProperty("user.dir");
            File dir = new File(carpeta);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String nomarchivo = "debugdata" + date + ".txt";
            File f = new File(carpeta + "/" + nomarchivo);
            if (f.exists()) {
                FileReader fr = new FileReader(f);
                try (BufferedReader br = new BufferedReader(fr)) {
                    StringBuilder archivo = new StringBuilder();
                    for (String leer; (leer = br.readLine()) != null;) {
                        archivo.append(leer);
                        archivo.append("\n");
                    }
                    FileWriter w = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.write(archivo.toString());
                    bw.append(time).append(" - CLASE: ").append(theClass).append(" - ").append(data);
                    bw.newLine();
                    br.close();
                    bw.close();
                }

            } else {
                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                bw.write("Archivo de Log-Debug para el dia " + date);
                bw.newLine();
                bw.append(time).append(" - CLASE: ").append(theClass).append(" - ").append(data);
                bw.newLine();
                bw.close();
            }

        } catch (IOException e) {
            System.out.println("Fallo " + e);
        }
    }

     public static String spaces(String cad, int cantidad, int cada) {
        
        String auxArea = cad;
        int auxCant, n;
        //auxCant = (cantidad * (auxArea.length() / cada)) + auxArea.length(); // tamaño total de la cadena
        StringBuilder sb = new StringBuilder();
        //hallar la cantidad de espacios que seran añadidos cada x caracteres
        n = 0;
        sb.setLength(0);
        for (int i = 0; i < auxArea.length(); i++) {
            n++;
            sb.append(auxArea.charAt(i));
            if (n == cada) {
                n = 0;
                while (n < cantidad) {
                    sb.append(" ");
                    n++;
                }
                n = 0;
            }
        }
        return sb.toString();
    }
}
