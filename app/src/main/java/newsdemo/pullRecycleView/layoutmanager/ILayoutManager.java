package newsdemo.pullRecycleView.layoutmanager;

import android.support.v7.widget.RecyclerView;

import newsdemo.pullRecycleView.BaseListAdapter;
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}
