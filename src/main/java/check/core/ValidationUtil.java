package check.core;

import javaslang.Lazy;
import javaslang.Value;
import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;
import javaslang.control.Validation;

import static check.core.Unit.UNIT;
import static java.util.function.Function.identity;
import static javaslang.control.Validation.sequence;
import static javaslang.control.Validation.valid;

public class ValidationUtil {

    // for T

    public static <T> Validation<List<CheckError>, Unit> checkAll(T t, List<Checker<T>> checkers) {
        List<Validation<CheckError, Unit>> mappeds = checkers.map(checker -> checker.check(t));

        List<Validation<List<CheckError>, Unit>> wrappeds = mappeds.map(mapped -> mapped.bimap(List::of, identity()));

        Validation<List<CheckError>, Seq<Unit>> sequenced = sequence(wrappeds);

        return sequenced.fold(Validation::invalid, x -> valid(UNIT));
    }

    // for Option<T> or Lazy<T>

    public static <T> Validation<List<CheckError>, Unit> checkAll(Value<T> valueT, List<Checker<T>> checkers) {
        List<Validation<CheckError, Unit>> mappeds = checkers.flatMap(checker -> valueT.map(checker::check));

        List<Validation<List<CheckError>, Unit>> wrappeds = mappeds.map(mapped -> mapped.bimap(List::of, identity()));

        Validation<List<CheckError>, Seq<Unit>> sequenced = sequence(wrappeds);

        return sequenced.fold(Validation::invalid, x -> valid(UNIT));
    }

    // for Lazy<Option<T>>

    public static <T> Validation<List<CheckError>, Unit> checkAll(Lazy<Option<T>> valueValueT, List<Checker<T>> checkers) {
        List<Value<Validation<CheckError, Unit>>> mappeds = checkers.flatMap(checker -> valueValueT.map(valueT -> valueT.map(checker::check)));

        List<Validation<List<CheckError>, Unit>> wrappeds = mappeds.flatMap(mapped -> mapped.map(v -> v.bimap(List::of, identity())));

        Validation<List<CheckError>, Seq<Unit>> sequenced = sequence(wrappeds);

        return sequenced.fold(Validation::invalid, x -> valid(UNIT));
    }
}
