package check.contract;

import check.core.CheckError;
import check.core.Unit;
import core.contract.ContractId;
import core.contract.OptionId;
import core.contract.Type;
import javaslang.control.Option;
import javaslang.control.Validation;
import lombok.Value;

import static check.contract.ContractCheckError.NOT_INDIVIDUAL;
import static check.contract.ContractCheckError.SOME_OPTIONS;
import static check.core.Unit.UNIT;
import static core.contract.Type.CORPORATION;
import static core.contract.Type.INDIVIDUAL;
import static javaslang.control.Validation.invalid;
import static javaslang.control.Validation.valid;

@Value
public class ContractForCheck {
    private final ContractId id;
    private final Type type;
    private final Option<OptionId> oid;

    public static Validation<CheckError, Unit> checkType(ContractForCheck own) {
        return own.type == INDIVIDUAL ? valid(UNIT) : invalid(NOT_INDIVIDUAL);
    }

    public static Validation<CheckError, Unit> checkOption(ContractForCheck own) {
        return own.oid.isEmpty() ? valid(UNIT) : invalid(SOME_OPTIONS);
    }

    // fixtures

    public static ContractForCheck fValid = new ContractForCheck(
            new ContractId(1),
            INDIVIDUAL,
            Option.none()
    );

    public static ContractForCheck fInvalid = new ContractForCheck(
            new ContractId(1),
            CORPORATION,
            Option.some(new OptionId(1))
    );
}
