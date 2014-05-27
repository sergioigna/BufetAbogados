/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class SQL {

    private Connection cnn;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    private ResultSetMetaData rsm;
    static String usuarioLogueado;
    
    public SQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://localhost/artsoft";
            cnn = DriverManager.getConnection(db, "root", "");
            st = cnn.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
///-----------------------------------------USUARIOS---------------------------------------------------------///
    //Validar Usuario y contraseña
    public boolean validarUsuario(String usuario, String clave) {
        try {
            String sql = "select contrasena from usuarios "
                    + "where usuario = '" + usuario + "'";
            boolean ok = false;
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String claveBD = rs.getString("contrasena");
                if (clave.equals(claveBD)) {
                    ok = true;
                }
            }
            return ok;
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
        public String validarPerfil(String usuario) {
        try {
            String sql = "select perfil from usuarios "
                    + "where usuario = '" + usuario + "'";
            rs = st.executeQuery(sql);
            
            String claveBD = "";
            
            if (rs.next()) {
                claveBD = rs.getString("perfil");
            }
            return claveBD;
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    //Consultar Usuario
    public String[] consultarUsuario(String usuario) 
    {
        try {
            String sql = "select * from usuarios "
                    + "where usuario = '" + usuario + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                
                String miUsuario[] = {rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("email"),
                        rs.getString("departamento"),rs.getString("perfil"),
                        rs.getString("estado"), rs.getString("fecha")};
                return miUsuario;
                
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    //Crear nuevo usuario 
        public void nuevoUsuario(String[] miUsuario) {
        try {
            String sql = "insert into usuarios "
                    + "(usuario, contrasena, nombre, apellido, email, departamento, perfil, estado, fecha) values "
                    + "('" + miUsuario[0] + "', '"
                    + miUsuario[1] + "', '"
                    + miUsuario[2] + "', '"
                    + miUsuario[3] + "', '"
                    + miUsuario[4] + "', '"
                    + miUsuario[5] + "', '"
                    + miUsuario[6] + "', '"
                    + miUsuario[7] + "', '"
                    + miUsuario[8] + "')";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
        
    //Modificar usuario 
    public void modificarUsuario(String[] miUsuario) {
        try {
            String sql = "update usuarios set contrasena = '"
                    + miUsuario[0] + "', nombre = '"
                    + miUsuario[1]+ "', apellido = '"
                    + miUsuario[2]+ "', email = '"
                    + miUsuario[3] + "', departamento = '"
                    + miUsuario[4]+ "', perfil = '"
                    + miUsuario[5] + "' where usuario = '"
                    + miUsuario[6] + "'";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    
        //Eliminar usuario 
    public void eliminarUsuario(String miUsuario) {
        try {
            String sql = "update usuarios set estado = '"
                    + "Inactivo" + "' where usuario = '"
                    + miUsuario + "'";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
   
   //Total usuarios tabla
         public ArrayList<Object[]> tablaUsuarios() {
         try {
                String sql = "select usuario, nombre, apellido, perfil"
                        + " from usuarios";

                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                rsm = rs.getMetaData();

                ArrayList<Object[]> data = new ArrayList<>();
                while (rs.next()){
                    Object[] rows = new Object[(rsm.getColumnCount())];
                    for (int i = 0; i < rows.length; i++){
                        rows[i] = rs.getObject(i + 1);
                    }
                    data.add(rows);
                }
 
                return data;
                
            }catch (Exception ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } 
         
         
         //
       public ArrayList<Object[]> nombresTecnicos() {
            try {
                
                String sql = "select * from usuarios";
                rs = st.executeQuery(sql);

                int i = 0;
                
                while (rs.next()) {
                    if ("Tecnico".equals(rs.getString("perfil"))){
                         i++;
                    }
                }
                
                String[] nombres = new String[i];
                String[] codigos = new String[i];
 
                i = 0;
 
                rs = st.executeQuery(sql);
                
                while (rs.next()) {
                    if ("Tecnico".equals(rs.getString("perfil"))){
                         nombres[i] = rs.getString("nombre");
                         codigos[i] = rs.getString("usuario");
                         i++;
                    }
                }
                
                ArrayList<Object[]> data = new ArrayList<>();
                
                data.add(nombres);
                data.add(codigos);
                
                return data;
                
            } catch (Exception ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } 
         
        
///-----------------------------------------ARTICULOS---------------------------------------------------------///
 
    //Consultar Usuario
    public String[] consultarArticulos(String articulo) 
    {
        try {
            String sql = "select * from articulos "
                    + "where numSerie = '" + articulo + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                
                String miArticulo[] = {rs.getString("tipo"),
                        rs.getString("marca"), rs.getString("modelo"),
                        rs.getString("fecha"),rs.getString("estado")
                };
                
                return miArticulo;
                
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
     
        //Crear nuevo articulo 
        public void nuevoArticulo(String[] miArticulo) {
        try {
            String sql = "insert into articulos "
                    + "(numSerie, tipo, marca, modelo, fecha, estado, adminIng) values "
                    + "('" + miArticulo[0] + "', '"
                    + miArticulo[1] + "', '"
                    + miArticulo[2] + "', '"
                    + miArticulo[3] + "', '"
                    + miArticulo[4] + "', '"
                    + miArticulo[5] + "', '"
                    + miArticulo[6] + "')";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
        
        //Total usuarios tabla
    public ArrayList<Object[]> tablaArticulos() {
         try {
                String sql = "select numSerie, tipo, marca, modelo"
                        + " from articulos";

                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                rsm = rs.getMetaData();

                ArrayList<Object[]> data = new ArrayList<>();
                while (rs.next()){
                    Object[] rows = new Object[(rsm.getColumnCount())];
                    for (int i = 0; i < rows.length; i++){
                        rows[i] = rs.getObject(i + 1);
                    }
                    data.add(rows);
                }
 
                return data;
                
            }catch (Exception ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } 
        
        
            //Modificar articulo 
    public void modificarArticulo(String[] miArticulo) {
        try {
            String sql = "update articulos set tipo = '"
                    + miArticulo[1] + "', marca = '"
                    + miArticulo[2]+ "', modelo = '"
                    + miArticulo[3]+ "', fecha = '"
                    + miArticulo[4] + "', estado = '"
                    + miArticulo[5] + "', adminIng = '"
                    + miArticulo[6] + "' where numSerie = '"
                    + miArticulo[0] + "'";
            st.executeUpdate(sql);

        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Eliminar articulo 
    public void eliminarArticulo(String miArticulo) {
        try {
            String sql = "update articulos set estado = '"
                    + "Inactivo" + "' where numSerie = '"
                    + miArticulo + "'";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    ///-----------------------------------------COMPONENTES---------------------------------------------------------///
 
    //Consultar componente
    public String[] consultarComponente(String componente) 
    {
        try {
            String sql = "select * from componentes "
                    + "where codigo = '" + componente + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                
                String miArticulo[] = {rs.getString("nombre"),
                        rs.getString("tipo"), rs.getString("ubicacion"),
                        rs.getString("descripcion"),rs.getString("cantidad")
                };
                
                return miArticulo;
                
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
     
        //Crear nuevo componente 
        public void nuevoComponete(String[] miComponente) {
        try {
            String sql = "insert into componentes "
                    + "(codigo, nombre, tipo, ubicacion, descripcion, cantidad) values "
                    + "('" + miComponente[0] + "', '"
                    + miComponente[1] + "', '"
                    + miComponente[2] + "', '"
                    + miComponente[3] + "', '"
                    + miComponente[4] + "', '"
                    + miComponente[5] + "')";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
        
    //Total componentes tabla
    public ArrayList<Object[]> tablaComponentes() {
         try {
                String sql = "select codigo, nombre, tipo, ubicacion, cantidad"
                        + " from componentes";

                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                rsm = rs.getMetaData();

                ArrayList<Object[]> data = new ArrayList<>();
                while (rs.next()){
                    Object[] rows = new Object[(rsm.getColumnCount())];
                    for (int i = 0; i < rows.length; i++){
                        rows[i] = rs.getObject(i + 1);
                    }
                    data.add(rows);
                }
 
                return data;
                
            }catch (Exception ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }        
    
    
            //Modificar componentes 
    public void modificarComponente(String[] miArticulo) {
        try {
            String sql = "update componentes set nombre = '"
                    + miArticulo[1]+ "', tipo = '"
                    + miArticulo[2]+ "', ubicacion = '"
                    + miArticulo[3] + "', descripcion = '"
                    + miArticulo[4] + "', cantidad = '"
                    + miArticulo[5] + "' where codigo = '"
                    + miArticulo[0] + "'";
            st.executeUpdate(sql);

        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Extraer componentes 
    public void extraerComponente(String[] miComponente) {
        try {
            String sql = "update componentes set cantidad = '"
                    + miComponente[1] + "' where codigo = '"
                    + miComponente[0] + "'";
            st.executeUpdate(sql);

        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Eliminar componente 
    public void eliminarComponente(String miComponente) {
        try {
            String sql = "update componentes set cantidad = '"
                    + "Inexistente" + "' where codigo = '"
                    + miComponente + "'";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
///-----------------------------------------REPARACIONESS---------------------------------------------------------///
 
        //Crear nuevo registro de reparacion 
        public void nuevaReparacion(String[] miReparacion) {
        try {
            
            String sql = "insert into reparaciones "
                    + "(usuario, articulo, chequeo, comentarios, fecha, adminIng, estado, tecnico) values "
                    + "('" 
                    + miReparacion[0] + "', '"
                    + miReparacion[1] + "', '"
                    + miReparacion[2] + "', '"
                    + miReparacion[3] + "', '"
                    + miReparacion[4] + "', '"
                    + miReparacion[5] + "', '"
                    + miReparacion[6] + "', '"
                    + miReparacion[7] + "')";
            st.executeUpdate(sql);
            
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public int numReparaciones () throws SQLException{
        
        int nRegistros;
        rs = st.executeQuery("SELECT count(*) as total from reparaciones");
        
        if(rs.next())
            nRegistros = Integer.parseInt(rs.getString("total"));
        else
            nRegistros = 0; 
       
        return nRegistros;
   }
   
   
    //Total reparaciones solicitud tabla
    public ArrayList<Object[]> tablaSolicitudes(String cod) {
         try {
                String sql = "select codigo, articulo, fecha, tecnico, estado"
                        + " from reparaciones";

                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                rsm = rs.getMetaData();

                ArrayList<Object[]> data = new ArrayList<>();
                
                while (rs.next()){
                    if (rs.getString("tecnico").equals(cod) && rs.getString("estado").equals("Solicitud")){
                        Object[] rows = new Object[(rsm.getColumnCount())];
                        for (int i = 0; i < rows.length; i++){
                            rows[i] = rs.getObject(i + 1);
                        }
                        data.add(rows);
                    }
                }
 
                return data;
                
            }catch (Exception ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } 
    
    
    
    //Consultar reparacion
    public String[] consultarReparaciones(String reparacion) 
    {
        try {
            String sql = "select * from reparaciones "
                    + "where codigo = '" + reparacion + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                
                String miArticulo[] = {rs.getString("usuario"),
                        rs.getString("articulo"), rs.getString("chequeo"),
                        rs.getString("comentarios"),rs.getString("fecha")
                };
                
                return miArticulo;
                
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
    //Finalizar Reparacion 
    public void finalizarReparacion(String[] miReparacion) {
        try {
            String sql = "update reparaciones set reporte = '"
                    + miReparacion[1]+ "', estado = '"
                    + miReparacion[2]+ "', componentes = '"
                    + miReparacion[3] + "', fechaFinal = '"
                    + miReparacion[4] + "' where codigo = '"
                    + miReparacion[0] + "'";
            st.executeUpdate(sql);

        } catch (Exception ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}