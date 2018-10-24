package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
//    Трудоемкость T=O(N*M), ресурсоемкость R=O(N*M). M и N - длины первой и второй стоки
    static public String longestCommonSubstring(String firs, String second) {
        int[][] matrix = new int[second.length()][firs.length()];   //R=O(N*M)
        char[] f = firs.toCharArray();
        char[] s = second.toCharArray();
        int max = 0;
        int maxCol = -1;
        for (int col= 0; col < f.length; col++) {                   //T=O(N*M)
            for (int row = 0; row < s.length; row++) {
                if (f[col]==s[row] && matrix[row][col]==0) {
                    int count = 1;
                    int col2 = col;
                    int row2 = row;
                    matrix[row2][col2] = count;
                    while (true) {
                        col2++;
                        row2++;
                        if (col2 < f.length && row2 < s.length && f[col2]==s[row2]) {
                            matrix[row2][col2] = ++count;
                        } else{
                            if (matrix[row2-1][col2-1] > max) {
                                max = matrix[row2-1][col2-1];
                                maxCol = col2 - 1;
                            }
                            break;
                        }
                    }
                }
            }
        }
        StringBuilder str = new StringBuilder();
        while (max >= 1) {
            str.append(f[maxCol--]);
            max--;
        }
        return str.reverse().toString();
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        Scanner scanner1 = new Scanner(new FileReader(inputName));
        int column = 0;
        int raw = 0;
        while (scanner1.hasNextLine()) {
            column = scanner1.nextLine().length()/2 +1;
            raw++;
        }
        Scanner scanner2 = new Scanner(new FileReader(inputName));
        Character[][] mat = new Character[raw][column];
        Character symbol;
        int count = 0;
        while (scanner2.hasNext()) {
            symbol = scanner2.next().charAt(0);
            if (!symbol.equals(' ') && !symbol.equals('\n')) {
                mat[count / column][count % column] = symbol;
                count++;
            }
        }
        Graph graph = new Graph();
        for (int i = 0; i < raw; i++) {
            for (int k = 0; k < column; k++){
                graph.addLatter(mat[i][k], k, i);
            }
        }
        for (int i = 0; i < raw; i++) {
            for (int k = 0; k < column; k++) {
                for (int m = 1; m < 3; m++) {
                    int checkX = k + (int) Math.pow(-1, m);
                    if (checkX < column && checkX >= 0) {
                        graph.connect(k, i, checkX, i);
                    }
                    int checkY = i + (int) Math.pow(-1, m);
                    if (checkY < raw && checkY >= 0) {
                        graph.connect(k, i, k, checkY);
                    }
                }
            }
        }
        Set<String> result = new HashSet<>();
        for (String word: words) {
            List<Character> list = new ArrayList<>();
            for (Character character: word.toCharArray()) {
                list.add(character);
            }
            for (int i = 0; i < raw; i++) {
                for (int k = 0; k < column; k++) {
                    if (graph.breadthFirstSearch(mat[i][k], k, i, list, new HashSet<>())) {
                        result.add(word);
                    }
                }
            }
        }
        return result;
    }

    private static class Graph {
        class Latter {
            private Character latter;
            private Integer x;
            private Integer y;
            Latter(Character name, int xCor, int yCor) {
                latter = name;
                x = xCor;
                y = yCor;
            }

            @Override
            public boolean equals(Object o) {
                return o instanceof Latter && x.equals(((Latter) o).x) && y.equals(((Latter) o).y)
                        && latter.equals(((Latter) o).latter);
            }
            @Override
            public String toString() {
                return " " + latter + "[" + x + ";" + y + "] ";
            }
            @Override
            public int hashCode(){
                int result = 17;
                result = 37 * result + x;
                result = 37 * result + y;
                result = 37 * result + (int) latter;
                return result;
            }
        }
        private class Point {
            Integer x;
            Integer y;
            Point(int xCor, int yCor) {
                x = xCor;
                y = yCor;
            }
            @Override
            public boolean equals(Object o) {
                return o instanceof Point && x.equals(((Point) o).x) && y.equals(((Point) o).y);
            }
            @Override
            public String toString() {
                return " Point[" + x + ";" + y + "] ";
            }
            @Override
            public int hashCode(){
                int result = 17;
                result = 37 * result + x;
                result = 37 * result + y;
                return result;
            }
        }

        private Map<Point, Latter> vertices = new HashMap<>();
        private Map<Latter, HashSet<Latter>> neighbors = new HashMap<>();

        void addLatter(Character name, int x, int y) {
            Point point = new Point(x, y);
            Latter latter = new Latter(name, x, y);
            vertices.put(point, latter);
        }

        void connect(int firX, int firY, int secX, int secY) {
            Latter first = vertices.get(new Point(firX, firY));
            Latter second = vertices.get(new Point(secX, secY));
            if (!neighbors.containsKey(first)) {
                neighbors.put(first, new HashSet<>());
            }
            if (!neighbors.containsKey(second)) {
                neighbors.put(second, new HashSet<>());
            }
            neighbors.get(first).add(second);
            neighbors.get(second).add(first);
        }

        Set<Latter> getNeighbors(int x, int y) {
            Latter latter = vertices.get(new Point(x, y));
            return neighbors.get(latter);
        }

        boolean breadthFirstSearch(Character start, int startX, int startY,
                                   List<Character> word, Set<Latter> visited) {   //МОжно сделать DEQUE вместо LIST
            visited.add(new Latter(start, startX, startY));
            if (word.size() == 1 && start.equals(word.get(0))) return true;
            if (!start.equals(word.get(0))) return false;
            for (Latter lat: getNeighbors(startX,startY)) {
                if (!word.get(1).equals(lat.latter) || visited.contains(lat)) continue;
                boolean flag = breadthFirstSearch(lat.latter, lat.x, lat.y,
                        word.subList(1, word.size()), visited);
                if (flag) {
                    return true;
                }
            }
            return false;
        }
    }
}
