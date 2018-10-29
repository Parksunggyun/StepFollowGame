package park.sunggyun.thomas.stepfollowgame;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import park.sunggyun.thomas.stepfollowgame.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    StepAdapter adapter;

    private static final int SECOND = 1000;

    private static final int BLOCK_SIZE = 3;

    private int width = 0, height = 0;
    boolean isStop;

    int count, min, sec;
    String sMin, sSec;

    boolean isFinished = false;

    Handler handler = new Handler();

    Timer timer = new Timer();

    CountTask countTask = new CountTask();

    int[] selectedPosition = new int[3];

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        update();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Log.e("onCreate", "onCreate()");
        binding.stepGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = (binding.stepGridView.getWidth() / BLOCK_SIZE) - 2;
                height = (binding.stepGridView.getHeight() / BLOCK_SIZE) - 2;
                Log.e("width : " + width, "height : " + height);
                adapter.setLength(width, height);
                adapter.notifyDataSetChanged();
                //리스너 해제
                binding.stepGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        init();

        binding.toggleView.setOnClickListener(view -> {
            if (isStop) {
                isFinished = true;
                timer.purge();
                timer.cancel();
                binding.toggleView.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            } else {
                isFinished = false;
                countTask = new CountTask();
                timer = new Timer();
                timer.schedule(countTask, 0);
                binding.toggleView.setImageResource(R.drawable.ic_pause_black_24dp);
            }
            isStop = !isStop;
        });
    }

    @Override
    protected void onDestroy() {
        isFinished = true;
        timer.cancel();
        super.onDestroy();
    }

    private void event() {
        binding.stepGridView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    private void init() {
        binding.stepGridView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new StepAdapter(this, binding.stepGridView);
        binding.stepGridView.setAdapter(adapter);
    }

    private void update() {
        try {
            for (int i = 0; i < 3; i++) {
                selectedPosition[i] = (int) (Math.random() * 9);
                for (int j = 0; j < i; j++) {
                    if (selectedPosition[i] == selectedPosition[j]) {
                        i--;
                        break;
                    }
                }

                Log.d("selectedPotiion", selectedPosition[i] + "");
                adapter.setup(i, selectedPosition[i]);
                adapter.notifyDataSetChanged();
                Thread.sleep(1000);
            }
        } catch (Exception e) {

        }
        timer.schedule(countTask, 0);
    }

    class CountTask extends TimerTask {
        @Override
        public void run() {

            while (!isFinished) {
                try {
                    Thread.sleep(SECOND);
                    count++;
                    min = count / 60;
                    sec = count % 60;
                    if (min < 10) sMin = "0" + min;
                    else sMin = String.valueOf(min);
                    if (sec < 10) sSec = "0" + sec;
                    else sSec = String.valueOf(sec);
                    runOnUiThread(() -> {
                        String time = sMin + ":" + sSec;
                        Log.e("time", time);
                        binding.countTextView.setText(time);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
