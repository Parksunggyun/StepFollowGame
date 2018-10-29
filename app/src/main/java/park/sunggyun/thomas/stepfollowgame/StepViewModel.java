package park.sunggyun.thomas.stepfollowgame;

import android.view.View;

public class StepViewModel implements ViewModel {

    @Override
    public void onCrete() {

    }

    @Override
    public void onDestroy() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggleView:
                break;
        }
    }

}
