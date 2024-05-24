package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.login.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView tv_id = view.findViewById(R.id.tv_id);
        TextView tv_pass = view.findViewById(R.id.tv_pass);
        TextView tv_name = view.findViewById(R.id.tv_name);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String userID = intent.getStringExtra("userID");
            String userPass = intent.getStringExtra("userPass");
            String userName = intent.getStringExtra("userName");

            tv_id.setText(userID);
            tv_pass.setText(userPass);
            if (userName != null) {
                String greeting = "안녕하세요 " + userName + "님 환영합니다.";
                tv_name.setText(greeting);
            }
        }
        Button logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
