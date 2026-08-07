package com.vitorraphael.gestor_comercial.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca um endpoint que só pode ser acessado por funcionários com perfil
 * GERENTE. Endpoints sem essa anotação exigem apenas estar autenticado
 * (qualquer perfil).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExigeGerente {
}
