package fr.pcmaintenance.healthy.Listener;

import android.view.View;
import android.widget.RelativeLayout;

public class OnSmileyClickListener implements View.OnClickListener {

    private RelativeLayout mlayout;

    public OnSmileyClickListener(RelativeLayout layout) {
        mlayout = layout;
    }

    @Override
    public void onClick(View view) {
        mlayout.setVisibility(View.INVISIBLE);
    }
}
