package io.devfactory.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@NoArgsConstructor
@Getter
public class User {

    private long seq;
    private String name;
    private String passwd;
    private String email;
    private int loginCount;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;

    @Builder
    public User(long seq, String name, String passwd, String email, int loginCount, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        this.seq = seq;
        this.name = name;
        this.passwd = passwd;
        this.email = email;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("seq=" + seq)
                .add("name='" + name + "'")
                .add("passwd='" + passwd + "'")
                .add("email='" + email + "'")
                .add("loginCount=" + loginCount)
                .add("lastLoginAt=" + lastLoginAt)
                .add("createAt=" + createAt)
                .toString();
    }
}
