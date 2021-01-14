package io.smallrye.context.storage;

public class BaseQuarkusThreadLocal<T> extends ThreadLocal<T> {

    private final int index;

    public BaseQuarkusThreadLocal(int index) {
        this.index = index;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof QuarkusThread) {
            return (T) ((QuarkusThread) currentThread).getQuarkusThreadContext()[index];
        } else {
            return super.get();
        }
    }

    @Override
    public void set(T t) {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof QuarkusThread) {
            ((QuarkusThread) currentThread).getQuarkusThreadContext()[index] = t;
        } else {
            super.set(t);
        }
    }

    @Override
    public void remove() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof QuarkusThread) {
            ((QuarkusThread) currentThread).getQuarkusThreadContext()[index] = null;
        } else {
            super.remove();
        }
    }

}
