package com.example.firstproject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//아래 어노테이션 궁금하면 구글링!
@Target({ ElementType.TYPE , ElementType.METHOD }) //어노테이션 적용 대상 지정
@Retention(RetentionPolicy.RUNTIME) //어노테이션 유지 기간
public @interface RunningTime {

}
