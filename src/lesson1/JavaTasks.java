package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */

    //T = O(N*logN)-трудоемкость    R=O(N)-ресурсоемкость
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        Comparator<String> compareStreet = (street1, street2) -> {
            String streetName1 = street1.split(" - ")[1].split(" \\d+")[0];
            String streetName2 = street2.split(" - ")[1].split(" \\d+")[0];
            Integer num1 = Integer.parseInt(street1.split(" ")[street1.split(" ").length-1]);
            Integer num2 = Integer.parseInt(street2.split(" ")[street2.split(" ").length-1]);
            String name1 = street1.split(" - ")[0];
            String name2 = street2.split(" - ")[0];
            if (streetName1.compareTo(streetName2) != 0) return streetName1.compareTo(streetName2);
            else if (!num1.equals(num2)) return num1.compareTo(num2);
            else return name1.compareTo(name2);
        };
        String regex = "(?:[A-ZА-ЯЁa-zа-яё]+\\s){2}-\\s(?:[A-ZА-ЯЁa-zа-яё-]+\\s)+\\d+(\\n|$)";
        Scanner scanner = new Scanner(new FileReader(inputName));
        TreeSet<String> relation = new TreeSet<>(compareStreet); //Ресурсоемкость R = O(N)
        while (scanner.hasNextLine()) {                          //O(N*logN)
            String oneNote = scanner.nextLine();
            if (!oneNote.matches(regex)) throw new NotImplementedError();
            relation.add(oneNote);                               //Вставка O(logN)
        }
        scanner.close();
        String str = " ";
        FileWriter wr = new FileWriter(new File(outputName));
        wr.write(relation.first().split(" - ")[1] + " - " + relation.first().split(" - ")[0]);
        for (String e: relation) {                               //O(N)
            if (Objects.equals(e, relation.first())) {
                str = e.split(" - ")[1];
                continue;
            }
            if (!Objects.equals(e.split(" - ")[1], str)) {
                wr.write("\n" + e.split(" - ")[1] + " - " + e.split(" - ")[0]);
            } else wr.write(", " + e.split(" - ")[0]);
            str = e.split(" - ")[1];
        }
        wr.close();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //Трудоемкость T=O(N*logN), ресурсоемкость R=O(Т)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        Scanner scanner = new Scanner(new FileReader(inputName));
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add((int) (Double.parseDouble(scanner.nextLine())*10));
        }
        scanner.close();
        int[] elements = new int[list.size()];
        int k=0;
        for (Integer integer: list) {
            elements[k++] = integer;
        }
        Sorts.mergeSort(elements);                                       //T = O(N*logN)
        FileWriter fileWriter = new FileWriter(new File(outputName));
        for (int el: elements) {
            fileWriter.write(((double) el)/10 + "\n");
        }
        fileWriter.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
