package check.core;

import javaslang.control.Validation;

public interface Checker<T, R extends CheckError> {
    Validation<R, Unit> check(T t);
}
