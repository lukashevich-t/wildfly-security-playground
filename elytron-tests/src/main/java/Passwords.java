import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.ClearPassword;
import org.wildfly.security.password.interfaces.SimpleDigestPassword;
import org.wildfly.security.password.spec.ClearPasswordSpec;
import org.wildfly.security.password.spec.HashPasswordSpec;

public class Passwords {
    static final Provider ELYTRON_PROVIDER = new WildFlyElytronProvider();
    static final String TEST_PASSWORD = "test_password";

    public static void main(String[] args) throws Exception {
        simpleDigestPassword();
    }

    private static void simpleDigestPassword() throws Exception {
        PasswordFactory passwordFactory = PasswordFactory.getInstance(SimpleDigestPassword.ALGORITHM_SIMPLE_DIGEST_SHA_512, ELYTRON_PROVIDER);

        ClearPasswordSpec clearSpec = new ClearPasswordSpec(TEST_PASSWORD.toCharArray());
        SimpleDigestPassword original = (SimpleDigestPassword) passwordFactory.generatePassword(clearSpec);

        byte[] digest = original.getDigest();
        HashPasswordSpec hashSpec = new HashPasswordSpec(digest);

        SimpleDigestPassword restored = (SimpleDigestPassword) passwordFactory.generatePassword(hashSpec);

        System.out.printf("Password Verified '%b'%n", passwordFactory.verify(restored, TEST_PASSWORD.toCharArray()));
    }

    private static void clearPassword() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        PasswordFactory passwordFactory = PasswordFactory.getInstance(ClearPassword.ALGORITHM_CLEAR, ELYTRON_PROVIDER);

        ClearPasswordSpec passwordSpec = new ClearPasswordSpec(TEST_PASSWORD.toCharArray());
        Password password = passwordFactory.generatePassword(passwordSpec);

        System.out.printf("Password Verified: %b%n", passwordFactory.verify(password, TEST_PASSWORD.toCharArray()));
    }

    private static void clearPassword2() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        PasswordFactory passwordFactory = PasswordFactory.getInstance(ClearPassword.ALGORITHM_CLEAR, ELYTRON_PROVIDER);

        Password rawPassword = ClearPassword.createRaw(ClearPassword.ALGORITHM_CLEAR, TEST_PASSWORD.toCharArray());
        final Password password = passwordFactory.translate(rawPassword);

        System.out.printf("Password Verified: %b%n", passwordFactory.verify(password, TEST_PASSWORD.toCharArray()));
    }
}
