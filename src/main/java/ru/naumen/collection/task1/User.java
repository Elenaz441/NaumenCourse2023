package ru.naumen.collection.task1;

import java.util.Arrays;
import java.util.Objects;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != User.class)
            return false;
        User other = (User) obj;
        if (this.hashCode() != other.hashCode())
            return false;
        return this.username.equals(other.username)
                && this.email.equals(other.email)
                && Arrays.equals(this.passwordHash, other.passwordHash);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, email);
        result = 31 * result + Arrays.hashCode(passwordHash);
        return result;
    }
}
