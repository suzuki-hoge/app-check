package uc.type_change.app_check;

import check.contract.ContractForCheck;
import check.core.CheckError;
import check.core.Unit;
import check.date.DateForCheck;
import check.user.UserForCheck;
import check.voice.VoiceForCheck;
import javaslang.Lazy;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Validation;

public class CallCenterTypeChangeAppCheckPolicy {
    public static Validation<List<CheckError>, Unit> check(
            ContractForCheck contract,
            Lazy<UserForCheck> user,
            Lazy<Option<VoiceForCheck>> voice,
            DateForCheck date) {
        return CoreTypeChangeAppCheckPolicy.check(
                contract,
                List.of(
                        ContractForCheck::checkType
                ),
                user,
                List.of(
                        UserForCheck::checkStatus
                ),
                voice,
                List.of(
                        VoiceForCheck::checkType,
                        VoiceForCheck::checkStatus,
                        VoiceForCheck::checkOption
                ),
                date,
                List.of(
                        DateForCheck::checkTime
                )
        );
    }
}
