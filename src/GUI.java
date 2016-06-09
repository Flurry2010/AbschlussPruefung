import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Flurry on 08.06.2016.
 */
public class GUI extends JFrame{

    private JPanel jPOben;
    private JTextField jTText;
    private JPanel jPUnten;
    private JPanel jPMitte;
    private JList<String> jLList;
    private List<String> einleseList;
    private DefaultListModel jListModel = new DefaultListModel();
    JScrollPane jSP;
    private JPanel jPLinks;
    private JPanel jPRechts;
    private ArrayList<Auto> autoList = new ArrayList<>();


    public GUI(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar jb = new JMenuBar();
        JMenu jm = new JMenu("Datei");
        JMenuItem jmiOeffnen = new JMenuItem("öffnen");

        ActionListener aclOeffnen = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fj = new JFileChooser();
                fj.showOpenDialog(null);
                einleseList = textEinlesen(fj.getSelectedFile().getPath());
                jListModel.clear();

                for (int i = 0; i < einleseList.size() ; i++) {

                    jListModel.addElement(einleseList.get(i).toString());
                    System.out.println(einleseList.get(i));
                }

            }
        };
        jmiOeffnen.addActionListener(aclOeffnen);
        jm.add(jmiOeffnen);
        jb.add(jm);
        setJMenuBar(jb);

        jPOben = new JPanel(new FlowLayout());
        jTText = new JTextField(30);
        JButton jBeinlesen = new JButton("Text Einlesen");

        jListModel = new DefaultListModel();
        jLList = new JList<>(jListModel);
        jSP = new JScrollPane(jLList);
        jSP.setViewportView(jLList);

        jLList.setCellRenderer(new ListCellRenderer<String>() {

            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                return null;
            }
        });

        ActionListener aclEinlesen = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                einleseList = textEinlesen(jTText.getText());

                jListModel.clear();

                for (int i = 0; i < einleseList.size() ; i++) {

                    jListModel.addElement(einleseList.get(i).toString());
                    System.out.println(einleseList.get(i));
                }
            }
        };

        KeyListener klEinlesen = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                if(e.getSource() instanceof JButton){
                    einleseList = textEinlesen(jTText.getText());

                    jListModel.clear();

                    for (int i = 0; i < einleseList.size() ; i++) {

                        jListModel.addElement(einleseList.get(i).toString());
                        System.out.println(einleseList.get(i));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        jBeinlesen.addActionListener(aclEinlesen);
        jBeinlesen.addKeyListener(klEinlesen);
        jPOben.add(jTText);
        jPOben.add(jBeinlesen);


        jPUnten = new JPanel();
        JButton jBAuto = new JButton(" Baue Auto");

        ActionListener aclBaueAuto = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                List<TreeMap> atemp = auswertenText(einleseList);
                ArrayList<Auto> btemp = baueAutos(atemp);

                jListModel.clear();

                for (int i = 0; i < btemp.size() ; i++) {

                    jListModel.addElement(btemp.get(i).toString());
                    System.out.println(btemp.get(i));
                }
                pack();
            }
        };

        jBAuto.addActionListener(aclBaueAuto);
        jPUnten.add(jBAuto);

        jPLinks = new JPanel();
        jPLinks.setLayout(new BoxLayout(jPLinks, BoxLayout.Y_AXIS));
        JButton jBSort = new JButton("Sortieren");

        JRadioButton namen = new JRadioButton("ABC");
        JRadioButton km_auf = new JRadioButton("KM aufsteigend");
        JRadioButton km_ab = new JRadioButton("KM absteigend");
        JRadioButton kw_auf = new JRadioButton("KW aufsteigend");
        JRadioButton kw_ab = new JRadioButton("KW absteigend");
        JRadioButton preis_auf = new JRadioButton("Preis aufsteigend");
        JRadioButton preis_ab = new JRadioButton("Preis absteigend");

        ButtonGroup bg = new ButtonGroup();
        bg.add(namen);
        bg.add(km_auf);
        bg.add(km_ab);
        bg.add(kw_auf);
        bg.add(kw_ab);
        bg.add(preis_auf);
        bg.add(preis_ab);

        ActionListener aclSort = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(namen.isSelected()){
                    Collections.sort(autoList);
                    updateView();
                }
                if(km_ab.isSelected()){
                    Collections.sort(autoList,Auto.KM_AB);
                    updateView();
                }
                if(km_auf.isSelected()){
                    Collections.sort(autoList,Auto.KM_AUF);
                    updateView();
                }
                if(kw_ab.isSelected()){
                    Collections.sort(autoList,Auto.KW_AB);
                    updateView();
                }
                if(kw_auf.isSelected()){
                    Collections.sort(autoList,Auto.KW_AUF);
                    updateView();
                }if(preis_ab.isSelected()){
                    Collections.sort(autoList,Auto.PREIS_AB);
                    updateView();
                }
                if(preis_auf.isSelected()){
                    Collections.sort(autoList,Auto.PREIS_AUF);
                    updateView();
                    pack();
                }

            }
        };

        jBSort.addActionListener(aclSort);

        jPLinks.add(namen);
        jPLinks.add(km_auf);
        jPLinks.add(kw_auf);
        jPLinks.add(preis_auf);
        jPLinks.add(new JPopupMenu.Separator());
        jPLinks.add(km_ab);
        jPLinks.add(kw_ab);
        jPLinks.add(preis_ab);

        JPanel jp = new JPanel(new FlowLayout());
        jp.add(jBSort);
        jPLinks.add(new JToolBar.Separator());
        jPLinks.add(jp);




        jPRechts = new JPanel();

        add(jPOben, BorderLayout.NORTH);
        add(jSP, BorderLayout.CENTER);
        add(jPLinks, BorderLayout.WEST);
        add(jPUnten, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }


    public List<String> textEinlesen(String pfad){
        List<String> list = new ArrayList<>();

        try
                (BufferedReader br = new BufferedReader(
                        new FileReader(pfad))) {
            String zeile = null;
            while ((zeile = br.readLine()) != null)
                list.add(zeile);

        } catch
                (IOException e) {
            System.out.println("Fehler beim lesen....");
        }

        return list;
    }

    public List<TreeMap> auswertenText(List<String> list){
        List<TreeMap> autoList = new ArrayList<>();

        System.out.println("Starte Check");

        Pattern p_TYP = Pattern.compile("(Limousine|Geländewagen / Pickup|Kleinwagen|Cabrio / Roadster|Van / Minibus|Kombi|Sportwagen / Coupé|Andere)");
        Pattern p_KM = Pattern.compile("([0-9]*.?[0-9]*)( *(km|KM|Km|kM))");
        Pattern p_EZ = Pattern.compile("(EZ *)([0-9]{1,2}/[\\d]{2,4})");
        Pattern p_SCHALTUNG = Pattern.compile("(Schaltung|Automatik)|(schaltung|automatik)");
        Pattern p_Preis = Pattern.compile("(\\d*\\.?\\d*)( ?€)");
        Pattern p_KW = Pattern.compile("(\\d{2,3})( * kW)");
        String[] daten = {  "markeModell" ,"typ" ,"kw", "schaltung","km", "erstZulassung" ,"preis"};
        Matcher matcher;

        for(int index = 0; index < list.size(); index++){

            TreeMap<String,String> auto = new TreeMap<>();
            for(String s : daten)
                auto.put(s,"N/A");

            if(list.get(index).matches("\\w{2,3}\\s-\\s\\d{5}.*")){
                index--;

                while(!list.get(index).startsWith("Finanzierung, Versicherung") && !list.get(index).startsWith("FinanzierungVersicherung")){
                    if(list.get(index+1).matches("\\w{2,3}\\s-\\s\\d{5}.*")) {
                        auto.put("markeModell", list.get(index));
                        index++;
                    }else{

                        matcher = p_EZ.matcher(list.get(index));
                        if(matcher.find()){
                            auto.put("ez",matcher.group(2));
                        }

                        matcher = p_KM.matcher(list.get(index));

                        if(matcher.find()){
                            auto.put("km",matcher.group(1));
                        }

                        matcher = p_Preis.matcher(list.get(index));
                        if(matcher.find()){
                            auto.put("preis",matcher.group(1));
                        }

                        matcher = p_SCHALTUNG.matcher(list.get(index));
                        if(matcher.find()){
                            auto.put("schaltung",list.get(index));
                        }

                        matcher = p_TYP.matcher(list.get(index));
                        if(matcher.find()){
                            auto.put("typ",list.get(index));
                        }

                        matcher = p_KW.matcher(list.get(index));
                        if (matcher.find()){
                            auto.put("kw",matcher.group(1));
                        }

                        index++;

                    }

                }//end innerWhile
                autoList.add(auto);
                //System.out.println(auto);
            }
        }

        return autoList;
    }

    public ArrayList<Auto> baueAutos(List<TreeMap> list){


        for (int i = 0; i < list.size() ; i++) {
           Auto x =  new Auto(list.get(i));
            autoList.add(x);
            //System.out.println(" Auto " + i + "wurde gebaut!!");
        }

        Collections.sort(autoList);

        return autoList;
    }

    public void updateView() {
        jListModel.clear();
        for (int i = 0; i < autoList.size() ; i++) {

            jListModel.addElement(autoList.get(i).toString());

        }

    }


}
