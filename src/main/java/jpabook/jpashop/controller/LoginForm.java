package jpabook.jpashop.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//@EqualsAndHashCode
//@ToString
public class LoginForm {
        @NotNull
        private Long loginId;

        @NotEmpty
        private String password;
}
