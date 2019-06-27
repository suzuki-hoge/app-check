package check.user;

import check.core.CheckError;
import check.core.Unit;
import core.user.Payment;
import core.user.Status;
import core.user.UserId;
import javaslang.Lazy;
import javaslang.control.Validation;
import lombok.Value;

import static check.core.Unit.UNIT;
import static check.user.UserCheckError.NOT_CREDIT_CARD;
import static check.user.UserCheckError.NOT_USING;
import static core.user.Payment.BANK;
import static core.user.Payment.CREDIT_CARD;
import static core.user.Status.STOPPING;
import static core.user.Status.USING;
import static javaslang.control.Validation.invalid;
import static javaslang.control.Validation.valid;

@Value
public class UserForCheck {
    private final UserId id;
    private final Status status;
    private final Payment payment;

    public static Validation<CheckError, Unit> checkStatus(UserForCheck own) {
        System.out.println("UserForCheck#checkStatus evaluated");
        return own.status == USING ? valid(UNIT) : invalid(NOT_USING);
    }

    public static Validation<CheckError, Unit> checkPayment(UserForCheck own) {
        System.out.println("UserForCheck#checkPayment evaluated");
        return own.payment == CREDIT_CARD ? valid(UNIT) : invalid(NOT_CREDIT_CARD);
    }

    // fixtures

    public static Lazy<UserForCheck> fValid = Lazy.of(
            () -> new UserForCheck(
                    new UserId(1),
                    USING,
                    CREDIT_CARD
            )
    );

    public static Lazy<UserForCheck> fInvalid = Lazy.of(
            () -> new UserForCheck(
                    new UserId(1),
                    STOPPING,
                    BANK
            )
    );
}
