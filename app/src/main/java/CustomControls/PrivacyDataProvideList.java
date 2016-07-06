package CustomControls;

import android.content.Context;

import com.adbelsham.HousePlans.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrivacyDataProvideList {
    public static HashMap<String, List<String>> getInfo(Context ctx) {
        HashMap<String, List<String>> privacyList = new HashMap<String, List<String>>();

        List<String> privacyAns1List = new ArrayList<String>();
        privacyAns1List.add(ctx.getResources().getString(R.string.privacy_ans1));

        List<String> privacyAns2List = new ArrayList<String>();
        privacyAns2List.add(ctx.getResources().getString(R.string.privacy_ans2));
        List<String> privacyAns3List = new ArrayList<String>();
        privacyAns3List.add(ctx.getResources().getString(R.string.privacy_ans3));
        List<String> privacyAns4List = new ArrayList<String>();
        privacyAns4List.add(ctx.getResources().getString(R.string.privacy_ans4));
        List<String> privacyAns5List = new ArrayList<String>();
        privacyAns5List.add(ctx.getResources().getString(R.string.privacy_ans5));
        List<String> privacyAns6List = new ArrayList<String>();
        privacyAns6List.add(ctx.getResources().getString(R.string.privacy_ans6));

        List<String> privacyAns7List = new ArrayList<String>();
        privacyAns7List.add(ctx.getResources().getString(R.string.privacy_ans7));

        List<String> privacyAns8List = new ArrayList<String>();
        privacyAns8List.add(ctx.getResources().getString(R.string.privacy_ans8));


        privacyList.put(ctx.getResources().getString(R.string.privacy_ques1), privacyAns1List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques2), privacyAns2List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques3), privacyAns3List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques4), privacyAns4List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques5), privacyAns5List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques6), privacyAns6List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques7), privacyAns7List);
        privacyList.put(ctx.getResources().getString(R.string.privacy_ques8), privacyAns8List);
        return privacyList;
    }
}
