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

    public static void printResult(int startIndex, int endIndex, List<Integer> valuesSortedList, Map<String, Integer> wordsDict) {
        int counter = 0;
        for (int i = startIndex; i < endIndex; i++) {
            for (Map.Entry<String, Integer> entry : wordsDict.entrySet()) {
                if (entry.getValue().equals(valuesSortedList.get(i))) {
                    counter++;
                    System.out.printf("%d. word: %s, count: %d\n", counter, entry.getKey(), valuesSortedList.get(i));
                }
                if (counter == 10)
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> wordsDict = new HashMap<>(); // Удобнее всего здесь использовать для подсчёта слов
        List<Integer> valuesList = new ArrayList<>(); // Понадобится для быстрого итерирования
        WordParser parser = new WordParser(WAR_AND_PEACE_FILE_PATH);
        parser.forEachWord(word -> {
            if (!wordsDict.containsKey(word))
                wordsDict.put(word, 0);
            wordsDict.put(word, wordsDict.get(word) + 1);
        });
        for (Map.Entry<String, Integer> entry : wordsDict.entrySet())
            valuesList.add(entry.getValue());
        Collections.sort(valuesList);
        Collections.reverse(valuesList);
        System.out.println("ТОП 10 слов произведения:");
        printResult(0, 10, valuesList, wordsDict);
        System.out.println("\nНаименее используемые слова:");
        printResult(valuesList.size() - 10, valuesList.size(), valuesList, wordsDict);
        /* Сложность O(n) = 3n + mlog(m) + 22m
        * n - количество слов в файле
        * m - количество уникальных слов в файле (m <= n)
        *
        * 3n - это при заполнении словаря wordDict (содержит три операции: containsKey и два put; у каждой сложность O(1))
        * mlog(m) - операция сортировки
        * 22m - для преобразования values в список требуется m операций O(m);
        *       для переворачивания списка требуется m операций O(m);
        *       10m на функцию printResult (а так как вызываем два раза, то получаем 20m)
        *
        * Для упрощения функции можно опустить два последних слогамых, однако тогда нужно увеличить константу перед n.
        * В итоге поучаем: O(n) = 4n
        *
        * HashMap - удобнее всего здесь использовать для подсчёта слов
        * ArrayList - для быстрого итерирования
        * */
    }
}
