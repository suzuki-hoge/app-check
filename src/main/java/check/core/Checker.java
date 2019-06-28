package check.core;

import javaslang.control.Validation;

public interface Checker<T> {
    Validation<CheckError, Unit> check(T t);
}
