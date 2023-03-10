package com.ciglink.authorizationserver.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记的接口方法不会被{@link com.ciglink.authorizationserver.advice.Rest}处理
 *
 * @author WANGKairen
 * @since 2022-12-29 13:59:50
 **/
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreRestBody {
}
