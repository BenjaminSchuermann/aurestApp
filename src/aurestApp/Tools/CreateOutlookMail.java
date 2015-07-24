package aurestApp.tools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CreateOutlookMail {
    private String betreff = "";
    private String empfaenger = "";
    private String HTMLbody = "";

    public void sendEMail() {
        Display display = new Display();
        Shell shell = new Shell(display);
        OleFrame frame = new OleFrame(shell, SWT.NONE);

        // This should start outlook if it is not running yet
        //OleClientSite site = new OleClientSite(frame, SWT.NONE, "OVCtl.OVCtl");
        //site.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);

        // Now get the outlook application
        OleClientSite site2 = new OleClientSite(frame, SWT.NONE, "Outlook.Application");
        OleAutomation outlook = new OleAutomation(site2);

        OleAutomation mail = invoke(outlook, "CreateItem", 0 /* Mail item */).getAutomation();

        setProperty(mail, "BodyFormat", 2 /* HTML */);
        setProperty(mail, "Subject", betreff);
        setProperty(mail, "To", empfaenger);
        setProperty(mail, "HtmlBody", "<html><body>" + HTMLbody + "</body></html>");

        /*
        File file = new File("m:\\sql.ini");
        if (file.exists()) {
            OleAutomation attachments = getProperty(mail, "Attachments");
            invoke(attachments, "Add", file.getAbsolutePath());
        } else {
            Dialoge.InfoAnzeigen("Attachment File "+file.getAbsolutePath()+" not found; will send email without attachment");
        }
        */
        invoke(mail, "Display" /* or "Send" */);
        display.close();

    }

    public void setBetreff(String betreff) {
        this.betreff = betreff;
    }

    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    public void setHTMLbody(String HTMLbody) {
        this.HTMLbody = HTMLbody;
    }

    private OleAutomation getProperty(OleAutomation auto, String name) {
        Variant varResult = auto.getProperty(property(auto, name));
        if (varResult != null && varResult.getType() != OLE.VT_EMPTY) {
            OleAutomation result = varResult.getAutomation();
            varResult.dispose();
            return result;
        }
        return null;
    }

    private Variant invoke(OleAutomation auto, String command,
                           String value) {
        return auto.invoke(property(auto, command),
                new Variant[]{new Variant(value)});
    }

    private Variant invoke(OleAutomation auto, String command) {
        return auto.invoke(property(auto, command));
    }

    private Variant invoke(OleAutomation auto, String command, int value) {
        return auto.invoke(property(auto, command),
                new Variant[]{new Variant(value)});
    }

    private boolean setProperty(OleAutomation auto, String name,
                                String value) {
        return auto.setProperty(property(auto, name), new Variant(value));
    }

    private boolean setProperty(OleAutomation auto, String name,
                                int value) {
        return auto.setProperty(property(auto, name), new Variant(value));
    }

    private int property(OleAutomation auto, String name) {
        return auto.getIDsOfNames(new String[]{name})[0];
    }
}
