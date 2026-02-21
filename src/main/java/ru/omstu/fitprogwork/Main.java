package ru.omstu.fitprogwork;

import ru.omstu.fitprogwork.config.DataProcessorFactory;
import ru.omstu.fitprogwork.processor.DataProcessor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Программа для чтения данных из JSON и XML файлов");
        System.out.println("Файлы должны находиться в папке resources");
        System.out.println("Примеры путей: /name, /relation/1/name, /skills/0\n");

        try {
            while (true) {
                System.out.println("\nВыберите действие:");
                System.out.println("1. Прочитать из JSON файла");
                System.out.println("2. Прочитать из XML файла");
                System.out.println("3. Выход");
                System.out.print("Ваш выбор: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        processFile("data.json", scanner);
                        break;
                    case "2":
                        processFile("data.xml", scanner);
                        break;
                    case "3":
                        System.out.println("Программа завершена.");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } finally {
            scanner.close();  // Добавить закрытие scanner
        }
    }

    private static void processFile(String fileName, Scanner scanner) {
        try {
            DataProcessor processor = DataProcessorFactory.getProcessorByFileName(fileName);

            System.out.println("\nФайл: " + fileName);
            System.out.println("Введите путь к полю (например: /name или /relation/1/name)");
            System.out.println("Для выхода введите 'exit'");

            while (true) {
                System.out.print("\nПуть: ");
                String path = scanner.nextLine();

                if (path.equalsIgnoreCase("exit")) {
                    break;
                }

                String result = processor.getValueByPath(fileName, path);
                System.out.println("Результат: " + result);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}