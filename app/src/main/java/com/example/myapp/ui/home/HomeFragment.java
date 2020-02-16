package com.example.myapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.example.myapp.ui.shouye.FaxianFragment;
import com.example.myapp.ui.shouye.KuaixunFragment;
import com.example.myapp.ui.shouye.ShouyeFragment;

public class HomeFragment extends Fragment {

    private RadioGroup mRg1;
    private RadioButton ratn,ratn1,ratn2;

    private ShouyeFragment shouyefragment;
    private FaxianFragment faxianfragment;
    private KuaixunFragment kauixunfragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //

        mRg1 = view.findViewById(R.id.rg_1);
        ratn = view.findViewById(R.id.rg_bt_1);
        ratn.setTextSize(30);
        ratn1 = view.findViewById(R.id.rg_bt_2);
        ratn2 = view.findViewById(R.id.rg_bt_3);

        shouyefragment = new ShouyeFragment();
        faxianfragment = new FaxianFragment();
        kauixunfragment = new KuaixunFragment();
        if(!getChildFragmentManager().beginTransaction().isEmpty()){
            getChildFragmentManager().beginTransaction().remove(shouyefragment).remove(faxianfragment).remove(kauixunfragment).commitAllowingStateLoss();
        }


        getChildFragmentManager().beginTransaction().add(R.id.fl_container,kauixunfragment).commitAllowingStateLoss();
        getChildFragmentManager().beginTransaction().add(R.id.fl_container,faxianfragment).commitAllowingStateLoss();
        getChildFragmentManager().beginTransaction().add(R.id.fl_container,shouyefragment).commitAllowingStateLoss();
        getChildFragmentManager().beginTransaction().hide(faxianfragment).hide(kauixunfragment).show(shouyefragment).commitAllowingStateLoss();

        mRg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton = group.findViewById(checkedId);
                if(radiobutton==ratn){
                    ratn.setTextSize(30);
                    ratn1.setTextSize(20);
                    ratn2.setTextSize(20);
                    getChildFragmentManager().beginTransaction().hide(faxianfragment).hide(kauixunfragment).show(shouyefragment).commitAllowingStateLoss();
                }
                else if(radiobutton==ratn1){
                    ratn.setTextSize(20);
                    ratn1.setTextSize(30);
                    ratn2.setTextSize(20);
                    getChildFragmentManager().beginTransaction().hide(shouyefragment).hide(kauixunfragment).show(faxianfragment).commitAllowingStateLoss();

                }
                else if(radiobutton==ratn2){
                    ratn.setTextSize(20);
                    ratn1.setTextSize(20);
                    ratn2.setTextSize(30);
                    getChildFragmentManager().beginTransaction().hide(faxianfragment).hide(shouyefragment).show(kauixunfragment).commitAllowingStateLoss();
                }
                System.out.println(checkedId);
            }
        });
    }
}