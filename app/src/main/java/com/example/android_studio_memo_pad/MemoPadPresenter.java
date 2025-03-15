package com.example.android_studio_memo_pad;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MemoPadPresenter implements PropertyChangeListener, interfaceContract.interfacePresenter{
    private MemoPadModel model;
    private MemoActivityView view;
    public static final String ELEMENT_ADD = "add";
    public static final String ELEMENT_DELETE = "delete";

    public void addModel(MemoPadModel model) {
        model.addPropertyChangeListener(this);
//        model = new MemoPadModel(view,null,null, MemoPadModel.DATABASE_VERSION);
        this.model = model;
        model.setPresenter(this);
    }

    public void addView(MemoActivityView view) {
        this.view = view;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ELEMENT_ADD)) {
            String memoText = evt.getNewValue().toString();
            // Update view so it displays current values in model
            view.addMemoToView(memoText);

        } else if (evt.getPropertyName().equals(ELEMENT_DELETE)) {
            int memoId = Integer.parseInt(evt.getOldValue().toString());
            view.deleteMemoFromView(memoId);
        }
    }

    @Override
    public void updateRecyclerView(String memoText, Integer id) {
        // Connect RecyclerViewAdapter to MainView
        if (id != null) { // Then delete button clicked
            model.deleteMemo(id);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(model.getAllMemosAsList(), view);
        // Update view so it displays current values in model
        view.updateRecyclerView(adapter);
    }

    // View to Presenter
    @Override
    public void addMemo(Memo memo) {
        model.addNewMemo(memo);
    }

    // View to Presenter
    @Override
    public void deleteMemo(int id) {
        model.deleteMemo(id);
    }
}
