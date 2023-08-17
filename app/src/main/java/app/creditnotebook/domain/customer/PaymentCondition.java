package app.creditnotebook.domain.customer;

import androidx.annotation.NonNull;

public class PaymentCondition {

    private int days;
    private String label;

    public PaymentCondition(int days, String label) {
        this.days = days;
        this.label = label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}
