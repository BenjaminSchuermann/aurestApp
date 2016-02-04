package aurestApp.tools;

import aurestApp.Model;
import aurestApp.tools.eigeneklassen.Suchprojekt;

import java.awt.*;
import java.io.*;

public class Generator {

    private static String status = "";

    public static String erstelleProjekt(String projektnummer, String kunde, String projektname, boolean projekttyp, String offerte, String ProjektUrprojekt, Model m) {
        if (projektnummer.isEmpty()) {
            status = "Projektnummer fehlt";
            Dialoge.DialogAnzeigeBox("fehler", "Projektnummer fehlt");
            return "Projektnummer fehlt";
        }
        String art;
        if (projekttyp) {
            art = "Eigenfertigung";
        } else {
            art = "Fremdfertigung";
        }
        //Falls die Projektnummer mit einem Jahr angegeben wurde, extrahiere alles ab dem Punkt
        if (projektnummer.contains("."))
            projektnummer = projektnummer.substring(projektnummer.indexOf(".") + 1);

        m.setProjektNummer(projektnummer);
        String pfad = m.getProjektNummer();

        if (!kunde.isEmpty())
            pfad = pfad + " " + kunde.replaceAll("[\\/:*?\"<>|]", "-");
        if (!projektname.isEmpty())
            pfad = pfad + ", " + projektname.replaceAll("[\\/:*?\"<>|]", "-");

        File ziel = new File("P:/" + pfad);
        if (ziel.exists()) {
            Dialoge.DialogAnzeigeBox("warnung", "Projekt ist bereits angelegt");
            return "Projekt ist bereits angelegt";
        }

        try {
            copyDir(new File(m.getVorlagenProjekt() + "/" + art), new File("P:/" + pfad), m.getProjektNummer());
        } catch (Exception e) {
            Dialoge.exceptionDialog(e, "Fehler beim Kopieren der Vorlage");
            e.printStackTrace();
            return "Fehler beim Kopieren der Vorlage";
        }

        if (!offerte.isEmpty()) {
            while (!ziel.exists()) {
                //Warteschleife
            }
            // pfad der offerte
            String pfadoff;
            String offshort;
            String offkomplett;

            offshort = offerte.substring(0, 3);
            // System.out.print(offshort);

            File test = new File("O:/" + offshort + "_/");
            offkomplett = searchFile(test, offerte);

            // System.out.print(test.toString());
            // System.out.print(offkomplett);

            pfadoff = "O:/" + offshort + "_/" + offkomplett;
            pfadoff = pfadoff.replace("/", "\\");
            // System.out.print(pfadoff);
            // Ausgabedatei öffnen

            //Link im Projektordner zur Offerte
            File link1 = createVBS(pfadoff + "\\Projekt " + pfad + ".lnk", "P:\\" + pfad, "link1");
            File link2 = createVBS("P:\\" + pfad + "\\Offerte " + offkomplett + ".lnk", pfadoff, "link2");

            try {
                Desktop.getDesktop().open(link1);
                Desktop.getDesktop().open(link2);
            } catch (IOException e) {
                e.printStackTrace();
                Dialoge.exceptionDialog(e, "Fehler beim öffnen der Linkdatei für das Logbuch");
            }

            while (!new File(pfadoff + "\\Projekt " + pfad + ".lnk").exists() && !new File("P:\\" + pfad + "\\Offerte " + offkomplett + ".lnk").exists()) {
                //Warteschleife
            }

        }
        m.setProjektUrprojekt(ProjektUrprojekt);
        // UrProjekt verlinken
        if (!m.getProjektUrprojekt().isEmpty()) {
            while (!ziel.exists()) {
                //Warteschleife
            }
            status = "Projektlink wird vorbereitet";
            Suchprojekt urprojekt = findeProjekt(ProjektUrprojekt);
            // pfad des urprojekts
            String pfadsp;
            String spkomplett;

            pfad = pfad.replace("/", "\\");

            pfadsp = urprojekt.getProjektpfad().getAbsolutePath();
            spkomplett = urprojekt.getProjektname();

            System.out.println("der Pfad:" + pfad);
            System.out.println("der pfadsp:" + pfadsp);
            System.out.println("der spkomplett:" + spkomplett);

            // Ausgabedatei öffnen
            status = "Projektlinkdatei wird erstellt";

            File link1 = createVBS("P:\\" + pfad + "\\UrProjekt " + spkomplett + ".lnk", pfadsp, "link1");
            File link2 = createVBS(pfadsp + "\\Projekt " + pfad + ".lnk", "P:\\" + pfad, "link2");

            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(link1);
                desktop.open(link2);
            } catch (IOException e) {
                e.printStackTrace();
                Dialoge.exceptionDialog(e, "Fehler beim öffnen der Linkdatei für das Ursprungsprojekt");
                return "Fehler beim öffnen der Linkdatei für das Ursprungsprojekt";
            }
            status = "UrProjekt wird verlinkt";
            while (!new File("P:\\" + pfad + "\\UrProjekt " + spkomplett + ".lnk").exists() && !new File(pfadsp + "\\Projekt " + pfad + ".lnk").exists()) {
                //Warteschleife
            }


        }
        return "Projekt angelegt";
    }

    public static String erstelleService(String servicenummer, String kunde, String servicename, boolean servicetyp, String serviceprojekt, Model m) {
        status = "Servicegenerierung gestartet";
        if (servicenummer.isEmpty()) {
            Dialoge.DialogAnzeigeBox("fehler", "Servicenummer fehlt");
            return "Servicenummer fehlt";
        }
        String snfl = "";

        m.setServiceNummer(servicenummer.substring(5, 9));
        String pfad = "Service_" + servicenummer.substring(2, 4) + "/"
                + m.getServiceNummer();

        if (!kunde.isEmpty()) {
            m.setServiceKunde(kunde.replaceAll("[\\/:*?\"<>|]", "-"));
            pfad = pfad + " " + m.getServiceKunde();
        }

        if (!servicename.isEmpty()) {
            pfad = pfad + ", " + servicename.replaceAll("[\\/:*?\"<>|]", "-");
            snfl = servicename.replaceAll("[\\/:*?\"<>|]", "-");
        }
        // System.out.println(pfad);
        status = "Zielordner wurde vorbereitet";

        File ziel = new File("Q:/" + pfad);
        if (ziel.exists()) {
            Dialoge.DialogAnzeigeBox("fehler", "Service ist bereits angelegt");
            return "Service ist bereits angelegt";
        }

        try {
            copyService(new File(m.getVorlagenService()), ziel);
        } catch (Exception e) {
            Dialoge.exceptionDialog(e, "Fehler beim kopieren der Servicevorlage");
            e.printStackTrace();
            return "Fehler beim kopieren der Servicevorlage";
        }
        status = "Vorlage wird kopiert";

        // Zieldatei anlegen und falls noetig loeschen um eine neue zu erhalten
        if (!serviceprojekt.isEmpty()) {
            m.setServiceUrprojekt(serviceprojekt);
            while (!ziel.exists()) {
                //Warteschleife
            }
            status = "Projektlink wird vorbereitet";
            // pfad des urprojekts
            Suchprojekt urprojekt = findeProjekt(serviceprojekt);
            String pfadsp;
            String spkomplett;

            pfadsp = urprojekt.getProjektpfad().getAbsolutePath();
            spkomplett = urprojekt.getProjektname();

            pfadsp = pfadsp.replace("/", "\\");
            pfad = pfad.replace("/", "\\");

            // System.out.print(pfadoff);
            // Ausgabedatei öffnen
            status = "Projektlinkdatei wird erstellt";

            File link1 = createVBS("Q:\\" + pfad + "\\Projekt " + spkomplett + ".lnk", pfadsp, "link1");
            File link2 = createVBS(pfadsp + "\\Service " + servicenummer + " " + snfl + ".lnk", "Q:\\" + pfad, "link2");

            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(link1);
                desktop.open(link2);
            } catch (IOException e) {
                Dialoge.exceptionDialog(e, "Fehler beim öffnen der Linkdatei(en)");
                e.printStackTrace();
                return "Fehler beim öffnen der Linkdatei(en)";
            }
            status = "Projekt wird verlinkt";
            while (!new File("Q:\\" + pfad + "\\Projekt " + spkomplett + ".lnk").exists() && !new File(pfadsp + "\\Service " + servicenummer + " " + snfl + ".lnk").exists()) {
                //Warteschleife
            }
            //System.out.println(ausgabe.delete());
            status = "Service angelegt";
        }
        status = "Service angelegt";
        return status;
    }

    public static String searchFile(File dir, String find) {
        File[] files = dir.listFiles();
        String matches = "";
        // System.out.println(dir);
        // System.out.println(find);
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(find)) { // überprüft ob
                    // der Dateiname mit
                    // dem Suchstring
                    // übereinstimmt. Groß/Kleinschreibung wird
                    // ignoriert.
                    matches = file.getName();
                    //System.out.println(file.getName());
                    break;
                }
            }
        }
        return matches;
    }

    private static void copyDir(File quelle, File ziel, String projekt) throws Exception {
        //Zum erzeugen einer Exception, falls nicht auf die Vorlage zugegriffen werden kann
        if (!quelle.exists())
            throw new IOException(quelle + " wurde nicht gefunden");

        File[] files = quelle.listFiles();
        File newFile;
        boolean mkdirs = ziel.mkdirs();

        if (files != null) {
            for (File file : files) {
                newFile = new File(ziel.getAbsolutePath()
                        + System.getProperty("file.separator")
                        + file.getName());
                newFile = new File(newFile.getAbsolutePath().replace("XXXX", projekt));
                if (file.isDirectory()) {
                    copyDir(file, newFile, projekt);
                } else {
                    copyFile(file, newFile);
                }
            }
        }
    }

    private static void copyService(File quelle, File ziel) throws Exception {
        //Zum erzeugen einer Exception, falls nicht auf die Vorlage zugegriffen werden kann
        if (!quelle.exists())
            throw new IOException(quelle + " wurde nicht gefunden");

        File[] files = quelle.listFiles();
        File newFile;
        ziel.mkdirs();
        if (files != null) {
            for (File file : files) {
                newFile = new File(ziel.getAbsolutePath()
                        + System.getProperty("file.separator")
                        + file.getName());
                if (file.isDirectory()) {
                    copyService(file, newFile);
                } else {
                    copyFile(file, newFile);
                }
            }
        }
    }

    private static void copyFile(File file, File ziel) throws Exception {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(ziel, true));
        int bytes;
        while ((bytes = in.read()) != -1) { // Datei einlesen
            out.write(bytes); // Datei schreiben
        }
        in.close();
        out.close();
    }

    private static File createVBS(String ziel, String quelle, String dateiname) {

        File ausgabe = new File(System.getProperty("java.io.tmpdir") + dateiname + ".vbs");

        try {
            FileWriter file = new FileWriter(ausgabe);
            BufferedWriter bf = new BufferedWriter(file);
            bf.write("set WshShell = WScript.CreateObject(\"WScript.Shell\")");
            bf.newLine();
            bf.write("set oShellLink = WshShell.CreateShortcut(\"" + ziel + "\")");
            bf.newLine();
            bf.write("oShellLink.TargetPath = \"" + quelle + "\"");
            bf.newLine();
            bf.write("oShellLink.WindowStyle = 1");
            bf.newLine();
            bf.write("oShellLink.Hotkey = \"CTRL+SHIFT+F\"");
            bf.newLine();
            bf.write("oShellLink.Description = \"Ben\"");
            bf.newLine();
            bf.write("oShellLink.Save");
            bf.newLine();
            bf.close();
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Fehler beim erstellen der Linkdatei");
            e.printStackTrace();
        }
        return ausgabe;
    }

    public static Suchprojekt findeProjekt(String projektnummer) {
        Suchprojekt projekt = new Suchprojekt();

        String projektkurz = projektnummer.substring(0, 2);
        // System.out.print(spshort);
        File archivpfad;
        if (Integer.parseInt(projektkurz) <= 60) {
            archivpfad = new File("Q:\\" + projektkurz + "__\\");
        } else {
            archivpfad = new File("Q:\\" + projektkurz + "_\\");
        }
        String projektname = searchFile(archivpfad, projektnummer);

        if (!projektname.isEmpty()) {
            projekt.setGefunden(true);
            projekt.setProjektlaufwerk("Q:\\");
            projekt.setProjektname(projektname);
            projekt.setProjektpfad(new File(archivpfad.getAbsolutePath() + "\\" + projektname));
        } else {
            projektname = searchFile(new File("P:\\"), projektnummer);
            if (!projektname.isEmpty()) {
                projekt.setGefunden(true);
                projekt.setProjektlaufwerk("P:\\");
                projekt.setProjektname(projektname);
                projekt.setProjektpfad(new File("P:\\" + projektname));
            }
        }
        System.out.println(projekt.isGefunden());
        System.out.println(projekt.getProjektlaufwerk());
        System.out.println(projekt.getProjektname());
        System.out.println(projekt.getProjektpfad());
        return projekt;
    }
}