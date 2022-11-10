package univpm.OpenWeather.Utils;

import univpm.OpenWeather.Exception.StatisticException;
import java.util.Vector;

public class StatisticCalculator extends Utils{

    /**
     * Valore massimo assunto dalla grandezza nell'intervallo preso in considerazione
     */
    private Double max;
    /**
     * Valore minimo assunto dalla grandezza nell'intervallo preso in considerazione
     */
    private Double min;
    /**
     * Contatore della quantità di campioni utilizzati
     */
    private Integer N_spazioVariabilis;
    /**
     * Accumulatore dei valori dei campioni utilizzati
     */
    private Double accumulatore;
    /**
     * Vettore contenente i campioni utilizzati
     */
    private Vector<Double> spazioVariabilis;

    /**
     * Costruttore di default
     */

    public StatisticCalculator(){
        this.max=-Double.MAX_VALUE;
        this.min=Double.MAX_VALUE;
        this.N_spazioVariabilis=0;
        this.accumulatore=0.0;
        this.spazioVariabilis=new Vector<Double>();
    }


    /**
     * Metodo che serve per aggiungere i dati
     * Andrò ad eseguire diverse operazioni per andare ad assegnare a max,min,N_spazioVariabilis,accumulatore
     * i corrispettivi valori corretti
     */

    public void addSpazioVaribili(double spazioVariabili){
        this.N_spazioVariabilis++;
        this.accumulatore+=spazioVariabili;
        if (spazioVariabili > this.max)
            this.max=spazioVariabili;


        if (spazioVariabili < this.min)
            this.min=spazioVariabili;


        this.spazioVariabilis.add(spazioVariabili);
    }


    public Double getMax()throws StatisticException{
        if (max==-Double.MAX_VALUE)
            throw new StatisticException("Campioni non trovati");
        else return max;
    }

    public Double getMin()throws StatisticException{
        if (max==Double.MAX_VALUE)
            throw new StatisticException("Campioni non trovati");
        else
            return min;
    }

    /**
     * Metodo che restituisce la media arrotondando le ultime due cifre dopo la virgola a soli due valori
     * @return Media
     * @throws EccezioneStatistiche
     */
    public Double getMedia() throws StatisticException {
        if (max==-Double.MAX_VALUE)
            throw new StatisticException("Campioni non trovati");

        else return Arrotonda(accumulatore/N_spazioVariabilis);
    }


    /**
     * Metodo che restituisce la varianza arrotondando le ultime due cifre dopo la virgola a soli due valori
     * @return Varianza
     * @throws EccezioneStatistiche
     */
    public Double getVarianza()throws StatisticException{
        if (max==-Double.MAX_VALUE)
            throw new StatisticException("Campioni non trovati");
        else {
            Double med=this.getMedia();
            Double temp=0.0;
            for (Double spazioVariabili:spazioVariabilis){
                temp+=Math.pow((spazioVariabili - med),2);
            }
            return Arrotonda(temp/this.N_spazioVariabilis);
        }
    }

}

