import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by Flurry on 08.06.2016.
 */
public class Auto implements Comparable<Auto>, Comparator<Auto> {


    private String markeModell;
    private int ps;
    private int km;
    private String schaltung;
    private String ez;
    private int preis;

    public Auto(TreeMap<String,String> list){

        markeModell =list.get("markeModell");
        if(list.get("kw").equals("N/A")){
            ps = 0;
        }else{
            ps = Integer.parseInt(list.get("kw"));
        }

        km = Integer.parseInt(list.get("km").replaceAll("\\.", ""));
        schaltung = list.get("schaltung");
        ez = list.get("ez");
        preis = Integer.parseInt(list.get("preis").replaceAll("\\.", ""));
    }

    public String toString() {
        return "Marke und Model: '" + markeModell + '\'' +
                ", KW: " + ps +
                ", KM: " + km +
                ", Schaltung: '" + schaltung + '\'' +
                ", EZ: '" + ez + '\'' +
                ", Preis: '" + preis + '\'' + " €";
    }

    public String getMarkeModell() {
        return markeModell;
    }

    public int getPs() {
        return ps;
    }

    public int getKm() {
        return km;
    }

    public String getSchaltung() {
        return schaltung;
    }

    public String getEz() {
        return ez;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int compareTo(Auto a) {
        return this.markeModell.compareTo(a.getMarkeModell());
    }

    public int compare(Auto a1, Auto a2) {
        if (a1 == null && a2 == null)
            return 0;

        if (a1 == null)
            return 1;

        if (a2 == null)
            return -1;

        return a1.markeModell.compareTo(a2.getMarkeModell());
    }

    public static  final Comparator<Auto> PREIS_AUF = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a1.preis-(a2.getPreis());
        }
    };

    public static  final Comparator<Auto> PREIS_AB = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a2.preis-(a1.getPreis());
        }
    };

    public static  final Comparator<Auto> KM_AB = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a2.km-(a1.getKm());
        }
    };

    public static  final Comparator<Auto> KM_AUF = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a1.km-(a2.getKm());
        }
    };

    public static  final Comparator<Auto> KW_AUF = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a1.ps-(a2.getPs());
        }
    };

    public static  final Comparator<Auto> KW_AB = new Comparator<Auto>(){

        public int compare(Auto a1, Auto a2) {
            if (a1 == null && a2 == null)
                return 0;

            if (a1 == null)
                return 1;

            if (a2 == null)
                return -1;

            return a2.ps-(a1.getPs());
        }
    };
}
