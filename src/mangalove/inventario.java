/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangalove;
import java.io.*;
import java.util.*;

/**
 *
 * @author alexis
 */
public class inventario implements Serializable {
    
    int cant,pre;
    String nom,ids1;
    public inventario(){}
    
    inventario (String nombre, int cantidad, int precio, String ids){
        nom=nombre;
        cant=cantidad;
        pre=precio;
        ids1=ids;
        
    }
    void muestrainfo() {
         String cad = "nombre articulo "+nom+"........."+cant;
         System.out.println(cad);
    }

    public int getCant() {
        return cant;
    }

    public String getIds1() {
        return ids1;
    }

    public String getNom() {
        return nom;
    }

    public int getPre() {
        return pre;
    }
    
    
    
}


class catalogo implements Serializable{
    
    private final LinkedList cat = new LinkedList ();
    
    catalogo (String [] nombres, int [] cantidades, int [] precios, String [] ids){
        
        for (int i=0 ; i< nombres.length;i++){
            cat.add(new inventario ( nombres[i],cantidades[i],precios[i], ids[i]));
        }
    }
    
   public void muestra (){
       
       ListIterator iterator = cat.listIterator();
       inventario inv;
       while (iterator.hasNext()){
           inv=(inventario)iterator.next();
           inv.muestrainfo();
       }
       
   }
   public LinkedList getInventario(){
       return cat;
   }
}