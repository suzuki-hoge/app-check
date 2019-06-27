package uc.type_change.app_check;

import check.contract.ContractForCheck;
import check.core.CheckError;
import check.core.Checker;
import check.core.Unit;
import check.date.DateForCheck;
import check.user.UserForCheck;
import check.voice.VoiceForCheck;
import javaslang.Lazy;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Validation;

import static check.core.ValidationUtil.checkAll;
import static java.util.function.Function.identity;

public class CoreTypeChangeAppCheckPolicy {
    public static Validation<List<CheckError>, Unit> check(
            ContractForCheck contract,
            List<Checker<ContractForCheck, CheckError>> contracts,
            Lazy<UserForCheck> user,
            List<Checker<UserForCheck, CheckError>> users,
            Lazy<Option<VoiceForCheck>> voice,
            List<Checker<VoiceForCheck, CheckError>> voices,
            DateForCheck date,
            List<Checker<DateForCheck, CheckError>> dates) {
        return checkAll(contract, contracts).flatMap(
                v1 -> checkAll(user, users).flatMap(
                        v2 -> checkAll(voice, voices).flatMap(
                                v3 -> checkAll(date, dates).map(identity())
                        )
                )
        );
    }
}
