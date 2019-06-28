package uc.type_change.app_check;

import check.contract.ContractChecker;
import check.core.CheckError;
import check.core.Checker;
import check.core.Unit;
import check.date.DateChecker;
import check.user.UserChecker;
import check.voice.VoiceChecker;
import javaslang.Lazy;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Validation;

import static check.core.ValidationUtil.checkAll;
import static java.util.function.Function.identity;

public class CoreTypeChangeAppCheckPolicy {
    public static Validation<List<CheckError>, Unit> check(
            ContractChecker contract,
            List<Checker<ContractChecker>> contracts,
            Lazy<UserChecker> user,
            List<Checker<UserChecker>> users,
            Lazy<Option<VoiceChecker>> voice,
            List<Checker<VoiceChecker>> voices,
            DateChecker date,
            List<Checker<DateChecker>> dates) {
        return checkAll(contract, contracts).flatMap(
                v1 -> checkAll(user, users).flatMap(
                        v2 -> checkAll(voice, voices).flatMap(
                                v3 -> checkAll(date, dates).map(identity())
                        )
                )
        );
    }
}
