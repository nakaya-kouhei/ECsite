package kagoyume;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author nakaya-k
 */
class URLDecoderMultibyte extends URLDecoder {

    public static String decode(String s, String enc) throws UnsupportedEncodingException {

        boolean needToChange = false;
        int numChars = s.length();
// FIXED HERE : StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        ByteArrayOutputStream sb = new ByteArrayOutputStream();
        int i = 0;

        if (enc.length() == 0) {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
                case '+':
// FIXED HERE : sb.append(' ');
                    sb.write(' ');
                    i++;
                    needToChange = true;
                    break;
                case '%':
                    /*
 * Starting with this instance of %, process all
 * consecutive substrings of the form %xy. Each
 * substring %xy will yield a byte. Convert all
 * consecutive bytes obtained this way to whatever
 * character(s) they represent in the provided
 * encoding.
                     */

                    try {

// (numChars-i)/3 is an upper bound for the number
// of remaining bytes
                        if (bytes == null) {
                            bytes = new byte[(numChars - i) / 3];
                        }
                        int pos = 0;

                        while (((i + 2) < numChars)
                                && (c == '%')) {
                            bytes[pos++]
                                    = (byte) Integer.parseInt(s.substring(i + 1, i + 3), 16);
                            i += 3;
                            if (i < numChars) {
                                c = s.charAt(i);
                            }
                        }

// A trailing, incomplete byte encoding such as
// "%x" will cause an exception to be thrown
                        if ((i < numChars) && (c == '%')) {
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");
                        }

                        sb.write(bytes, 0, pos);
// FIXED HERE : sb.append(new String(bytes, 0, pos, enc));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
// FIXED HERE : sb.append(c);
                    sb.write(c);
                    i++;
                    break;
            }
        }

// return (needToChange? sb.toString() : s);
        return (needToChange ? new String(sb.toByteArray(), enc) : s);
    }
}
