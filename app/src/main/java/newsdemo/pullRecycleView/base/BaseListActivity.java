package newsdemo.pullRecycleView.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import davidtgnewsproject.newsdemo.com.davidtgnewsproject.R;
import newsdemo.pullRecycleView.BaseListAdapter;
import newsdemo.pullRecycleView.BaseViewHolder;
import newsdemo.pullRecycleView.DividerItemDecoration;
import newsdemo.pullRecycleView.PullRecycler;
import newsdemo.pullRecycleView.layoutmanager.ILayoutManager;
import newsdemo.pullRecycleView.layoutmanager.MyLinearLayoutManager;

public abstract class BaseListActivity<T> extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_base_list);
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);

    }

    @Override
    protected void initData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }


    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }
    protected int getItemType(int position) {
        return 0;
    }
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

}
