package transurbanobst;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class VentanaArbol extends javax.swing.JFrame {

    private ArbolBinarioBusqueda arbol;

    // Nodo resaltado durante búsqueda
    private Nodo nodoActualAnimado = null;

    // Nodo encontrado
    private Nodo nodoEncontradoAnimado = null;

    // Animación de creación
    private List<Nodo> nodosVisibles = new ArrayList<>();

    private boolean animacionEnProgreso = false;

    // =====================================================
    // PANEL PERSONALIZADO
    // =====================================================
    class PanelArbol extends javax.swing.JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (arbol != null && arbol.raiz != null) {
                dibujarArbol(
                        g2,
                        arbol.raiz,
                        getWidth() / 2,
                        40,
                        getWidth() / 5
                );
            }
        }
    }

    public VentanaArbol(ArbolBinarioBusqueda arbol) {
        initComponents();
        this.arbol = arbol;

        panelDibujo = new PanelArbol();
        jScrollPane4.setViewportView(panelDibujo);

        panelDibujo.setPreferredSize(new Dimension(1400, 900));

        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);

        aplicarDiseno();

        // Mantener creación animada
        animarCreacion();
    }

    private void aplicarDiseno() {
        panelDibujo.setBackground(new Color(235, 240, 245));
        panelDibujo.setPreferredSize(new Dimension(1400, 900));

        Font fuenteLabel = new Font("Segoe UI", Font.BOLD, 13);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel2.setFont(fuenteLabel);
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jLabel4.setFont(fuenteLabel);
        jLabel5.setFont(fuenteLabel);
        jLabel6.setFont(fuenteLabel);

        Font fuenteText = new Font("Segoe UI", Font.PLAIN, 13);

        jTextField1.setFont(fuenteText);
        jTextField2.setFont(fuenteText);
        jTextField3.setFont(fuenteText);
        jTextField4.setFont(fuenteText);

        estilizarBoton(jButton1, new Color(52, 152, 219));
        estilizarBoton(CrearBtn, new Color(46, 204, 113));
        estilizarBoton(ActualizarBtn, new Color(155, 89, 182));
        estilizarBoton(EliminarBtn, new Color(231, 76, 60));

        jLabel3.setForeground(new Color(120, 120, 120));
    }

    private void estilizarBoton(javax.swing.JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    // =====================================================
    // ÁRBOL MÁS COMPACTO
    // =====================================================
    private void dibujarArbol(Graphics g, Nodo nodo, int x, int y, int separacion) {

        if (nodo == null || !nodosVisibles.contains(nodo)) return;

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int ancho = 130;
        int alto = 52;

        int rectX = x - (ancho / 2);
        int rectY = y - (alto / 2);

        // Sombra
        g2.setColor(new Color(180, 180, 180));
        g2.fillRoundRect(rectX + 2, rectY + 2, ancho, alto, 12, 12);

        // Colores
        if (nodo == nodoEncontradoAnimado) {
            g2.setColor(new Color(46, 204, 113));
        } else if (nodo == nodoActualAnimado) {
            g2.setColor(new Color(243, 156, 18));
        } else {
            g2.setColor(new Color(52, 152, 219));
        }

        g2.fillRoundRect(rectX, rectY, ancho, alto, 12, 12);

        g2.setColor(Color.BLACK);
        g2.drawRoundRect(rectX, rectY, ancho, alto, 12, 12);

        // =====================================================
        // MOSTRAR DPI + NIT + NOMBRE COMPLETO
        // =====================================================
        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
        g2.drawString("DPI: " + nodo.usuario.dpi, rectX + 6, rectY + 14);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        g2.drawString("NIT: " + nodo.usuario.nit, rectX + 6, rectY + 27);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        g2.drawString(nodo.usuario.nombre, rectX + 6, rectY + 40);

        // Nodo izquierdo
        if (nodo.izquierda != null && nodosVisibles.contains(nodo.izquierda)) {

            int nuevoX = x - separacion;
            int nuevoY = y + 65;

            g2.setStroke(new BasicStroke(1.5f));
            g2.setColor(new Color(80, 80, 80));

            g2.drawLine(
                    x,
                    y + (alto / 2),
                    nuevoX,
                    nuevoY - (alto / 2)
            );

            dibujarArbol(
                    g,
                    nodo.izquierda,
                    nuevoX,
                    nuevoY,
                    Math.max(separacion / 2, 45)
            );
        }

        // Nodo derecho
        if (nodo.derecha != null && nodosVisibles.contains(nodo.derecha)) {

            int nuevoX = x + separacion;
            int nuevoY = y + 65;

            g2.setStroke(new BasicStroke(1.5f));
            g2.setColor(new Color(80, 80, 80));

            g2.drawLine(
                    x,
                    y + (alto / 2),
                    nuevoX,
                    nuevoY - (alto / 2)
            );

            dibujarArbol(
                    g,
                    nodo.derecha,
                    nuevoX,
                    nuevoY,
                    Math.max(separacion / 2, 45)
            );
        }
    }

    // =====================================================
    // ANIMACIÓN DE CREACIÓN
    // =====================================================
    private void animarCreacion() {

        nodosVisibles.clear();

        new Thread(() -> {

            List<Nodo> lista = new ArrayList<>();

            obtenerNodosEnPreOrden(arbol.raiz, lista);

            for (Nodo nodo : lista) {

                nodosVisibles.add(nodo);

                refrescarArbol();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

        }).start();
    }

    private void obtenerNodosEnPreOrden(Nodo nodo, List<Nodo> lista) {

        if (nodo == null) return;

        lista.add(nodo);

        obtenerNodosEnPreOrden(nodo.izquierda, lista);
        obtenerNodosEnPreOrden(nodo.derecha, lista);
    }

    // =====================================================
    // BÚSQUEDA
    // =====================================================
    private void animarBusqueda(String dpiBuscar) {

        if (animacionEnProgreso) return;

        animacionEnProgreso = true;

        nodoEncontradoAnimado = null;
        nodoActualAnimado = null;

        refrescarArbol();

        jLabel3.setText("Buscando...");

        new Thread(() -> {

            List<Nodo> ruta = new ArrayList<>();

            boolean encontrado = encontrarRuta(arbol.raiz, dpiBuscar, ruta);

            for (int i = 0; i < ruta.size(); i++) {

                Nodo nodo = ruta.get(i);

                nodoActualAnimado = nodo;

                refrescarArbol();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                if (!(encontrado && i == ruta.size() - 1)) {
                    nodoActualAnimado = null;
                }
            }

            SwingUtilities.invokeLater(() -> {

                if (encontrado) {

                    Nodo nodoFinal = ruta.get(ruta.size() - 1);

                    nodoEncontradoAnimado = nodoFinal;

                    // =====================================================
                    // MOSTRAR DPI + NIT + NOMBRE COMPLETO
                    // =====================================================
                    jLabel3.setText(
                            "<html>DPI: " + nodoFinal.usuario.dpi
                            + "<br>NIT: " + nodoFinal.usuario.nit
                            + "<br>Nombre: " + nodoFinal.usuario.nombre
                            + "</html>"
                    );

                } else {

                    jLabel3.setText("Usuario no encontrado.");
                }

                refrescarArbol();

                animacionEnProgreso = false;

            });

        }).start();
    }

    private boolean encontrarRuta(Nodo actual, String dpi, List<Nodo> ruta) {

        if (actual == null) return false;

        ruta.add(actual);

        if (actual.usuario.dpi.equals(dpi)) return true;

        if (encontrarRuta(actual.izquierda, dpi, ruta)) return true;

        if (encontrarRuta(actual.derecha, dpi, ruta)) return true;

        ruta.remove(ruta.size() - 1);

        return false;
    }

    // =====================================================
    // REFRESCAR
    // =====================================================
    public void refrescarArbol() {
        panelDibujo.revalidate();
        panelDibujo.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelDibujo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        CrearBtn = new javax.swing.JButton();
        ActualizarBtn = new javax.swing.JButton();
        EliminarBtn = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelDibujoLayout = new javax.swing.GroupLayout(panelDibujo);
        panelDibujo.setLayout(panelDibujoLayout);
        panelDibujoLayout.setHorizontalGroup(
            panelDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 706, Short.MAX_VALUE)
        );
        panelDibujoLayout.setVerticalGroup(
            panelDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(panelDibujo);

        jLabel2.setText("Ingrese DPI:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("====================================================");

        jLabel1.setText("Nuevo Usuario");

        jLabel6.setText("Ingrese Nombre:");

        jLabel4.setText("Ingrese DPI:");

        jLabel5.setText("Ingrese NIT:");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        CrearBtn.setLabel("Crear");

        ActualizarBtn.setText("Actualizar");
        ActualizarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarBtnActionPerformed(evt);
            }
        });

        EliminarBtn.setText("Eliminar");
        EliminarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(992, 992, 992))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CrearBtn)
                                    .addComponent(EliminarBtn)
                                    .addComponent(ActualizarBtn)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CrearBtn))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ActualizarBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EliminarBtn))
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String dpiBuscar = jTextField1.getText();

        if (dpiBuscar.trim().isEmpty()) {
            return;
        }

        // Llamamos al método que creamos con el hilo (Thread) y el delay de 1000ms
        animarBusqueda(dpiBuscar);
    
            
        
    refrescarArbol();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void ActualizarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarBtnActionPerformed
        // TODO add your handling code here:
        
            // Obtener datos escritos
    String dpi = jTextField2.getText();
    String nuevoNit = jTextField3.getText();
    String nuevoNombre = jTextField4.getText();

    // Buscar usuario en el árbol
    Usuario usuarioActualizar = arbol.buscar(dpi);

    // Verificar si existe
    if (usuarioActualizar != null) {

        // Actualizar datos
        usuarioActualizar.nit = nuevoNit;
        usuarioActualizar.nombre = nuevoNombre;

        // Mensaje
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Usuario actualizado correctamente."
        );

        // Redibujar árbol
        refrescarArbol();

    } else {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se encontró el usuario.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
        
        
    }//GEN-LAST:event_ActualizarBtnActionPerformed

    private void EliminarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarBtnActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActualizarBtn;
    private javax.swing.JButton CrearBtn;
    private javax.swing.JButton EliminarBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel panelDibujo;
    // End of variables declaration//GEN-END:variables
}


