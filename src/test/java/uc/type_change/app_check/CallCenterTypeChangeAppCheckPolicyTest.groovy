package uc.type_change.app_check

import check.contract.ContractCheckError
import check.contract.ContractForCheck
import check.date.DateCheckError
import check.date.DateForCheck
import check.user.UserForCheck
import check.voice.VoiceCheckError
import check.voice.VoiceForCheck
import javaslang.collection.List
import spock.lang.Specification
import spock.lang.Unroll

import static check.core.Unit.UNIT
import static javaslang.control.Validation.invalid
import static javaslang.control.Validation.valid

@Unroll
class CallCenterTypeChangeAppCheckPolicyTest extends Specification {
    def "test #label"() {
        expect:
        CallCenterTypeChangeAppCheckPolicy.check(contract, user, voice, date) == exp

        where:
        label                             | contract                  | user                  | voice                  | date                  || exp
        'invalid, invalid, none, invalid' | ContractForCheck.fInvalid | UserForCheck.fInvalid | VoiceForCheck.fNone    | DateForCheck.fInvalid || invalid(List.of(ContractCheckError.NOT_INDIVIDUAL))
        'valid, valid, none, invalid'     | ContractForCheck.fValid   | UserForCheck.fValid   | VoiceForCheck.fNone    | DateForCheck.fInvalid || invalid(List.of(DateCheckError.OUTSIDE_OF_OPERATION_TIME))
        'valid, valid, invalid, invalid'  | ContractForCheck.fValid   | UserForCheck.fValid   | VoiceForCheck.fInvalid | DateForCheck.fInvalid || invalid(List.of(VoiceCheckError.NOT_MAIN, VoiceCheckError.STOPPING, VoiceCheckError.SOME_OPTIONS))
        'valid, valid, valid, invalid'    | ContractForCheck.fValid   | UserForCheck.fValid   | VoiceForCheck.fValid   | DateForCheck.fValid   || valid(UNIT)
    }
}
