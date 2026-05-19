package Empresa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class ControlEmpleados {

    public static void main(String[] args) {
        
        // Mostrar interfaz gráfica
        
        Scanner sc = new Scanner(System.in);
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        // Ruta del CSV
        String archivoCSV = "usuarios.csv";

        // Cargar datos
        cargarCSV(arbol, archivoCSV);
        
        VentanaArbol ventana = new VentanaArbol(arbol);
        ventana.setVisible(true);

        
    }

    // Método para cargar CSV
    public static void cargarCSV(ArbolBinarioBusqueda arbol, String archivo) {

        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos.length == 3) {

                    String CDE = datos[0].trim();
                    String puesto = datos[1].trim();
                    String nombre = datos[2].trim();

                    Usuario usuario = new Usuario(CDE, puesto, nombre);

                    arbol.insertar(usuario);
                }
            }

            System.out.println("Datos cargados correctamente desde CSV.");

        } catch (IOException e) {
            System.out.println("Error al leer archivo CSV: " + e.getMessage());
        }
    }
}