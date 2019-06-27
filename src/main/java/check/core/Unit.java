package check.core;

import javaslang.Function2;
import javaslang.Function3;

public class Unit {
    public static Unit UNIT = new Unit();

    public static Function2<Unit, Unit, Unit> id2 = (x1, x2) -> UNIT;

    public static Function3<Unit, Unit, Unit, Unit> id3 = (x1, x2, x3) -> UNIT;
}
