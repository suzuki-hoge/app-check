package check.date;

import check.core.CheckError;
import check.core.Unit;
import javaslang.control.Validation;
import lombok.Value;

import java.time.LocalDateTime;

import static check.core.Unit.UNIT;
import static check.date.DateCheckError.OUTSIDE_OF_OPERATION_TIME;
import static javaslang.control.Validation.invalid;
import static javaslang.control.Validation.valid;

@Value
public class DateForCheck {
    private final LocalDateTime now;

    public static Validation<CheckError, Unit> checkTime(DateForCheck own) {
        return 9 <= own.now.getHour() && own.now.getHour() <= 21 ? valid(UNIT) : invalid(OUTSIDE_OF_OPERATION_TIME);
    }

    // fixtures

    public static DateForCheck fValid = new DateForCheck(
            LocalDateTime.of(2019, 6, 28, 15, 0, 0)
    );

    public static DateForCheck fInvalid = new DateForCheck(
        LocalDateTime.of(2019, 6, 28, 23, 0, 0)
    );
}
