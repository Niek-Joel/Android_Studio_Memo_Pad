package com.example.android_studio_memo_pad;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android_studio_memo_pad.databinding.ActivityMainBinding;

public class MemoActivityView extends AppCompatActivity implements  interfaceContract.interfaceView{
    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();
    private ActivityMainBinding binding;
    private MemoPadPresenter presenter;
    private MemoPadModel model;
    Integer highlightedMemo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Creating and linking MVP members. (Normally I'd do this from a separate class not in the view.)
        presenter = new MemoPadPresenter();
        model = new MemoPadModel(this,null,null,MemoPadModel.DATABASE_VERSION);
        presenter.addView(this);
        presenter.addModel(model);


        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memoText = binding.memoInput.getText().toString();
                presenter.addMemo(new Memo(memoText));
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (highlightedMemo != null) {
                    presenter.deleteMemo(highlightedMemo);
                }
            }
        });
    }
    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.output.getChildLayoutPosition(v);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();
            if (adapter != null) {
                Memo memo = adapter.getItem(position);
                highlightedMemo = memo.getId();
                Toast.makeText(v.getContext(), String.valueOf(highlightedMemo), Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void addMemoToView(String memoText) {
        // Clear input field
        binding.memoInput.setText(R.string.empty_string);
        // Add memo to RecyclerView
        presenter.updateRecyclerView(memoText, true);
    }

    @Override
    public void deleteMemoFromView() {
        presenter.updateRecyclerView(null, false);
    }

    @Override
    public void updateRecyclerView(RecyclerViewAdapter adapter) {
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);
    }
    public MemoPadItemClickHandler getItemClick() { return itemClick; }

    public Integer getHighlightedMemo() {return highlightedMemo;}
}