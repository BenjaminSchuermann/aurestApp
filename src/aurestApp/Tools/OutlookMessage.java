package aurestApp.Tools;

import org.apache.commons.io.EndianUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.poifs.filesystem.*;

import java.io.*;
import java.util.Date;
import java.util.Iterator;

//**
// * Reads an Outlook MSG File in and provides hooks into its data structure. Some hints to the structure were found under
// *
// * <ul>
// *   <li>http://www.fileformat.info/format/outlookmsg/</li>
// *   <li>http://svn.apache.org/viewvc/poi/trunk/src/scratchpad/src/org/apache/poi/hsmf/</li>
// *   <li>http://www.tech-archive.net/Archive/Development/microsoft.public.win32.programmer.ole/2006-08/msg00123.html
// *   </li>
// * </ul>
// *
// */
class OutlookMessage {
    private static final Log LOG = LogFactory.getLog(OutlookMessage.class);

    /**
     * id for the mapi property "subject"
     */
    private static final int PROP_SUBJECT = 0x0037;

    /**
     * id for the mapi property "display from"
     */
    private static final int PROP_DISPLAY_FROM = 0x0C1A;

    /**
     * id for the mapi property "display to"
     */
    private static final int PROP_DISPLAY_TO = 0x0E04;

    /**
     * id for the mapi property "display cc"
     */
    private static final int PROP_DISPLAY_CC = 0x0E03;

    /**
     * reversed endian format of the property id "client submit time"
     */
    private static final byte[] PROP_CLIENT_SUBMIT_TIME = {0x40, 0x00, 0x39, 0x00};

    /**
     * reversed endian format of the property id "message delivery time"
     */
    private static final byte[] PROP_MESSAGE_DELIVERY_TIME = {0x40, 0x00, 0x06, 0x0E};

    private String subject = null;

    private String displayFrom;

    private String displayTo;

    private String displayCc;

    private Date clientSubmitTime;

    private Date messageDeliveryTime;

    public OutlookMessage(String filename) throws IOException {
        this(new FileInputStream(new File(filename)));
    }

    private OutlookMessage(InputStream in) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(in);
            initMapiProperties(fs);
        } catch (IOException ioe) {
            LOG.warn("Some properties could be not parsed from given MSG message " + ioe);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    private void initMapiProperties(POIFSFileSystem fs) throws IOException {
        DirectoryEntry root = fs.getRoot();

        for (@SuppressWarnings("rawtypes")
             Iterator iter = root.getEntries(); iter.hasNext(); ) {
            Entry entry = (Entry) iter.next();
            if (!(entry instanceof DocumentEntry)) {
                continue;
            }

            String entryName = entry.getName();
            if (entryName == null) {
                continue;
            }

            // parse MAPI properties
            if (entryName.startsWith("__substg1.0_")) {
                byte[] substgBytes = getBytes((DocumentEntry) entry);

                int id = Integer.parseInt(entryName.substring(12, 16), 16);
                int type = Integer.parseInt(entryName.substring(16, 20), 16);

                if (id == PROP_SUBJECT) {
                    // subject
                    this.subject = getString(substgBytes, isUnicodeString(type));
                } else if (id == PROP_DISPLAY_FROM) {
                    // display from
                    this.displayFrom = getString(substgBytes, isUnicodeString(type));
                } else if (id == PROP_DISPLAY_TO) {
                    // display to
                    this.displayTo = getString(substgBytes, isUnicodeString(type));
                } else if (id == PROP_DISPLAY_CC) {
                    // display cc
                    this.displayCc = getString(substgBytes, isUnicodeString(type));
                }
            } else if (entryName.startsWith("__properties_version1.0")) {
                byte[] propBytes = getBytes((DocumentEntry) entry);
                int offset = 0;
                int bytesLength = propBytes.length;

                while (offset + 16 <= bytesLength) {
                    byte[] propId = read4(propBytes, offset);

                    if (compare(propId, PROP_CLIENT_SUBMIT_TIME)) {
                        // read value
                        this.clientSubmitTime = getDate(propBytes, offset);
                    } else if (compare(propId, PROP_MESSAGE_DELIVERY_TIME)) {
                        // read value
                        this.messageDeliveryTime = getDate(propBytes, offset);
                    }

                    offset = offset + 16;
                }
            }
        }
    }

    private byte[] getBytes(DocumentEntry docEntry) throws IOException {
        DocumentInputStream dis = new DocumentInputStream(docEntry);
        byte[] propBytes = new byte[dis.available()];

        try {
            byte[] bytes = new byte[4096];
            int readCount;
            int curPosition = 0;
            while ((readCount = dis.read(bytes)) > -1) {
                System.arraycopy(bytes, 0, propBytes, curPosition, readCount);
                curPosition = curPosition + readCount;
            }
        } finally {
            dis.close();
        }

        return propBytes;
    }

    private String getString(byte[] bytes, boolean isUnicode) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        try {
            String str;
            if (isUnicode) {
                str = new String(bytes, 0, bytes.length, "UTF-16LE");
            } else {
                str = new String(bytes, 0, bytes.length, "ISO8859_1");
            }

            int len = str.length();
            while (len > 0 && str.charAt(len - 1) == '\0') {
                len--;
            }

            if (len != str.length()) {
                str = str.substring(0, len);
            }

            if (StringUtils.isBlank(str)) {
                str = null;
            }

            return str;
        } catch (UnsupportedEncodingException ignore) {
        }

        return null;
    }

    private boolean isUnicodeString(int type) {
        return (type == 0x001F); // for not unicode string type = 0x001E
    }

    private byte[] read4(byte[] data, int offset) {
        byte[] readBytes = new byte[4];
        System.arraycopy(data, offset, readBytes, 0, 4);

        return readBytes;
    }

    private byte[] read8(byte[] data, int offset) {
        byte[] readBytes = new byte[8];
        System.arraycopy(data, offset, readBytes, 0, 8);

        return readBytes;
    }

    private boolean compare(byte[] b1, byte[] b2) {
        for (int i = 0; i < b1.length; ++i) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }

        return true;
    }

    private Date getDate(byte[] propBytes, int offset) {
        // read value
        byte[] value = read8(propBytes, offset + 8);

        // convert to long (reverse Endian format)
        long time = EndianUtils.readSwappedLong(value, 0);

        // FILETIME 64-bit int number of 100ns periods since Jan 1, 1601 ==>
        // convert ns to ms and substruct milliseconds between 1/1/1601 and 1/1/1970
        time = (time / 10 / 1000) - 1000L * 60L * 60L * 24L * (365L * 369L + 89L);

        return new Date(time);
    }

    // getter

    public String getSubject() {
        return subject;
    }

    public String getDisplayFrom() {
        return displayFrom;
    }

    public String getDisplayTo() {
        return displayTo;
    }

    public String getDisplayCc() {
        return displayCc;
    }

    public Date getClientSubmitTime() {
        return clientSubmitTime;
    }

    public Date getMessageDeliveryTime() {
        return messageDeliveryTime;
    }
}