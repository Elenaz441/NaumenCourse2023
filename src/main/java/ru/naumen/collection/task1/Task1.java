package ru.naumen.collection.task1;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно написать утилитный метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task1 {

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        Set<User> collBSet = new HashSet<>(collB); // для быстрого использования функции contains
        List<User> duplicates = new ArrayList<>();
        for (User user:collA) {
            if (collBSet.contains(user))
                duplicates.add(user);
        }
        return duplicates.stream().toList();
        /* Сложность O(n) = n + m
            n - Размер коллекции collA
            m - Размер коллекции collB
            Примерно n операций будут из-за цикла; m операций уйдут на преобразование коллекции в HashSet.
            для корректной работы contains в классе User были переопределены методы equals и hashCode
            HashSet - для быстрого использования функции contains и добавления
        */
    }
}
