package net.jcip.annotations;
import java.lang.annotation.*;

/**
 * 应用这个注解的类都是线程安全的。也就是说没有操作序列（公共属性的读写，公共方法的调用）会把对象变成无效状态，
 * 即使这些操作被运行时交互执行，并且不需要调用着额外的同步或协调。
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadSafe {
}
