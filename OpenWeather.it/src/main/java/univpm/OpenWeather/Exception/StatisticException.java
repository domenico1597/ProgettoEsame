package univpm.OpenWeather.Exception;



public class StatisticException extends Exception {

    public static final long serialVersionUID=3L;

    public StatisticException(){
        super();
    }

    public StatisticException(String messaggio){
        super(messaggio);
    }
}
