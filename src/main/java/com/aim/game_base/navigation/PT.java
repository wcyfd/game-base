package com.aim.game_base.navigation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PT {
	public short value();
}
