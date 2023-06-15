package auth.annotation;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(Permissions.List.class)
public @interface Permissions {
    String AUTODETECTED = "<<autodetected>>";
    String PERMISSION_TO_ACTION_SEPARATOR = ":";

    String[] value();

    String[] params() default {"<<autodetected>>"};

    boolean inclusive();

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        Permissions[] value();
    }
}
