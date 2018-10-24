package lesson1;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

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
        class Address implements Comparable<Address>{
            private String nameStreet;
            private Integer numberStreet;
            private String namePerson;

            private Address(String note) {
                nameStreet = note.split(" - ")[1].split(" \\d+")[0];
                numberStreet = Integer.parseInt(note.split(" ")[note.split(" ").length-1]);
                namePerson = note.split(" - ")[0];
            }

            @Override
            public int compareTo(@NotNull Address o) {
                if (!this.nameStreet.equals(o.nameStreet))
                    return this.nameStreet.compareTo(o.nameStreet);
                else if (!this.numberStreet.equals(o.numberStreet))
                    return this.numberStreet.compareTo(o.numberStreet);
                else return this.namePerson.compareTo(o.namePerson);
            }

            private boolean equals(Address o) {
                return this.namePerson.equals(o.namePerson) && this.nameStreet.equals(o.nameStreet) &&
                        this.numberStreet.equals(o.numberStreet);
            }
        }
        String regex = "(?:[A-ZА-ЯЁa-zа-яё]+\\s){2}-\\s(?:[A-ZА-ЯЁa-zа-яё-]+\\s)+\\d+(\\n|$)";
        Scanner scanner = new Scanner(new FileReader(inputName));
        TreeSet<Address> streets = new TreeSet<>();              //Ресурсоемкость R = O(N)
        while (scanner.hasNextLine()) {                          //O(N*logN)
            String oneNote = scanner.nextLine();
            if (!oneNote.matches(regex)) throw new NotImplementedError();
            Address addressNote = new Address(oneNote);
            streets.add(addressNote);                            //Вставка O(logN)
        }
        scanner.close();
        String sameStreet = " ";
        FileWriter wr = new FileWriter(new File(outputName));
        wr.write(streets.first().nameStreet+" "+streets.first().numberStreet+" - "+streets.first().namePerson);
        for (Address ad: streets) {
            if (ad.equals(streets.first())) {
                sameStreet = ad.nameStreet + ad.numberStreet;
                continue;
            }
            if (sameStreet.equals(ad.nameStreet + ad.numberStreet)) wr.write(", " + ad.namePerson);
            else wr.write("\n" + ad.nameStreet + " " + ad.numberStreet + " - " + ad.namePerson);
            sameStreet = ad.nameStreet + ad.numberStreet;
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
    //Трудоемкость T=O(N), ресурсоемкость R=O(Т)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        Scanner scanner = new Scanner(new FileReader(inputName));
        List<Integer> list = new ArrayList<>();               //R=O(N)
        while (scanner.hasNextLine()) {
            list.add((int) (Double.parseDouble(scanner.nextLine())*10));
        }
        scanner.close();
        int[] range = new int[2730+5000+1];
        for (Integer num: list) {                             //T=O(N)
            range[num + 2730]++;
        }
        FileWriter fileWriter = new FileWriter(new File(outputName));
        for (int i=0; i<range.length; i++) {
            while (range[i] > 0) {
                fileWriter.write(((double) (i-2730)) / 10 + "\n");
                range[i]--;
            }
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
