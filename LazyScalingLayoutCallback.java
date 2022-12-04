
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.12.2022, воскресенье
 *
 *
 * Recreate ScalingLazyColumn effect from Compose for Wear OS
 **/
public class LazyScalingLayoutCallback extends WearableLinearLayoutManager.LayoutCallback {

    public static WearableLinearLayoutManager get(@NonNull Context context){
        return new WearableLinearLayoutManager(context, new LazyScalingLayoutCallback());
    }

    @Override
    public void onLayoutFinished(@NonNull View child, @NonNull RecyclerView parent) {
        float size = Math.max(parent.getWidth(), parent.getHeight());
        float childMidpoint = (child.getTop() + child.getBottom()) / 2.f;
        float scaleAlphaFactor = Math.min(Math.max((calculateChord(child, size) * -1f / size + 1f), -1f), 1f);
        child.setPivotY(childMidpoint > Math.max(parent.getWidth(), parent.getHeight()) / 2f ? 0 : child.getHeight());
        child.setScaleX(scaleAlphaFactor);
        child.setScaleY(scaleAlphaFactor);
        child.setAlpha(scaleAlphaFactor);
    }

    private float calculateChord(@NonNull View view, float size) {
        float childMidpoint = (view.getTop() + view.getBottom()) / 2.f;
        float chordLength = (float) (2 * Math.abs((size / 2f) - childMidpoint) * Math.tan(Math.toRadians(Math.abs((size / 2f) - childMidpoint) * 90f / size)));
        float totalWidth = view.getMeasuredWidth() - (view.getPaddingStart() + view.getPaddingEnd());
        chordLength -= totalWidth / Math.PI;
        return chordLength;
    }
}
