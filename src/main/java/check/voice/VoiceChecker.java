package check.voice;

import check.core.CheckError;
import check.core.Unit;
import core.voice.OptionId;
import core.voice.Status;
import core.voice.Type;
import core.voice.VoiceId;
import javaslang.Lazy;
import javaslang.control.Option;
import javaslang.control.Validation;
import lombok.Value;

import static check.core.Unit.UNIT;
import static check.voice.VoiceCheckError.*;
import static core.voice.Status.UN_STOPPING;
import static core.voice.Type.MAIN;
import static core.voice.Type.SHARE;
import static javaslang.control.Validation.invalid;
import static javaslang.control.Validation.valid;

@Value
public class VoiceChecker {
    private final VoiceId id;
    private final Type type;
    private final Status status;
    private final Option<OptionId> oid;

    public static Validation<CheckError, Unit> checkType(VoiceChecker own) {
        return own.type == MAIN ? valid(UNIT) : invalid(NOT_MAIN);
    }

    public static Validation<CheckError, Unit> checkStatus(VoiceChecker own) {
        return own.status == UN_STOPPING ? valid(UNIT) : invalid(STOPPING);
    }

    public static Validation<CheckError, Unit> checkOption(VoiceChecker own) {
        return own.oid.isEmpty() ? valid(UNIT) : invalid(SOME_OPTIONS);
    }

    // fixtures

    public static Lazy<Option<VoiceChecker>> fValid = Lazy.of(
            () -> Option.of(
                    new VoiceChecker(
                            new VoiceId(1),
                            MAIN,
                            UN_STOPPING,
                            Option.none()
                    )
            )
    );

    public static Lazy<Option<VoiceChecker>> fInvalid = Lazy.of(
            () -> Option.of(
                    new VoiceChecker(
                            new VoiceId(1),
                            SHARE,
                            Status.STOPPING,
                            Option.some(new OptionId(1))
                    )
            )
    );

    public static Lazy<Option<VoiceChecker>> fNone = Lazy.of(
            Option::none
    );
}
