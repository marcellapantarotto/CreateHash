import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class CreateHash {

	private static String inputFileName = "";
	private static String inputHashCode = "";
	private static String hexHashCode = ""; // final hash code
	private static byte[] dataBytes = new byte[0];
	private static byte[] digest = new byte[0];

	public static void readingArguments(String[] args) {
		if (args.length == 1) { // Verifying that a file has been passed as an argument
			System.out.println("Try one of the options:");
			System.out.println(" --hash <filename>");
			System.out.println(" --verify <fileName> <hashCode>");
		} else if (args.length == 2) { // 2 argument = command + input file
			inputFileName = args[1];
			System.out.println("---------- INPUT ----------");
			System.out.println("File Name:\t" + inputFileName + "\n");
		} else if (args.length == 3) { // 2 arguments = command + input file + hash code
			inputFileName = args[1];
			inputHashCode = args[2];
			System.out.println("---------- INPUT ----------");
			System.out.println("File Name:\t" + inputFileName);
			System.out.println("Hash Code:\t" + inputHashCode + "\n");
		}
	}

	public static String hasher() throws NoSuchAlgorithmException, IOException {
		// Using algorithm SHA-256 to create hash
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

		// Reading bytes in file
		File file = new File(inputFileName);
		dataBytes = Files.readAllBytes(file.toPath());

		// Applying algorithm to the data
		digest = messageDigest.digest(dataBytes); // digest is the hash code
		hexHashCode = DatatypeConverter.printHexBinary(digest); // hash code in hexadecimal

		return hexHashCode;
	}

	public static boolean verify() throws NoSuchAlgorithmException, IOException {
		String hashCode = hasher();

		if (hashCode.contentEquals(inputHashCode)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		try {
			readingArguments(args);
			switch (args[0]) { // to see witch command to execute
			case "--hash":
				System.out.println("------ CREATING HASH ------");
				System.out.println("Hash Code:\t" + hasher());
				break;
			case "--verify":
				System.out.println("-------- VERIFYING --------");
				System.out.println("   Result:\t" + verify());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
