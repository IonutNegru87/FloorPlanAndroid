package CustomControls;

import android.content.Context;


import com.adbelsham.HousePlans.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FaqDataProvideList {
    public static HashMap<String, List<String>> getInfo(Context ctx) {
        HashMap<String, List<String>> faqList = new HashMap<String, List<String>>();

        List<String> faqAns1List = new ArrayList<String>();
        faqAns1List.add(ctx.getResources().getString(R.string.faq_ans1));

        List<String> faqAns2List = new ArrayList<String>();
        faqAns2List.add(ctx.getResources().getString(R.string.faq_ans2));
        List<String> faqAns3List = new ArrayList<String>();
        faqAns3List.add(ctx.getResources().getString(R.string.faq_ans3));
        List<String> faqAns4List = new ArrayList<String>();
        faqAns4List.add(ctx.getResources().getString(R.string.faq_ans4));
        List<String> faqAns5List = new ArrayList<String>();
        faqAns5List.add(ctx.getResources().getString(R.string.faq_ans5));
        List<String> faqAns6List = new ArrayList<String>();
        faqAns6List.add(ctx.getResources().getString(R.string.faq_ans6));
        List<String> faqAns7List = new ArrayList<String>();
        faqAns7List.add(ctx.getResources().getString(R.string.faq_ans7));


        faqList.put(ctx.getResources().getString(R.string.faq_ques1), faqAns1List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques2), faqAns2List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques3), faqAns3List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques4), faqAns4List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques5), faqAns5List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques6), faqAns6List);
        faqList.put(ctx.getResources().getString(R.string.faq_ques7), faqAns7List);
        return faqList;
    }
}
