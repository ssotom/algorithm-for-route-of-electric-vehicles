/**
 * Esta Clase donde se realiza el algoritmo de TSP con una serie de renstricciones y modificaciones
 *
 * @author  Santiago Soto y Kevin Gomez
 * @version 3.3
 * @since   2018-04-13
 */
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    /**
     * Este es el metodo realiza la invocacion del algoritmo de TSP
     * Pero este metodo no hace un solo llamado a TSP(), hace una serie de llamado pasando diferentes atributos
     * y con respecto estos atributos se compara cual respuesta es mejor y se imprime
     * ademas en este metodo se realiza una optimizacion de carga de bateria
     * @param graph Es un grafo que representa el mapa y que se creo a partir de los DataSets
     */
    public static void tsp(Graph graph) {
        List<Ruta> mejorRuta = new LinkedList<>();
        float mejorTiempo = Float.MAX_VALUE;
        for (float j = 0; j < 10; j ++) {
            for (float k = 0; k < 10; k += 0.5) {
                for (float i = 0; i < 2; i += 0.1) {
                    List<Ruta> rutas = new LinkedList<>();
                    float timep = 0;
                    float timeT = tsp(graph, rutas, i, k, j);
                    for (Ruta ruta : rutas) {
                        if (ruta.tCarga > 0) {
                            timep = ruta.bateriaFinal / Main.data.getCharge(ruta.tipoS);
                            if (Math.round((ruta.tCarga - timep) * 60) < 1)
                                ruta.parte1 += ruta.parte2;
                            else
                                ruta.parte1 += "(Carga: " + Math.round((ruta.tCarga - timep) * 60) + " min) " + ruta.parte2;
                            timeT -= timep;
                        }
                    }
                    if (timeT < mejorTiempo) {
                        mejorTiempo = timeT;
                        mejorRuta = rutas;
                    }
                }
            }
        }
        for (Ruta ruta : mejorRuta)
            System.out.println(ruta.parte1);
        System.out.println(mejorTiempo);
    }

    /**
     * Este es el metodo es algoritmo de TSP con una serie de restricciones
     * Entre estas restricciones esta la duracion de bateria y tiempo maximo de una ruta
     * @param graph Es un grafo que representa el mapa y que se creo a partir de los DataSets
     * @param rutas Es una lista de la clase Ruta donde se alamcenara la respusta de una posible mejor ruta
     * @param t Esta varible modificara el tiempo maximo de una ruta
     * @param b Esta variable modifica la anticipacion con la que se debe cargar la bateria
     * @param v Esta variable modifca la anticipacion con la que se debe acaba una ruta
     * @return float Este el valor en horas de la duracion de todas las rutas
     */
    public static float tsp(Graph graph, List<Ruta> rutas, float t, float b, float v) {
        boolean[] cVisited = new boolean[Main.data.getM()+1];
        cVisited[0] = true;
        short element = 0, dst = 0;
        float timeT = 0f;
        float timeP = 0f;
        short noRutas = 1;
        float speed = Main.data.getSpeed();
        float Q = Main.data.getQ();
        short krecor = 0;
        float crecor = (Main.data.getQ()/Main.data.getR())-4;
        boolean cargo = false;
        byte cont = 0;
        Ruta ruta = new Ruta();
        String parte = "";
        parte += "Ruta "+noRutas+": 0";
        //System.out.println(Main.nodes.get(0).getxCoordinate()+";"+Main.nodes.get(0).getyCoordinate());
            while (true) {
                boolean minFlag = false;
                float min = Float.MAX_VALUE;
                for (short i = 1; i <= Main.data.getM(); i++){
                    if (graph.getWeight(element, i) > 0 && !cVisited[i]) {
                        if (min > graph.getWeight(element, i)) {
                            min = graph.getWeight(element, i);
                            dst = i;
                            minFlag = true;
                        }
                    }
                }
                float minStation = Float.MAX_VALUE;
                short station = 0;
                for (short i = (short)(Main.data.getM()+1); i < Main.data.getN(); i++){
                    if (minStation > graph.getWeight(dst, i)) {
                        minStation = graph.getWeight(dst, i);
                        station = i;
                    }
                }
                if (minFlag) {
                    if ( ((cargo || timeP > Main.data.getTmax()-2) && (krecor+graph.getWeight(element, dst) + graph.getWeight(dst, 0)) > crecor-v)
                            || t+timeP+((graph.getWeight(element, dst) + graph.getWeight(dst, 0))/ speed) > Main.data.getTmax()) {
                        timeP += graph.getWeight(element,0)/speed;
                        timeT += timeP;
                        Q -= graph.getWeight(element,0)*Main.data.getR();
                        //System.out.println(Main.nodes.get(0).getxCoordinate()+";"+Main.nodes.get(0).getyCoordinate());
                        parte += " (" + Math.round((graph.getWeight(element,0)/speed)*60)+" min) 0";
                        noRutas++;
                        timeP = 0;
                        element = 0;
                        krecor = 0;
                        cont = 0;
                        if(cargo)
                            ruta.parte2 = parte;
                        else
                            ruta.parte1 = parte;
                        ruta.bateriaFinal = Q;
                        rutas.add(ruta);
                        ruta = new Ruta();
                        Q = Main.data.getQ();
                        parte = "";
                        parte += "Ruta "+noRutas+": 0";
                        cargo = false;
                        //System.out.println(Main.nodes.get(0).getxCoordinate()+";"+Main.nodes.get(0).getyCoordinate());
                        continue;
                    }
                    if (cont > 1 && !cargo && (krecor+graph.getWeight(element, dst) + graph.getWeight(dst, 0)) > crecor-b ) {
                        timeP += graph.getWeight(element,station)/speed;
                        float charge = Main.data.getCharge(Main.nodes.get(station).getStationType());
                        parte += " (" + Math.round((graph.getWeight(element,station)/speed)*60)+" min) "+station;
                        element = station;
                        timeP += (Main.data.getQ()-Q)/charge;
                        cargo = true;
                        krecor = 0;
                        ruta.parte1 = parte;
                        ruta.tipoS = Main.nodes.get(station).getStationType();
                        parte = "";
                        ruta.tCarga = (Main.data.getQ()-Q)/charge;
                        Q = Main.data.getQ();
                        continue;
                    }
                    cVisited[dst] = true;
                    element = dst;
                    timeP += min/speed + Main.data.getStCustomer();
                    Q -= min*Main.data.getR();
                    krecor += min;
                    cont++;
                    //System.out.println(dst + "\t");
                    //System.out.println(Main.nodes.get(dst).getxCoordinate()+";"+Main.nodes.get(dst).getyCoordinate());
                    parte += " (" + Math.round((min/speed)*60)+" min) " +dst;
                    continue;
                }
                break;
            }
        Q -= graph.getWeight(element,0)*Main.data.getR();
        timeT += graph.getWeight(dst,0)/speed;
        //System.out.println(Main.nodes.get(0).getxCoordinate()+";"+Main.nodes.get(0).getyCoordinate());
        parte += " (" + Math.round((graph.getWeight(dst,0)/speed)*60)+" min) 0";
        if(cargo)
            ruta.parte2 = parte;
        else
            ruta.parte1 = parte;
        ruta.bateriaFinal = Q;
        rutas.add(ruta);
        return timeT;

    }
}

