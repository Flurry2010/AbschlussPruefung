import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Flurry on 08.06.2016.
 */
public class GUI extends JFrame {

    private JPanel jPOben;
    private JTextField jTText;
    private JPanel jPUnten;
    private JList jLList;
    private List<String> einleseList;
    private DefaultListModel jListModel = new DefaultListModel();
    private JScrollPane jSP;
    private JPanel jPLinks;
    private JPanel jPRechts;
    private ArrayList<Auto> autoList = new ArrayList<>();


    //-----------------------------------------------KONSTRUKTOR-------------------------------------------------------
    public GUI() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //--------------------------------------------MENÜBAR----------------------------------------------------------
        JMenuBar jb = new JMenuBar();
        JMenu jm = new JMenu("Datei");
        JMenuItem jmiOeffnen = new JMenuItem("öffnen");
        JMenuItem jmiSpeichern = new JMenuItem("speichern");


        //-------------------------------------------ACL ÖFFNEN--------------------------------------------------------
        ActionListener aclOeffnen = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fj = new JFileChooser();
                fj.showOpenDialog(null);
                einleseList = textEinlesen(fj.getSelectedFile().getPath());
                jTText.setText(fj.getSelectedFile().getPath());
                jListModel.clear();

                for (int i = 0; i < einleseList.size(); i++) {

                    jListModel.addElement(einleseList.get(i).toString());
                    //System.out.println(einleseList.get(i));
                }
            }
        };


        //-------------------------------------------ACL SPEICHERN-----------------------------------------------------
        ActionListener aclSpeichern = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                List speicherList = jLList.getSelectedValuesList();
                textSchreiben(speicherList);

            }
        };

        jmiOeffnen.addActionListener(aclOeffnen);
        jmiSpeichern.addActionListener(aclSpeichern);
        jm.add(jmiOeffnen);
        jm.add(jmiSpeichern);
        jb.add(jm);
        setJMenuBar(jb);


        //---------------------------------------------PANEL OBEN------------------------------------------------------
        jPOben = new JPanel(new FlowLayout());
        jTText = new JTextField(30);
        JButton jBeinlesen = new JButton("Text Einlesen");

        jListModel = new DefaultListModel();
        jLList = new JList(jListModel);
        jSP = new JScrollPane(jLList);
        jSP.setViewportView(jLList);

        //---------------------------------------------ACL EINLESEN----------------------------------------------------
        ActionListener aclEinlesen = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                einleseList = textEinlesen(jTText.getText());

                jListModel.clear();

                for (int i = 0; i < einleseList.size(); i++) {

                    jListModel.addElement(einleseList.get(i).toString());
                    //System.out.println(einleseList.get(i));
                }
            }
        };

        //-----------------------------------------------KL ÖFFNEN-----------------------------------------------------
        KeyListener klEinlesen = new KeyListener() {

            public void keyTyped(KeyEvent e) {
            }


            public void keyPressed(KeyEvent e) {

                if (e.getSource() instanceof JButton) {
                    einleseList = textEinlesen(jTText.getText());

                    jListModel.clear();

                    for (int i = 0; i < einleseList.size(); i++) {

                        jListModel.addElement(einleseList.get(i).toString());
                        //System.out.println(einleseList.get(i));
                    }
                }
            }


            public void keyReleased(KeyEvent e) {
            }
        };

        jBeinlesen.addActionListener(aclEinlesen);
        jBeinlesen.addKeyListener(klEinlesen);
        jPOben.add(jTText);
        jPOben.add(jBeinlesen);


        //-----------------------------------------------PANEL UNTEN---------------------------------------------------
        jPUnten = new JPanel();
        JButton jBAuto = new JButton(" Baue Auto");


        //-----------------------------------------------ACL BAUE_AUTO-------------------------------------------------
        ActionListener aclBaueAuto = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                List<TreeMap> atemp = auswertenText(einleseList);
                ArrayList<Auto> btemp = baueAutos(atemp);

                jListModel.clear();

                jListModel = new DefaultListModel<Auto>();
                jLList.setModel(jListModel);


                //--------------------------------------CELLRENDERER---------------------------------------------------
                jLList.setCellRenderer(new ListCellRenderer<Auto>() {

                    public Component getListCellRendererComponent(JList<? extends Auto> list, Auto value, int index, boolean isSelected, boolean cellHasFocus) {

                        JPanel jp = new JPanel(new FlowLayout());

                        for (int i = 0; i < list.getComponentCount(); i++) {
                            jp = new JPanel(new FlowLayout());
                            jp.setBackground(Color.DARK_GRAY);
                            JLabel jl1 = new JLabel("Marke: " + value.getMarkeModell());
                            jl1.setBorder(new LineBorder(Color.BLUE));
                            JLabel jl2 = new JLabel("KM-Stand: " + value.getKm() + "");
                            jl2.setBorder(new LineBorder(Color.BLUE));
                            JLabel jl3 = new JLabel("Erstzulassung: " + value.getEz());
                            jl3.setBorder(new LineBorder(Color.BLUE));
                            JLabel jl4 = new JLabel("KW :" + value.getPs() + "");
                            jl4.setBorder(new LineBorder(Color.BLUE));
                            JLabel jl5 = new JLabel("Preis: " + value.getPreis() + "€");
                            jl5.setBorder(new LineBorder(Color.BLUE));

                            jl1.setForeground(Color.WHITE);
                            jl2.setForeground(Color.WHITE);
                            jl3.setForeground(Color.WHITE);
                            jl4.setForeground(Color.WHITE);
                            jl5.setForeground(Color.WHITE);

                            jp.add(jl1);
                            jp.add(jl2);
                            jp.add(jl3);
                            jp.add(jl4);
                            jp.add(jl5);
                        }

                        if (isSelected) {
                            jp.setBackground(Color.BLACK);
                            jp.getComponent(0).setForeground(Color.YELLOW);
                        }

                        if (cellHasFocus) {
                            jp.getComponent(0).setForeground(Color.RED);
                        }
                        return jp;
                    }
                });

                for (int i = 0; i < btemp.size(); i++) {

                    jListModel.addElement(btemp.get(i));
                   // System.out.println(btemp.get(i));
                }

                pack();
            }
        };

        //-------------------------MOUSE LISTENER ZUM ÄNDERN----------------------------------------------------------
        jLList.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {


                    int index = jLList.locationToIndex(e.getPoint());

                    JLabel head = new JLabel("Hier können Sie den Preis ändern");
                    JTextField tf = new JTextField();
                    Object[] ob = {head, tf};
                    int result = JOptionPane.showConfirmDialog(null, ob, "Neuer Preis", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        Auto a = (Auto) jListModel.getElementAt(index);
                        a.setPreis(Integer.parseInt(tf.getText()));
                        jLList.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Dann fick dich du Affe", "fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        jBAuto.addActionListener(aclBaueAuto);
        jPUnten.add(jBAuto);


        //-----------------------------------------------PANEL LINKS---------------------------------------------------
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


        //-----------------------------------------------ACL SORT------------------------------------------------------
        ActionListener aclSort = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (namen.isSelected()) {
                    Collections.sort(autoList);
                    updateView();
                }
                if (km_ab.isSelected()) {
                    Collections.sort(autoList, Auto.KM_AB);
                    updateView();
                }
                if (km_auf.isSelected()) {
                    Collections.sort(autoList, Auto.KM_AUF);
                    updateView();
                }
                if (kw_ab.isSelected()) {
                    Collections.sort(autoList, Auto.KW_AB);
                    updateView();
                }
                if (kw_auf.isSelected()) {
                    Collections.sort(autoList, Auto.KW_AUF);
                    updateView();
                }
                if (preis_ab.isSelected()) {
                    Collections.sort(autoList, Auto.PREIS_AB);
                    updateView();
                }
                if (preis_auf.isSelected()) {
                    Collections.sort(autoList, Auto.PREIS_AUF);
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


        //-----------------------------------------------SUPER PANEL---------------------------------------------------
        add(jPOben, BorderLayout.NORTH);
        add(jSP, BorderLayout.CENTER);
        add(jPLinks, BorderLayout.WEST);
        add(jPUnten, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    //---------------------------------------------------METHODEN------------------------------------------------------

    //-------------TEXT EINLESEN---------------------------
    public List<String> textEinlesen(String pfad) {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pfad))) {

            String zeile = null;
            while ((zeile = br.readLine()) != null)
                list.add(zeile);

        } catch
                (IOException e) {
            System.out.println("Fehler beim lesen....");
        }

        return list;
    }

    //-------------TEXT Schreiben---------------------------
    public void textSchreiben(List list) {

        System.out.println("----------Schreibe----------------------------");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("GewaehlteAutos.txt"))) {

            for (int i = 0; i < list.size(); i++) {
                System.out.println(i+1 + ". Auto -- " + list.get(i).toString());
                bw.write(list.get(i).toString());
                bw.newLine();
            }

        } catch
                (IOException e) {
            System.out.println("Fehler beim schreiben....");
        }
    }


    //------------TEST AUSWERTEN--------------------------
    public List<TreeMap> auswertenText(List<String> list) {
        List<TreeMap> autoList = new ArrayList<>();

        //System.out.println("Starte Check");

        Pattern p_TYP = Pattern.compile("(Limousine|Geländewagen / Pickup|Kleinwagen|Cabrio / Roadster|Van / Minibus|Kombi|Sportwagen / Coupé|Andere)");
        Pattern p_KM = Pattern.compile("([0-9]*.?[0-9]*)( *(km|KM|Km|kM))");
        Pattern p_EZ = Pattern.compile("(EZ *)([0-9]{1,2}/[\\d]{2,4})");
        Pattern p_SCHALTUNG = Pattern.compile("(Schaltung|Automatik)|(schaltung|automatik)");
        Pattern p_Preis = Pattern.compile("(\\d*\\.?\\d*)( ?€)");
        Pattern p_KW = Pattern.compile("(\\d{2,3})( * kW)");
        String[] daten = {"markeModell", "typ", "kw", "schaltung", "km", "erstZulassung", "preis"};
        Matcher matcher;

        for (int index = 0; index < list.size(); index++) {

            TreeMap<String, String> auto = new TreeMap<>();
            for (String s : daten)
                auto.put(s, "N/A");

            if (list.get(index).matches("\\w{2,3}\\s-\\s\\d{5}.*")) {
                index--;

                while (!list.get(index).startsWith("Finanzierung, Versicherung") && !list.get(index).startsWith("FinanzierungVersicherung")) {
                    if (list.get(index + 1).matches("\\w{2,3}\\s-\\s\\d{5}.*")) {
                        auto.put("markeModell", list.get(index));
                        index++;
                    } else {

                        matcher = p_EZ.matcher(list.get(index));
                        if (matcher.find()) {
                            auto.put("ez", matcher.group(2));
                        }

                        matcher = p_KM.matcher(list.get(index));

                        if (matcher.find()) {
                            auto.put("km", matcher.group(1));
                        }

                        matcher = p_Preis.matcher(list.get(index));
                        if (matcher.find()) {
                            auto.put("preis", matcher.group(1));
                        }

                        matcher = p_SCHALTUNG.matcher(list.get(index));
                        if (matcher.find()) {
                            auto.put("schaltung", list.get(index));
                        }

                        matcher = p_TYP.matcher(list.get(index));
                        if (matcher.find()) {
                            auto.put("typ", list.get(index));
                        }

                        matcher = p_KW.matcher(list.get(index));
                        if (matcher.find()) {
                            auto.put("kw", matcher.group(1));
                        }

                        index++;
                    }
                }
                autoList.add(auto);
                //System.out.println(auto);
            }
        }

        return autoList;
    }

    //---------------AUTOS BAUEN------------------------------------
    public ArrayList<Auto> baueAutos(List<TreeMap> list) {

        for (int i = 0; i < list.size(); i++) {
            Auto x = new Auto(list.get(i));
            autoList.add(x);
            //System.out.println(" Auto " + i + "wurde gebaut!!");
        }

        Collections.sort(autoList);
        pack();

        return autoList;
    }

    //-------------GUI UPDATEN------------------------------------
    public void updateView() {
        jListModel.clear();
        for (int i = 0; i < autoList.size(); i++) {

            jListModel.addElement(autoList.get(i).toString());
        }
    }


}
