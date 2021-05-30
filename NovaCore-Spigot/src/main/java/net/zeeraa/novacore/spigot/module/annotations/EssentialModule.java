package net.zeeraa.novacore.spigot.module.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation can be added to a nova module to prevent it from being
 * enabled or disabled by the novacore command
 * <p>
 * This will not prevent other plugins from enabling or disabling the module
 * 
 * @author Zeeraa
 * @since 1.1
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EssentialModule {
}