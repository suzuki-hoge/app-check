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
public class VoiceForCheck {
    private final VoiceId id;
    private final Type type;
    private final Status status;
    private final Option<OptionId> oid;

    public static Validation<CheckError, Unit> checkType(VoiceForCheck own) {
        System.out.println("VoiceForCheck#checkType evaluated");
        return own.type == MAIN ? valid(UNIT) : invalid(NOT_MAIN);
    }

    public static Validation<CheckError, Unit> checkStatus(VoiceForCheck own) {
        System.out.println("VoiceForCheck#checkStatus evaluated");
        return own.status == UN_STOPPING ? valid(UNIT) : invalid(STOPPING);
    }

    public static Validation<CheckError, Unit> checkOption(VoiceForCheck own) {
        System.out.println("VoiceForCheck#checkOption evaluated");
        return own.oid.isEmpty() ? valid(UNIT) : invalid(SOME_OPTIONS);
    }

    // fixtures

    public static Lazy<Option<VoiceForCheck>> fValid = Lazy.of(
            () -> Option.of(
                    new VoiceForCheck(
                            new VoiceId(1),
                            MAIN,
                            UN_STOPPING,
                            Option.none()
                    )
            )
    );

    public static Lazy<Option<VoiceForCheck>> fInvalid = Lazy.of(
            () -> Option.of(
                    new VoiceForCheck(
                            new VoiceId(1),
                            SHARE,
                            Status.STOPPING,
                            Option.some(new OptionId(1))
                    )
            )
    );

    public static Lazy<Option<VoiceForCheck>> fNone = Lazy.of(
            Option::none
    );
}
