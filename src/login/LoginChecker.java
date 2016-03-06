package login;

/**
 * This class make the compares the paswword entered by the user and the real account.
 * 
 * @author Pierre Casati
 * @version 1.0
 * @since 2016-03-03
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.excpetion.ErrorConnectionException;
import common.excpetion.IncorrectLoginException;
import common.excpetion.UnknownLoginException;

public class LoginChecker {
	private Account account = null;	
	

	/**
	 * This function compare the real password, and the password entered by the user (in parameter).
	 * The password is transformed in sha2.
	 * 
	 * @author Pierre Casati
	 * @version 1.0
	 * @since 2016-03-03
	 * @param String password : the password to be compared.
	 * @return a boolean, true is the password are equals.
	 */
	public boolean isValidPassword(String password) {
		if (this.account == null) {
			throw new NullPointerException() ;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			String hashPassword = "";
			for(byte b : hash) {
				hashPassword += String.format("%02x", b);
			}
			return hashPassword.equals(this.account.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false ;
		
	}

	/**
	 * This function create an account.
	 * Throw an error if the login contains invalid caracters.
	 * 
	 * @author Pierre Casati
	 * @version 1.0
	 * @since 2016-03-03
	 */
	public void createAccount(String login) throws UnknownLoginException, ErrorConnectionException, IncorrectLoginException {
		Pattern pattern = Pattern.compile("[[^ \\w] && [^ \\p{javaLowerCase}] && [^ \\p{javaUpperCase}]]");
		Matcher matcher = pattern.matcher(login);
		
		if(!matcher.find()) {
			AccountFactory factory = new JDBCAccountFactory();
			this.account = factory.build(login);
		} else {
			throw new IncorrectLoginException();
		}
	}
	
	public Account getAccount(){
		return this.account;
	}

}
