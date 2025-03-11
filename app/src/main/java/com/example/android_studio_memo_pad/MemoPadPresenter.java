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
        model = new MemoPadModel(view,null,null, MemoPadModel.DATABASE_VERSION);
        this.model = model;
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
            view.deleteMemoFromView();
        }
    }

    @Override
    public void updateRecyclerView(String memoText, boolean isAdd) {
        // TODO: This should just bridge the gap between MainView and RecyclerView. Mainview needs object of RecyclerView to display
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(model.getAllMemosAsList());
        if (isAdd) {
            model.addNewMemo(new Memo(memoText));
        }
        else {
            model.deleteMemo(view.getHighlightedMemo());
        }
        // Update view so it displays current values in model
        view.updateRecyclerView(adapter);
    }

    @Override
    public void addMemo(Memo memo) {
        model.addNewMemo(memo);
    }

    @Override
    public void deleteMemo(int id) {
        model.deleteMemo(id);
    }
}
