import java.util.Calendar;
import java.io.*;

public class Reader {

	public static void printDay(int day, int month, int year, File file) {
		System.out.println(("-----%02d.%02d.%d-----").formatted(day, month, year));

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();

			while (line != null) {
				if (line.equals(("%02d.%02d.%d").formatted(day, month, year))) {
					line = reader.readLine();
					while (line != null && !(line.equals(""))) {
						System.out.println(line);
						line = reader.readLine();
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

		System.out.println("--------------------");
		System.out.println();
	}

	public static void printToday(File file) {
		Calendar calendar = Calendar.getInstance();
		int month = (calendar.get(Calendar.MONTH) + 1);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		printDay(day, month, year, file);
	}
}
