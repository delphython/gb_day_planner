import java.util.Scanner;
import java.io.*;

public class Planner {
	public static void main(String args[]) {

		Scanner kb = new Scanner(System.in);
		File file = new File("calendar.txt");
		boolean running = true;
		String event = new String();

		int month;

		System.out.println("Консольное приложение 'Календарь'");
		System.out.println();

		if (!file.exists()) {
			System.out.println(
					"Приложение не может найти файл calendar.txt. Он необходим для сохранения данных. Хотите его создать? y/n?");

			char create = kb.next().charAt(0);

			if (create == 'y') {
				try {

					file.createNewFile();

					RandomAccessFile accessor = new RandomAccessFile(file, "rw");
					accessor.seek(0);
					accessor.writeBytes(System.getProperty("line.separator"));

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Всего хорошего!");
				System.exit(1);
			}

		}

		Reader.printToday(file);

		while (running) {
			System.out.println("Введите команду. Наберите help для справки.");
			String command[] = kb.nextLine().split(" ", -2);

			switch (command[0]) {
				case "today":
					Reader.printToday(file);
					break;
				case "help":
					help();
					break;
				case "exit":
					running = false;
					break;
				case "print":
					month = monthFormatter(command[2]);
					Reader.printDay(Integer.parseInt(command[1]), month, Integer.parseInt(command[3]), file);
					break;
				case "add":
					month = monthFormatter(command[2]);
					Writer.addEvent(Integer.parseInt(command[1]), month, Integer.parseInt(command[3]), file);
					break;
				case "remove":
					month = monthFormatter(command[2]);
					System.out.println("Выберите событие, которое хотите удалить в формате 'HH:MM текст события':");
					event = kb.nextLine();
					Writer.deleteEvent(Integer.parseInt(command[1]), month, Integer.parseInt(command[3]), event, file);
					break;
				case "clear":
					month = monthFormatter(command[2]);
					Writer.clear( Integer.parseInt(command[1]), month, Integer.parseInt(command[3]), file);
				default:
					break;
			}
		}

	}

	public static void help() {
		System.out.println();
		System.out.println("Для выхода, введите 'exit'.");
		System.out.println("Для вывода календаря на сегодня, введите 'today'.");
		System.out.println(
				"Для вывода календаря на конкретный день, введите команду 'print' и дату в формате DD MM YYYY, например: 'print 02 03 2023'.");
		System.out.println(
				"Для добавления события в календарь на конкретный день, введите команду 'add' и дату в формате DD MM YYYY, например: 'add 02 03 2023'.");
		System.out.println(
				"Для удаления события из календаря, введите 'remove' и дату в формате DD MM YYYY, например: 'remove 02 03 2023'. Далее следуйте инструкции на экране.");
		System.out.println(
				"Для удаления событий на определенную дату, введите 'clear' и дату в формате DD MM YYYY, например: 'clear 02 03 2023'.");
		System.out.println();
	}

	public static int monthFormatter(String input) {
		if (input.charAt(0) == '0') {
			return Character.getNumericValue(input.charAt(1));
		} else {
			return Integer.parseInt(input);
		}
	}
}