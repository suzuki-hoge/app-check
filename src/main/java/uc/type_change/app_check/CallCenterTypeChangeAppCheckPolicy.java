package uc.type_change.app_check;

import check.contract.ContractChecker;
import check.core.CheckError;
import check.core.Unit;
import check.date.DateChecker;
import check.user.UserChecker;
import check.voice.VoiceChecker;
import javaslang.Lazy;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Validation;

public class CallCenterTypeChangeAppCheckPolicy {
    public static Validation<List<CheckError>, Unit> check(
            ContractChecker contract,
            Lazy<UserChecker> user,
            Lazy<Option<VoiceChecker>> voice,
            DateChecker date) {
        return CoreTypeChangeAppCheckPolicy.check(
                contract,
                List.of(
                        ContractChecker::checkType
                ),
                user,
                List.of(
                        UserChecker::checkStatus
                ),
                voice,
                List.of(
                        VoiceChecker::checkType,
                        VoiceChecker::checkStatus,
                        VoiceChecker::checkOption
                ),
                date,
                List.of(
                        DateChecker::checkTime
                )
        );
    }
}
