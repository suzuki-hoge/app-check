package uc.type_change.app_check

import check.contract.ContractCheckError
import check.contract.ContractChecker
import check.date.DateCheckError
import check.date.DateChecker
import check.user.UserChecker
import check.voice.VoiceCheckError
import check.voice.VoiceChecker
import javaslang.collection.List
import spock.lang.Specification
import spock.lang.Unroll

import static check.core.Unit.UNIT
import static javaslang.control.Validation.invalid
import static javaslang.control.Validation.valid
import static uc.type_change.LazyUtil.na

@Unroll
class CallCenterTypeChangeAppCheckPolicyTest extends Specification {
    def "test #label"() {
        expect:
        CallCenterTypeChangeAppCheckPolicy.check(contract, user, voice, date) == exp

        where:
        label                            | contract                 | user               | voice                 | date                 || exp
        'invalid, n/a, n/a, invalid'     | ContractChecker.fInvalid | na()               | na()                  | DateChecker.fInvalid || invalid(List.of(ContractCheckError.NOT_INDIVIDUAL))
        'valid, valid, none, invalid'    | ContractChecker.fValid   | UserChecker.fValid | VoiceChecker.fNone    | DateChecker.fInvalid || invalid(List.of(DateCheckError.OUTSIDE_OF_OPERATION_TIME))
        'valid, valid, invalid, invalid' | ContractChecker.fValid   | UserChecker.fValid | VoiceChecker.fInvalid | DateChecker.fInvalid || invalid(List.of(VoiceCheckError.NOT_MAIN, VoiceCheckError.STOPPING, VoiceCheckError.SOME_OPTIONS))
        'valid, valid, valid, invalid'   | ContractChecker.fValid   | UserChecker.fValid | VoiceChecker.fValid   | DateChecker.fValid   || valid(UNIT)
    }
}
