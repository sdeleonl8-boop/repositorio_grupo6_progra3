package transurbanobst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TransurbanoBST {

    public static void main(String[] args) {

        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        String archivoCSV = "usuarios.csv";

        cargarCSV(arbol, archivoCSV);

        VentanaArbol ventana =
                new VentanaArbol(arbol);

        ventana.setVisible(true);
    }

    // =====================================================
    // CARGAR CSV
    // =====================================================
    public static void cargarCSV(
            ArbolBinarioBusqueda arbol,
            String archivo) {

        String linea;

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(archivo))) {

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos.length == 3) {

                    String noEmpleado =
                            datos[0].trim();

                    String nombre =
                            datos[1].trim();

                    String puesto =
                            datos[2].trim();

                    Usuario usuario =
                            new Usuario(
                                    noEmpleado,
                                    nombre,
                                    puesto
                            );

                    arbol.insertar(usuario);
                }
            }

            System.out.println(
                    "Datos cargados correctamente."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error al leer CSV: "
                            + e.getMessage()
            );
        }
    }
}