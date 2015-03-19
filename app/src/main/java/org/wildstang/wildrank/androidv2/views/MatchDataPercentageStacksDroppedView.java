package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingStacksView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Nathan on 3/18/2015.
 */
public class MatchDataPercentageStacksDroppedView extends MatchDataView implements IMatchDataView {

    public MatchDataPercentageStacksDroppedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if(documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double totalStacks = 0;
        double droppedStacks = 0;
        for(Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if(data == null) {
                return;
            }
            List<Map<String, Object>> stacks = (List<Map<String, Object>>) data.get("stacks");
            for(Map<String, Object> stack : stacks) {
                totalStacks++;
                boolean dropped = (boolean) stack.get(ScoutingStacksView.STACK_DROPPED_KEY);
                if(dropped) {
                    droppedStacks++;
                }
                Log.d("wildstang", stack.toString());
            }
        }
        double percentage = (droppedStacks / totalStacks);
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);
        String string = format.format(percentage);
        setValueText(string);
    }
}