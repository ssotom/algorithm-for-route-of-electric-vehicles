/**
 * Esta es la clase principal del proyecto
 * en esta se leen los DataSets, se crea el Grafo con los datos
 * tambien invoca el algoritmo de TSP
 * @author  Santiago Soto y Kevin Gomez
 * @version 2.1
 * @since   2018-04-20
 */
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Node> nodes;
    public static Graph map;
    public static Data data;

    /**
     * Este Metodo realiza la lectura de los DataSets
     * @param dir Es la direccion donde esta el archivo a leer
     * @exception IOException Error con el arrivo de entrada
     */
    public static void read(String dir) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(dir));

        String[] dat = new String[10];
        for (int i = 0; i < dat.length; i++)
                dat[i] = b.readLine();
        data = new Data(dat);
        b.readLine(); b.readLine(); b.readLine();

        nodes = new ArrayList<>(data.getN());

        for (int i = 0; i < data.getN(); i++)
            nodes.add(new Node((b.readLine()).split(" ")));
        b.readLine(); b.readLine(); b.readLine();

        String[] l = new String[3];
        String[] g = new String[3];
        for (int i = 0; i < 3; i++)
            l[i] = b.readLine();
        b.readLine(); b.readLine(); b.readLine();

        for (int i = 0; i < 3; i++)
            g[i] = b.readLine();
        data.addLG(l,g);
        data.addCharge();

        b.close();
    }

    private static void makeGraph(){
        map = new Graph(data.getN());
        for ( Node node : nodes ) {
            for ( Node node2 : nodes ) {
                map.addArc(node.getID(),node2.getID(), (float) Math.sqrt(
                            Math.pow(node.getxCoordinate() - node2.getxCoordinate(),2) +
                            Math.pow(node.getyCoordinate() - node2.getyCoordinate(),2)
                            )
                );
            }
        }
    }
    /**
     * Este es le metodo principal
     * En este se invoca el metodo de lectura y del algoritmo de TSP
     */
    public static void main(String[] args){
            try {
            String dir = JOptionPane.showInputDialog("Ingrese dirección del DataSet")+"";
            read(dir);
            makeGraph();
            ShortestPath.tsp(map);
        } catch (IOException e) {
            System.err.println("IOException");
        }
        //for (Node node : nodes) {
        //    System.out.println(node.getID()+";"+node.getxCoordinate()+";"+node.getyCoordinate()+";"+node.getNodeType());
        //}
    }

}
