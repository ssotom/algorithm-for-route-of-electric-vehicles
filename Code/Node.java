/**
 * Esta Clase almacena toda informacion de los coordenadas
 *
 * @author  Santiago Soto y Kevin Gomez
 * @version 1.0
 * @since   2018-04-14
 */
public class Node {

    private short ID;
    private String name;
    private float xCoordinate;
    private float yCoordinate;
    private char nodeType;
    private byte stationType;

    /**
     * Este es el metodo constructor de la clase
     * @param ID Es el numero de identificacion del nodo
     * @param name Es el nombre del cliente
     * @param xCoordinate Es La coordenda en el eje X
     * @param yCoordinate Es La coordenda en el eje Y
     * @param nodeType Es el tipo de nodo
     * @param stationType Es el tipo de estacion
     */
    public Node(short ID, String name, float xCoordinate, float yCoordinate, char nodeType, byte stationType) {
        this.ID = ID;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.nodeType = nodeType;
        this.stationType = stationType;
    }

    /**
     * Este es el metodo constructor de la clase
     * @param data Es una arreglo de Strings que cotiene todos los datos
     */
    public Node(String[] data){
        ID = Short.parseShort(data[0]);
        name = data[1];
        xCoordinate = Float.parseFloat(data[2]);
        yCoordinate = Float.parseFloat(data[3]);
        nodeType = data[4].charAt(0);
        stationType = Byte.parseByte(data[5]);
    }

    /* Metodos getters*/
    public short getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public char getNodeType() {
        return nodeType;
    }

    public byte getStationType() {
        return stationType;
    }

    @Override
    public String toString() {
        return "ID: " + ID + " - Name: " + name;
    }
}