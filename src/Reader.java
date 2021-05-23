import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public interface Reader {
	//this interface has only one method which is read a txt file
	//and it returns it as a 2d String array.
	default String[][] txtReader(String fileString) {
		try {
			Scanner myReader = new Scanner(new File(fileString));
			ArrayList<String[]> lines = new ArrayList<>();

			while (myReader.hasNextLine()) {
				String[] splitted = myReader.nextLine().split(" ");
				lines.add(splitted);
			}

			String[][] result = new String[lines.size()][];
			for (int i = 0; i < result.length; i++) {
				result[i] = lines.get(i);
			}
			return result;
		} catch (Exception e) {
			System.out.println("file is not occurred " + e.getMessage());
		}
		return null;

	}
}
