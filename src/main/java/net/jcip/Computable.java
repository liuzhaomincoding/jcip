package net.jcip;

/**
 * User: 智深
 * Date-Time: 13-4-18 - 下午9:37
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
