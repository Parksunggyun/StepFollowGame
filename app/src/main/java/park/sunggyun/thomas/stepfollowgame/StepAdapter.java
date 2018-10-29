package park.sunggyun.thomas.stepfollowgame;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import park.sunggyun.thomas.stepfollowgame.databinding.ItemStepBinding;

import java.util.Vector;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Vector<Step> steps = new Vector<>();

    private Context context;
    private int width = 0, height = 0, start = 1, end = 4;
    ItemStepBinding binding;
    private int position;
    private RecyclerView recyclerView;
    private View.OnClickListener listener = view -> {
        int itemPosition = recyclerView.getChildAdapterPosition(view);
        Log.e("itemPosition", itemPosition+"");
        int clicked = steps.get(itemPosition).getValue();
        Log.d("clicked " + clicked, "start " + start);
        if(start == clicked) {
            start++;
            binding.stepTextView.setTextColor(Color.RED);
        }
        if(start == end) {
            Toast.makeText(context, "잘하셨어요.", Toast.LENGTH_SHORT).show();
        }

    };

    Handler handler = new Handler();

    StepAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        for (int i = 0; i < 9; i++) {
            steps.add(new Step(0, 0));
        }
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemStepBinding binding = ItemStepBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        binding.getRoot().setOnClickListener(listener);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int position) {
        binding = stepViewHolder.binding;
        Step step = steps.get(position);
        binding.stepTextView.setText(String.valueOf(step.getValue()));
        if(step.getValue() == 0) binding.stepTextView.setText("");
        else binding.stepTextView.setVisibility(View.VISIBLE);
        if (width != 0 && height != 0) {
            binding.stepLayout.getLayoutParams().width = width;
            binding.stepLayout.getLayoutParams().height = height;
            Log.d("width : " + width, "height : " + height);
        }

            /*if (position == this.position) {
                binding.stepTextView.setText("1");
                binding.stepTextView.setVisibility(View.VISIBLE);
                try {
                    Log.d("steps.size() : ", position+"");
                    Thread.sleep(1500);
                    binding.stepTextView.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        /*binding.stepTextView.setOnClickListener(view -> {
            String val = binding.stepTextView.getText().toString();
            if(!val.equals("")) {
                int value = Integer.parseInt(binding.stepTextView.getText().toString());
                if (value == start) {
                    Log.e("correct", "correct" + binding.stepTextView.getText().toString());
                    start++;
                } else {
                    Log.e("error", "error" + binding.stepTextView.getText().toString());
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    void update(Step step) {
        steps.add(step);
    }

/*    void setup(int... positions) {
        for (int a = 0; a < positions.length; a++) {
            final int b = a;
                steps.remove(positions[b]);
                steps.add(positions[b], new Step(positions[b], b+1));
        }
    }*/

    void setup(int pos, int position) {
        Log.e("asd", "asd");
            steps.remove(position);
            steps.add(position, new Step(position, pos +1));
        Log.e("pos and position", pos + " and " + position);
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        ItemStepBinding binding;

        StepViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    void setLength(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
