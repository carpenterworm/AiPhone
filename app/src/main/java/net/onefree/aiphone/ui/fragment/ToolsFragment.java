package net.onefree.aiphone.ui.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import net.onefree.aiphone.R;
import net.onefree.aiphone.controler.CameraManager;
import net.onefree.aiphone.ui.activity.FeedbackActivity;
import net.onefree.aiphone.ui.activity.FlashActivity;
import net.onefree.aiphone.ui.activity.PhoneInfoActivity;
import net.onefree.aiphone.ui.activity.PhoneNumberWhereActivity;
import net.onefree.aiphone.ui.activity.QrCodeCaptureActivity;
import net.onefree.aiphone.adapter.ToolAdapter;
import net.onefree.aiphone.bean.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoah on 14-10-18.
 */
public class ToolsFragment extends BaseFragment {

    private GridView gridView;
    private ToolAdapter toolAdapter;
    private List<Tool> tools = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        initData();
        gridView = (GridView) view.findViewById(R.id.gridview);
        toolAdapter = new ToolAdapter(this.getActivity(), tools);
        gridView.setAdapter(toolAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tool tool = tools.get(position);
                if (null == tool.getActivity()) {
                    return;
                }
                if (tool.getCode() == Tool.FLASH) {
                    switchFlash();
                    return;
                }
                startActivity(new Intent(getActivity(), tool.getActivity()));
            }


        });
    }

    private void initData() {

        tool.setName("手电筒");
        tool.setIcon(R.drawable.tool_icon_flash_light);
        tool.setActivity(FlashActivity.class);
        tool.setCode(Tool.FLASH);

        Tool qrCode = new Tool();
        qrCode.setIcon(R.drawable.tool_icon_qr_code);
        qrCode.setActivity(QrCodeCaptureActivity.class);
        qrCode.setName("二维码");

        Tool phoneNumberWhere = new Tool();
        phoneNumberWhere.setIcon(R.drawable.tool_icon_define_location);
        phoneNumberWhere.setActivity(PhoneNumberWhereActivity.class);
        phoneNumberWhere.setName("归属地");

        Tool phoneInfo = new Tool();
        phoneInfo.setIcon(R.drawable.android);
        phoneInfo.setName("手机信息");
        phoneInfo.setActivity(PhoneInfoActivity.class);

        Tool too4 = new Tool();
        too4.setIcon(R.drawable.tool_icon_head_idea);
        too4.setName("我想要");
        too4.setActivity(FeedbackActivity.class);

        tools.add(tool);
        tools.add(qrCode);
        tools.add(phoneNumberWhere);
        tools.add(phoneInfo);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_acitivity, menu);
    }

    private boolean isopent = false;
    private Camera camera;
    private Tool tool = new Tool();

    private void switchFlash() {
        if (!isopent) {
            CameraManager.openFlash(getActivity());
            tool.setName("手电筒-开");
        } else {
            CameraManager.closeFlash();
            CameraManager.release();
            tool.setName("手电筒");
        }
        isopent = !isopent;
        toolAdapter.notifyDataSetChanged();
    }

    public void onDestroy() {
        if (isopent) {
            switchFlash();
        }
        super.onDestroy();
    }
}
