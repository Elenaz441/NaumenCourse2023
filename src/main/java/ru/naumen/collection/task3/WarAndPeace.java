package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace {

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void printResult(Queue<Map.Entry<String, Integer>> queue) {
        for (int i = 1; i <= 10; i++) {
            Map.Entry<String, Integer> value = queue.poll();
            System.out.printf("%d) word: %s\t count: %d\n", i, value.getKey(), value.getValue());
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> wordsDict = new LinkedHashMap<>();
        Queue<Map.Entry<String, Integer>> queueTop = new PriorityQueue<>(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Queue<Map.Entry<String, Integer>> queueLast = new PriorityQueue<>(Map.Entry.comparingByValue());
        WordParser parser = new WordParser(WAR_AND_PEACE_FILE_PATH);
        parser.forEachWord(word -> wordsDict.merge(word, 1, Integer::sum));
        for (Map.Entry<String, Integer> entry : wordsDict.entrySet()) {
            queueTop.add(entry);
            queueLast.add(entry);
        }
        System.out.println("ТОП 10 слов произведения:");
        printResult(queueTop);
        System.out.println("\nНаименее используемые слова:");
        printResult(queueLast);
        /* Сложность O(n) = 2n + 20
        * n - количество уникальных слов в файле
        *
        * 2n - для заполнения queue требуется 2n операций O(n);
        * 10 - на функцию printResult (а так как вызываем два раза, то получаем 20)
        *
        * LinkedHashMap - для быстрой итерации
        * PriorityQueue - для быстрого вычисления наименьших/наибольших значений
        * */
    }
}
