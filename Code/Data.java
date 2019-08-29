/**
 * Esta Clase almacena todas la variables de problema pero no de las coordenadas
 *
 * @author  Santiago Soto y Kevin Gomez
 * @version 1.3
 * @since   2018-04-20
 */
public class Data {

    private short n;
    private short m;
    private short u;
    private short breaks;
    private float r;
    private float speed;
    private float Tmax;
    private float Smax;
    private float stCustomer;
    private float Q;
    private float[][] l;
    private float[][] g;
    private float charge[];

    /**
     * Este es el metodo constructor de la clase
     * ademas en este se pasa de texto a su respectivo tipo de dato cada variable
     * @param data Es un arreglo de Strings en el que estan los datos
     * @exception NumberFormatException Erroe en el formato de los números
     */
    public Data(String[] data) throws NumberFormatException {
        n = Short.parseShort(data[0].substring(data[0].indexOf('=')+2));
        m = Short.parseShort(data[1].substring(data[1].indexOf('=')+2));
        u = Short.parseShort(data[2].substring(data[2].indexOf('=')+2));
        breaks = Short.parseShort(data[3].substring(data[3].indexOf('=')+2));
        r = Float.parseFloat(data[4].substring(data[4].indexOf('=')+2));
        speed = Float.parseFloat(data[5].substring(data[5].indexOf('=')+2));
        Tmax = Float.parseFloat(data[6].substring(data[6].indexOf('=')+2));
        Smax = Float.parseFloat(data[7].substring(data[7].indexOf('=')+2));
        stCustomer = Float.parseFloat(data[8].substring(data[8].indexOf('=')+2));
        Q = Float.parseFloat(data[9].substring(data[9].indexOf('=')+2));
        l = new float[3][breaks];
        g = new float[3][breaks];
        charge = new float[3];
    }
    /**
     * Este metodo gurada en la clase informacion de L y G
     * @param l Es un arreglo de Strings en el que estan los datos de l(t)
     * @param g Es un arreglo de Strings en el que estan los datos de g(W)
     */
    public void addLG(String[] l, String[] g){
        for (int i = 0; i < 3; i++){
            String[] temL = l[i].split(" ");
            String[] temG = g[i].split(" ");
            for(int j = 0; j < breaks;j++){
                this.l[i][j] =  Float.parseFloat(temL[j]);
                this.g[i][j] =  Float.parseFloat(temG[j]);
            }
        }
    }
    /**
     * Este metodo gurada la pendiende de energia que tiene cada tipo de estacion
     */
    public void addCharge() {

        for (int i = 0; i < 3; i++)
            charge[i] = g[i][breaks-1] / l[i][breaks-1];
    }
    //Getters
    public short getN() {
        return n;
    }

    public short getM() {
        return m;
    }

    public short getU() {
        return u;
    }

    public short getBreaks() {
        return breaks;
    }

    public float getR() {
        return r;
    }

    public float getSpeed() {
        return speed;
    }

    public float getTmax() {
        return Tmax;
    }

    public float getSmax() {
        return Smax;
    }

    public float getStCustomer() {
        return stCustomer;
    }

    public float getQ() {
        return Q;
    }

    public float[][] getL() {
        return l;
    }

    public float[][] getG() {
        return g;
    }

    public float getCharge(int s) { return charge[s]; }


}