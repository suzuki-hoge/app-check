package uc.type_change

import javaslang.Lazy

class LazyUtil {
    static <T> Lazy<T> na() {
        Lazy.<T> of({ throw new RuntimeException("n/a") })
    }
}
