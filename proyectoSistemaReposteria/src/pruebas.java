import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import model.SQLConnection;

public class pruebas {
    public static void main(String[] args) {
        /*String nombreArchivo = "src/materiaPrima.txt"; // Nombre del archivo a leer
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
        }*/

        SQLConnection connector = new SQLConnection();

        connector.setUser("sa");
        connector.setPassword("sasa");

        Connection con = connector.getConnection();

        addProduct("src/images/brownies.jpeg", con, "Brownie", 100, "Delicioso brownie de chocolate", 15, 25.0f, 0, "dulce", "stock");
        
    }

    public static void addProduct(String pathImage, Connection con,
                                  String name, int weight, String description,
                                  int stock, float normalPrice, float discount,
                                  String typeOfProduct, String normalOrPersonalized)
    {
        if (con != null) {
            try {
                File imageFile = new File(pathImage);
                byte[] datosImagen = Files.readAllBytes(imageFile.toPath());
                // Aquí tienes los datos binarios de la imagen en datosImagen[]
                String sql = "{call addProduct(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setInt(2, weight);
                    statement.setString(3, description);
                    statement.setInt(4, stock);
                    statement.setFloat(5, normalPrice);
                    statement.setFloat(6, discount);
                    statement.setString(7, typeOfProduct);
                    statement.setString(8, normalOrPersonalized);
                    statement.setBytes(9, datosImagen);
                    statement.execute();

                    System.out.println("Imagen insertada correctamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error");
        }
    }
}
