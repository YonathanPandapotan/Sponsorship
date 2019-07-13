package com.example.yonariva.sponsorship;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class fragmentMainMenu extends Fragment {

    private tabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton mainFloat;
    private LinearLayout acaraFloat, sponsorFloat;
    private Boolean isFABOpen=false;

    public View myRoot;

    public fragmentMainMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout);
        mainFloat = (FloatingActionButton) getView().findViewById(R.id.mainFloat);
        acaraFloat = (LinearLayout) getView().findViewById(R.id.acaraFloat);
        sponsorFloat = (LinearLayout) getView().findViewById(R.id.sponsorFloat);

        acaraFloat.animate().translationY(+getResources().getDimension(R.dimen.standard_105));
        sponsorFloat.animate().translationY(+getResources().getDimension(R.dimen.standard_55));
        sponsorFloat.setVisibility(View.GONE);
        acaraFloat.setVisibility(View.GONE);

        adapter = new tabAdapter(getChildFragmentManager());
        adapter.addFragment(new fragmentFeed(), "Feed");
        adapter.addFragment(new fragmentFeed(), "Post");
        adapter.addFragment(new fragmentYours(), "Yours");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        try {
            if(getArguments().getInt("tabKey") == 3) {
                tabLayout.getTabAt(2).select();
            }
        }catch (NullPointerException e){

        }

        mainFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }

            }
        });

        acaraFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityBuatAcara.class));
            }
        });

        sponsorFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "sponsor pressed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showFABMenu(){
        isFABOpen=true;
        sponsorFloat.setVisibility(View.VISIBLE);
        acaraFloat.setVisibility(View.VISIBLE);
        acaraFloat.animate().translationY(0);
        sponsorFloat.animate().translationY(0);
        mainFloat.animate().rotation(45);
    }

    public void closeFABMenu(){
        isFABOpen=false;
        sponsorFloat.animate().translationY(+getResources().getDimension(R.dimen.standard_55));
        acaraFloat.animate().translationY(+getResources().getDimension(R.dimen.standard_105));
        sponsorFloat.setVisibility(View.GONE);
        acaraFloat.setVisibility(View.GONE);
        mainFloat.animate().rotation(0);
    }


}
