package ru.naumen.collection.task1;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<User> collAList = collA.stream().toList(); // для быстрого итерирования
        Set<User> collBSet = new HashSet<>(collB); // для быстрого использования функции contains
        Set<User> duplicates = new HashSet<>(); // для быстрого добавления
        for (User user:collAList) {
            if (collBSet.contains(user))
                duplicates.add(user);
        }
        return duplicates.stream().toList();
        /* Сложность O(n) = 2n
            n - Размер коллекции collA
            константа = 2, потому что в цикле используются две элементарные операции (contains и add)
            обе операции имеют сложность O(1)
            для корректной работы contains в классе User были переопределены методы equals и hashCode
            List - для быстрого итерирования
            HashSet - для быстрого использования функции contains и добавления
        */
    }
}
