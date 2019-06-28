package uc.type_change.app_check

import check.contract.ContractCheckError
import check.contract.ContractChecker
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
class WebTypeChangeAppCheckPolicyTest extends Specification {
    def "test #label"() {
        expect:
        WebTypeChangeAppCheckPolicy.check(contract, user, voice, date) == exp

        where:
        label                            | contract                 | user                 | voice                 | date                 || exp
        'invalid, invalid, n/a, invalid' | ContractChecker.fInvalid | UserChecker.fInvalid | na()                  | DateChecker.fInvalid || invalid(List.of(ContractCheckError.NOT_INDIVIDUAL, ContractCheckError.NOT_INDIVIDUAL.SOME_OPTIONS))
        'valid, valid, none, invalid'    | ContractChecker.fValid   | UserChecker.fValid   | VoiceChecker.fNone    | DateChecker.fInvalid || valid(UNIT)
        'valid, valid, invalid, invalid' | ContractChecker.fValid   | UserChecker.fValid   | VoiceChecker.fInvalid | DateChecker.fInvalid || invalid(List.of(VoiceCheckError.NOT_MAIN, VoiceCheckError.STOPPING))
        'valid, valid, valid, invalid'   | ContractChecker.fValid   | UserChecker.fValid   | VoiceChecker.fValid   | DateChecker.fInvalid || valid(UNIT)
    }
}
