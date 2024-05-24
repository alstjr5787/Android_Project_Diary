package com.example.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SettingsFragment extends Fragment {
    Button button;
    DatePicker datePicker;
    EditText editText;
    String filename;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);
        datePicker = view.findViewById(R.id.datepicker);
        editText = view.findViewById(R.id.edit_text);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        filename = Integer.toString(year) + "_" + Integer.toString(month) + "_" + Integer.toString(day);

        String str = readDiary(filename);
        editText.setText(str);
        button.setEnabled(true);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                filename = Integer.toString(i) + "_" + Integer.toString(i1) + "_" + Integer.toString(i2);
                String str = readDiary(filename);
                editText.setText(str);
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = requireActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                    String str = editText.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(requireActivity(),"업로드 완료", Toast.LENGTH_SHORT).show();
                    button.setText("수정하기");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private String readDiary(String filename) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = requireActivity().openFileInput(filename);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            button.setText("수정하기");
        } catch (IOException e) {
            editText.setHint("오늘의 한줄을 남겨주세요.");
            button.setText("저장하기");
        }
        return diaryStr;
    }
}
