import java.io.*;
import java.util.Scanner;

public class Writer {
	public static void addEvent(int day, int month, int year, File file) {
		Scanner kb = new Scanner(System.in);

		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			Boolean eventExistsAlready = false;

			String event = scheduleEvent();
			while (line != null && !(eventExistsAlready)) {
				line = reader.readLine();
				if (line == null) {
					writer.newLine();
					writer.newLine();
					writer.newLine();
					writer.write(("%02d.%02d.%d").formatted(day, month, year));
					writer.newLine();
					writer.write(event);
					writer.newLine();
					writer.newLine();
					writer.flush();
				} else if (line.equals(
						(("%02d.%02d.%d").formatted(day, month, year)))) {
					line = reader.readLine();
					if (line.equals(event)) {
						System.out.println("Событие уже есть!");
						eventExistsAlready = true;
					}
				}
			}
			reader.close();
			writer.close();

			System.out.println();
			Reader.printDay(day, month, year, file);
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void clear(int day, int month, int year, File file) {
		boolean fileEnd = false;
		try {

			while (!fileEnd) {
				long position = 0;
				long linelength = 0;
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				boolean running = true;

				RandomAccessFile accessor = new RandomAccessFile(file, "rw");

				while (running == true) {

					String line = reader.readLine();

					if (line == null) {
						running = false;
						fileEnd = true;
					} else if (line.equals((("%02d.%02d.%d").formatted(day, month, year)))) {
						linelength = line.getBytes().length + 1;
						line = reader.readLine();
						linelength += line.getBytes().length + 1;

						long end = position + linelength;
						while (position < end - 2) {
							accessor.seek(position);
							accessor.write(' ');
							position++;
						}
						accessor.seek(position);
						accessor.writeBytes(System.getProperty("line.separator"));
						running = false;
					} else {
						linelength = line.getBytes().length + 1;
						line = reader.readLine();
						if (line == null) {
							running = false;
							fileEnd = true;
						} else {
							linelength += line.getBytes().length + 1;
						}
					}
					position += (linelength);
				}
				reader.close();
				accessor.close();
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void deleteEvent(int day, int month, int year, String event, File file) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			RandomAccessFile accessor = new RandomAccessFile(file, "rw");

			boolean running = true;

			long position = 0;
			long linelength = 0;

			while (running == true) {

				String line = reader.readLine();

				if (line == null) {
					running = false;

				} else if (line.equals(
						(("%02d.%02d.%d").formatted(day, month, year)))) {
					linelength = line.getBytes().length + 1;
					line = reader.readLine();
					linelength += line.getBytes().length + 1;

					if (line.equals(event)) {
						long end = position + linelength;
						while (position < end - 2) {
							accessor.seek(position);
							accessor.write(' ');
							position++;
						}
						accessor.seek(position);
						accessor.writeBytes(System.getProperty("line.separator"));
						running = false;
					}
				} else {
					linelength = line.getBytes().length + 1;
					line = reader.readLine();
					linelength += line.getBytes().length + 1;
				}
				position += (linelength);
			}

			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static String scheduleEvent() {
		Scanner kb = new Scanner(System.in);

		System.out.println();
		System.out.println("Введите время события в формате HH:MM, например 13:00");
		String eventTime[] = kb.nextLine().split(":", 2);

		System.out.println();
		System.out.println("Введите описание события");
		String eventDescription = kb.nextLine();

		String event = eventTime[0] + ":" + eventTime[1] + " " + eventDescription;

		return event;
	}
}