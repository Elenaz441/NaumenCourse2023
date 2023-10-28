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

    public static void printResult(Queue<Integer> queue, Map<String, Integer> wordsDict) {
        int counter = 0;
        for (int i = 1; i < 11; i++) {
            int value = queue.poll();
            for (Map.Entry<String, Integer> entry : wordsDict.entrySet()) {
                if (entry.getValue().equals(value)) {
                    counter++;
                    System.out.printf("%d) word: %s\t count: %d\n", counter, entry.getKey(), value);
                }
                if (counter == 10)
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> wordsDict = new LinkedHashMap<>();
        Queue<Integer> queueTop = new PriorityQueue<>(Comparator.reverseOrder());
        Queue<Integer> queueLast = new PriorityQueue<>();
        WordParser parser = new WordParser(WAR_AND_PEACE_FILE_PATH);
        parser.forEachWord(word -> {
            wordsDict.merge(word, 1, Integer::sum);
        });
        for (Map.Entry<String, Integer> entry : wordsDict.entrySet()) {
            queueTop.add(entry.getValue());
            queueLast.add(entry.getValue());
        }
        System.out.println("ТОП 10 слов произведения:");
        printResult(queueTop, wordsDict);
        System.out.println("\nНаименее используемые слова:");
        printResult(queueLast, wordsDict);
        /* Сложность O(n) = 22n
        * n - количество уникальных слов в файле
        *
        * 22n - для заполнения queue требуется 2n операций O(n);
        *       10n на функцию printResult (а так как вызываем два раза, то получаем 20n)
        *
        * LinkedHashMap - для быстрой итерации
        * PriorityQueue - для быстрого вычисления наименьших/наибольших значений
        * */
    }
}
