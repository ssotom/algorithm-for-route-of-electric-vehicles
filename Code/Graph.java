/**
 * Esta es la clase de un grafo dirigido hecho con matriz de adyacencia
 * Este alamacena las distancia entre todos los puntos
 * @author  Santiago Soto y Kevin Gomez
 * @version 1.0
 * @since   2018-03-31
 */
import java.util.ArrayList;

public class Graph {
    protected int size;
    float[][] matrix;

    /**
     * Este es el metodo constructor de la clase
     * @param size Es el numero de vertices
     */
    public Graph(int size) {
        matrix = new float[size][size];
        this.size = size;
    }
    /**
     * Este metodo agraga un arista entre dos vertices
     * @param source Es el vertice de origen
     * @param destination Es el vertice de destino
     * @param weight Es la distancia entre los dos vertices
     */
    public void addArc(short source, short destination, float weight) {
        matrix[source][destination] = weight;
    }

    /**
     * Este metodo obtiene una lista de los sucesores o hijos de un vertice
     * @param vertex Es el vertice de origen
     * @return ArrayList<Short> Lista de succesores
     */
    public ArrayList<Short> getSuccessors(int vertex) {
        ArrayList<Short> s = new ArrayList<>();
        for (short i = 0; i < size; ++i)
            if (matrix[vertex][i] != 0)
                s.add(i);
        return s.size() == 0 ? null : s;
    }

    /**
     * Este metodo retorna la distancia entre dos vertices
     * @param source Es el vertice de origen
     * @param destination Es el vertice de destino
     * @return Float distancia entre dos vertices
     */
    public float getWeight(int source, int destination) {
        return matrix[source][destination];
    }
}