package app.creditnotebook.domain.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.creditnotebook.R;

public class CustomerActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextNickname;
    private EditText editTextCPF;
    private Spinner spinnerPaymentCondition;
    private RadioGroup radioGroupGenres;
    private CheckBox checkBoxActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextNickname = findViewById(R.id.editTextNickname);
        editTextCPF = findViewById(R.id.editTextCPF);
        spinnerPaymentCondition = findViewById(R.id.spinnerPaymentCondition);
        radioGroupGenres = findViewById(R.id.radioGroupGenres);
        checkBoxActive = findViewById(R.id.checkBoxActive);

        checkBoxActive.setChecked(true);
        spinnerPaymentConditionPopulate();
    }

    public void save(View view) {

        String validateResult = validate();

        if (!validateResult.isEmpty()) {
            Toast.makeText(this, validateResult, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.saved_successfully, Toast.LENGTH_LONG).show();
            clearForm(view);
        }

    }

    private String validate() {

        if (editTextFullName.getText().toString().trim().isEmpty()) {
            return getString(R.string.fullname_required);
        } else if (editTextCPF.getText().toString().trim().isEmpty()) {
            return getString(R.string.cpf_required);
        } else if (radioGroupGenres.getCheckedRadioButtonId() == -1) {
            return getString(R.string.gender_required);
        }

        return "";
    }

    public void clearForm(View view) {

        editTextFullName.setText(null);
        editTextNickname.setText(null);
        editTextCPF.setText(null);
        spinnerPaymentCondition.setSelection(0);
        radioGroupGenres.clearCheck();
        checkBoxActive.setChecked(true);

        editTextFullName.requestFocus();
    }

    private void spinnerPaymentConditionPopulate() {

        TypedArray paymentConditions = this.getResources().obtainTypedArray(R.array.payment_conditions);
        List<PaymentCondition> paymentConditionList = new ArrayList<>();
        final int DEFAULT_VALUE = 0;
        final int PAYMENT_CONDITION_DAYS_INDEX = 0;
        final int PAYMENT_CONDITION_LABEL_INDEX = 1;

        for (int i = 0; i < paymentConditions.length(); i++) {

            int paymentConditionId = paymentConditions.getResourceId(i, DEFAULT_VALUE);

            TypedArray rawPaymentCondition = this.getResources().obtainTypedArray(paymentConditionId);

            PaymentCondition paymentCondition = new PaymentCondition(
                    rawPaymentCondition.getInt(PAYMENT_CONDITION_DAYS_INDEX, DEFAULT_VALUE),
                    rawPaymentCondition.getString(PAYMENT_CONDITION_LABEL_INDEX)
            );

            paymentConditionList.add(paymentCondition);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                paymentConditionList.stream()
                        .map(PaymentCondition::toString)
                        .toArray(String[]::new)
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentCondition.setAdapter(adapter);
    }
}