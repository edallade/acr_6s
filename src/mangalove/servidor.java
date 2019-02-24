package mangalove;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author alexis
 */
public class servidor{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException,ClassNotFoundException {
            try {
                ServerSocket s = new ServerSocket(9090);
              //  s.setReuseAddress(true);
              DataOutputStream dos=null;
                System.out.println("espearando conexion del cliente......");
                for (;;){
                    Socket cliente = s.accept();
                    System.out.println ("conexion establecida desde "+
                                         cliente.getInetAddress()+":"+cliente.getPort());
                   
  
// creacion y envio del catalogo ================================================================================================
            File data = new File("data.bin");
            int[] precio= new int[9];
            int[] cantidad=new int[9];
            String[] nombres=new String[9];
            String[] ids=new String[9];
            if(data.exists()){//existe el catalogo
                BufferedInputStream bw = new BufferedInputStream(new FileInputStream(data));
                System.out.println("Cargando catalogo");
                int ch;
                String cat="";
                ch = bw.read();
                while(ch!=-1){
                    cat+=(char)ch;
                    ch =bw.read();
                }
                String[] datos = cat.split(",",9*4);
                int i;
                ch=0;
                for(i=0;i<9*4;i+=4){
                    nombres[ch]=datos[i];
                    cantidad[ch]=Integer.parseInt(datos[i+1]);
                    precio[ch]=Integer.parseInt(datos[i+2]);
                    ids[ch]=datos[i+3];
                ch++;
                }
                bw.close();
            }
            else{ //no existe el catalogo================================================================================================================================
                System.out.println("Servidor nuevo");
                System.out.println("creando el catalogo .................");
                nombres = new String[] {"Kochikame","Dragon Ball","One Piece","Kaichou wa Maid-sama!","Ao Haru Ride","Horimiya","Ao no exorsist","Black clover","Another"};
                cantidad = new int[]{10,17,14,4,12,6,6,1,10};
                precio = new int[] {100,120,90,140,135,99,90,90,90};
                ids = new String[] {"id1","id2","id3","id4","id5","id6","id7","id8","id9"};
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(data));
                int i;
                for(i=0;i<9;i++){
                    bw.write(nombres[i].getBytes());
                    bw.write(",".getBytes());
                     bw.write(Integer.toString(cantidad[i]).getBytes());
                    bw.write(",".getBytes());
                     bw.write(Integer.toString(precio[i]).getBytes());
                    bw.write(",".getBytes());
                     bw.write(ids[i].getBytes());
                    bw.write(",".getBytes());
                }
                bw.close();
            }
            catalogo cata = new catalogo (nombres,cantidad,precio,ids);
            ObjectOutputStream  salida = new ObjectOutputStream (cliente.getOutputStream());
            salida.writeObject(cata);
            salida.flush();
            
                  
           
//==================================================================================================================
            
 //==============Envio de imagenes =================================================================================
            String path= "fotos_enviar";
            File dir  = new File(path);
            File [] f = dir.listFiles();
                int num=f.length;
                 dos=new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = null;
                System.out.printf("\nNumero de Archivos:" +num+"\n");
                dos.writeInt(num);  
                for (int i=0; i<f.length; i++) {
                    System.out.printf("\nArchivo Numero "+(i+1)+"\n");
                    System.out.printf(f[i].getName() + "," +f[i].getAbsolutePath()+ ","+f[i].length()+"\n");
                    String archivo=f[i].getAbsolutePath();
                    String nombre=f[i].getName();
                    long tam=f[i].length();
                    dis=new DataInputStream(new FileInputStream(archivo));
                    dos.flush();
                    dos.writeUTF(nombre);
                    dos.flush();
                    dos.writeLong(tam);
                    dos.flush();
                    //Seccion para el envio del archivo
                    byte[] b=new byte[1024];
                    long enviados=0;
                    int porcentaje, n;
                    System.out.print("Archivo "+ i);
                    while(enviados<tam){
                        n=dis.read(b);
                        dos.write(b,0, Math.min(b.length, (int)tam));
                        dos.flush();
                        enviados=enviados+n;
                        porcentaje=(int)(enviados*100/tam);
                        System.out.print("Enviado: "+porcentaje+"%\r");
                    }//while
                    System.out.print("\nArchivo Enviado\n\n");
                
                }
                dis.close();
                dos.close();
               cliente.close();
                       
                }
                

    }
            catch (Exception e){e.printStackTrace();}

}

       
    
}
