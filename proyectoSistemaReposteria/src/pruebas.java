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

        addProduct("src/images/brownies.jpeg", con, "Brownie", 100, "Delicioso brownie de chocolate", 15, 25.0f, 0.1f, "dulce");
        addProduct("src/images/gelatina1.jpg", con, "Gelatina mosaico", 500, "Deliciosa gelatina mosaico", 10, 100.0f, 0, "dulce");
        addProduct("src/images/gelatina2.jpg", con, "Gelatina de fresa", 500, "Deliciosa gelatina de fresa", 10, 100.0f, 0, "dulce");
        addProduct("src/images/muffinChocolate.jpg", con, "Muffin de chocolate", 100, "Delicioso muffin de chocolate", 20, 20.0f, 0, "dulce");
        addProduct("src/images/muffinChocolateDoble.jpg", con, "Muffin de chocolate doble", 100, "Delicioso muffin de chocolate doble", 20, 30.0f, 0, "dulce");
        addProduct("src/images/pastel1.jpg", con, "Pastel de colores con betún", 800, "Delicioso pastel", 15, 150.0f, 0, "pastel");
        addProduct("src/images/pastel2.jpg", con, "Pastel de chocolate con chocolate", 800, "Delicioso pastel", 15, 200.0f, 0, "pastel");
        addProduct("src/images/pastel3.jpg", con, "Pastel de 3 leches", 800, "Delicioso pastel", 15, 180.0f, 0, "pastel");
        addProduct("src/images/payDeQueso.jpg", con, "Pay de Queso", 400, "Delicioso pay de quesoooooooooo", 30, 80.0f, 0, "dulce");
        
    }

    public static void addProduct(String pathImage, Connection con,
                                  String name, int weight, String description,
                                  int stock, float normalPrice, float discount,
                                  String typeOfProduct)
    {
        if (con != null) {
            try {
                File imageFile = new File(pathImage);
                byte[] datosImagen = Files.readAllBytes(imageFile.toPath());
                // Aquí tienes los datos binarios de la imagen en datosImagen[]
                String sql = "{call addProduct(?, ?, ?, ?, ?, ?, ?, ?)}";

                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setInt(2, weight);
                    statement.setString(3, description);
                    statement.setInt(4, stock);
                    statement.setFloat(5, normalPrice);
                    statement.setFloat(6, discount);
                    statement.setString(7, typeOfProduct);
                    statement.setBytes(8, datosImagen);
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
