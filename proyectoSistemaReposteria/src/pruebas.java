import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class pruebas {
    public static void main(String[] args) {
        String nombreArchivo = "src/materiaPrima.txt"; // Nombre del archivo a leer
        LinkedHashSet<String> conjunto = new LinkedHashSet<>();

        String textoFinal = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo)); // BufferedReader para leer líneas

            String linea;

            while ((linea = br.readLine()) != null) conjunto.add(linea); // Leer cada línea hasta el final del archivo

            Object[] conj = conjunto.toArray();

            for (int i = 0; i < conj.length - 1; i++) textoFinal += conj[i] + "\n";
            textoFinal += conj[conj.length - 1];

            BufferedWriter bw = new BufferedWriter(new FileWriter("src/archivoFiltrado.txt")); // Crear BufferedWriter para escribir líneas de texto
            bw.write(textoFinal);

            bw.close();
            br.close(); // Cerrar el BufferedReader después de terminar

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
