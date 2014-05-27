package formularios;

import java.awt.Event;

public class validacionesCampos {
    
    public void soloNumeros (java.awt.event.KeyEvent evt) {
        
      char caracter = evt.getKeyChar();

      if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' ))
      {
         evt.consume();
      }
    }
    
    public void soloLetras (java.awt.event.KeyEvent evt) {
        
    }
    
}
