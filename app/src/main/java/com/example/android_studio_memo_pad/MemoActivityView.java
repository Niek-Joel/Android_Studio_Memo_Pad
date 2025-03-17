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
    Integer highlightedMemoId = null;

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

        // If list isn't empty then retrieve it
        if (!presenter.getAllMemosAsList().isEmpty()) {
            onDeleteMemo();
        }


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
                if (highlightedMemoId != null) {
                    presenter.deleteMemo(highlightedMemoId);
                }
                highlightedMemoId = null;
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
                highlightedMemoId = memo.getId();
                Toast.makeText(v.getContext(), String.valueOf(highlightedMemoId), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDeleteMemo() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(presenter.getAllMemosAsList(), this);
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);
    }

    @Override
    public void onAddMemo() {
        // Clear input field
        binding.memoInput.setText(R.string.empty_string);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(presenter.getAllMemosAsList(), this);
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);
    }
    public MemoPadItemClickHandler getItemClick() { return itemClick; }
}