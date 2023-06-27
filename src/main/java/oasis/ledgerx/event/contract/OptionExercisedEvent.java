package oasis.ledgerx.event.contract;

import oasis.ledgerx.contract.option.Option;
import oasis.ledgerx.event.LedgerEvent;

public final class OptionExercisedEvent extends LedgerEvent {
    public OptionExercisedEvent(
            Option option,
            OptionExerciseCause cause
    ) {
        this.option = option;
        this.cause = cause;
    }

    private final Option option;
    private final OptionExerciseCause cause;

    public Option getOption() {
        return option;
    }

    public OptionExerciseCause getCause() {
        return cause;
    }
}
