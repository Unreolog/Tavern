package telegram.infrastructure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JacksonConfigurationSources {
	String[] value();
}
