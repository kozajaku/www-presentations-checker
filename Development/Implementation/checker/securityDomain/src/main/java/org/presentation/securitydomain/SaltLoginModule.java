package org.presentation.securitydomain;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;
import org.jboss.security.PicketBoxMessages;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

/**
 *
 * @author radio.koza
 */
public class SaltLoginModule extends DatabaseServerLoginModule {

    private static final String SALT_QUERY = "saltQuery";

    private static final String[] ALL_VALID_OPTIONS
            = {
                SALT_QUERY
            };

    protected String saltQuery = "select Salt from Principals where PrincipalID=?";

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        addValidOptions(ALL_VALID_OPTIONS);
        super.initialize(subject, callbackHandler, sharedState, options);
        Object tmp = options.get(SALT_QUERY);
        if (tmp != null) {
            saltQuery = tmp.toString();
        }
    }

    protected String getUsersSalt(String username) throws LoginException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String salt = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dsJndiName);
            conn = ds.getConnection();
//            PicketBoxLogger.LOGGER.traceExecuteQuery(saltQuery, username);
            ps = conn.prepareStatement(saltQuery);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next() == false) {
                throw PicketBoxMessages.MESSAGES.noMatchingUsernameFoundInPrincipals();
            }
            salt = rs.getString(1);
        } catch (NamingException ex) {
            LoginException le = new LoginException(PicketBoxMessages.MESSAGES.failedToLookupDataSourceMessage(dsJndiName));
            le.initCause(ex);
            throw le;
        } catch (SQLException ex) {
            LoginException le = new LoginException(PicketBoxMessages.MESSAGES.failedToProcessQueryMessage());
            le.initCause(ex);
            throw le;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return salt;
    }

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Method used for converting array of bytes to HEX representation in String
     *
     * @param bytes Array of bytes to be converted
     * @return Representation of bytes in hex
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected String createPasswordHash(String username, String password, String digestOption) throws LoginException {
        try {
            String salt = getUsersSalt(username);
            byte[] saltBytes = new byte[salt.length() / 2];
            for (int i = 0; i < salt.length(); i += 2) {
                saltBytes[i / 2] = (byte) ((Character.digit(salt.charAt(i), 16) << 4)
                        + Character.digit(salt.charAt(i + 1), 16));
            }
            byte[] passBytes = password.getBytes(Charset.forName("UTF-8"));
            //setup hashing algorithm
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(saltBytes);
            sha.update(passBytes);
            return bytesToHex(sha.digest());
        } catch (NoSuchAlgorithmException ex) {
            LoginException le = PicketBoxMessages.MESSAGES.failedToInvokeCallbackHandler();
            le.initCause(ex);
            throw le;
        }
    }

}
